package org.gcreator.runero.gml.lex;

import static org.gcreator.runero.gml.lex.Token.NUMBER;

import org.gcreator.runero.gml.lex.GmlLexer.CharData;

class TokenWord {
    boolean hasData;
    boolean hasNumber;
    Token token;
    CharData data;
    double number;

    public TokenWord(double n) {
        this.hasNumber = true;
        this.number = n;
        this.token = NUMBER;
    }

    public TokenWord(Token t) {
        this.token = t;
    }

    public TokenWord(CharData data, Token t) {
        this.token = t;
        this.hasData = true;
        this.data = data;
    }

    protected TokenWord(TokenWord t) {
        this.hasData = t.hasData;
        this.hasNumber = t.hasNumber;
        this.token = t.token;
        this.data = t.data;
        this.number = t.number;
    }
    
    public String toString() {
        String s = "" + token;
        if (hasNumber)
            s += " " + number;
        if (hasData)
            s += " " + data.getData();
        return s;
    }
}