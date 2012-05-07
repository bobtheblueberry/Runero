package org.gcreator.runero.gml.lib;

import org.gcreator.runero.RuneroGame;
import org.gcreator.runero.inst.RoomInstance;

public abstract class RoomLibrary {

    // TODO: Room persistence
    // TODO: Room transitions
    // TODO: Object persistence
    // TODO: Room End events

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
