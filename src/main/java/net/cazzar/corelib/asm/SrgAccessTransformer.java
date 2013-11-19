/*
 * Copyright (C) 2013 cazzar
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see [http://www.gnu.org/licenses/].
 */

package net.cazzar.corelib.asm;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.io.LineProcessor;
import com.google.common.io.Resources;
import net.cazzar.corelib.lib.LogHelper;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import static org.objectweb.asm.Opcodes.*;

/**
 * @Author: Cayde
 */
public class SrgAccessTransformer implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (bytes == null) return bytes;
        if (!modifiers.containsKey(transformedName)) return bytes;

        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);

        Collection<Modifier> mods = modifiers.get(transformedName);
        for (Modifier m : mods) {
            if (m.modifyClassVisibility) {
                classNode.access = getFixedAccess(classNode.access, m);
                LogHelper.coreLog.fine("Class: %s %s -> %s", name, toBinary(m.oldAccess), toBinary(m.newAccess));
                continue;
            }

            if (m.desc.isEmpty()) {
                //Variable
                for (FieldNode n : (List<FieldNode>) classNode.fields) {
                    LogHelper.coreLog.fine("Field: %s.%s", name, n.name);
                    if (n.name.equals(m.name) || n.name.equals(McpMappings.instance().getField(transformedName + "." + m.name).getName()) || m.name.equals("*")) {
                        n.access = getFixedAccess(n.access, m);

                        if (!m.name.equals("*")) break;
                    }
                }
            } else {
                for (MethodNode n : (List<MethodNode>) classNode.methods) {
                    if ((n.name.equals(m.name) && n.desc.equals(m.desc)) ||
                            (n.name.equals(McpMappings.instance().getMethod(m.name).getName()) && n.desc.equals(McpMappings.instance().getMethod(m.name).getDesc())) ||
                            m.name.equals("*")) {
                        n.access = getFixedAccess(n.access, m);

                        if (!m.name.equals("*")) break;
                    }
                }
            }
        }

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(writer);
        return writer.toByteArray();
    }

    private String toBinary(int num) {
        return String.format("%16s", Integer.toBinaryString(num)).replace(' ', '0');
    }

    private class Modifier {
        public String name = "";
        public String desc = "";
        public int oldAccess = 0;
        public int newAccess = 0;
        public int targetAccess = 0;
        public boolean changeFinal = false;
        public boolean markFinal = false;
        protected boolean modifyClassVisibility;

        private void setTargetAccess(String name) {
            if (name.startsWith("public")) targetAccess = ACC_PUBLIC;
            else if (name.startsWith("private")) targetAccess = ACC_PRIVATE;
            else if (name.startsWith("protected")) targetAccess = ACC_PROTECTED;

            if (name.endsWith("-f")) {
                changeFinal = true;
                markFinal = false;
            } else if (name.endsWith("+f")) {
                changeFinal = true;
                markFinal = true;
            }
        }
    }

    private Multimap<String, Modifier> modifiers = ArrayListMultimap.create();

    public SrgAccessTransformer(String rulesFile) throws IOException {
        File file = new File(rulesFile);
        URL rulesResource;
        if (file.exists()) {
            rulesResource = file.toURI().toURL();
        } else {
            rulesResource = Resources.getResource(rulesFile);
        }
        Resources.readLines(rulesResource, Charsets.UTF_8, new LineProcessor<Void>() {

            /**
             * This method will be called once for each line.
             *
             * @param input the line read from the input, without delimiter
             *
             * @return true to continue processing, false to stop
             */
            @Override
            public boolean processLine(String input) throws IOException {
                String line = Iterables.getFirst(Splitter.on('#').limit(2).trimResults().split(input), "");
                if (line.length() == 0) return true;

                List<String> parts = Lists.newArrayList(Splitter.on(" ").trimResults().split(line));
                if (parts.size() > 2)
                    throw new RuntimeException(String.format("Invalid Srg AccessTransformer line \"%s\"", input));

                Modifier m = new Modifier();

                m.setTargetAccess(parts.get(0));

                List<String> desc = Lists.newArrayList(Splitter.on(".").trimResults().split(parts.get(1)));

                if (desc.size() == 1) m.modifyClassVisibility = true;
                else {
                    String nameref = desc.get(1);
                    int pareIdx = nameref.indexOf('(');
                    if (pareIdx > 0) {
                        m.desc = nameref.substring(pareIdx);
                        m.name = nameref.substring(0, pareIdx);
                    } else
                        m.name = nameref;
                }
                modifiers.put(desc.get(0).replace('/', '.'), m);
                return true;
            }

            /**
             * Return the result of processing all the lines.
             */
            @Override
            public Void getResult() {
                return null;
            }
        });

        LogHelper.coreLog.fine("Parsed %s", rulesFile);
    }

    private int getFixedAccess(int access, Modifier target) {
        target.oldAccess = access;
        int t = target.targetAccess;
        int ret = (access & ~7);

        switch (access & 7) {
            case ACC_PRIVATE:
                ret |= t;
                break;
            case 0: // default
                ret |= (t != ACC_PRIVATE ? t : 0 /* default */);
                break;
            case ACC_PROTECTED:
                ret |= (t != ACC_PRIVATE && t != 0 /* default */ ? t : ACC_PROTECTED);
                break;
            case ACC_PUBLIC:
                ret |= (t != ACC_PRIVATE && t != 0 /* default */ && t != ACC_PROTECTED ? t : ACC_PUBLIC);
                break;
            default:
                throw new RuntimeException("The fuck?");
        }

        // Clear the "final" marker on fields only if specified in control field
        if (target.changeFinal && target.desc.equals("")) {
            if (target.markFinal) {
                ret |= ACC_FINAL;
            } else {
                ret &= ~ACC_FINAL;
            }
        }
        target.newAccess = ret;
        return ret;
    }
}
