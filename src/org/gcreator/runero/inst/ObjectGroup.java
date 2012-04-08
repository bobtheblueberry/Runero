package org.gcreator.runero.inst;

import java.util.ArrayList;

import org.gcreator.runero.res.GameObject;

/**
 * Room instances are stored by object type
 * 
 * @author serge
 *
 */
public class ObjectGroup implements Comparable<ObjectGroup> {

    public GameObject obj;
    public ArrayList<Instance> instances;
    
    public ObjectGroup(GameObject obj) {
        this.obj = obj;
        instances = new ArrayList<Instance>();
    }

    public void add(Instance i) {
        instances.add(i);
    }
    
    @Override
    public int compareTo(ObjectGroup o) {
        // Do it backwards (highest to lowest)
        return Integer.compare(o.obj.depth, obj.depth);
    }
}
