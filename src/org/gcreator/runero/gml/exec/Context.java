package org.gcreator.runero.gml.exec;

import java.util.ArrayList;

import org.gcreator.runero.gml.Constant;
import org.gcreator.runero.inst.Instance;

public class Context {

    boolean hasInstance;
    public Instance instance;
    public Instance other;
    public Constant returnVal;
    public Scope scope;
    
    public static final int BREAK = 1;
    public static final int CONTINUE = 2;
    public static final int RETURN = 3;

    public Context(Instance instance, Instance other) {
        hasInstance = instance != null;
        this.instance = instance;
        this.other = other;
        this.scope = new Scope();
    }

    
    public void returnStatement(Constant val) {
        returnVal = val;
    }
    
    public void execute(ArrayList<Statement> code) {
        //TODO: scope
        for (Statement s : code) 
            s.execute(this);
    }
    
    public void with (Instance inst, ArrayList<Statement> code) {
        Instance reali = instance;
        Instance realo = other;
        instance = inst;
        other = reali;
        execute(code);
        instance = reali;
        other = realo;
    }
}
