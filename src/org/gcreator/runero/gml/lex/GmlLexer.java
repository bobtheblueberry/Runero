package org.gcreator.runero.gml.lex;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;



/**
 * This is a useless piece of shit!
 * 
 * 
 * @author serge
 *
 */
public class GmlLexer {

    public static void main(String[] args) {
        try {
            new GmlLexer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    BullshitlessInputStream in;

    public GmlLexer() throws IOException {
        strings = new ArrayList<String>();
        words = new ArrayList<String>();

        File test = new File("test.gml");
        in = new BullshitlessInputStream(new BufferedInputStream(new FileInputStream(test)));
        int data;
        while ((data = in.read()) != -1) {
            char c = (char) data;
            parse(c);
        }
        parse('\n');
        System.out.println();
        if (isStr)
            System.out.println("Error! expected String End");
        
    }

    ArrayList<String> strings;
    ArrayList<String> words;

    // x = EXPR
    // VAR
    // OPERATOR
    // EXPR { x.x.x.x.x (100000).x.x.x.x.x.x obj_tard.x.x.x
    // obj_deep.var.x = getOBJ(obj_deep).getOBJ(var).x }
    // ( 0000 )
    // ( X * Y - 2 + X ) ... so then ( {VAR|NUMBER {OPERATOR VAR|NUMBER}? } | func(..args...) )
    //
    // ; ?

    boolean isStr = false;
    boolean isComment;// // comments
    boolean isComment2; // /* */ comments
    char lastChar = 0;
    char stringChar;
    char[] strChars;
    int strIndex;

    char[] curWord = new char[20];
    int curIndex;

    boolean expr;

    private void parse(char c) throws IOException {
        if (!expr && !isComment && !isComment2 && !isStr && c == '/') {
            lastChar = c;
            return;
        }
        // Comment type 1: //
        if (!isComment && !isStr && c == '/' && lastChar == '/')
            isComment = true;
        if (c == '\n' && isComment) {
            isComment = false;
            return;
        }
        // Comment type 2: /* */
        if (!isComment && !isComment2 && !isStr && c == '*' && lastChar == '/')
            isComment2 = true;

        if (isComment2 && lastChar == '*' && c == '/') {
            isComment2 = false;
            return;
        }
        if (isComment || isComment2) {
            lastChar = c;
            return;
        }

        // Strings
        if (isStr && c == '\\') {
            lastChar = c;
            return;
        }
        if ((!isStr && c == '\'') || (!isStr && c == '"') || (isStr && c == stringChar))
            if (lastChar != '\\') {
                isStr = !isStr;
                if (isStr) {
                    stringChar = c;
                    strChars = new char[60];
                } else {
                    stringChar = 0;
                    strings.add(new String(strChars, 0, strIndex));
                    strIndex = 0;
                }
                return;
            }

        if (isStr) {
            if (strIndex >= strChars.length) {
                strChars = Arrays.copyOf(strChars, strChars.length + 20);
            }
            strChars[strIndex++] = c;
        }

        if (c == '\n') {
            resetChars();
            lastChar = c;
            return;
        }
        if (isLetterNumber(c))
            addChar(c);
        else {
            String word = resetChars();
            if (word != null)
                statement(word, c);
            else if (c != ';')
                System.out.println("unexpected " + c);
        }
        lastChar = c;
    }

    private void statement(String type, char firstchar) throws IOException {
        if (type.equals("if"))
            ;
        else if (type.equals("else"))
            ;
        else if (type.equals("for"))
            ;
        else if (type.equals("while"))
            ;
        else if (type.equals("do"))
            ;
        else if (type.equals("repeat"))
            ;
        else if (type.equals("switch"))
            ;
        else if (type.equals("with"))
            ;
        else {
            Function func = readFunction(type);
            System.out.println(func.name + " (");
            for (Argument a : func.args) {
                System.out.println("argument " + a.expressions.size());
                for (Expression e : a.expressions)
                    System.out.println(e.type);
            }
        }

        // block {STATEMENT}
        // if, if - else, else
        // for (i=0; i<|CONDITION|; i+=1)
        // while (cond) {}
        // do {} until (cond);
        // repeat (x) {}
        // switch (expr) {
        // case EXPRESSION: STATEMENT; break;
        // default: STATEMENT; }
        // with (expr) {}
        // = expr
        // [function](args )
        // [while] true

    }

    private Function readFunction(String name) {
        // function( x , y , x) --> x,y,x)
        Function f = new Function(name);
        int bracket = 0;
        ExpressionReader r = new ExpressionReader();
        Argument curArg = new Argument();
        while (true) {
            char c = next();

            if (isLetterNumber(c) || c == '.')
                r.addChar(c);

            switch (c) {
            case '(':
                bracket++;
                if (r.ecurIndex > 0) {
                    // function....
                    Function childFunc = readFunction(r.reset());
                    curArg.expressions.add(new Expression(childFunc));
                }
                break;
            case ')':
                bracket--;
                break;
            case '\'':
            case '"':
                String s = readStr(c);
                Constant con = new Constant(s);
                curArg.expressions.add(new Expression(con));

                break;
            }
            if (c == ',' || bracket < 0) {
                if (r.ecurIndex > 0) {
                    // curArg.expressions.add(readExpr(r.reset()));
                    f.args.add(curArg);
                    System.out.println("nextarg");
                    curArg = new Argument();
                } else if (c == ',' && curArg.expressions.size() == 0)
                    throw new ParseError("Empty parameter at line " + in.line + " pos " + in.pos);
            }
            if (bracket < 0) {
                break;
            }
        }
        f.args.add(curArg);

        return f;
    }

    private Argument readExpr(char endChar) {
        Argument a = new Argument();
        char c = next();
        while (isUseless(c) || c == '\n')
            c = next();
        if (c == endChar)
            return a;
        // test for String
        if (c == '\'' || c == '\"') {
            String s = readStr(c);
            a.add(new Expression(new Constant(s)));
        }
        if (c == '-' && a.expressions.size() == 0)
            a.add(new Expression(Operator.NEGATE));
        else if (c == '+' && a.expressions.size() == 0)
            ;// useless
        if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
           a.add(readVar(c));
        } else if (c >= '0' && c <= '9' || c == '.') {
            boolean dot = c == '.';
            ExpressionReader r = new ExpressionReader();
            while (true) {
                r.addChar(c);
                
                c = next();
                if (dot && c == '.')
                    throw new ParseError("NUMBAH FUUUUUUUU");
                if (c != '.')
                    if (c < '0' || c > '9') {
                        // end of number
                        double d = Double.parseDouble(r.reset());
                        a.add(new Expression(new Constant(d)));
                        System.out.println("double " + d);
                        break;
                    }
                
            }
        }

        return a;
    }
    
    private Expression readVar(char c) {
        // variable function
        ExpressionReader r = new ExpressionReader();
        Variable curVar = null;
        Expression e = null;
        while (true) {
            r.addChar(c);
            c = next();
            if (!isUseless(c) && !isLetterNumber(c)) {
                if (c == '(')
                    e = new Expression(readFunction(r.reset()));
                else if (c == '[') {
                    Variable array = new Variable(r.reset());
                    array.isArray = true;
                    array.arrayIndex = readExpr(']');
                    curVar = array;
                    e = new Expression(array);
                } else if (c == '.') {
                    if (curVar == null)
                        throw new ParseError("Unexpected . at line " +in.line + " pos " + in.pos);
                    c = next();
                    while (isUseless(c) || c == '\n')
                        c = next();
                    curVar.child = readVar(c).variable;
                } else if (c == ';')
                    ;
                break;
            }
        }
        return e;
        
    }
    
    private String readStr(char type) {
        char[] chars = new char[20];
        int i = 0;
        char lastChar = 0;
        while (true) {
            char c = next();
            if (c == type && lastChar != '\\') {
                return new String(chars, 0, i);
            }
            boolean cond = (c == '\\' && lastChar == '\\');

            if (c != '\\' || cond) {
                if (i >= chars.length)
                    chars = Arrays.copyOf(chars, chars.length + 20);
                chars[i++] = c;
            }
            if (cond)
                lastChar = 0;
            else
                lastChar = c;
        }
    }

    private boolean isUseless(char c) {
        return !isStr && c != '\n' && (c < 33 || c > 126);
    }

    private String resetChars() {
        String s = null;
        if (curIndex > 0)
            words.add(s = new String(curWord, 0, curIndex));
        curIndex = 0;
        return s;
    }

    private void addChar(char c) {
        if (curIndex >= curWord.length)
            curWord = Arrays.copyOf(curWord, curWord.length + 10);

        curWord[curIndex++] = c;
    }

    private boolean isLetterNumber(char c) {
        return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c == '_';
    }

    private char next() {
        try {
            int c = in.read();
            if (c != -1)
                return (char) c;
        } catch (IOException exc) {
            exc.printStackTrace();
        }
        throw new ParseError(" end of stream!");
    }

    private static class ExpressionReader {
        char[] eCurWord = new char[10];
        int ecurIndex;

        void addChar(char c) {
            if (ecurIndex >= eCurWord.length)
                eCurWord = Arrays.copyOf(eCurWord, eCurWord.length + 10);

            eCurWord[ecurIndex++] = c;
        }

        String reset() {
            String s = null;
            if (ecurIndex > 0)
                s = new String(eCurWord, 0, ecurIndex);
            ecurIndex = 0;
            return s;
        }
    }
}
