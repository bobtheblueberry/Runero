package org.gcreator.runero.gml.lex;

public enum Token {

    BEGIN // { begin
    , END // } end
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
    , WORD // word, not used after the final stages of lexing
    , FUNCTION // func(x,y,z)
    , ARRAY // grid[x,y]
    , VARIABLE // x y obj0.x room_width obj5.target.y obj2.array[5]
    
    , DOT // .
    , SEMICOLON // ;
    , PAR_OPEN // (
    , PAR_CLOSE // )
    , BRACKET_OPEN // [
    , BRACKET_CLOSE // ]
    , COMMA // ,

    , EQUAL // =
    , ASSIGN_EQUAL // :=
    , PLUS_EQUAL // +=
    , MINUS_EQUAL // -=
    , MULTIPLY_EQUAL // *=
    , DIVIDE_EQUAL // /=
    , OR_EQUALS // |=
    , AND_EQUAL // &=
    , XOR_EQUAL // ^=
    , MOD_EQUAL // %=

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