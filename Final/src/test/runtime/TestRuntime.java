package test.runtime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import iloc.Simulator;
import iloc.model.Program;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.junit.Test;

import compiler.compiler.LyCompiler;
import compiler.exception.ParseException;


@SuppressWarnings("javadoc")
public class TestRuntime {
	protected final static String extension = ".ly";
	protected final LyCompiler compiler = LyCompiler
			.instance();

	@Test
	public void testDivByZero() throws IOException, ParseException {
		Program prog = compile("runtime");
		try {
			String out = sim(prog, "");
		} catch (RuntimeException e) {
			// TODO: handle exception
			assertTrue(true);
		}
	}

	@Test
	public void testNullPoint() throws IOException, ParseException {
		Program prog = compile("runtime1");
		try {
			String out = sim(prog, "");
		} catch (RuntimeException e) {
			// TODO: handle exception
			assertTrue(true);
		}
	}

	private Program compile(String filename) throws IOException, ParseException {
		return this.compiler.compile(new File(getBaseDir(), filename + extension));
	}

	private String sim(Program prog, String input) {
		Simulator sim = new Simulator(prog);
		sim.setIn(new ByteArrayInputStream(input.getBytes()));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		sim.setOut(out);
		sim.run();
		return out.toString();
	}
	
	/**
	 * Gives the base direction, which is assumed to be src/package1/.../packageN/filename.ly
	 */
	protected String getBaseDir() {
		return "src/" + this.getClass().getPackage().getName().replaceAll("\\.", "/");
	}
}
