package sg.edu.nus.comp.cs4218.impl;

import java.util.List;
import java.util.ArrayList;

import sg.edu.nus.comp.cs4218.impl.token.AbstractToken;
import sg.edu.nus.comp.cs4218.Utility;

public class Parser {
	
	public Parser() {}
	
	public static List<AbstractToken> tokenize(String input) {
		List<AbstractToken> tokens = new ArrayList<AbstractToken>();
		AbstractToken currentToken = null;
		
		for (int i = 0; i < input.length(); i++) {
			if (currentToken == null) {
				currentToken = Utility.generateToken(input, i);
			} else if (!currentToken.appendNext()) {
				tokens.add(currentToken);
				currentToken = Utility.generateToken(input, i);
			}
		}
		
		if (currentToken != null) {
			tokens.add(currentToken);
		}
		
		return tokens;
	}
	
}
