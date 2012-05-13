package org.gcreator.runero.gml.exec;

import java.util.ArrayList;

import org.gcreator.runero.gml.Constant;
import org.gcreator.runero.inst.Instance;

public class Context {

    boolean hasInstance;
    public Instance instance;
    public Instance other;
    public Constant returnVal;

    public Context(Instance instance, Instance other) {
        hasInstance = instance != null;
        this.instance = instance;
        this.other = other;
    }

    boolean good;

    public void setGood(boolean b) {
        good = b;
    }

    public boolean isGood() {
        return true;
    }
    
    public void breakLoop() {
        //TODO: This
    }
    
    public void continueLoop() {
        //TODO: this
    }
    
    public void returnStatement(Constant val) {
        //TODO: this
        good = false;
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
