package t5750.asm.visitor;

import static org.objectweb.asm.Opcodes.ASM6;

import java.io.PrintWriter;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.util.TraceClassVisitor;

/**
 * cv -> tracer
 */
public class AddFieldAdapter extends ClassVisitor {
	private String fieldName;
	private int access = org.objectweb.asm.Opcodes.ACC_PUBLIC;
	private boolean isFieldPresent;
	private TraceClassVisitor tracer;
	private PrintWriter pw = new PrintWriter(System.out);

	public AddFieldAdapter(String fieldName, int fieldAccess, ClassVisitor cv) {
		super(ASM6, cv);
		this.cv = cv;
		this.fieldName = fieldName;
		this.access = fieldAccess;
		tracer = new TraceClassVisitor(cv, pw);
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc,
			String signature, Object value) {
		if (name.equals(fieldName)) {
			isFieldPresent = true;
		}
		return tracer.visitField(access, name, desc, signature, value);
	}

	@Override
	public void visitEnd() {
		if (!isFieldPresent) {
			FieldVisitor fv = tracer.visitField(access, fieldName,
					Type.BOOLEAN_TYPE.toString(), null, null);
			if (fv != null) {
				fv.visitEnd();
			}
		}
		tracer.visitEnd();
	}
}