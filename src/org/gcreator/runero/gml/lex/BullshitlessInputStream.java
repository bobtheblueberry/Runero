package org.gcreator.runero.gml.lex;

import java.io.IOException;
import java.io.InputStream;

/**
 * Removes extra whitespace
 * 
 * 
 * 
 * @author serge
 *
 */
public class BullshitlessInputStream extends InputStream {

    InputStream in;
    
    public BullshitlessInputStream(InputStream in) {
        this.in = in;
    }
    
    boolean bullshit;

    int line = 1;
    int pos = 1;
    
    int prev;
    char str;
    boolean string;
    
    @Override
    public int read() throws IOException {
        int n = in.read();
        pos++;
        if (n == '\n') {
            line ++;
            pos = 0;
        }

        if (string && n == str && prev != '\\') {
            string = false;
            prev = n;
            return n;
        }
        
        if (!string && (n == '\'' || n == '\"')) {
            string = true;
            str = (char)n;
        }
        
        if (!string && isBullshit(n)) {
            if (bullshit)
                n = noBullshit();
            else
                bullshit = true;
        } else {
            bullshit = false;
        }
        
        prev = n;
        return n;
    }

    private int noBullshit() throws IOException {
        int n;
        while ((n = in.read()) != -1) {
            if (!isBullshit(n))
                break;
        }
        return n;
    }
    
    private boolean isBullshit(int n) {
        return n == ' ' || n == '\t' || n == '\n';
    }
}
