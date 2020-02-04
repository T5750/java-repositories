package t5750.asm;

import java.io.IOException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import t5750.asm.visitor.AddFieldAdapter;
import t5750.asm.visitor.AddInterfaceAdapter;
import t5750.asm.visitor.PublicizeMethodAdapter;

public class CustomClassWriter {
	final static String CLASS_NAME = "java.lang.Integer";
	ClassReader reader;
	ClassWriter writer;
	AddFieldAdapter addFieldAdapter;
	PublicizeMethodAdapter pubMethAdapter;
	AddInterfaceAdapter addInterfaceAdapter;

	public CustomClassWriter() {
		try {
			reader = new ClassReader(CLASS_NAME);
			writer = new ClassWriter(reader, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public CustomClassWriter(byte[] contents) {
		reader = new ClassReader(contents);
		writer = new ClassWriter(reader, 0);
	}

	public byte[] addField() {
		addFieldAdapter = new AddFieldAdapter("aNewBooleanField",
				org.objectweb.asm.Opcodes.ACC_PUBLIC, writer);
		reader.accept(addFieldAdapter, 0);
		return writer.toByteArray();
	}

	public byte[] publicizeMethod() {
		pubMethAdapter = new PublicizeMethodAdapter(writer);
		reader.accept(pubMethAdapter, 0);
		return writer.toByteArray();
	}

	public byte[] addInterface() {
		addInterfaceAdapter = new AddInterfaceAdapter(writer);
		reader.accept(addInterfaceAdapter, 0);
		return writer.toByteArray();
	}
}