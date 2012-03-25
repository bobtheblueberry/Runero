package org.gcreator.runero.gml.exec;

import org.gcreator.runero.inst.Instance;

public class Context {

    boolean hasInstance;
    public Instance instance;
    public Instance other;

    public Context(Instance instance, Instance other) {
        hasInstance = instance != null;
        this.instance = instance;
        this.other = other;
    }

    public Context() {
        this(null, null);
    }

    boolean good;

    public void setGood(boolean b) {
        good = b;
    }

    public boolean isGood() {
        return true;
    }
}
