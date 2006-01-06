package org.junit.tests;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.junit.runner.extensions.Enclosed;
import org.junit.runner.internal.RunnerFactory;

import static org.junit.Assert.assertEquals;

public class EnclosedTest {
	@RunWith(Enclosed.class)
	public static class Enclosing {
		public static class A {
			@Test public void a() {}
			@Test public void b() {}
		}
		public static class B {
			@Test public void a() {}
			@Test public void b() {}
			@Test public void c() {}
		}
	}
	
	@Test public void enclosedRunnerPlansEnclosedClasses() throws Exception {
		Runner runner = new RunnerFactory().getRunner(null, Enclosing.class);
		assertEquals(5, runner.testCount());
	}
	
	@Test public void enclosedRunnerRunsEnclosedClasses() throws Exception {
		Result result = JUnitCore.runClasses(Enclosing.class);
		assertEquals(5, result.getRunCount());
	}
}
