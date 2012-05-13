package org.gcreator.runero.gml.lib;

import org.gcreator.runero.RuneroCollision;
import org.gcreator.runero.RuneroGame;
import org.gcreator.runero.Runner;
import org.gcreator.runero.inst.Instance;
import org.gcreator.runero.inst.ObjectGroup;
import org.gcreator.runero.res.GameObject;

public abstract class CollisionLibrary {

    public static boolean place_meeting(Instance inst, double x, double y, int obj) {
        GameObject o = RuneroGame.game.getObject(obj);
        if (o == null) {
            Runner.Error("Unknown object with id " + obj);
            return false;
        }
        ObjectGroup g = RuneroGame.room.getObjectGroup(obj);
        if (g == null)
            return false;
        for (Instance i : g.instances) {
            if (RuneroCollision.checkCollision(inst, i, true))
                return true;
        }
        return false;
    }
}
