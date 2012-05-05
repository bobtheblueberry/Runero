package org.gcreator.runero.res;

import java.util.ArrayList;

import org.gcreator.runero.gml.CodeManager;
import org.gcreator.runero.gml.exec.Code;
import org.gcreator.runero.gml.exec.Statement;

public class CodeRes {

    String src;
    Code code;

    public static CodeRes load(String src) {
        if (src == null || src.length() == 0)
            return null;
        return new CodeRes(src);
    }

    public Code getCode() {
        return code;
    }

    private CodeRes(String src)
        {
            this.src = src;
            ArrayList<Statement> c = CodeManager.getCode(src);
            Statement[] s = new Statement[c.size()];
            s = c.toArray(s);
            code = new Code(s);
        }
}
