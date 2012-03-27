package org.gcreator.runero.gml.lex;

import java.util.LinkedList;

public class TokenVariable extends TokenWord {

    private LinkedList<TokenWord> variables;// TokenWordPair for arrays
    
    protected TokenVariable(TokenWord t) {
        super(t);
    }

    public void add(TokenWord t) {
        if (variables == null)
            variables = new LinkedList<TokenWord>();
        variables.add(t);
    }

    public TokenWord[] getVariables() {
        return (variables == null) ? null : variables.toArray(new TokenWord[variables.size()]);
    }

    public String toString() {
        String s = super.toString();
        if (variables == null)
            return s;
        s += ".";
        for (int i = 0; i < variables.size(); i++) {
            s += variables.get(i);
            if (i + 1 < variables.size())
                s += ".";
        }
        return s;
    }
}
