package org.gcreator.runero.gml.exec;

public enum Operator {
    AND, OR, XOR, NOT, // && || ^^ !
    LESS, GREATER, LESS_EQUAL, GREATER_EQUAL, // < > <= >=
    EQUALS, NOT_EQUAL, PLUS, MINUS, MULTIPLY, DIVIDE, // == != + - * /
    INT_DIVIDE, MODULO, NEGATE, // div mod or % -
    BITW_AND, BITW_OR, BITW_XOR, // & | ^
    BITW_LEFT,BITW_RIGHT, // << >>
    BITW_INVERT // ~
}
