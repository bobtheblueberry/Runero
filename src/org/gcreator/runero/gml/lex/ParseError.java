package org.gcreator.runero.gml.lex;

public class ParseError extends RuntimeException {
    private static final long serialVersionUID = -1858126923693472004L;
    
    public ParseError() {}
    
    public ParseError(String s) {
        super(s);
    }

}
