package org.gcreator.runero.gml.exec;

import org.gcreator.runero.inst.Instance;

/**
 * 
 * This class executes some code or script
 * 
 * @author serge
 *
 */
public class Code {

    public Code(Statement[] code) {
        this.code = code;
    }
    
    Statement[] code;
    public void execute(Instance instance, Instance other) {
        Context c = new Context(instance, other);
        for (Statement s : code) {
            //TODO: break, continue
            s.execute(c); //TODO: scope
        }
    }
    
}
