package org.gcreator.runero.gml.lex;

public enum Operator {
    BOOL_AND, BOOL_OR, BOOL_XOR, BOOL_NOT, // && || ^^ !
    LESS, GREATER, LESS_EQUAL, GREATER_EQUAL, // < > <= >=
    EQUALS, NOT_EQUAL, ADD, SUB, MULTIPLY, DIVIDE, // == != + - * /
    INT_DIVIDE, MODULO, NEGATE, // div mod or % -
    BITW_AND, BITW_OR, BITW_XOR, // & | ^
    EQUAL                // x = 2
}
