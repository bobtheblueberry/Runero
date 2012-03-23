package org.gcreator.runero.gml.lex;

public enum Token {

    BEGIN // { begin
    , END // } end
    , WORD // word, or function name or something
    , IF // if
    , THEN // then
    , ELSE // else
    , WHILE // while
    , DO // do
    , FOR // for
    , WITH// with
    , UNTIL// until
    , REPEAT// repeat
    , EXIT// exit
    , RETURN // return
    , BREAK // break
    , CONTINUE // continue
    , SWITCH // switch
    , CASE // case
    , DEFAULT // default
    , VAR // var
    , GLOBALVAR // globalvar
    , STRING // " This is a string " ' and this is too!'
    , NUMBER // a number
    
    , DOT // .
    , SEMICOLON // ;
    , PAR_OPEN // (
    , PAR_CLOSE // )
    , BRACKET_OPEN // [
    , BRACKET_CLOSE // ]
    , COMMA // ,

    , ASSIGN_EQUALS // :=
    , EQUALS // =
    , PLUS_EQUALS // +=
    , MINUS_EQUALS // -=
    , MULTIPLY_EQUALS // *=
    , DIVIDE_EQUALS // /=
    , OR_EQUALS // |=
    , AND_EQUALS // &=
    , XOR_EQUALS // ^=

    , AND // && and
    , OR // or ||
    , XOR // xor ^^
    , NOT // ! not
    , PLUS // +
    , MINUS // -
    , NOT_EQUALS // != <>
    , INT_DIVIDE // div
    , DIV // /
    , MULTIPLY // *
    , MODULO // % mod
    , GREATER // >
    , LESS // <
    , GREATER_EQUAL // >=
    , LESS_EQUAL // <=
    , COMPARATOR_EQUALS // ==

    , BITW_AND // &
    , BITW_OR // |
    , BITW_XOR // ^
    , BITW_INVERT // ~

    , BITW_RIGHT // <<
    , BITW_LEFT // >>
}