package t5750.instrument;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import t5750.asm.visitor.LogMethodClassVisitor;

public class AsmLogClassTransformer implements ClassFileTransformer {
	public byte[] transform(ClassLoader loader, String className,
			Class classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {
		ClassReader cr = new ClassReader(classfileBuffer);
		ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);
		ClassVisitor cv = new LogMethodClassVisitor(cw, className);
		cr.accept(cv, 0);
		return cw.toByteArray();
	}
}