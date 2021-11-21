package t5750.jdk8;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class TemporalAdjusters {
	public static void main(String args[]) {
		TemporalAdjusters java8tester = new TemporalAdjusters();
		java8tester.testAdjusters();
	}

	public void testAdjusters() {
		//Get the current date
		LocalDate date1 = LocalDate.now();
		System.out.println("Current date: " + date1);
		//get the next tuesday
		LocalDate nextTuesday = date1.with(java.time.temporal.TemporalAdjusters.next(DayOfWeek.TUESDAY));
		System.out.println("Next Tuesday on : " + nextTuesday);
		//get the second saturday of next month
		LocalDate firstInYear = LocalDate.of(date1.getYear(), date1.getMonth(), 1);
		LocalDate secondSaturday = firstInYear.with(java.time.temporal.TemporalAdjusters.nextOrSame(
				DayOfWeek.SATURDAY)).with(java.time.temporal.TemporalAdjusters.next(DayOfWeek.SATURDAY));
		System.out.println("Second Saturday on : " + secondSaturday);
	}
}