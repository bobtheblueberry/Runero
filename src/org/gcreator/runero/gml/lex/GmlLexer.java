package org.gcreator.runero.gml.lex;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Reads code and chunks comments and strings and numbers, whitespace etc.
 * 
 * 
 * @author serge
 *
 */
public class GmlLexer {

    private int line = 1;
    private LinkedList<CharData> data;
    private CharData curData;
    private InputStream in;
    private int pos;

    public static CharData[] process(InputStream in) throws IOException {
        GmlLexer p = new GmlLexer(in);
        return p.data.toArray(new CharData[p.data.size()]);
    }

    private GmlLexer(InputStream in) throws IOException {
        this.in = in;
        data = new LinkedList<GmlLexer.CharData>();
        while (true) {
            char c = next();
            if (c == 0)
                return;
            parse(c);
        }
    }

    private char next() throws IOException {
        int data = in.read();
        pos++;
        if (data == -1)
            return 0;
        char c = (char) data;
        if (c == '\n')
            line++;
        if (c == '\r')
            return next();
        return c;

    }

    boolean isStr;
    boolean isComment;// // comments
    boolean isComment2; // /* */ comments
    boolean isWord;
    boolean isNumber;
    char stringChar;
    int cb;

    private void parse(char c) throws IOException {
        if (c == 0)
            return;

        if (!isWord && !isNumber && !isComment && !isComment2 && !isStr && c == '/') {
            char next = next();
            if (next == '/')
                isComment = true;
            else if (next == '*')
                isComment2 = true;

            if (isComment || isComment2) {
                block();
                curData.isComment = true;
                add(c);
                add(next);

            } else {
                add(c);
                parse(next);
            }
            return;

        }
        if (c == '\n' && isComment) {
            add(c);
            isComment = false;
            curData = null;
            return;
        }

        if (isComment2 && c == '*') {
            char next = next();
            if (next == '/') {
                add(c);
                add(next);
                isComment2 = false;
                curData = null;

            } else {
                add(c);
                parse(next);
            }
            return;
        }

        if (isComment || isComment2) {
            add(c);
            return;
        }
        if ((!isStr && c == '\'') || (!isStr && c == '"') || (isStr && c == stringChar)) {
            isStr = !isStr;
            if (isStr) {
                block();
                curData.isString = true;
            } else
                curData = null;
            stringChar = c;
            return;
        }

        // Seperate into words and numbers
        if (!isComment && !isComment2 && !isStr && !isWord && !isNumber
                && ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c == '_')) {
            block();
            curData.isWord = true;
            isWord = true;
            add(c);
            return;
        }
        // end of word
        if (curData != null && isWord && !isLetterNumber(c)) {
            curData = null;
            isWord = false;
            add(c);
            return;
        }

        // number
        boolean cond = (c == '$' || (c >= '0' && c <= '9'));
        char next = 0;
        if (c == '.' && !isStr && !isWord && !isNumber && !cond) {
            next = next();
            if (next >= '0' && next <= '9')
                cond = true;
        }
            
        if (!isStr && !isWord && !isNumber && cond) {
            block();
            curData.isNumber = true;
            if (c == '$')
                curData.isHexNumber = true;
            isNumber = true;
            add(c);
            if (next > 0) parse(next);
            return;
        }
        // end of number
        if (curData != null && isNumber && !((c >= '0' && c <= '9') || (!curData.isHexNumber && c == '.'))
                && !(curData.isHexNumber ? ((c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f')) : false)) {
            curData = null;
            isNumber = false;
            add(c, true);
            return;
        }

        add(c);
        if (next > 0) parse(next);
    }

    private boolean isUseless(char c) {
        return (c == ' ' || c == '\n' || c == '\t' || c == '\r');
    }

    private boolean isLetterNumber(char c) {
        return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c == '_';
    }

    public class CharData {
        int sline;
        int position; // position in file/stream/whatever
        int datalen;
        char[] cdata;
        boolean isString;
        boolean isComment;
        boolean isWord; // words ( variable name, etc.)
        boolean isNumber; // number constant
        boolean isHexNumber; // $00FFAA
        boolean isWhitespace;

        private CharData() {
            cdata = new char[100];
            sline = line;
            this.position = pos;
        }

        public String getData() {
            return new String(cdata, 0, datalen);
        }

        public boolean isNothing() {
            return !isString && !isComment && !isWord && !isNumber && !isWhitespace;
        }
    }

    private void block() {
        curData = new CharData();
        data.add(curData);
    }
    
    private void add(char c) {
        add(c, false);
    }
    
    private void add(char c, boolean force) {
        if (!isStr && !force && c == '\n' && curData == null) // avoid useless code blocks
            return;
        if (!isComment && !isComment2 && !isStr && !isWord && !isNumber) {
            if (isUseless(c)) { // deal with useless/whitespace
                if (curData == null)
                    block();
                else if (!curData.isWhitespace)
                    block();
                curData.isWhitespace = true;
            } else if (curData != null && curData.isWhitespace)
                block();
        }
        if (curData == null)
            block();
        if (curData.datalen + 1 == curData.cdata.length)
            curData.cdata = Arrays.copyOf(curData.cdata, curData.cdata.length + 20);
        curData.cdata[curData.datalen++] = c;
    }
}
