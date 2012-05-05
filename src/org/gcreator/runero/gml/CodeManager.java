package org.gcreator.runero.gml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.gcreator.runero.gml.exec.ExprArgument;
import org.gcreator.runero.gml.exec.Statement;
import org.gcreator.runero.gml.exec.VariableRef;
import org.gcreator.runero.gml.lex.GmlCompiler;
import org.gcreator.runero.gml.lex.GmlLexer;
import org.gcreator.runero.gml.lex.GmlTokenizer;
import org.gcreator.runero.gml.lex.GmlLexer.CharData;
import org.gcreator.runero.gml.lex.TokenWord;

public class CodeManager {

    private CodeManager() {
    }
    public static ArrayList<Statement> getCode(String code) {
        try {
            CharData[] data = GmlLexer.process(new StringInputStream(code));
            TokenWord[] tw = GmlTokenizer.process(data);
            return GmlCompiler.interpretGml(tw);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ExprArgument getArgument(String code) {
        try {
            CharData[] data = GmlLexer.process(new StringInputStream(code));
            TokenWord[] tw = GmlTokenizer.process(data);
            GmlCompiler c = new GmlCompiler(tw);
            return c.getArgument();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static VariableRef getVariable(String snippet) {
        try {
            CharData[] data = GmlLexer.process(new StringInputStream(snippet));
            TokenWord[] tw = GmlTokenizer.process(data);
            GmlCompiler c = new GmlCompiler(tw);
            return c.getVariable();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class StringInputStream extends InputStream {

        int index;
        String data;

        public StringInputStream(String s) {
            this.data = s;
        }

        @Override
        public int read() throws IOException {
            if (index >= data.length())
                return -1;
            return data.charAt(index++);
        }

    }
}
