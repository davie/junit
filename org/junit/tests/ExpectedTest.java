package org.junit.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

public class ExpectedTest {
	
	public static class Expected {
		@Test(expected= Exception.class) public void expected() throws Exception {
			throw new Exception();
		}
	}
	@Test public void expected() {
		JUnitCore core= new JUnitCore();
		Result runner= core.run(Expected.class);
		assertTrue(runner.wasSuccessful());
	}
	
	public static class Unexpected {
		@Test(expected= Exception.class) public void expected() throws Exception {
			throw new Error();
		}
	}
	@Test public void unexpected() {
		Result runner = JUnitCore.runClasses(Unexpected.class);
		String message= runner.getFailures().get(0).getMessage();
		assertTrue(message.contains("expected<java.lang.Exception> but was<java.lang.Error>"));
	}
	
	public static class NoneThrown {
		@Test(expected= Exception.class) public void nothing() {
		}
	}
	@Test public void noneThrown() {
		JUnitCore core= new JUnitCore();
		Result runner= core.run(NoneThrown.class);
		assertFalse(runner.wasSuccessful());
		String message= runner.getFailures().get(0).getMessage();
		assertTrue(message.contains("Expected exception: java.lang.Exception"));
	}
	
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(ExpectedTest.class);
	}
}