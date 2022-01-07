package compiler.result;

import java.util.HashMap;
import java.util.Map;
import compiler.type.Type;

/**
 * Symbol table to keep track of 
 * functions and variables that have been
 * declared
 */
public class SymbolTable {
	/**
	 * Variable class, used to store the type and offset
	 */
	public static class Variable {
		public Type type;
		public int offset;
	}
	
	/**
	 * Function class, used to store the type and parameters
	 */
	public static class Function {
		public Type type;
		public String[] params;
	}
	
	/** Variable map: key = identifier of the variable, value = information about the variable*/
	private Map<String, Variable> variables = new HashMap<String, Variable>();
	/** Function map: key = identifier of the function, value = information abot the function */
	private Map<String, Function> functions = new HashMap<String, Function>();
	
	/**
	 * Puts in a variable without an offset. Used for variables
	 * declared within functions
	 * @require id != null and containsVar(id) == false
	 * @require type != null || type.getKind() != TypeKind.VOID
	 * @return !(old.containsVar(id))
	 */
	public Variable putVar(String id, Type type) {
		return putVar(id, type, -1);
	}
	
	/**
	 * Puts in a variable with an offset. Used for variables
	 * declared within the main program body
	 * @require id != null and containsVar(id) == false
	 * @require type != null || type.getKind() != TypeKind.VOID
	 * @require offset > 0
	 * @return !(old.containsVar(id))
	 */
	public Variable putVar(String id, Type type, int offset) {
		Variable var = new Variable();
		var.type = type;
		var.offset = offset;
		this.variables.put(id, var);
		return var;
	}
	
	/**
	 * Puts in a function with type void, also know as an procedure
	 * @require id != null && containsFunc(id) == false
	 * @require params != null
	 * @return !(old.containsFunc(id))
	 */
	public Function putProcedure(String id, String[] params) {
		return putFunc(id, new Type.Void(), params);
	}
	
	/**
	 * Puts in a function with a custom type
	 * @require id != null && containsFunc(id) == false
	 * @require type != null
	 * @require params != null
	 * @return !(old.containsFunc(id))
	 */
	public Function putFunc(String id, Type type, String[] params) {		
		Function func = new Function();
		func.type = type;
		func.params = params;
		this.functions.put(id, func);
		return func;
	}
	
	/**
	 * Checks if the symboltable contains a variable
	 * with the given indentifier
	 * @require id != null
	 * @return {@link SymbolTable#variables}.containsKey(id)
	 */
	public boolean containsVar(String id) {
		return this.variables.containsKey(id);
	}
	
	/**
	 * Checks if the symboltable contains a function
	 * with the given indentifier
	 * @require id != null
	 * @return {@link SymbolTable#functions}.containsKey(id)
	 */
	public boolean containsFunc(String id) {
		return this.functions.containsKey(id);
	}
	
	/**
	 * Returns the variable under the given indentifier
	 * @return if(!containsVar(id)) null else {@link SymbolTable#variables}.get(id)
	 */
	public Variable getVar(String id) {	
		return this.variables.get(id);
	}
	
	/**
	 * Returns the function under the given indentifier
	 * @return if(!containsFunc(id)) null else {@link SymbolTable#functions}.get(id)
	 */
	public Function getFunc(String id) {
		return this.functions.get(id);
	}
	
	
}
