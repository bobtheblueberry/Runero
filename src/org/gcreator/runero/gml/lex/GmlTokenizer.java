package org.gcreator.runero.gml.lex;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.gcreator.runero.gml.lex.GmlLexer.CharData;
import static org.gcreator.runero.gml.lex.Token.*;

/**
 * Awesome GML Lexer/Parser that is so great
 * 
 * 
 * @author serge
 *
 */
public class GmlTokenizer {

    public static void main(String[] args) {
        try {
            new GmlTokenizer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Hashtable<String, Token> tokens;
    static {
        tokens = new Hashtable<String, Token>();
        tokens.put(",", COMMA);
        tokens.put("{", BEGIN);
        tokens.put("begin", BEGIN);
        tokens.put("}", END);
        tokens.put("end", END);
        // Keywords that can only be put in one way anyway
        tokens.put("if", IF);
        tokens.put("then", THEN);
        tokens.put("else", ELSE);
        tokens.put("while", WHILE);
        tokens.put("do", DO);
        tokens.put("for", FOR);
        tokens.put("with", WITH);
        tokens.put("until", UNTIL);
        tokens.put("repeat", REPEAT);
        tokens.put("exit", EXIT);
        tokens.put("return", RETURN);
        tokens.put("break", BREAK);
        tokens.put("continue", CONTINUE);
        tokens.put("switch", SWITCH);
        tokens.put("case", CASE);
        tokens.put("default", DEFAULT);
        tokens.put("var", VAR);
        tokens.put("globalvar", GLOBALVAR);

        tokens.put(".", DOT);
        tokens.put(";", SEMICOLON);
        tokens.put("(", PAR_OPEN);
        tokens.put(")", PAR_CLOSE);
        tokens.put("[", BRACKET_OPEN);
        tokens.put("]", BRACKET_CLOSE);
        tokens.put(",", COMMA);
        tokens.put("-", MINUS);
        tokens.put("+", PLUS);
        tokens.put(":=", ASSIGN_EQUALS);
        tokens.put("=", EQUALS);
        tokens.put("+=", PLUS_EQUALS);
        tokens.put("-=", MINUS_EQUALS);
        tokens.put("*=", MULTIPLY_EQUALS);
        tokens.put("/=", DIVIDE_EQUALS);
        tokens.put("|=", OR_EQUALS);
        tokens.put("&=", AND_EQUALS);
        tokens.put("^=", XOR_EQUALS);
        tokens.put("&&", AND);
        tokens.put("and", AND);
        tokens.put("||", OR);
        tokens.put("or", OR);
        tokens.put("^^", XOR);
        tokens.put("xor", XOR);
        tokens.put("!", NOT);
        tokens.put("not", NOT);
        tokens.put("!=", NOT_EQUALS);
        tokens.put("<>", NOT_EQUALS);
        tokens.put("div", INT_DIVIDE);
        tokens.put("/", DIV);
        tokens.put("*", MULTIPLY);
        tokens.put("%", MODULO);
        tokens.put("mod", MODULO);
        tokens.put("&", BITW_AND);
        tokens.put("|", BITW_OR);
        tokens.put("^", BITW_XOR);
        tokens.put("~", BITW_INVERT);
        tokens.put("<<", BITW_RIGHT);
        tokens.put(">>", BITW_LEFT);
        tokens.put(">", GREATER);
        tokens.put(">=", GREATER_EQUAL);
        tokens.put("<", LESS);
        tokens.put("<=", LESS_EQUAL);
        tokens.put("==", COMPARATOR_EQUALS);
    }

    // The following line is valid GML
    // x = y b = 2 n = 5 g = 2 var h i j ; a += b for (i = 0 i < 55 i += 1) x = 1 do y =x div 2 until i < 2 y = 2 x =
    // show_message('bbbb')

    // So, syntax rules must follow
    // var x,y,z;
    // var x y z;
    // x = 2 y = 2 b = 2
    // x = 2; y = 2; b = 2
    // ;;;;;;;;;;;;;;;;
    // for ( x = 3 x < 54 x +=1 ) x = 2
    // do x = 2 until c > 2
    // f = show_message("ZZZzzz") y -= 2
    // variables start with _A-Za-z (not a number)
    // {{{{ }}}}
    // $0A0B0d -- hex numbers
    // FFff[4]
    // FFff

    // So WORD
    // --- special (var, while, do, for, etc)
    // -- [ASSIGNMENT] [EXPR]
    // -- ( ARGS... )
    // -- .othervar.var.var [ASSIGNMENT] EXPR
    // ASSIGNMENT = [EXPR] { func(ARGS...), instance.x + 2 - x, "BOB" + 2, 12.0, $0FFAA}

    boolean ignoreComments = true;
    ArrayList<TokenWord> tokenList;

    public GmlTokenizer() throws IOException {

        File test = new File("test.gml");
        CharData[] data = GmlLexer.process(new BufferedInputStream(new FileInputStream(test)));

        if (data == null || data.length == 0) {
            System.out.println("No data");
            return;
        }

        System.out.println("================= PHASE ONE =================");
        for (CharData c : data) {
            String line;
            String s2 = "";
            if (c.isString) {
                line = ANSI.BLUE + "\"";
                s2 = "\"";
            } else if (c.isComment) {
                if (ignoreComments)
                    continue;
                else
                    line = "" + ANSI.GREEN;
            } else if (c.isWord)
                line = "" + ANSI.MAGENTA;
            else if (c.isNumber)
                line = "" + ANSI.YELLOW;
            else if (c.isWhitespace)
                line = "" + ANSI.BLACK;
            else
                line = "" + ANSI.CYAN;
            String str = c.getData();
            if (!c.isString && str.equals(""))
                System.out.print(ANSI.RED + "$");
            else
                System.out.print(line + c.getData() + s2);
        }
        System.out.println(ANSI.BLACK);

        System.out.println("================= PHASE TWO =================");
        tokenList = new ArrayList<TokenWord>();
        // Create a string of tokens from the data
        for (CharData c : data) {
            debugLine = c.sline;
            String s = c.getData();
            if (c.isNothing()) {
                // Should be a { * & && or something
                if (c.datalen == 0 && !c.isString) {
                    error("Unexpected nothing ??? ");
                }
                if (c.datalen == 1) {
                    Token t = tokens.get(s);
                    if (t == null)
                        error("Unexpected " + s + " !");
                    else
                        tokenList.add(new TokenWord(t));
                } else {
                    // There could be multiple operators in a single chunk
                    // so we have to look for them
                    int start = 0;
                    for (int i = c.datalen; i > 0; i--) {
                        String s2 = s.substring(start, i);
                        Token t = tokens.get(s2);
                        if (t != null) {
                            start = i;
                            i = c.datalen + 1; // will i-- later
                            tokenList.add(new TokenWord(t));
                        }
                        if (start >= c.datalen)
                            break;
                    }
                }
            } else if (c.isWord) {
                // begin, end, delphi shizz
                Token t = tokens.get(s);
                if (t != null) {
                    System.out.println(ANSI.CYAN + t + ANSI.BLACK);
                    tokenList.add(new TokenWord(t));
                } else {
                    // just a regular old word... or function, maybe
                    tokenList.add(new TokenWord(c, WORD));
                }
            } else if (c.isString) {
                tokenList.add(new TokenWord(c, Token.STRING));
            } else if (c.isNumber) {
                // try to parse the number and if it's invalid then error
                double number;
                try {
                    if (c.isHexNumber)
                        number = Integer.parseInt(s.substring(1), 16);
                    else
                        number = Double.parseDouble(s);
                } catch (NumberFormatException exc) {
                    error("Invalid number " + s);
                    continue;
                } catch (Exception e) {
                    error("Invalid number " + s);
                    continue;
                }
                tokenList.add(new TokenWord(number));
            }
            // ignore comments and whitespace =D
        }
        for (TokenWord w : tokenList)
            System.out.println(w);
        System.out.println("================ PHASE THREE ================");
        for (int i = 0; i < tokenList.size(); i++) {
            boolean end = i + 1 >= tokenList.size();
            TokenWord w = tokenList.get(i);

            if (w.token == SEMICOLON)
                continue;

            if (w.token == WORD) {
                if (end) {
                    // wtf is this shit??
                    error("unexpected end of data");
                    break;
                }
                TokenWord next = tokenList.get(i);
                if (next.token == PAR_OPEN) {
                    // function!
                    // i = some shit
                    continue;
                } else if (next.token == BRACKET_OPEN) {
                    // Array.. SHIT!!!
                } else if (next.token == ASSIGN_EQUALS || next.token == EQUALS) { // := =

                } else if (next.token == PLUS_EQUALS) { // +=

                } else if (next.token == MINUS_EQUALS) { // -=

                } else if (next.token == DIVIDE_EQUALS) { // /=

                } else if (next.token == AND_EQUALS) { // &=

                } else if (next.token == OR_EQUALS) { // |=

                } else if (next.token == XOR_EQUALS) { // ^=

                } else {
                    error("Unexpected " + next.token);
                    break;
                }
                continue;
            } else if (w.token == BEGIN) {
                // useless...
            } else if (w.token == END) {
                // also useless..
            } else {
                error("unexpected " + w);
                break;
            }
        }
    }

    int debugPos;
    int debugLine;

    private void error(String msg) {
        System.out.println(ANSI.RED + "Error in parser line " + debugLine + " position " + (debugPos + 1) + ": " + msg
                + ANSI.BLACK);
    }

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

        public String toString() {
            String s = "" + token;
            if (hasNumber)
                s += " " + number;
            if (hasData)
                s += " " + data.getData();
            return s;
        }
    }

    final class ANSI {

        public static final String SANE = "\u001B[0m";

        public static final String HIGH_INTENSITY = "\u001B[1m";
        public static final String LOW_INTESITY = "\u001B[2m";

        public static final String ITALIC = "\u001B[3m";
        public static final String UNDERLINE = "\u001B[4m";
        public static final String BLINK = "\u001B[5m";
        public static final String RAPID_BLINK = "\u001B[6m";
        public static final String REVERSE_VIDEO = "\u001B[7m";
        public static final String INVISIBLE_TEXT = "\u001B[8m";

        public static final String BLACK = "\u001B[30m";
        public static final String RED = "\u001B[31m";
        public static final String GREEN = "\u001B[32m";
        public static final String YELLOW = "\u001B[33m";
        public static final String BLUE = "\u001B[34m";
        public static final String MAGENTA = "\u001B[35m";
        public static final String CYAN = "\u001B[36m";
        public static final String WHITE = "\u001B[37m";

        public static final String BACKGROUND_BLACK = "\u001B[40m";
        public static final String BACKGROUND_RED = "\u001B[41m";
        public static final String BACKGROUND_GREEN = "\u001B[42m";
        public static final String BACKGROUND_YELLOW = "\u001B[43m";
        public static final String BACKGROUND_BLUE = "\u001B[44m";
        public static final String BACKGROUND_MAGENTA = "\u001B[45m";
        public static final String BACKGROUND_CYAN = "\u001B[46m";
        public static final String BACKGROUND_WHITE = "\u001B[47m";

        private ANSI() {
        } // disable automatic constructor

    }
}