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
import net.cazzar.corelib.lib.LogHelper;
import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.util.Collections;
import java.util.List;

/**
 * A basic transformer that is selective to which class it is selecting
 */
public abstract class BasicTransformer implements IClassTransformer {
    final Logger logger = LogHelper.coreLog;
    private final List<String> classes = Lists.newArrayList();

    @SuppressWarnings("UnusedDeclaration")
    protected void addClass(String... names) {
        Collections.addAll(classes, names);
    }

    @Override
    public final byte[] transform(String name, String transformedName, byte[] bytes) {
        if (!classes.contains(transformedName)) {
            return bytes;
        }

        logger.info("Inserting hooks into %s (%s) for %s", name, transformedName, this.getClass().getCanonicalName());
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);

        transform(classNode);

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(writer);
        return writer.toByteArray();

    }

    /**
     * The stub function called to transform the loading class.
     *
     * @param classNode the class it is working on
     */
    public abstract void transform(ClassNode classNode);
}
