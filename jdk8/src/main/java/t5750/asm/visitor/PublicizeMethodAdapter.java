package t5750.asm.visitor;

import static org.objectweb.asm.Opcodes.*;

import java.io.PrintWriter;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.util.TraceClassVisitor;

/**
 * 5.1. Using the TraceClassVisitor
 */
public class PublicizeMethodAdapter extends ClassVisitor {
	private TraceClassVisitor tracer;
	private PrintWriter pw = new PrintWriter(System.out);

	public PublicizeMethodAdapter(int api, ClassVisitor cv) {
		super(ASM6, cv);
		this.cv = cv;
	}

	public PublicizeMethodAdapter(ClassVisitor cv) {
		super(ASM6, cv);
		this.cv = cv;
		tracer = new TraceClassVisitor(cv, pw);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		if (name.equals("toUnsignedString0")) {
			// return cv.visitMethod(ACC_PUBLIC + ACC_STATIC, name, desc,
			// signature, exceptions);
			return tracer.visitMethod(ACC_PUBLIC + ACC_STATIC, name, desc,
					signature, exceptions);
		}
		// return cv.visitMethod(access, name, desc, signature, exceptions);
		return tracer.visitMethod(access, name, desc, signature, exceptions);
	}

	@Override
	public void visitEnd() {
		tracer.visitEnd();
		System.out.println(tracer.p.getText());
	}
}