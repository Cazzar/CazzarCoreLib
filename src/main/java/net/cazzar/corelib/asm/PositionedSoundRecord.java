package net.cazzar.corelib.asm;

import org.objectweb.asm.Label;
import org.objectweb.asm.tree.*;

import java.util.ArrayList;

import static org.objectweb.asm.Opcodes.*;

@SuppressWarnings("UnusedDeclaration")
public class PositionedSoundRecord extends MethodTransformer {
    static {
        addMapping("volume", "field_147662_b");
        addMapping("pitch", "field_147663_c");
        addMapping("xPosF", "field_147660_d");
        addMapping("yPosF", "field_147661_e");
        addMapping("zPosF", "field_147658_f");
        addMapping("field_147665_h", "field_147665_h");
        addMapping("field_147666_i", "field_147666_i");
    }

    public PositionedSoundRecord() {
        addClass("net.minecraft.client.audio.PositionedSound");
    }

    @Override
    public void transform(ClassNode node) {
        MethodNode mtd = new MethodNode();
        mtd.name = "hashCode";
        mtd.desc = "()I";

        InsnList insns = new InsnList();
        insns.add(new VarInsnNode(ALOAD, 0));
        insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/audio/PositionedSound", "field_147664_a", "Lnet/minecraft/util/ResourceLocation;"));
        insns.add(new MethodInsnNode(INVOKEVIRTUAL, "net/minecraft/util/ResourceLocation", "hashCode", "()I"));
        insns.add(new VarInsnNode(ISTORE, 1));
        insns.add(new IntInsnNode(BIPUSH, 31));
        insns.add(new VarInsnNode(ILOAD, 1));
        insns.add(new InsnNode(IMUL));
        insns.add(new VarInsnNode(ALOAD, 0));
        insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/audio/PositionedSound", "volume", "F"));
        insns.add(new InsnNode(FCONST_0));
        insns.add(new InsnNode(FCMPL));
        LabelNode l2 = new LabelNode(new Label());
        insns.add(new JumpInsnNode(IFEQ, l2));
        insns.add(new VarInsnNode(ALOAD, 0));
        insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/audio/PositionedSound", "volume", "F"));
        insns.add(new MethodInsnNode(INVOKESTATIC, "java/lang/Float", "floatToIntBits", "(F)I"));
        LabelNode l3 = new LabelNode(new Label());
        insns.add(new JumpInsnNode(GOTO, l3));
        insns.add(l2);
        insns.add(new FrameNode(F_FULL, 2, new Object[] {"net/minecraft/client/audio/PositionedSound", INTEGER}, 1, new Object[] {INTEGER}));
        insns.add(new InsnNode(ICONST_0));
        insns.add(l3);
        insns.add(new FrameNode(F_FULL, 2, new Object[] {"net/minecraft/client/audio/PositionedSound", INTEGER}, 2, new Object[] {INTEGER, INTEGER}));
        insns.add(new InsnNode(IADD));
        insns.add(new VarInsnNode(ISTORE, 1));
        insns.add(new IntInsnNode(BIPUSH, 31));
        insns.add(new VarInsnNode(ILOAD, 1));
        insns.add(new InsnNode(IMUL));
        insns.add(new VarInsnNode(ALOAD, 0));
        insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/audio/PositionedSound", "field_147663_c", "F"));
        insns.add(new InsnNode(FCONST_0));
        insns.add(new InsnNode(FCMPL));
        LabelNode l5 = new LabelNode(new Label());
        insns.add(new JumpInsnNode(IFEQ, l5));
        insns.add(new VarInsnNode(ALOAD, 0));
        insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/audio/PositionedSound", "field_147663_c", "F"));
        insns.add(new MethodInsnNode(INVOKESTATIC, "java/lang/Float", "floatToIntBits", "(F)I"));
        LabelNode l6 = new LabelNode(new Label());
        insns.add(new JumpInsnNode(GOTO, l6));
        insns.add(l5);
        insns.add(new FrameNode(F_SAME1, 0, null, 1, new Object[] {INTEGER}));
        insns.add(new InsnNode(ICONST_0));
        insns.add(l6);
        insns.add(new FrameNode(F_FULL, 2, new Object[] {"net/minecraft/client/audio/PositionedSound", INTEGER}, 2, new Object[] {INTEGER, INTEGER}));
        insns.add(new InsnNode(IADD));
        insns.add(new VarInsnNode(ISTORE, 1));
        insns.add(new IntInsnNode(BIPUSH, 31));
        insns.add(new VarInsnNode(ILOAD, 1));
        insns.add(new InsnNode(IMUL));
        insns.add(new VarInsnNode(ALOAD, 0));
        insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/audio/PositionedSound", "xPosF", "F"));
        insns.add(new InsnNode(FCONST_0));
        insns.add(new InsnNode(FCMPL));
        LabelNode l8 = new LabelNode(new Label());
        insns.add(new JumpInsnNode(IFEQ, l8));
        insns.add(new VarInsnNode(ALOAD, 0));
        insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/audio/PositionedSound", "xPosF", "F"));
        insns.add(new MethodInsnNode(INVOKESTATIC, "java/lang/Float", "floatToIntBits", "(F)I"));
        LabelNode l9 = new LabelNode(new Label());
        insns.add(new JumpInsnNode(GOTO, l9));
        insns.add(l8);
        insns.add(new FrameNode(F_SAME1, 0, null, 1, new Object[] {INTEGER}));
        insns.add(new InsnNode(ICONST_0));
        insns.add(l9);
        insns.add(new FrameNode(F_FULL, 2, new Object[] {"net/minecraft/client/audio/PositionedSound", INTEGER}, 2, new Object[] {INTEGER, INTEGER}));
        insns.add(new InsnNode(IADD));
        insns.add(new VarInsnNode(ISTORE, 1));
        insns.add(new IntInsnNode(BIPUSH, 31));
        insns.add(new VarInsnNode(ILOAD, 1));
        insns.add(new InsnNode(IMUL));
        insns.add(new VarInsnNode(ALOAD, 0));
        insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/audio/PositionedSound", "yPosF", "F"));
        insns.add(new InsnNode(FCONST_0));
        insns.add(new InsnNode(FCMPL));
        LabelNode l11 = new LabelNode(new Label());
        insns.add(new JumpInsnNode(IFEQ, l11));
        insns.add(new VarInsnNode(ALOAD, 0));
        insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/audio/PositionedSound", "yPosF", "F"));
        insns.add(new MethodInsnNode(INVOKESTATIC, "java/lang/Float", "floatToIntBits", "(F)I"));
        LabelNode l12 = new LabelNode(new Label());
        insns.add(new JumpInsnNode(GOTO, l12));
        insns.add(l11);
        insns.add(new FrameNode(F_SAME1, 0, null, 1, new Object[] {INTEGER}));
        insns.add(new InsnNode(ICONST_0));
        insns.add(l12);
        insns.add(new FrameNode(F_FULL, 2, new Object[] {"net/minecraft/client/audio/PositionedSound", INTEGER}, 2, new Object[] {INTEGER, INTEGER}));
        insns.add(new InsnNode(IADD));
        insns.add(new VarInsnNode(ISTORE, 1));
        insns.add(new IntInsnNode(BIPUSH, 31));
        insns.add(new VarInsnNode(ILOAD, 1));
        insns.add(new InsnNode(IMUL));
        insns.add(new VarInsnNode(ALOAD, 0));
        insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/audio/PositionedSound", "zPosF", "F"));
        insns.add(new InsnNode(FCONST_0));
        insns.add(new InsnNode(FCMPL));
        LabelNode l14 = new LabelNode(new Label());
        insns.add(new JumpInsnNode(IFEQ, l14));
        insns.add(new VarInsnNode(ALOAD, 0));
        insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/audio/PositionedSound", "zPosF", "F"));
        insns.add(new MethodInsnNode(INVOKESTATIC, "java/lang/Float", "floatToIntBits", "(F)I"));
        LabelNode l15 = new LabelNode(new Label());
        insns.add(new JumpInsnNode(GOTO, l15));
        insns.add(l14);
        insns.add(new FrameNode(F_SAME1, 0, null, 1, new Object[] {INTEGER}));
        insns.add(new InsnNode(ICONST_0));
        insns.add(l15);
        insns.add(new FrameNode(F_FULL, 2, new Object[] {"net/minecraft/client/audio/PositionedSound", INTEGER}, 2, new Object[] {INTEGER, INTEGER}));
        insns.add(new InsnNode(IADD));
        insns.add(new VarInsnNode(ISTORE, 1));
        insns.add(new IntInsnNode(BIPUSH, 31));
        insns.add(new VarInsnNode(ILOAD, 1));
        insns.add(new InsnNode(IMUL));
        insns.add(new VarInsnNode(ALOAD, 0));
        insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/audio/PositionedSound", "repeat", "Z"));
        LabelNode l17 = new LabelNode(new Label());
        insns.add(new JumpInsnNode(IFEQ, l17));
        insns.add(new InsnNode(ICONST_1));
        LabelNode l18 = new LabelNode(new Label());
        insns.add(new JumpInsnNode(GOTO, l18));
        insns.add(l17);
        insns.add(new FrameNode(F_SAME1, 0, null, 1, new Object[] {INTEGER}));
        insns.add(new InsnNode(ICONST_0));
        insns.add(l18);
        insns.add(new FrameNode(F_FULL, 2, new Object[] {"net/minecraft/client/audio/PositionedSound", INTEGER}, 2, new Object[] {INTEGER, INTEGER}));
        insns.add(new InsnNode(IADD));
        insns.add(new VarInsnNode(ISTORE, 1));
        insns.add(new IntInsnNode(BIPUSH, 31));
        insns.add(new VarInsnNode(ILOAD, 1));
        insns.add(new InsnNode(IMUL));
        insns.add(new VarInsnNode(ALOAD, 0));
        insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/audio/PositionedSound", "field_147665_h", "I"));
        insns.add(new InsnNode(IADD));
        insns.add(new VarInsnNode(ISTORE, 1));
        insns.add(new IntInsnNode(BIPUSH, 31));
        insns.add(new VarInsnNode(ILOAD, 1));
        insns.add(new InsnNode(IMUL));
        insns.add(new VarInsnNode(ALOAD, 0));
        insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/audio/PositionedSound", "field_147666_i", "Lnet/minecraft/client/audio/ISound$AttenuationType;"));
        insns.add(new MethodInsnNode(INVOKEVIRTUAL, "net/minecraft/client/audio/ISound$AttenuationType", "hashCode", "()I"));
        insns.add(new InsnNode(IADD));
        insns.add(new VarInsnNode(ISTORE, 1));
        insns.add(new VarInsnNode(ILOAD, 1));
        insns.add(new InsnNode(IRETURN));
        mtd.instructions.add(transformInsns(insns));
        mtd.exceptions = new ArrayList<String>();

        node.methods.add(mtd);
    }
}
