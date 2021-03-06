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
import com.google.common.collect.Maps;
import net.cazzar.corelib.CoreMod;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.*;

@SuppressWarnings("UnusedDeclaration")
public abstract class MethodTransformer extends BasicTransformer {
    private static final Map<String, String> deobfMappings = Maps.newHashMap();
    private static final Map<String, String> srgMappings = Maps.newHashMap();

    /**
     * Add a mapping for the transformations
     *
     * @param deobfName The domesticated name for the transformations
     * @param srgName   the SRG mapping for the transformation
     */
    public static void addMapping(String deobfName, String srgName) {
        if (srgMappings.containsKey(deobfName)) {
            return;
        }

        srgMappings.put(deobfName, srgName);
        deobfMappings.put(deobfName, deobfName);
    }

    /**
     * Get the mapping for the deobf'd name
     *
     * @param deobfName the deobfuscated name of the mapping
     *
     * @return the SRG mapping
     */
    protected static String getMapping(String deobfName) {
        if (CoreMod.getRuntimeDeobfuscationEnabled()) {
            return srgMappings.get(deobfName);
        } else {
            return deobfMappings.get(deobfName);
        }
    }

    /**
     * Replace the said method with the instructions
     *
     * @param node           the Class being worked on
     * @param access         the Opcode access to the class
     * @param name           the name of the function
     * @param desc           the method description
     * @param insns          the instructions of the class
     * @param tryCatchBlocks a
     */
    public final void replaceMethod(ClassNode node, int access, String name, String desc, InsnList insns, TryCatchBlockNode... tryCatchBlocks) {
        InsnList instructions = transformInsns(insns);

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
                    logger.debug("Removed {}{} for {}.{}{}", m.name, m.desc, node.name, name, desc);
                    i.remove();
                    break;
                }
            }
        }

        node.methods.add(method);
    }

    /**
     * Append the instructions to the method
     *
     * @param node               the Class being worked on
     * @param access             the Opcode access to the class
     * @param name               the name of the function
     * @param desc               the method description
     * @param insnList           the instructions to append
     * @param tryCatchBlockNodes a
     */

    public final void appendToMethod(ClassNode node, int access, String name, String desc, InsnList insnList, TryCatchBlockNode... tryCatchBlockNodes) {
        InsnList insns = transformInsns(insnList);
        String srgName = getMapping(name);
        List<MethodDescription> methodNames = Lists.newArrayList(
                new MethodDescription(name, desc),
                new MethodDescription(srgName, desc),
                McpMappings.instance().getMethod(srgName)
        );

        MethodNode mtd = new MethodNode(access, srgName, desc, null, null);
        Collections.addAll(mtd.tryCatchBlocks, tryCatchBlockNodes);
        for (MethodNode methodNode : node.methods) {
            for (MethodDescription match : methodNames) {
                if (match == null)
                    continue;
                if (methodNode.name.equals(match.getName()) && methodNode.desc.equals(match.getDesc())) {
                    AbstractInsnNode lastInsn = methodNode.instructions.getLast();
                    while (lastInsn instanceof LabelNode || lastInsn instanceof LineNumberNode)
                        lastInsn = lastInsn.getPrevious();

                    if (isReturn(lastInsn))
                        methodNode.instructions.insertBefore(lastInsn, insns);
                    else
                        methodNode.instructions.add(insns);
                }
            }
        }
    }

    /**
     * Prepend the instructions to the method
     *
     * @param node               the Class being worked on
     * @param access             the Opcode access to the class
     * @param name               the name of the function
     * @param desc               the method description
     * @param insnList           the instructions to append
     * @param tryCatchBlockNodes a
     */

    public final void prependToMethod(ClassNode node, int access, String name, String desc, InsnList insnList, TryCatchBlockNode... tryCatchBlockNodes) {
        InsnList insns = transformInsns(insnList);
        String srgName = getMapping(name);
        List<MethodDescription> methodNames = Lists.newArrayList(
                new MethodDescription(name, desc),
                new MethodDescription(srgName, desc),
                McpMappings.instance().getMethod(srgName)
        );

        MethodNode mtd = new MethodNode(access, srgName, desc, null, null);
        Collections.addAll(mtd.tryCatchBlocks, tryCatchBlockNodes);
        for (MethodNode methodNode : node.methods) {
            for (MethodDescription match : methodNames) {
                if (match == null)
                    continue;
                if (methodNode.name.equals(match.getName()) && methodNode.desc.equals(match.getDesc())) {
                    methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), insns);
                }
            }
        }
    }

    protected final void replaceMethod(ClassNode node, String name, String desc, InsnList instructions, TryCatchBlockNode... tryCatchBlocks) {
        replaceMethod(node, Opcodes.ACC_PUBLIC, name, desc, instructions, tryCatchBlocks);
    }

    protected final void appendToMethod(ClassNode node, String name, String desc, InsnList insns, TryCatchBlockNode... tryCatchBlockNodes) {
        appendToMethod(node, Opcodes.ACC_PUBLIC, name, desc, insns, tryCatchBlockNodes);
    }

    private boolean isReturn(AbstractInsnNode node) {
        switch (node.getOpcode()) {
            case Opcodes.RET:
            case Opcodes.RETURN:
            case Opcodes.ARETURN:
            case Opcodes.DRETURN:
            case Opcodes.FRETURN:
            case Opcodes.IRETURN:
            case Opcodes.LRETURN:
                return true;

            default:
                return false;
        }
    }

    /**
     * Transform the insn list for use in a obf environment
     *
     * @param insnList the instructions to fiddle with
     *
     * @return the new insnList
     */
    public InsnList transformInsns(InsnList insnList) {
        if (!CoreMod.getRuntimeDeobfuscationEnabled())
            return insnList;

        InsnList insns = new InsnList();

        for (ListIterator<AbstractInsnNode> i = insnList.iterator(); i.hasNext(); ) {
            AbstractInsnNode currNode = i.next();
            if (currNode instanceof FieldInsnNode) {
                FieldInsnNode node = (FieldInsnNode) currNode;

                node.name = getMapping(node.name);
            } else if (currNode instanceof MethodInsnNode) {
                MethodInsnNode node = (MethodInsnNode) currNode;

                String mtd = getMapping(node.name);
                node.name = getMapping(node.name);
            }

            insns.add(currNode);
        }

        return insns;
    }
}
