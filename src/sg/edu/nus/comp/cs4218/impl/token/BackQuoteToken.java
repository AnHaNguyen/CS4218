package sg.edu.nus.comp.cs4218.impl.token;

import java.io.ByteArrayOutputStream;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;

public class BackQuoteToken extends AbstractToken {
	
	public BackQuoteToken(String parent, int begin) {
		super(parent, begin);
	}
	
	public BackQuoteToken(String parent, int begin, int end) {
		super(parent, begin, end);
	}
	

	@Override
	public boolean appendNext() {
		if (end >= parent.length() - 1) {
			return false;
		}

		if (end > begin && parent.charAt(begin) == '`'
				&& parent.charAt(end) == '`') {
			return false;
		} else {
			end++;
			return true;
		}
	}

	@Override
	public TokenType getType() {
		return TokenType.BACK_QUOTES;
	}

	@Override
	public String value() throws ShellException, AbstractApplicationException {
		checkValid();
		
		//String cmd = parent.substring(begin + 1, end);
		// run backquote command here
		ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
		return byteOutStream.toString();
	}

	@Override
	public void checkValid() throws ShellException {
		if (end > begin && parent.charAt(begin) == '`'
				&& parent.charAt(end) == '`') {
			return;
		}
		
		throw new ShellException("Quote does not match");
	}
}
