package compiler.type;

import iloc.eval.Machine;

/** Ly Data type. */
abstract public class Type {	
	private final TypeKind kind;
	/** Variable for if this type is also a reference **/
	private boolean reference;
	
	/** Variable for if this type is also a constant **/
	private boolean constant;

	/**
	 * Initialize the type, using a typekind
	 */
	public Type(TypeKind kind) {
		this.kind = kind;
	}

	/**
	 * Returns the typekind, see {@link TypeKind}
	 */
	public TypeKind getKind() {
		return this.kind;
	}
	
	/**
	 * True if this type is a reference
	 * @return {@link Type#reference}
	 */
	public boolean isRef() {
		return this.reference;
	}
	
	/**
	 * True if this type is a constant
	 * @return {@link Type#constant}
	 */
	public boolean isConst() {
		return this.constant;
	}
	
	/**
	 * Sets the type to reference
	 * @ensure {@link Type#reference} = ref
	 */
	public void setRef(boolean ref) {
		this.reference = ref;
	}
	
	/**
	 * Sets the type to constant
	 * @ensure {@link Type#constant} = cnst
	 */
	public void setConst(boolean cnst) {
		this.constant = cnst;
	}

	/** returns the size (in bytes) of a value of this type. */
	abstract public int size();

	static public class Bool extends Type {
		public Bool() {
			super(TypeKind.BOOL);
		}

		@Override
		public int size() {
			return Machine.INT_SIZE;
		}

		@Override
		public String toString() {
			return "Boolean";
		}
	}

	static public class Int extends Type {
		public Int() {
			super(TypeKind.INT);
		}

		@Override
		public int size() {
			return Machine.INT_SIZE;
		}

		@Override
		public String toString() {
			return "Integer";
		}
	}
	
	static public class Char extends Type {
		public Char() {
			super(TypeKind.CHR);
		}

		@Override
		public int size() {
			return Machine.INT_SIZE;
		}

		@Override
		public String toString() {
			return "Char";
		}
	}
	
	static public class Void extends Type {
		public Void() {
			super(TypeKind.VOID);
		}
		
		@Override
		public int size() {
			return Machine.INT_SIZE;
		}
		
		@Override
		public String toString() {
			return "Void";
		}
	}

	static public class Array extends Type {
		private final int lower;
		private final int upper;
		private final Type elemType;

		public Array(int lower, int upper, Type elemType) {
			super(TypeKind.ARRAY);
			assert upper >= lower;
			this.lower = lower;
			this.upper = upper;
			this.elemType = elemType;
		}

		public int getLower() {
			return this.lower;
		}

		public int getUpper() {
			return this.upper;
		}

		public Type getElemType() {
			return this.elemType;
		}

		@Override
		public int size() {
			return (getUpper() - getLower() + 1) * this.elemType.size();
		}

		@Override
		public String toString() {
			return "Array [" + this.lower + ".." + this.upper + "] of "
					+ this.elemType;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + this.elemType.hashCode();
			result = prime * result + this.lower;
			result = prime * result + this.upper;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof Array)) {
				return false;
			}
			Array other = (Array) obj;
			if (!this.elemType.equals(other.elemType)) {
				return false;
			}
			if (this.lower != other.lower) {
				return false;
			}
			if (this.upper != other.upper) {
				return false;
			}
			return true;
		}

	}
}
