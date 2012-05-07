package org.gcreator.runero.gml.lib;

import org.gcreator.runero.RuneroGame;
import org.gcreator.runero.inst.RoomInstance;

public abstract class RoomLibrary {

    // TODO: Room persistence
    // TODO: Room transitions
    // TODO: Object persistence
    // TODO: Room End events
    /*
     0 = no effect
    1 = Create from left
    2 = Create from right
    3 = Create from top
    4 = Create from bottom
    5 = Create from center
    6 = Shift from left
    7 = Shift from right
    8 = Shift from top
    9 = Shift from bottom
    10 = Interlaced from left
    11 = Interlaced from right
    12 = Interlaced from top
    13 = Interlaced from bottom
    14 = Push from left
    15 = Push from right
    16 = Push from top
    17 = Push from bottom
    18 = Rotate to the left
    19 = Rotate to the right
    20 = Blend the rooms
    21 = Fade out and in
    */

    public static void room_goto_next(int transition) {
        RuneroGame.room = new RoomInstance(RuneroGame.game, RuneroGame.game.getRoom(RuneroGame.game.room_index++));
        RuneroGame.room.init(false);
    }

    public static void room_goto_previous(int transition) {
        RuneroGame.room = new RoomInstance(RuneroGame.game, RuneroGame.game.getRoom(RuneroGame.game.room_index--));
        RuneroGame.room.init(false);
    }

    public static void room_restart(int transition) {
        RuneroGame.room = new RoomInstance(RuneroGame.game, RuneroGame.room.room);
        RuneroGame.room.init(false);
    }

    public static void room_goto(int room, int transition) {
        RuneroGame.room = new RoomInstance(RuneroGame.game, RuneroGame.game.getRoom(room));
        RuneroGame.room.init(false);
    }

}
