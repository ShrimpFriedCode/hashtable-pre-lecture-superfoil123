import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class IUAutogradingRunner {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Too many arguments!");
			System.out.println(args);
			System.exit(1);
		}

		Class<?> test_class = null;

		try {
			test_class = Class.forName(args[0]);
		} catch (ClassNotFoundException e) {
			System.out.println("Unable to find specified test class.");
			System.exit(1);
		}

		Result result = JUnitCore.runClasses(test_class);

		int total_tests    = result.getRunCount(),
		    total_failures = result.getFailureCount();

		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}

		System.out.println(total_tests + " " + (total_tests - total_failures) + " " + total_failures);
	}
}
