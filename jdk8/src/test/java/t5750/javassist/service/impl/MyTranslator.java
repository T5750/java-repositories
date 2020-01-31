package t5750.javassist.service.impl;

import javassist.*;

public class MyTranslator implements Translator {
	@Override
	public void start(ClassPool pool)
			throws NotFoundException, CannotCompileException {
	}

	@Override
	public void onLoad(ClassPool pool, String classname)
			throws NotFoundException, CannotCompileException {
		CtClass cc = pool.get(classname);
		cc.setModifiers(Modifier.PUBLIC);
	}
}