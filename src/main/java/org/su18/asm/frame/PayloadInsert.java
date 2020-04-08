package org.su18.asm.frame;

import org.objectweb.asm.MethodVisitor;
import org.su18.utils.Logger;
import org.su18.utils.StringUtil;

import java.lang.reflect.Method;

public class PayloadInsert {

	public static void insert(String className, MethodVisitor mv) {
		try {
			Class<?> c = Class.forName("org.su18.asm.payload." + StringUtil.getClassName(className));
			Method   m = c.getDeclaredMethod("insert", String.class, MethodVisitor.class);
			m.invoke(null, className, mv);

		} catch (Exception e) {
			Logger.error("生成恶意字节码出错：" + e);
		}
	}
}
