package org.gcreator.runero.gml.lex;

import java.util.LinkedList;

public class TokenVariable extends TokenWord {

    private LinkedList<String> variables;// x.y.a.b.c.y

    protected TokenVariable(TokenWord t) {
        super(t);
    }

    public void add(String name) {
        if (variables == null)
            variables = new LinkedList<String>();
        variables.add(name);
    }

    public String[] getVariables() {
        return variables.toArray(new String[variables.size()]);
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
