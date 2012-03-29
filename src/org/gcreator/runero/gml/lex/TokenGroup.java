package org.gcreator.runero.gml.lex;

import java.util.LinkedList;

/**
 * Used for ( x + y - z) and array[12+2/(9-2), 444] = 2 etc.
 * @author serge
 *
 */
public class TokenGroup extends TokenWord {
    public TokenGroup(Token t) {
        super(t);
        tokens = new LinkedList<TokenWord>();
    }

    LinkedList<TokenWord> tokens;

    public String toString() {
        if (tokens.size() == 0)
            return "<Empty Group>";
        String s = "( ";
        for (TokenWord w : tokens)
            s += w + " ";
        s += ")";
        return s;
    }
}