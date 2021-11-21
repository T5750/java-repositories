package t5750.jdk8;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class BackwardCompatibility {
	public static void main(String args[]) {
		BackwardCompatibility java8tester = new BackwardCompatibility();
		java8tester.testBackwardCompatability();
	}

	public void testBackwardCompatability() {
		//Get the current date
		Date currentDate = new Date();
		System.out.println("Current date: " + currentDate);
		//Get the instant of current date in terms of milliseconds
		Instant now = currentDate.toInstant();
		ZoneId currentZone = ZoneId.systemDefault();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(now, currentZone);
		System.out.println("Local date: " + localDateTime);
		ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(now, currentZone);
		System.out.println("Zoned date: " + zonedDateTime);
	}
}