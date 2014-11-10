package net.cazzar.corelib.asm.transformers;

import net.cazzar.corelib.asm.MethodTransformer;
import org.objectweb.asm.tree.*;

import static org.objectweb.asm.Opcodes.*;

/**
 * Created by Cayde on 8/11/2014.
 */
public class BlockTransformer extends MethodTransformer {
    public BlockTransformer() {
        addClass("net.minecraft.block.Block", "net.minecraft.item.ItemStack");
    }

    @Override
    public void transform(ClassNode classNode, String transformedName) {
        if ("net.minecraft.block.Block".equals(transformedName)) {
            String desc = "(Ljava/lang/String;)Lnet/minecraft/block/Block;";
            MethodNode method = new MethodNode(ACC_PUBLIC, "setBlockName", desc, null, null);
            method.instructions.add(new VarInsnNode(ALOAD, 0));
            method.instructions.add(new VarInsnNode(ALOAD, 1));
            method.instructions.add(new MethodInsnNode(INVOKEVIRTUAL, "net/minecraft/block/Block", "setUnlocalizedName", desc, false));
            method.instructions.add(new InsnNode(ARETURN));

            classNode.methods.add(method);

//            MethodNode method = new MethodNode(ACC_PUBLIC, "setBlockTexture", desc, null, null);
//            method.instructions.add(new VarInsnNode(ALOAD, 0));
//            method.instructions.add(new VarInsnNode(ALOAD, 1));
//            method.instructions.add(new MethodInsnNode(INVOKEVIRTUAL, "net/minecraft/block/Block", "setUnlocalizedName", desc, false));
//
//            classNode.methods.add(method);
        }



        else if ("net.minecraft.item.ItemStack".equals(transformedName)) {
            String desc = "()I";
            MethodNode method = new MethodNode(ACC_PUBLIC, "getItemDamage", desc, null, null);
            method.instructions.add(new VarInsnNode(ALOAD, 0));
            method.instructions.add(new MethodInsnNode(INVOKEVIRTUAL, "net/minecraft/item/ItemStack", "getCurrentDurability", desc, false));
            method.instructions.add(new InsnNode(IRETURN));

            classNode.methods.add(method);
        }
    }
}
