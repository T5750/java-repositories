package t5750.javassist.domain;

public class CflowDomain {
	int fact(int n) {
		if (n <= 1)
			return n;
		else
			return n * fact(n - 1);
	}
}