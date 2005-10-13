package org.junit.runner.internal;

import java.io.PrintStream;
import java.lang.reflect.Method;
import java.text.NumberFormat;

import org.junit.runner.Failure;
import org.junit.runner.Result;
import org.junit.runner.RunListener;

public class TextListener implements RunListener {

	private final PrintStream fWriter;

	public TextListener() {
		this(System.out);
	}

	public TextListener(PrintStream writer) {
		this.fWriter= writer;
	}

	// TestListener implementation
	public void testRunStarted(int testCount) {
	}

	public void testRunFinished(Result runner) {
		printHeader(runner.getRunTime());
		printFailures(runner);
		printFooter(runner);
	}

	public void testStarted(Object test, String name) {
		fWriter.append('.');
	}

	public void testFailure(Failure failure) {
		fWriter.append('E');
	}
	
	public void testIgnored(Method method) {
		fWriter.append('I');
	}
	
	/*
	 * Internal methods
	 */

	private PrintStream getWriter() {
		return fWriter;
	}

	protected void printHeader(long runTime) {
		getWriter().println();
		getWriter().println("Time: " + elapsedTimeAsString(runTime));
	}

	protected void printFailures(Result result) {
		if (result.getFailureCount() == 0)
			return;
		if (result.getFailureCount() == 1)
			getWriter().println("There was " + result.getFailureCount() + " failure:");
		else
			getWriter().println("There were " + result.getFailureCount() + " failures:");
		int i= 1;
		for (Failure each : result.getFailures())
			printFailure(each, i++);
	}

	protected void printFailure(Failure failure, int count) {
		printFailureHeader(failure, count);
		printFailureTrace(failure);
	}

	protected void printFailureHeader(Failure failure, int count) {
		getWriter().println(count + ") " + failure.getTestHeader());
	}

	protected void printFailureTrace(Failure failure) {
		getWriter().print(failure.getTrace());
	}

	protected void printFooter(Result result) {
		if (result.wasSuccessful()) {
			getWriter().println();
			getWriter().print("OK");
			getWriter().println(" (" + result.getRunCount() + " test" + (result.getRunCount() == 1 ? "" : "s") + ")");

		} else {
			getWriter().println();
			getWriter().println("FAILURES!!!");
			getWriter().println("Tests run: " + result.getRunCount() + ",  Failures: " + result.getFailureCount());
		}
		getWriter().println();
	}

	/**
	 * Returns the formatted string of the elapsed time. Duplicated from
	 * BaseTestRunner. Fix it.
	 */
	protected String elapsedTimeAsString(long runTime) {
		return NumberFormat.getInstance().format((double) runTime / 1000);
	}

	public void testFinished(Object test, String name) {
	}


}