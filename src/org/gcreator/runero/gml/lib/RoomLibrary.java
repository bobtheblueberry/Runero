package org.gcreator.runero.gml.lib;

import org.gcreator.runero.RuneroGame;
import org.gcreator.runero.Runner;
import org.gcreator.runero.inst.RoomInstance;
import org.gcreator.runero.res.GameRoom;

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
        int n = RuneroGame.room.room.orderIndex + 1;
        if (n >= RuneroGame.game.roomOrder.length) {
            Runner.Error("No next room!");
            return;
        }
        GameRoom r = RuneroGame.game.getRoom(RuneroGame.game.roomOrder[n]);
        RuneroGame.game.room_index = r.getId();
        RuneroGame.room = new RoomInstance(RuneroGame.game, r);
        RuneroGame.room.init(false);
    }

    public static void room_goto_previous(int transition) {
        int n = RuneroGame.room.room.orderIndex - 1;
        if (n < 0) {
            Runner.Error("No previous room!");
            return;
        }
        GameRoom r = RuneroGame.game.getRoom(RuneroGame.game.roomOrder[n]);
        RuneroGame.game.room_index = r.getId();
        RuneroGame.room = new RoomInstance(RuneroGame.game, r);
        RuneroGame.room.init(false);
    }

    public static void room_restart(int transition) {
        RuneroGame.room = new RoomInstance(RuneroGame.game, RuneroGame.room.room);
        RuneroGame.room.init(false);
    }

    public static void room_goto(int room, int transition) {
        GameRoom r = RuneroGame.game.getRoom(room);
        if (r == null) {
            Runner.Error("Room Goto: No such room with ID " + room);
            return;
        }
        RuneroGame.room = new RoomInstance(RuneroGame.game, r);
        RuneroGame.room.init(false);
    }

    public static int room_next(int numb) {
        GameRoom r = RuneroGame.game.getRoom(numb);
        if (r == null) {
            Runner.Error("No such room with ID " + numb);
            return -1;
        }
        int next = r.orderIndex + 1;
        if (next >= RuneroGame.game.roomOrder.length)
            return -1;
        return RuneroGame.game.roomOrder[next];
    }

    public static int room_previous(int numb) {
        GameRoom r = RuneroGame.game.getRoom(numb);
        if (r == null) {
            Runner.Error("No such room with ID " + numb);
            return -1;
        }
        int next = r.orderIndex - 1;
        if (next < 0)
            return -1;
        return RuneroGame.game.roomOrder[next];
    }
}
