package org.su18.asm.frame;

import org.objectweb.asm.*;
import org.su18.utils.StringUtil;

import java.io.File;
import java.io.FileOutputStream;

public class Frame implements Opcodes {

	public static byte[] generate(String filename) throws Exception {

		ClassWriter   cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		MethodVisitor mv;
		FieldVisitor  fieldVisitor;

		String version = StringUtil.getVersion(filename);

		int ver = V1_8;
		if ("7".equals(version)) {
			ver = V1_7;
		}

		cw.visit(ver, ACC_PUBLIC | ACC_SUPER, filename, null, "java/lang/Object", null);

		{
			fieldVisitor = cw.visitField(ACC_PRIVATE, "result", "Ljava/lang/String;", null, null);
			fieldVisitor.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, new String[]{"java/lang/Exception"});
			mv.visitCode();
			PayloadInsert.insert(filename, mv);
			Label label0 = new Label();
			mv.visitLabel(label0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
			Label label1 = new Label();
			mv.visitLabel(label1);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKEVIRTUAL, filename, "echo", "()V", false);
			Label label2 = new Label();
			mv.visitLabel(label2);
			mv.visitInsn(RETURN);
			Label label3 = new Label();
			mv.visitLabel(label3);
			mv.visitLocalVariable("this", "L" + filename + ";", null, label0, label3, 0);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "echo", "()V", null, new String[]{"java/lang/Exception"});
			mv.visitCode();
			EchoInsert.insert(filename, mv);
			Label label0 = new Label();
			mv.visitLabel(label0);
			mv.visitInsn(RETURN);
			Label label1 = new Label();
			mv.visitLabel(label1);
			mv.visitLocalVariable("this", "L" + filename + ";", null, label0, label1, 0);
			mv.visitMaxs(0, 1);
			mv.visitEnd();
		}
		cw.visitEnd();

		return cw.toByteArray();


	}

	public static void main(String[] args) throws Exception {

		File             f   = new File("file.class");
		FileOutputStream fos = new FileOutputStream(f);
		fos.write(Frame.generate("BasicInfo8"));
		fos.close();
	}


}
