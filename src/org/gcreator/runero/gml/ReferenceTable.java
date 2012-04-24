package org.gcreator.runero.gml;

import java.util.ArrayList;
import java.util.Hashtable;

public class ReferenceTable<T> {

    private ArrayList<SubTable> table;

    public ReferenceTable() {
        table = new ArrayList<SubTable>();
    }

    public void put(String name, T val) {
        char c = name.charAt(0);
        SubTable t = getTable(c);
        if (t == null) {
            t = new SubTable(c);
            table.add(t);
        }
        t.values.put(name, val);
    }

    public T get(String name) {
        if (name == null)
            return null;
        char c = name.charAt(0);
        SubTable t = getTable(c);
        if (t == null)  {
            return null;
        }
        return t.values.get(name);
    }

    private SubTable getTable(char c) {
        for (SubTable t : table)
            if (t.type == c)
                return t;
        return null;
    }

    class SubTable {
        char type;
        private Hashtable<String, T> values;

        public SubTable(char type) {
            this.type = type;
            values = new Hashtable<String, T>();
        }
    }
}
