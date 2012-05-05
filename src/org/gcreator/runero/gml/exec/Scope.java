package org.gcreator.runero.gml.exec;

import org.gcreator.runero.gml.ReferenceTable;
import org.gcreator.runero.gml.VariableVal;

public class Scope {
    public ReferenceTable<VariableVal> vars;

    public Scope()
        {
            vars = new ReferenceTable<VariableVal>();
        }
}
