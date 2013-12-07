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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.cazzar.corelib.CoreMod;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TryCatchBlockNode;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class MethodTransformer extends BasicTransformer {
    private static final Map<String, String> deobfMappings = Maps.newHashMap();
    private static final Map<String, String> srgMappings = Maps.newHashMap();

    protected static void addMapping(String deobfName, String srgName) {
        if (srgMappings.containsKey(deobfName)) {
            return;
        }

        srgMappings.put(deobfName, srgName);
        deobfMappings.put(deobfName, deobfName);
    }

    protected static String getMapping(String deobfName) {
        if (CoreMod.getRuntimeDeobfuscationEnabled()) {
            return srgMappings.get(deobfName);
        } else {
            return deobfMappings.get(deobfName);
        }
    }

    protected final void replaceMethod(ClassNode node, String name, String desc, InsnList instructions, TryCatchBlockNode... tryCatchBlocks) {
        replaceMethod(node, Opcodes.ACC_PUBLIC, name, desc, instructions, tryCatchBlocks);
    }

    @SuppressWarnings("Unchecked")
    public final void replaceMethod(ClassNode node, int access, String name, String desc, InsnList instructions, TryCatchBlockNode... tryCatchBlocks) {
        String srgName = getMapping(name);
        List<MethodDescription> methodNames = Lists.newArrayList(
                new MethodDescription(name, desc),
                new MethodDescription(srgName, desc),
                McpMappings.instance().getMethod(srgName)
        );

        MethodNode method = new MethodNode(access, srgName, desc, null, null);
        Collections.addAll(method.tryCatchBlocks, tryCatchBlocks);
        method.instructions.add(instructions);

        for (Iterator<MethodNode> i = node.methods.iterator(); i.hasNext(); ) {
            MethodNode m = i.next();
            for (MethodDescription match : methodNames) {
                if (match == null) {
                    continue;
                }
                if (m.name.equals(match.getName()) && m.desc.equals(match.getDesc())) {
                    logger.fine("Removed %s%s for %s.%s%s", m.name, m.desc, node.name, name, desc);
                    i.remove();
                    break;
                }
            }
        }

        node.methods.add(method);
    }
}