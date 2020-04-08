package org.su18.asm.frame;

import org.objectweb.asm.MethodVisitor;
import org.su18.utils.Logger;
import org.su18.utils.StringUtil;

import java.lang.reflect.Method;

public class EchoInsert {

	public static void insert(String className, MethodVisitor mv) {

		try {
			String   funName = StringUtil.getCurrentPropertiesValue("echo") + "Echo";
			Class<?> c       = Class.forName("org.su18.asm.echo." + funName);
			Method   m       = c.getDeclaredMethod("insert", String.class, MethodVisitor.class);
			m.setAccessible(true);
			m.invoke(null, className, mv);
		} catch (Exception e) {
			Logger.error("生成回显字节码出错：" + e);
		}


	}

}
