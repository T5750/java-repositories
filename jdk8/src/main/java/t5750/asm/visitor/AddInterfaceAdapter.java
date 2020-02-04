package t5750.asm.visitor;

import static org.objectweb.asm.Opcodes.ASM6;
import static org.objectweb.asm.Opcodes.V1_5;

import java.io.PrintWriter;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.util.TraceClassVisitor;

/**
 * cv -> tracer
 */
public class AddInterfaceAdapter extends ClassVisitor {
	final static String CLONEABLE_INTERFACE = "java/lang/Cloneable";
	private TraceClassVisitor tracer;
	private PrintWriter pw = new PrintWriter(System.out);

	public AddInterfaceAdapter(ClassVisitor cv) {
		super(ASM6, cv);
		tracer = new TraceClassVisitor(cv, pw);
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		String[] holding = new String[interfaces.length + 1];
		holding[holding.length - 1] = CLONEABLE_INTERFACE;
		System.arraycopy(interfaces, 0, holding, 0, interfaces.length);
		tracer.visit(V1_5, access, name, signature, superName, holding);
	}

	@Override
	public void visitEnd() {
		tracer.visitEnd();
		System.out.println(tracer.p.getText());
	}
}