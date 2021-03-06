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

package net.cazzar.corelib.asm.transformers;

import net.cazzar.corelib.asm.MethodTransformer;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.*;

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

    @SuppressWarnings("OverlyLongMethod")
    @Override
    public void transform(ClassNode node, String transformedName) {
        MethodNode mtd = new MethodNode(ACC_PUBLIC, "hashCode", "()I", null, new String[0]);
//        mtd.name = "hashCode";
//        mtd.desc = "()I";

        InsnList insns = new InsnList();
        insns.add(new VarInsnNode(ALOAD, 0));
        insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/audio/PositionedSound", "volume", "F"));
        insns.add(new InsnNode(FCONST_0));
        insns.add(new InsnNode(FCMPL));
        LabelNode l1 = new LabelNode(new Label());
        insns.add(new JumpInsnNode(IFEQ, l1));
        insns.add(new VarInsnNode(ALOAD, 0));
        insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/audio/PositionedSound", "volume", "F"));
        insns.add(new MethodInsnNode(INVOKESTATIC, "java/lang/Float", "floatToIntBits", "(F)I", false));
        LabelNode l2 = new LabelNode(new Label());
        insns.add(new JumpInsnNode(GOTO, l2));
        insns.add(l1);
        insns.add(new FrameNode(F_SAME, 0, null, 0, null));
        insns.add(new InsnNode(ICONST_0));
        insns.add(l2);
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
        insns.add(new MethodInsnNode(INVOKESTATIC, "java/lang/Float", "floatToIntBits", "(F)I", false));
        LabelNode l6 = new LabelNode(new Label());
        insns.add(new JumpInsnNode(GOTO, l6));
        insns.add(l5);
        insns.add(new FrameNode(F_SAME1, 0, null, 1, new Object[]{INTEGER}));
        insns.add(new InsnNode(ICONST_0));
        insns.add(l6);
        insns.add(new FrameNode(F_FULL, 2, new Object[]{"net/minecraft/client/audio/PositionedSound", INTEGER}, 2, new Object[]{INTEGER, INTEGER}));
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
        insns.add(new MethodInsnNode(INVOKESTATIC, "java/lang/Float", "floatToIntBits", "(F)I", false));
        LabelNode l9 = new LabelNode(new Label());
        insns.add(new JumpInsnNode(GOTO, l9));
        insns.add(l8);
        insns.add(new FrameNode(F_SAME1, 0, null, 1, new Object[]{INTEGER}));
        insns.add(new InsnNode(ICONST_0));
        insns.add(l9);
        insns.add(new FrameNode(F_FULL, 2, new Object[]{"net/minecraft/client/audio/PositionedSound", INTEGER}, 2, new Object[]{INTEGER, INTEGER}));
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
        insns.add(new MethodInsnNode(INVOKESTATIC, "java/lang/Float", "floatToIntBits", "(F)I", false));
        LabelNode l12 = new LabelNode(new Label());
        insns.add(new JumpInsnNode(GOTO, l12));
        insns.add(l11);
        insns.add(new FrameNode(F_SAME1, 0, null, 1, new Object[]{INTEGER}));
        insns.add(new InsnNode(ICONST_0));
        insns.add(l12);
        insns.add(new FrameNode(F_FULL, 2, new Object[]{"net/minecraft/client/audio/PositionedSound", INTEGER}, 2, new Object[]{INTEGER, INTEGER}));
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
        insns.add(new MethodInsnNode(INVOKESTATIC, "java/lang/Float", "floatToIntBits", "(F)I", false));
        LabelNode l15 = new LabelNode(new Label());
        insns.add(new JumpInsnNode(GOTO, l15));
        insns.add(l14);
        insns.add(new FrameNode(F_SAME1, 0, null, 1, new Object[]{INTEGER}));
        insns.add(new InsnNode(ICONST_0));
        insns.add(l15);
        insns.add(new FrameNode(F_FULL, 2, new Object[]{"net/minecraft/client/audio/PositionedSound", INTEGER}, 2, new Object[]{INTEGER, INTEGER}));
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
        insns.add(new FrameNode(F_SAME1, 0, null, 1, new Object[]{INTEGER}));
        insns.add(new InsnNode(ICONST_0));
        insns.add(l18);
        insns.add(new FrameNode(F_FULL, 2, new Object[]{"net/minecraft/client/audio/PositionedSound", INTEGER}, 2, new Object[]{INTEGER, INTEGER}));
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
        insns.add(new MethodInsnNode(INVOKEVIRTUAL, "net/minecraft/client/audio/ISound$AttenuationType", "hashCode", "()I", false));
        insns.add(new InsnNode(IADD));
        insns.add(new VarInsnNode(ISTORE, 1));
        insns.add(new VarInsnNode(ILOAD, 1));
        insns.add(new InsnNode(IRETURN));
        mtd.instructions.add(transformInsns(insns));

        node.methods.add(mtd);

        mtd = new MethodNode(ACC_PUBLIC, "equals", "(Ljava/lang/Object;)Z", null, new String[0]);

        insns = new InsnList();
        insns.add(new VarInsnNode(ALOAD, 0));
        insns.add(new VarInsnNode(ALOAD, 1));
        l1 = new LabelNode(new Label());
        insns.add(new JumpInsnNode(IF_ACMPNE, l1));
        insns.add(new InsnNode(ICONST_1));
        insns.add(new InsnNode(IRETURN));
        insns.add(l1);
        insns.add(new FrameNode(F_SAME, 0, null, 0, null));
        insns.add(new VarInsnNode(ALOAD, 1));
        insns.add(new TypeInsnNode(INSTANCEOF, "net/minecraft/client/audio/PositionedSound"));
        l2 = new LabelNode(new Label());
        insns.add(new JumpInsnNode(IFNE, l2));
        insns.add(new InsnNode(ICONST_0));
        insns.add(new InsnNode(IRETURN));
        insns.add(l2);
        insns.add(new FrameNode(F_SAME, 0, null, 0, null));
        insns.add(new VarInsnNode(ALOAD, 1));
        insns.add(new TypeInsnNode(CHECKCAST, "net/minecraft/client/audio/PositionedSound"));
        insns.add(new VarInsnNode(ASTORE, 2));
        insns.add(new VarInsnNode(ALOAD, 2));
        insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/audio/PositionedSound", "field_147663_c", "F"));
        insns.add(new VarInsnNode(ALOAD, 0));
        insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/audio/PositionedSound", "field_147663_c", "F"));
        insns.add(new MethodInsnNode(INVOKESTATIC, "java/lang/Float", "compare", "(FF)I", false));
        LabelNode l4 = new LabelNode(new Label());
        insns.add(new JumpInsnNode(IFEQ, l4));
        insns.add(new InsnNode(ICONST_0));
        insns.add(new InsnNode(IRETURN));
        insns.add(new FrameNode(F_APPEND, 1, new Object[]{"net/minecraft/client/audio/PositionedSound"}, 0, null));
        insns.add(new VarInsnNode(ALOAD, 0));
        insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/audio/PositionedSound", "field_147665_h", "I"));
        insns.add(new VarInsnNode(ALOAD, 2));
        insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/audio/PositionedSound", "field_147665_h", "I"));
        l5 = new LabelNode(new Label());
        insns.add(new JumpInsnNode(IF_ICMPEQ, l5));
        insns.add(new InsnNode(ICONST_0));
        insns.add(new InsnNode(IRETURN));
        insns.add(l5);
        insns.add(new FrameNode(F_SAME, 0, null, 0, null));
        insns.add(new VarInsnNode(ALOAD, 0));
        insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/audio/PositionedSound", "volume", "F"));
        insns.add(new VarInsnNode(ALOAD, 2));
        insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/audio/PositionedSound", "volume", "F"));
        insns.add(new MethodInsnNode(INVOKESTATIC, "java/lang/Float", "compare", "(FF)I", false));
        LabelNode l7 = new LabelNode(new Label());
        insns.add(new JumpInsnNode(IFEQ, l7));
        insns.add(new InsnNode(ICONST_0));
        insns.add(new InsnNode(IRETURN));
        insns.add(l7);
        insns.add(new FrameNode(F_SAME, 0, null, 0, null));
        insns.add(new VarInsnNode(ALOAD, 0));
        insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/audio/PositionedSound", "xPosF", "F"));
        insns.add(new VarInsnNode(ALOAD, 2));
        insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/audio/PositionedSound", "xPosF", "F"));
        insns.add(new MethodInsnNode(INVOKESTATIC, "java/lang/Float", "compare", "(FF)I", false));
        l7 = new LabelNode(new Label());
        insns.add(new JumpInsnNode(IFEQ, l7));
        insns.add(new InsnNode(ICONST_0));
        insns.add(new InsnNode(IRETURN));
        insns.add(l7);
        insns.add(new FrameNode(F_SAME, 0, null, 0, null));
        insns.add(new VarInsnNode(ALOAD, 0));
        insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/audio/PositionedSound", "yPosF", "F"));
        insns.add(new VarInsnNode(ALOAD, 2));
        insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/audio/PositionedSound", "yPosF", "F"));
        insns.add(new MethodInsnNode(INVOKESTATIC, "java/lang/Float", "compare", "(FF)I", false));
        l7 = new LabelNode(new Label());
        insns.add(new JumpInsnNode(IFEQ, l7));
        insns.add(new InsnNode(ICONST_0));
        insns.add(new InsnNode(IRETURN));
        insns.add(l7);
        insns.add(new FrameNode(F_SAME, 0, null, 0, null));
        insns.add(new VarInsnNode(ALOAD, 0));
        insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/audio/PositionedSound", "zPosF", "F"));
        insns.add(new VarInsnNode(ALOAD, 2));
        insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/audio/PositionedSound", "zPosF", "F"));
        insns.add(new MethodInsnNode(INVOKESTATIC, "java/lang/Float", "compare", "(FF)I", false));
        l7 = new LabelNode(new Label());
        insns.add(new JumpInsnNode(IFEQ, l7));
        insns.add(new InsnNode(ICONST_0));
        insns.add(new InsnNode(IRETURN));
        insns.add(l7);

//        insns.add(new FrameNode(F_SAME, 0, null, 0, null));
//        insns.add(new VarInsnNode(ALOAD, 0));
//        insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/audio/PositionedSound", "field_147664_a", "Lnet/minecraft/util/ResourceLocation;"));
//        insns.add(new VarInsnNode(ALOAD, 2));
//        insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/audio/PositionedSound", "field_147664_a", "Lnet/minecraft/util/ResourceLocation;"));
//        insns.add(new MethodInsnNode(INVOKEVIRTUAL, "net/minecraft/util/ResourceLocation", "equals", "(Ljava/lang/Object;)Z"));
//        l7 = new LabelNode(new Label());
//        insns.add(new JumpInsnNode(IFNE, l7));
//        insns.add(new InsnNode(ICONST_0));
//        insns.add(new InsnNode(IRETURN));
//        insns.add(l7 );

        insns.add(new FrameNode(F_SAME, 0, null, 0, null));
        insns.add(new VarInsnNode(ALOAD, 0));
        insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/audio/PositionedSound", "field_147666_i", "Lnet/minecraft/client/audio/ISound$AttenuationType;"));
        insns.add(new VarInsnNode(ALOAD, 2));
        insns.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/audio/PositionedSound", "field_147666_i", "Lnet/minecraft/client/audio/ISound$AttenuationType;"));
        l7 = new LabelNode(new Label());
        insns.add(new JumpInsnNode(IF_ACMPEQ, l7));
        insns.add(new InsnNode(ICONST_0));
        insns.add(new InsnNode(IRETURN));
        insns.add(l7);
        insns.add(new InsnNode(ICONST_1));
        insns.add(new InsnNode(IRETURN));

        mtd.instructions.add(transformInsns(insns));
    }
}
