package net.cazzar.corelib.asm;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.cazzar.corelib.CoreMod;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Author: Cayde
 */
public abstract class MethodExitTransformer extends BasicTransformer {
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

    public final void appendToMethod(ClassNode node, int access, String name, String desc, InsnList insns, TryCatchBlockNode... tryCatchBlockNodes){
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
                        methodNode.instructions.insert(insns);
                }
            }
        }
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
}
