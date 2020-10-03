package t5750.utils.jackson.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

//@JsonFormat(shape = JsonFormat.Shape.NUMBER)
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Code {
	BLOCKING, CRITICAL, MEDIUM, LOW;
}