package org.junit.runner;

import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * A <code>Failure</code> collects a failed test together with
 * the caught exception.
 */
public class Failure extends Object {
	protected Throwable fThrownException;
	
	/**
	 * Constructs a TestFailure with only the given exception.
	 */
	public Failure(Throwable thrownException) {
		fThrownException= thrownException;
	}
	/**
	 * Gets the thrown exception.
	 */
	public Throwable getException() {
	    return fThrownException;
	}
	/**
	 * Returns a short description of the failure.
	 */
	@Override
	public String toString() {
	    StringBuffer buffer= new StringBuffer();
	    buffer.append(getTestHeader() + ": "+fThrownException.getMessage());
	    return buffer.toString();
	}
	
	public String getTrace() {
		StringWriter stringWriter= new StringWriter();
		PrintWriter writer= new PrintWriter(stringWriter);
		getException().printStackTrace(writer);
		StringBuffer buffer= stringWriter.getBuffer();
		return buffer.toString();
	}
	
	public String getMessage() {
		return getException().getMessage();
	}
	
	public String getTestHeader() {
		return "Test mechanism failure";
	}
}