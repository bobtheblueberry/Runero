package org.gcreator.runero;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import org.gcreator.runero.gfx.RuneroDisplay;
import org.lwjgl.input.Keyboard;

public class Runner {

    public static ResourceLoader rl;
    public static boolean error_occurred = false;
    public static String error_last = "";

    public static File GameFolder;

    public static void Error(final String message) {
        error_occurred = true;
        error_last = message;

        StackTraceElement[] e = new Throwable().getStackTrace();
        String s = "";
        for (StackTraceElement el : e)
            s += el + "\n";
        System.err.println(message);
        System.err.println(s);
        new ErrorDialog(null, "Runtime Error", message, s);
    }

    public static void Error(String message, String debugInfo) {
        error_occurred = true;
        error_last = message;
        new ErrorDialog(null, "Runtime Error", message, debugInfo);
    }

    public static void Error(String message, Throwable error) {
        error_occurred = true;
        error_last = message;
        new ErrorDialog(null, "Runtime Error", message, error);
    }

    public Runner(File folder)
        {

            // Load LWJGL
            try {
                new LWJGLDownloader().checkLWJGL();
            } catch (IOException e) {
                System.err.println("Error download LWJGL");
                e.printStackTrace();
                return;
            }

            GameFolder = folder;

            RuneroGame game = new RuneroGame();
            // Load Resources
            try {
                rl = new ResourceLoader(game);
                rl.loadResources();
            } catch (IOException e) {
                System.err.println("ERROR LOADING GAME!");
                e.printStackTrace();
            }
            game.loadGame();
            RuneroGame.display = new RuneroDisplay();
            RuneroGame.display.start(game);
            System.exit(0);
            // Java Usually crashes here
        }

    /**
     * Gets LWJGL key code for a KeyEvent key code (or tries to)
     * 
     * @param code KeyEvent.xxx int
     * 
     * @return Keyboard.xxx
     */
    public static int getLWJGLKey(int code) {
        // Special check keys (no key code, must use Shift)
        // ! ~ # $ % & * ( )
        // Game Maker does not support most of these keys anyways
        switch (code) {
            case KeyEvent.VK_CANCEL:
                return -1;// Keyboard.KEY_CANCEL;
            case KeyEvent.VK_CLEAR:
                return -1;// Keyboard.KEY_CLEAR;
            case KeyEvent.VK_SHIFT:
                return -1;// Keyboard.KEY_SHIFT;
            case KeyEvent.VK_CONTROL:
                return -1;// Keyboard.KEY_CONTROL;
            case KeyEvent.VK_ALT:
                return -1;// Keyboard.KEY_ALT;
            case KeyEvent.VK_PAUSE:
                return Keyboard.KEY_PAUSE;
            case KeyEvent.VK_CAPS_LOCK:
                return Keyboard.KEY_CAPITAL;
            case KeyEvent.VK_ESCAPE:
                return Keyboard.KEY_ESCAPE;
            case KeyEvent.VK_SPACE:
                return Keyboard.KEY_SPACE;
            case KeyEvent.VK_PAGE_UP:
                return Keyboard.KEY_PRIOR;
            case KeyEvent.VK_PAGE_DOWN:
                return Keyboard.KEY_NEXT;
            case KeyEvent.VK_END:
                return Keyboard.KEY_END;
            case KeyEvent.VK_HOME:
                return Keyboard.KEY_HOME;
            case KeyEvent.VK_LEFT:
                return Keyboard.KEY_LEFT;
            case KeyEvent.VK_UP:
                return Keyboard.KEY_UP;
            case KeyEvent.VK_RIGHT:
                return Keyboard.KEY_RIGHT;
            case KeyEvent.VK_DOWN:
                return Keyboard.KEY_DOWN;
            case KeyEvent.VK_COMMA:
                return Keyboard.KEY_COMMA;
            case KeyEvent.VK_MINUS:
                return Keyboard.KEY_MINUS;
            case KeyEvent.VK_PERIOD:
                return Keyboard.KEY_PERIOD;
            case KeyEvent.VK_SLASH:
                return Keyboard.KEY_SLASH;
            case KeyEvent.VK_0:
                return Keyboard.KEY_0;
            case KeyEvent.VK_1:
                return Keyboard.KEY_1;
            case KeyEvent.VK_2:
                return Keyboard.KEY_2;
            case KeyEvent.VK_3:
                return Keyboard.KEY_3;
            case KeyEvent.VK_4:
                return Keyboard.KEY_4;
            case KeyEvent.VK_5:
                return Keyboard.KEY_5;
            case KeyEvent.VK_6:
                return Keyboard.KEY_6;
            case KeyEvent.VK_7:
                return Keyboard.KEY_7;
            case KeyEvent.VK_8:
                return Keyboard.KEY_8;
            case KeyEvent.VK_9:
                return Keyboard.KEY_9;
            case KeyEvent.VK_SEMICOLON:
                return Keyboard.KEY_SEMICOLON;
            case KeyEvent.VK_EQUALS:
                return Keyboard.KEY_EQUALS;
            case KeyEvent.VK_A:
                return Keyboard.KEY_A;
            case KeyEvent.VK_B:
                return Keyboard.KEY_B;
            case KeyEvent.VK_C:
                return Keyboard.KEY_C;
            case KeyEvent.VK_D:
                return Keyboard.KEY_D;
            case KeyEvent.VK_E:
                return Keyboard.KEY_E;
            case KeyEvent.VK_F:
                return Keyboard.KEY_F;
            case KeyEvent.VK_G:
                return Keyboard.KEY_G;
            case KeyEvent.VK_H:
                return Keyboard.KEY_H;
            case KeyEvent.VK_I:
                return Keyboard.KEY_I;
            case KeyEvent.VK_J:
                return Keyboard.KEY_J;
            case KeyEvent.VK_K:
                return Keyboard.KEY_K;
            case KeyEvent.VK_L:
                return Keyboard.KEY_L;
            case KeyEvent.VK_M:
                return Keyboard.KEY_M;
            case KeyEvent.VK_N:
                return Keyboard.KEY_N;
            case KeyEvent.VK_O:
                return Keyboard.KEY_O;
            case KeyEvent.VK_P:
                return Keyboard.KEY_P;
            case KeyEvent.VK_Q:
                return Keyboard.KEY_Q;
            case KeyEvent.VK_R:
                return Keyboard.KEY_R;
            case KeyEvent.VK_S:
                return Keyboard.KEY_S;
            case KeyEvent.VK_T:
                return Keyboard.KEY_T;
            case KeyEvent.VK_U:
                return Keyboard.KEY_U;
            case KeyEvent.VK_V:
                return Keyboard.KEY_V;
            case KeyEvent.VK_W:
                return Keyboard.KEY_W;
            case KeyEvent.VK_X:
                return Keyboard.KEY_X;
            case KeyEvent.VK_Y:
                return Keyboard.KEY_Y;
            case KeyEvent.VK_Z:
                return Keyboard.KEY_Z;
            case KeyEvent.VK_OPEN_BRACKET:
                return Keyboard.KEY_LBRACKET;
            case KeyEvent.VK_BACK_SLASH:
                return Keyboard.KEY_BACKSLASH;
            case KeyEvent.VK_CLOSE_BRACKET:
                return Keyboard.KEY_RBRACKET;
            case KeyEvent.VK_NUMPAD0:
                return Keyboard.KEY_NUMPAD0;
            case KeyEvent.VK_NUMPAD1:
                return Keyboard.KEY_NUMPAD1;
            case KeyEvent.VK_NUMPAD2:
                return Keyboard.KEY_NUMPAD2;
            case KeyEvent.VK_NUMPAD3:
                return Keyboard.KEY_NUMPAD3;
            case KeyEvent.VK_NUMPAD4:
                return Keyboard.KEY_NUMPAD4;
            case KeyEvent.VK_NUMPAD5:
                return Keyboard.KEY_NUMPAD5;
            case KeyEvent.VK_NUMPAD6:
                return Keyboard.KEY_NUMPAD6;
            case KeyEvent.VK_NUMPAD7:
                return Keyboard.KEY_NUMPAD7;
            case KeyEvent.VK_NUMPAD8:
                return Keyboard.KEY_NUMPAD8;
            case KeyEvent.VK_NUMPAD9:
                return Keyboard.KEY_NUMPAD9;
            case KeyEvent.VK_MULTIPLY:
                return Keyboard.KEY_MULTIPLY;
            case KeyEvent.VK_ADD:
                return Keyboard.KEY_ADD;

            case KeyEvent.VK_SUBTRACT:
                return Keyboard.KEY_SUBTRACT;
            case KeyEvent.VK_DECIMAL:
                return Keyboard.KEY_DECIMAL;
            case KeyEvent.VK_DIVIDE:
                return Keyboard.KEY_DIVIDE;
            case KeyEvent.VK_DELETE:
                return Keyboard.KEY_DELETE;
            case KeyEvent.VK_NUM_LOCK:
                return Keyboard.KEY_NUMLOCK;
            case KeyEvent.VK_SCROLL_LOCK:
                return Keyboard.KEY_SCROLL;
            case KeyEvent.VK_F1:
                return Keyboard.KEY_F1;
            case KeyEvent.VK_F2:
                return Keyboard.KEY_F2;
            case KeyEvent.VK_F3:
                return Keyboard.KEY_F3;
            case KeyEvent.VK_F4:
                return Keyboard.KEY_F4;
            case KeyEvent.VK_F5:
                return Keyboard.KEY_F5;
            case KeyEvent.VK_F6:
                return Keyboard.KEY_F6;
            case KeyEvent.VK_F7:
                return Keyboard.KEY_F7;
            case KeyEvent.VK_F8:
                return Keyboard.KEY_F8;
            case KeyEvent.VK_F9:
                return Keyboard.KEY_F9;
            case KeyEvent.VK_F10:
                return Keyboard.KEY_F10;
            case KeyEvent.VK_F11:
                return Keyboard.KEY_F11;
            case KeyEvent.VK_F12:
                return Keyboard.KEY_F12;
            case KeyEvent.VK_F13:
                return Keyboard.KEY_F13;
            case KeyEvent.VK_F14:
                return Keyboard.KEY_F14;
            case KeyEvent.VK_F15:
                return Keyboard.KEY_F15;
            case KeyEvent.VK_PRINTSCREEN:
                return Keyboard.KEY_SYSRQ;
            case KeyEvent.VK_INSERT:
                return Keyboard.KEY_INSERT;
            case KeyEvent.VK_HELP:
                return -1;//Keyboard.KEY_HELP;
            case KeyEvent.VK_META:
                return -1;// Keyboard.KEY_META;
            case KeyEvent.VK_BACK_QUOTE:
                return -1;//Keyboard.KEY_BACK_QUOTE;
            case KeyEvent.VK_QUOTE:
                return Keyboard.KEY_APOSTROPHE;
            case KeyEvent.VK_KP_UP:
                return Keyboard.KEY_NUMPAD8;
            case KeyEvent.VK_KP_DOWN:
                return Keyboard.KEY_NUMPAD2;
            case KeyEvent.VK_KP_LEFT:
                return Keyboard.KEY_NUMPAD4;
            case KeyEvent.VK_KP_RIGHT:
                return Keyboard.KEY_NUMPAD6;
            case KeyEvent.VK_DEAD_GRAVE:
                return Keyboard.KEY_GRAVE;
       //    case KeyEvent.VK_DEAD_ACUTE:
        //        return Keyboard.KEY_ACUTE;
            case KeyEvent.VK_DEAD_CIRCUMFLEX:
                return Keyboard.KEY_CIRCUMFLEX;
                /*
            case KeyEvent.VK_DEAD_TILDE:
                return Keyboard.KEY_TILDE;
            case KeyEvent.VK_DEAD_MACRON:
                return Keyboard.KEY_MACRON;
            case KeyEvent.VK_DEAD_BREVE:
                return Keyboard.KEY_BREVE;
            case KeyEvent.VK_DEAD_ABOVEDOT:
                return Keyboard.KEY_ABOVEDOT;
            case KeyEvent.VK_DEAD_DIAERESIS:
                return Keyboard.KEY_DEAD_DIAERESIS;
            case KeyEvent.VK_DEAD_ABOVERING:
                return Keyboard.KEY_DEAD_ABOVERING;
            case KeyEvent.VK_DEAD_DOUBLEACUTE:
                return Keyboard.KEY_DEAD_DOUBLEACUTE;
            case KeyEvent.VK_DEAD_CARON:
                return Keyboard.KEY_DEAD_CARON;
            case KeyEvent.VK_DEAD_CEDILLA:
                return Keyboard.KEY_DEAD_CEDILLA;
            case KeyEvent.VK_DEAD_OGONEK:
                return Keyboard.KEY_DEAD_OGONEK;
            case KeyEvent.VK_DEAD_IOTA:
                return Keyboard.KEY_DEAD_IOTA;
            case KeyEvent.VK_DEAD_VOICED_SOUND:
                return Keyboard.KEY_DEAD_VOICED_SOUND;
            case KeyEvent.VK_DEAD_SEMIVOICED_SOUND:
                return Keyboard.KEY_DEAD_SEMIVOICED_SOUND;
                */
            case KeyEvent.VK_AMPERSAND:
                return Keyboard.KEY_AT;
        /*    case KeyEvent.VK_ASTERISK:
                return Keyboard.KEY_ASTERISK;
            case KeyEvent.VK_QUOTEDBL:
                return Keyboard.KEY_QUOTEDBL;
            case KeyEvent.VK_LESS:
                return Keyboard.KEY_LESS;
            case KeyEvent.VK_GREATER:
                return Keyboard.KEY_GREATER;
            case KeyEvent.VK_BRACELEFT:
                return Keyboard.KEY_LBRACKET;
            case KeyEvent.VK_BRACERIGHT:
                return Keyboard.KEY_RBRACKET;*/
            case KeyEvent.VK_AT:
                return Keyboard.KEY_AT;
            case KeyEvent.VK_COLON:
                return Keyboard.KEY_COLON;
            case KeyEvent.VK_CIRCUMFLEX:
                return Keyboard.KEY_CIRCUMFLEX;
                /*
            case KeyEvent.VK_DOLLAR:
                return Keyboard.KEY_DOLLAR;
            case KeyEvent.VK_EURO_SIGN:
                return Keyboard.KEY_EURO_SIGN;
            case KeyEvent.VK_EXCLAMATION_MARK:
                return Keyboard.KEY_EXCLAMATION_MARK;
            case KeyEvent.VK_INVERTED_EXCLAMATION_MARK:
                return Keyboard.KEY_INVERTED_EXCLAMATION_MARK;
            case KeyEvent.VK_LEFT_PARENTHESIS:
                return Keyboard.KEY_LEFT_PARENTHESIS;
            case KeyEvent.VK_NUMBER_SIGN:
                return Keyboard.KEY_NUMBER_SIGN;
                */
            case KeyEvent.VK_PLUS:
                return Keyboard.KEY_ADD;
                /*
            case KeyEvent.VK_RIGHT_PARENTHESIS:
                return Keyboard.KEY_RIGHT_PARENTHESIS;
                */
            case KeyEvent.VK_UNDERSCORE:
                return Keyboard.KEY_UNDERLINE;
                /*
            case KeyEvent.VK_WINDOWS:
                return Keyboard.KEY_WINDOWS;
            case KeyEvent.VK_CONTEXT_MENU:
                return Keyboard.KEY_CONTEXT_MENU;
            case KeyEvent.VK_FINAL:
                return Keyboard.KEY_FINAL;
                */
            case KeyEvent.VK_CONVERT:
                return Keyboard.KEY_CONVERT;/*
            case KeyEvent.VK_NONCONVERT:
                return Keyboard.KEY_NONCONVERT;
            case KeyEvent.VK_ACCEPT:
                return Keyboard.KEY_ACCEPT;
            case KeyEvent.VK_MODECHANGE:
                return Keyboard.KEY_MODECHANGE;
            case KeyEvent.VK_KANA_LOCK:
                return Keyboard.KEY_KANA_LOCK;*/
            case KeyEvent.VK_KANA:
                return Keyboard.KEY_KANA;
          /*  case KeyEvent.VK_INPUT_METHOD_ON_OFF:
                return Keyboard.KEY_INPUT_METHOD_ON_OFF;*/
            case KeyEvent.VK_KANJI:
                return Keyboard.KEY_KANJI;
            case KeyEvent.VK_ALPHANUMERIC:/*
                return Keyboard.KEY_ALPHANUMERIC;
            case KeyEvent.VK_KATAKANA:
                return Keyboard.KEY_KATAKANA;
            case KeyEvent.VK_HIRAGANA:
                return Keyboard.KEY_HIRAGANA;
            case KeyEvent.VK_FULL_WIDTH:
                return Keyboard.KEY_FULL_WIDTH;
            case KeyEvent.VK_HALF_WIDTH:
                return Keyboard.KEY_HALF_WIDTH;
            case KeyEvent.VK_ROMAN_CHARACTERS:
                return Keyboard.KEY_ROMAN_CHARACTERS;
            case KeyEvent.VK_ALL_CANDIDATES:
                return Keyboard.KEY_ALL_CANDIDATES;
            case KeyEvent.VK_PREVIOUS_CANDIDATE:
                return Keyboard.KEY_PREVIOUS_CANDIDATE;
            case KeyEvent.VK_CODE_INPUT:
                return Keyboard.KEY_CODE_INPUT;
            case KeyEvent.VK_JAPANESE_KATAKANA:
                return Keyboard.KEY_JAPANESE_KATAKANA;
            case KeyEvent.VK_JAPANESE_HIRAGANA:
                return Keyboard.KEY_JAPANESE_HIRAGANA;
            case KeyEvent.VK_JAPANESE_ROMAN:
                return Keyboard.KEY_JAPANESE_ROMAN;
            case KeyEvent.VK_KANA_LOCK:
                return Keyboard.KEY_KANA_LOCK;
            case KeyEvent.VK_INPUT_METHOD_ON_OFF:
                return Keyboard.KEY_INPUT_METHOD_ON_OFF;
            case KeyEvent.VK_CUT:
                return Keyboard.KEY_CUT;
            case KeyEvent.VK_COPY:
                return Keyboard.KEY_COPY;
            case KeyEvent.VK_PASTE:
                return Keyboard.KEY_PASTE;
            case KeyEvent.VK_UNDO:
                return Keyboard.KEY_UNDO;
            case KeyEvent.VK_AGAIN:
                return Keyboard.KEY_AGAIN;
            case KeyEvent.VK_FIND:
                return Keyboard.KEY_FIND;
            case KeyEvent.VK_PROPS:
                return Keyboard.KEY_PROPS;*/
            case KeyEvent.VK_STOP:
                return Keyboard.KEY_STOP;
        /*    case KeyEvent.VK_COMPOSE:
                return Keyboard.KEY_COMPOSE;
            case KeyEvent.VK_ALT_GRAPH:
                return Keyboard.KEY_ALT_GRAPH;
            case KeyEvent.VK_BEGIN:
                return Keyboard.KEY_BEGIN;*/
        }
        return -1;
    }
}
