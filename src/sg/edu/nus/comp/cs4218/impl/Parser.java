package sg.edu.nus.comp.cs4218.impl;

import java.util.Arrays;
import java.util.List;

import sg.edu.nus.comp.cs4218.impl.token.AbstractToken;

public class Parser {
	static final Character WHITE_SPACE = ' ';
	static final Character DOUBLE_QUOTE = '"';
	static final Character SINGLE_QUOTE = '\'';
	static final Character BACK_QUOTE = '`';
	static final Character SEMICOLON = ';';
	static final Character IN_STREAM = '<';
	static final Character OUT_STREAM = '>';
	
	static final List<Character> SPECIALS = Arrays.asList(SEMICOLON, IN_STREAM, OUT_STREAM);
	
	public Parser() {
		
	}
	
	public static List<AbstractToken> tokenize(String input) {
		return null;
	}
	
	
	
	public static Boolean isInStream(Character character) {
		return character == IN_STREAM;
	}
	
	public static Boolean isOutStream(Character character) {
		return character == OUT_STREAM;
	}
	
	public static Boolean isQuote(Character character) {
		return character.equals(SINGLE_QUOTE) || character.equals(DOUBLE_QUOTE) || character.equals(BACK_QUOTE);
 	}
	
	public static Boolean isNormaCharacter(Character character) {
		return !isQuote(character) && !SPECIALS.contains(character) && !character.equals(WHITE_SPACE);
	}
}
