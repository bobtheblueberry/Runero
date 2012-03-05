package org.gcreator.runero.res;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Code {

    String code;

    public Code(String code) {
        this.code = code;
    }
    
    private Code() {
    }
    

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    public static Code load(File dir, String name) throws IOException {
        Code c = new Code();
        File ccodeFile = new File(dir, name);
        BufferedReader cr = new BufferedReader(new FileReader(ccodeFile));
        StringBuilder sb = new StringBuilder();
        String cl;
        while ((cl = cr.readLine()) != null) {
            sb.append(cl);
            sb.append('\n');
        }
        cr.close();
        
        c.code = cr.toString();
        return c;
    }

}
