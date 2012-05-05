package org.gcreator.runero.res;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.gcreator.runero.gml.CodeManager;
import org.gcreator.runero.gml.exec.Code;
import org.gcreator.runero.gml.exec.Statement;

public class CodeRes {

    String src;
    Code code;

    public CodeRes(String src)
        {
            this.src = src;
            ArrayList<Statement> c = CodeManager.getCode(src);
            Statement[] s = new Statement[c.size()];
            s = c.toArray(s);
            code = new Code(s);
        }

    private CodeRes()
        {
        }

    public Code getCode() {
        return code;
    }

    public static CodeRes load(File dir, String name) throws IOException {
        CodeRes c = new CodeRes();
        File ccodeFile = new File(dir, name);
        BufferedReader cr = new BufferedReader(new FileReader(ccodeFile));
        StringBuilder sb = new StringBuilder();
        String cl;
        while ((cl = cr.readLine()) != null) {
            sb.append(cl);
            sb.append('\n');
        }
        cr.close();

        c.src = cr.toString();
        return c;
    }

}
