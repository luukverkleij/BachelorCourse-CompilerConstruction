// Generated from ly.g4 by ANTLR 4.5
package grammar;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class lyLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		FNCTYPE=1, PROGRAM=2, TRUE=3, FALSE=4, READ=5, PRINT=6, IF=7, ELSE=8, 
		WHILE=9, RETURN=10, CONST=11, VOID=12, INT=13, BOOL=14, CHAR=15, ASS=16, 
		AND=17, COLON=18, COMMA=19, DOT=20, DQUOTE=21, EQ=22, GE=23, GT=24, LE=25, 
		LBRACE=26, LPAR=27, LBLOCK=28, LT=29, MINUS=30, NE=31, NOT=32, PLUS=33, 
		RBLOCK=34, RBRACE=35, RPAR=36, SEMI=37, SLASH=38, REF=39, STAR=40, QUOTE=41, 
		OR=42, HT=43, ID=44, NUM=45, STR=46, CHR=47, COMMENT=48, MULTICOMMENT=49, 
		WS=50;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"FNCTYPE", "PROGRAM", "TRUE", "FALSE", "READ", "PRINT", "IF", "ELSE", 
		"WHILE", "RETURN", "CONST", "VOID", "INT", "BOOL", "CHAR", "ASS", "AND", 
		"COLON", "COMMA", "DOT", "DQUOTE", "EQ", "GE", "GT", "LE", "LBRACE", "LPAR", 
		"LBLOCK", "LT", "MINUS", "NE", "NOT", "PLUS", "RBLOCK", "RBRACE", "RPAR", 
		"SEMI", "SLASH", "REF", "STAR", "QUOTE", "OR", "HT", "ID", "NUM", "STR", 
		"CHR", "LETTER", "DIGIT", "COMMENT", "MULTICOMMENT", "WS"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'~~'", "'program'", "'true'", "'false'", "'read'", "'print'", "'if'", 
		"'else'", "'while'", "'return'", "'const'", "'void'", "'int'", "'bool'", 
		"'char'", "'<-'", "'&&'", "':'", "','", "'.'", "'\"'", "'='", "'>='", 
		"'>'", "'<='", "'{'", "'('", "'['", "'<'", "'-'", "'!='", "'!'", "'+'", 
		"']'", "'}'", "')'", "';'", "'/'", "'@'", "'*'", "'''", "'||'", "'#'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "FNCTYPE", "PROGRAM", "TRUE", "FALSE", "READ", "PRINT", "IF", "ELSE", 
		"WHILE", "RETURN", "CONST", "VOID", "INT", "BOOL", "CHAR", "ASS", "AND", 
		"COLON", "COMMA", "DOT", "DQUOTE", "EQ", "GE", "GT", "LE", "LBRACE", "LPAR", 
		"LBLOCK", "LT", "MINUS", "NE", "NOT", "PLUS", "RBLOCK", "RBRACE", "RPAR", 
		"SEMI", "SLASH", "REF", "STAR", "QUOTE", "OR", "HT", "ID", "NUM", "STR", 
		"CHR", "COMMENT", "MULTICOMMENT", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public lyLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "ly.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\64\u013a\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3"+
		"\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r"+
		"\3\r\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20"+
		"\3\20\3\21\3\21\3\21\3\22\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26"+
		"\3\26\3\27\3\27\3\30\3\30\3\30\3\31\3\31\3\32\3\32\3\32\3\33\3\33\3\34"+
		"\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3"+
		"$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3+\3+\3+\3,\3,\3-\3-\3-\3-\7-"+
		"\u00fd\n-\f-\16-\u0100\13-\3.\3.\7.\u0104\n.\f.\16.\u0107\13.\3/\3/\7"+
		"/\u010b\n/\f/\16/\u010e\13/\3/\3/\3\60\3\60\5\60\u0114\n\60\3\60\3\60"+
		"\3\61\3\61\3\62\3\62\3\63\3\63\7\63\u011e\n\63\f\63\16\63\u0121\13\63"+
		"\3\63\3\63\3\63\3\63\3\64\3\64\3\64\7\64\u012a\n\64\f\64\16\64\u012d\13"+
		"\64\3\64\3\64\3\64\3\64\3\64\3\65\6\65\u0135\n\65\r\65\16\65\u0136\3\65"+
		"\3\65\5\u010c\u011f\u012b\2\66\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13"+
		"\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61"+
		"\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61"+
		"a\2c\2e\62g\63i\64\3\2\5\4\2C\\c|\3\2\62;\5\2\13\f\17\17\"\"\u0140\2\3"+
		"\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2"+
		"\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31"+
		"\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2"+
		"\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2"+
		"\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2"+
		"\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2"+
		"I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3"+
		"\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2e\3\2\2"+
		"\2\2g\3\2\2\2\2i\3\2\2\2\3k\3\2\2\2\5n\3\2\2\2\7v\3\2\2\2\t{\3\2\2\2\13"+
		"\u0081\3\2\2\2\r\u0086\3\2\2\2\17\u008c\3\2\2\2\21\u008f\3\2\2\2\23\u0094"+
		"\3\2\2\2\25\u009a\3\2\2\2\27\u00a1\3\2\2\2\31\u00a7\3\2\2\2\33\u00ac\3"+
		"\2\2\2\35\u00b0\3\2\2\2\37\u00b5\3\2\2\2!\u00ba\3\2\2\2#\u00bd\3\2\2\2"+
		"%\u00c0\3\2\2\2\'\u00c2\3\2\2\2)\u00c4\3\2\2\2+\u00c6\3\2\2\2-\u00c8\3"+
		"\2\2\2/\u00ca\3\2\2\2\61\u00cd\3\2\2\2\63\u00cf\3\2\2\2\65\u00d2\3\2\2"+
		"\2\67\u00d4\3\2\2\29\u00d6\3\2\2\2;\u00d8\3\2\2\2=\u00da\3\2\2\2?\u00dc"+
		"\3\2\2\2A\u00df\3\2\2\2C\u00e1\3\2\2\2E\u00e3\3\2\2\2G\u00e5\3\2\2\2I"+
		"\u00e7\3\2\2\2K\u00e9\3\2\2\2M\u00eb\3\2\2\2O\u00ed\3\2\2\2Q\u00ef\3\2"+
		"\2\2S\u00f1\3\2\2\2U\u00f3\3\2\2\2W\u00f6\3\2\2\2Y\u00f8\3\2\2\2[\u0101"+
		"\3\2\2\2]\u0108\3\2\2\2_\u0111\3\2\2\2a\u0117\3\2\2\2c\u0119\3\2\2\2e"+
		"\u011b\3\2\2\2g\u0126\3\2\2\2i\u0134\3\2\2\2kl\7\u0080\2\2lm\7\u0080\2"+
		"\2m\4\3\2\2\2no\7r\2\2op\7t\2\2pq\7q\2\2qr\7i\2\2rs\7t\2\2st\7c\2\2tu"+
		"\7o\2\2u\6\3\2\2\2vw\7v\2\2wx\7t\2\2xy\7w\2\2yz\7g\2\2z\b\3\2\2\2{|\7"+
		"h\2\2|}\7c\2\2}~\7n\2\2~\177\7u\2\2\177\u0080\7g\2\2\u0080\n\3\2\2\2\u0081"+
		"\u0082\7t\2\2\u0082\u0083\7g\2\2\u0083\u0084\7c\2\2\u0084\u0085\7f\2\2"+
		"\u0085\f\3\2\2\2\u0086\u0087\7r\2\2\u0087\u0088\7t\2\2\u0088\u0089\7k"+
		"\2\2\u0089\u008a\7p\2\2\u008a\u008b\7v\2\2\u008b\16\3\2\2\2\u008c\u008d"+
		"\7k\2\2\u008d\u008e\7h\2\2\u008e\20\3\2\2\2\u008f\u0090\7g\2\2\u0090\u0091"+
		"\7n\2\2\u0091\u0092\7u\2\2\u0092\u0093\7g\2\2\u0093\22\3\2\2\2\u0094\u0095"+
		"\7y\2\2\u0095\u0096\7j\2\2\u0096\u0097\7k\2\2\u0097\u0098\7n\2\2\u0098"+
		"\u0099\7g\2\2\u0099\24\3\2\2\2\u009a\u009b\7t\2\2\u009b\u009c\7g\2\2\u009c"+
		"\u009d\7v\2\2\u009d\u009e\7w\2\2\u009e\u009f\7t\2\2\u009f\u00a0\7p\2\2"+
		"\u00a0\26\3\2\2\2\u00a1\u00a2\7e\2\2\u00a2\u00a3\7q\2\2\u00a3\u00a4\7"+
		"p\2\2\u00a4\u00a5\7u\2\2\u00a5\u00a6\7v\2\2\u00a6\30\3\2\2\2\u00a7\u00a8"+
		"\7x\2\2\u00a8\u00a9\7q\2\2\u00a9\u00aa\7k\2\2\u00aa\u00ab\7f\2\2\u00ab"+
		"\32\3\2\2\2\u00ac\u00ad\7k\2\2\u00ad\u00ae\7p\2\2\u00ae\u00af\7v\2\2\u00af"+
		"\34\3\2\2\2\u00b0\u00b1\7d\2\2\u00b1\u00b2\7q\2\2\u00b2\u00b3\7q\2\2\u00b3"+
		"\u00b4\7n\2\2\u00b4\36\3\2\2\2\u00b5\u00b6\7e\2\2\u00b6\u00b7\7j\2\2\u00b7"+
		"\u00b8\7c\2\2\u00b8\u00b9\7t\2\2\u00b9 \3\2\2\2\u00ba\u00bb\7>\2\2\u00bb"+
		"\u00bc\7/\2\2\u00bc\"\3\2\2\2\u00bd\u00be\7(\2\2\u00be\u00bf\7(\2\2\u00bf"+
		"$\3\2\2\2\u00c0\u00c1\7<\2\2\u00c1&\3\2\2\2\u00c2\u00c3\7.\2\2\u00c3("+
		"\3\2\2\2\u00c4\u00c5\7\60\2\2\u00c5*\3\2\2\2\u00c6\u00c7\7$\2\2\u00c7"+
		",\3\2\2\2\u00c8\u00c9\7?\2\2\u00c9.\3\2\2\2\u00ca\u00cb\7@\2\2\u00cb\u00cc"+
		"\7?\2\2\u00cc\60\3\2\2\2\u00cd\u00ce\7@\2\2\u00ce\62\3\2\2\2\u00cf\u00d0"+
		"\7>\2\2\u00d0\u00d1\7?\2\2\u00d1\64\3\2\2\2\u00d2\u00d3\7}\2\2\u00d3\66"+
		"\3\2\2\2\u00d4\u00d5\7*\2\2\u00d58\3\2\2\2\u00d6\u00d7\7]\2\2\u00d7:\3"+
		"\2\2\2\u00d8\u00d9\7>\2\2\u00d9<\3\2\2\2\u00da\u00db\7/\2\2\u00db>\3\2"+
		"\2\2\u00dc\u00dd\7#\2\2\u00dd\u00de\7?\2\2\u00de@\3\2\2\2\u00df\u00e0"+
		"\7#\2\2\u00e0B\3\2\2\2\u00e1\u00e2\7-\2\2\u00e2D\3\2\2\2\u00e3\u00e4\7"+
		"_\2\2\u00e4F\3\2\2\2\u00e5\u00e6\7\177\2\2\u00e6H\3\2\2\2\u00e7\u00e8"+
		"\7+\2\2\u00e8J\3\2\2\2\u00e9\u00ea\7=\2\2\u00eaL\3\2\2\2\u00eb\u00ec\7"+
		"\61\2\2\u00ecN\3\2\2\2\u00ed\u00ee\7B\2\2\u00eeP\3\2\2\2\u00ef\u00f0\7"+
		",\2\2\u00f0R\3\2\2\2\u00f1\u00f2\7)\2\2\u00f2T\3\2\2\2\u00f3\u00f4\7~"+
		"\2\2\u00f4\u00f5\7~\2\2\u00f5V\3\2\2\2\u00f6\u00f7\7%\2\2\u00f7X\3\2\2"+
		"\2\u00f8\u00fe\5a\61\2\u00f9\u00fd\5a\61\2\u00fa\u00fd\5c\62\2\u00fb\u00fd"+
		"\7a\2\2\u00fc\u00f9\3\2\2\2\u00fc\u00fa\3\2\2\2\u00fc\u00fb\3\2\2\2\u00fd"+
		"\u0100\3\2\2\2\u00fe\u00fc\3\2\2\2\u00fe\u00ff\3\2\2\2\u00ffZ\3\2\2\2"+
		"\u0100\u00fe\3\2\2\2\u0101\u0105\5c\62\2\u0102\u0104\5c\62\2\u0103\u0102"+
		"\3\2\2\2\u0104\u0107\3\2\2\2\u0105\u0103\3\2\2\2\u0105\u0106\3\2\2\2\u0106"+
		"\\\3\2\2\2\u0107\u0105\3\2\2\2\u0108\u010c\5+\26\2\u0109\u010b\13\2\2"+
		"\2\u010a\u0109\3\2\2\2\u010b\u010e\3\2\2\2\u010c\u010d\3\2\2\2\u010c\u010a"+
		"\3\2\2\2\u010d\u010f\3\2\2\2\u010e\u010c\3\2\2\2\u010f\u0110\5+\26\2\u0110"+
		"^\3\2\2\2\u0111\u0113\5S*\2\u0112\u0114\13\2\2\2\u0113\u0112\3\2\2\2\u0113"+
		"\u0114\3\2\2\2\u0114\u0115\3\2\2\2\u0115\u0116\5S*\2\u0116`\3\2\2\2\u0117"+
		"\u0118\t\2\2\2\u0118b\3\2\2\2\u0119\u011a\t\3\2\2\u011ad\3\2\2\2\u011b"+
		"\u011f\5W,\2\u011c\u011e\13\2\2\2\u011d\u011c\3\2\2\2\u011e\u0121\3\2"+
		"\2\2\u011f\u0120\3\2\2\2\u011f\u011d\3\2\2\2\u0120\u0122\3\2\2\2\u0121"+
		"\u011f\3\2\2\2\u0122\u0123\7\f\2\2\u0123\u0124\3\2\2\2\u0124\u0125\b\63"+
		"\2\2\u0125f\3\2\2\2\u0126\u0127\5W,\2\u0127\u012b\5W,\2\u0128\u012a\13"+
		"\2\2\2\u0129\u0128\3\2\2\2\u012a\u012d\3\2\2\2\u012b\u012c\3\2\2\2\u012b"+
		"\u0129\3\2\2\2\u012c\u012e\3\2\2\2\u012d\u012b\3\2\2\2\u012e\u012f\5W"+
		",\2\u012f\u0130\5W,\2\u0130\u0131\3\2\2\2\u0131\u0132\b\64\2\2\u0132h"+
		"\3\2\2\2\u0133\u0135\t\4\2\2\u0134\u0133\3\2\2\2\u0135\u0136\3\2\2\2\u0136"+
		"\u0134\3\2\2\2\u0136\u0137\3\2\2\2\u0137\u0138\3\2\2\2\u0138\u0139\b\65"+
		"\2\2\u0139j\3\2\2\2\13\2\u00fc\u00fe\u0105\u010c\u0113\u011f\u012b\u0136"+
		"\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}