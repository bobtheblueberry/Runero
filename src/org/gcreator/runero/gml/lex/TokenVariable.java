package org.gcreator.runero.gml.lex;

import java.util.ArrayList;

public class TokenVariable extends TokenWord {

    public ArrayList<TokenVariableSub> variables;

    protected TokenVariable(TokenWord t) {
        super(t);
        variables = new ArrayList<TokenVariableSub>();
    }

    public void add(TokenVariableSub t) {
        variables.add(t);
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < variables.size(); i++) {
            s += variables.get(i);
            if (i + 1 < variables.size())
                s += ".";
        }
        return s;
    }

    public static class TokenVariableSub {
        String name;
        public boolean isArray;
        public TokenGroup arrayIndex;
        public boolean isExpression;
        public TokenGroup expression;

        public String toString() {
            if (name == null)
                return "(" + expression + ")";
            else
                return name;
        }
    }
}
