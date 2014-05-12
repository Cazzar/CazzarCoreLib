/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Cayde Dixon
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.cazzar.corelib.asm;

import com.google.common.collect.Lists;
import net.cazzar.corelib.lib.LogHelper;
import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.util.CheckClassAdapter;

import java.io.PrintWriter;
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

        logger.info("Inserting hooks into {} ({}) for {}", name, transformedName, this.getClass().getCanonicalName());
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);

        transform(classNode);

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(writer);
        CheckClassAdapter.verify(new ClassReader(writer.toByteArray()), false, new PrintWriter(System.err));
        return writer.toByteArray();

    }

    /**
     * The stub function called to transform the loading class.
     *
     * @param classNode the class it is working on
     */
    public abstract void transform(ClassNode classNode);
}
