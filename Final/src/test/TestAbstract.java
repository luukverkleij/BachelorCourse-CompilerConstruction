package test;

import iloc.Simulator;
import iloc.model.Program;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;
import compiler.compiler.LyCompiler;
import compiler.exception.ParseException;
import compiler.result.Result;

/**
 * Class test can inherent from this
 * class as it has some nice functions to reduce overhead
 */
public abstract class TestAbstract {
	protected final static boolean DEBUG = true;
	protected final static String extension = ".ly";
	protected final LyCompiler compiler = LyCompiler
			.instance();
	
	/**
	 * The main test class, used by {@link TestComplete} to run all other tests
	 */
	@Test
	public abstract void test() throws Exception;
	
	/***
	 * Runs the program that should be succesfull given the input and the expected output
	 * 
	 * @param filename Without the .ly extensions, as this is added
	 * @param input example: "5\n1\n0\n"
	 * @param output example: new String{"1", "2"}
	 */
	protected void success(String filename, String input, String[] output) throws ParseException, IOException {
		Program prog = compile(filename);
		if(DEBUG) {
			System.out.println("Pretty Print Basic:");
			System.out.println(prog.prettyPrint());
		}
		
		String out = sim(prog, input);
		
		if(DEBUG) {
			System.out.println("output:\n" +out);
		}
		
		String[] result = out.split("\n");
		for(int i = 0; i < result.length; i++)
			result[i] = result[i].replaceAll("\\s", "");
		assertArrayEquals(output, result);
	}
	
	/**
	 * See {@link TestAbstract#success(ParseTree, String, String[])}, Same but with a given ParseTree
	 */
	protected void success(ParseTree tree, String input, String[] output) throws ParseException, IOException {
		Program prog = this.compiler.compile(tree);
		if(DEBUG) {
			System.out.println("Pretty Print Basic:");
			System.out.println(prog.prettyPrint());
		}
		
		String out = sim(prog, input);
		
		if(DEBUG) {
			System.out.println("output:\n" +out);
		}
		
		assertArrayEquals(out.split("\n"), output);
	}
	
	/**
	 * Give a file that should fail at with the checker
	 * @param filename
	 * @throws IOException
	 */
	protected void failChecker(String filename) throws IOException {
		try {
			check(filename);
			fail(filename + " shouldn't check but did");
		} catch (ParseException exc) {
			//Expected behavoir
			System.out.println(exc.getMessage());
			if(DEBUG) {
				
			}
		}
		
	}
	
	/**
	 * See {@link TestAbstract#failChecker(String)}, same but with an given ParseTree
	 */
	protected void failChecker(ParseTree tree) throws IOException {
		try {
			this.compiler.check(tree);
			fail(tree.toString() + " shouldn't check but did");
		} catch (ParseException exc) {
			// this is the expected behaviour
		}
	}
	
	/**
	 * Simulates the given program using {@link Simulator}
	 * @return The output of the simulator, gained with {@link Simulator#setOut(java.io.OutputStream)}
	 */
	protected String sim(Program prog, String input) {
		Simulator sim = new Simulator(prog);
		sim.setIn(new ByteArrayInputStream(input.getBytes()));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		sim.setOut(out);
		sim.run();
		return out.toString();
	}
	
	/**
	 * Uses {@link LyCompiler#check(ParseTree)} and {@link LyCompiler#parse(File)} to compile the given file.
	 * The file is assumed to be within {@link TestAbstract#getBaseDir()} with the extension of {@link TestAbstract#extension}
	 * @param filename WITHOUT EXTENSION!
	 */
	protected Result check(String filename) throws ParseException, IOException {
		return this.compiler.check(this.compiler.parse(new File(getBaseDir(), filename + extension)));
	}
	
	/**
	 * Compiles the given filename
	 */
	protected Program compile(String filename) throws ParseException, IOException {
		return this.compiler.compile(new File(getBaseDir(), filename + extension));
	}
	
	/**
	 * Gives the base direction, which is assumed to be src/package1/.../packageN/filename.ly
	 */
	protected String getBaseDir() {
		return "src/" + this.getClass().getPackage().getName().replaceAll("\\.", "/");
	}
}
