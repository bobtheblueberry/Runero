package org.gcreator.runero.gml.exec;

/**
 * GML has 2 basic statements
 * assignments: var = x
 * functions: func(1,2,3)
 *
 * also: 
 * var x,y z
 * globalvar x y, z
 * 
 * and special things:
 * 
 * for, while, break, etc.
 * 
 * @author serge
 *
 */
public interface Statement {

    /**
     * 
     * @param context
     * @return 0 for normal statement, Context.BREAK for break, etc.
     */
    public int execute(Context context);
}
