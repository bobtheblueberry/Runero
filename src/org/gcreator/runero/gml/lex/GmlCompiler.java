package org.gcreator.runero.gml.lex;

import static org.gcreator.runero.gml.lex.Token.*;

import java.util.ArrayList;

import org.gcreator.runero.gml.exec.Argument;
import org.gcreator.runero.gml.exec.Assignment;
import org.gcreator.runero.gml.exec.Expression;
import org.gcreator.runero.gml.exec.Function;
import org.gcreator.runero.gml.exec.Operator;
import org.gcreator.runero.gml.exec.Statement;
import org.gcreator.runero.gml.lex.GmlTokenizer.ANSI;

/**
 * Turns GML tokens into something useful (maybe)
 * 
 * 
 * @author serge
 *
 */
public class GmlCompiler {

    public static ArrayList<Statement> interpretGml(TokenWord[] tokens) {
        GmlCompiler c = new GmlCompiler(tokens);
        c.execute();
        return c.execution;
    }

    private GmlCompiler(TokenWord[] tokens) {
        this.tokens = tokens;
        execution = new ArrayList<Statement>();
    }

    ArrayList<Statement> execution;
    TokenWord[] tokens;

    int i;
    boolean end;
    /**
     * Where the magic happens!
     * 
     */
    private void execute() {
        for (i = 0; i < tokens.length; i++) {
            end = i + 1 >= tokens.length;
            TokenWord w = tokens[i];
            if (w.token == SEMICOLON)
                continue;
            else
                System.out.println(w);

            String s = null;
            if (w.hasData)
                s = w.data.getData();

            if (w.token == WORD) {
                if (end) {
                    // wtf is this shit??
                    error("unexpected end of data");
                    break;
                }
                TokenWord next = tokens[i + 1];
                if (next.token == PAR_OPEN) {
                    addFunction(s, (TokenGroup) next);
                } else if (next.token == BRACKET_OPEN) { // [
                    // Array.. SHIT!!!
                    // TODO: support arrays...
                    // /// should happen in phase2
                } else if (next.token == ASSIGN_EQUAL || next.token == EQUAL) { // := =
                    addAssignment(Assignment.OPERATION.SET);
                } else if (next.token == PLUS_EQUAL) { // +=
                    addAssignment(Assignment.OPERATION.ADD);
                } else if (next.token == MINUS_EQUAL) { // -=
                    addAssignment(Assignment.OPERATION.SUB);
                } else if (next.token == MULTIPLY_EQUAL) { // *=
                    addAssignment(Assignment.OPERATION.MULTIPLY);
                } else if (next.token == DIVIDE_EQUAL) { // /=
                    addAssignment(Assignment.OPERATION.DIVIDE);
                } else if (next.token == MOD_EQUAL) { // %=
                    addAssignment(Assignment.OPERATION.MOD);
                } else if (next.token == AND_EQUAL) { // &=
                    addAssignment(Assignment.OPERATION.AND);
                } else if (next.token == OR_EQUALS) { // |=
                    addAssignment(Assignment.OPERATION.OR);
                } else if (next.token == XOR_EQUAL) { // ^=
                    addAssignment(Assignment.OPERATION.XOR);
                } else if (next.token == IF) { // if also handles THEN and ELSE
                    addIf();
                } else if (next.token == WHILE) {
                    addWhile();
                } else if (next.token == DO) { // do also handles UNTIL (expr)
                    addDo();
                } else if (next.token == FOR) {
                    addFor();
                } else if (next.token == WITH) {
                    addWith();
                } else if (next.token == REPEAT) {
                    addRepeat();
                } else if (next.token == EXIT) {
                    addExit();
                } else if (next.token == RETURN) {
                    addReturn();
                } else if (next.token == BREAK) {
                    addBreak();
                } else if (next.token == CONTINUE) {
                    addContinue();
                } else if (next.token == SWITCH) { // switch takes care of CASE and DEFAULT
                    addSwitch();
                } else if (next.token == VAR) {
                    addVar(false);
                } else if (next.token == GLOBALVAR) {
                    addVar(true);
                } else {
                    error("Unexpected " + next);
                    break;
                }
                continue;
            } else if (w.token == BEGIN) {
                // useless...
            } else if (w.token == END) {
                // also useless..
            } else {
                error("unexpected " + w);
                break;
            }
        }
    }

    private Expression getExpression() {
        return null;
    }

    private void addVar(boolean b) {
        // TODO Auto-generated method stub

    }

    private void addReturn() {
        // TODO Auto-generated method stub

    }

    private void addSwitch() {
        // TODO Auto-generated method stub

    }

    private void addContinue() {
        // TODO Auto-generated method stub

    }

    private void addBreak() {
        // TODO Auto-generated method stub

    }

    private void addExit() {
        // TODO Auto-generated method stub

    }

    private void addFor() {
        // TODO Auto-generated method stub

    }

    private void addWith() {
        // TODO Auto-generated method stub

    }

    private void addRepeat() {
        // TODO Auto-generated method stub

    }

    private void addDo() {
        // TODO Auto-generated method stub

    }

    private void addWhile() {
        // TODO Auto-generated method stub

    }

    private void addIf() {
        // TODO Auto-generated method stub

    }

    private void addAssignment(Assignment.OPERATION op) {
        Assignment a = new Assignment(op);
        execution.add(a);
    }

    private void addFunction(String name, TokenGroup g) {
        Function f = new Function(name);
        // look for args
        if (g.tokens.size() > 0) {
            Argument a = null;
            for (TokenWord t : g.tokens) {
                if (t.token == COMMA) {
                    if (a == null) {
                        error("Unexpected ','");
                        break;
                    } else {
                        f.args.add(a);
                        a = null;
                        continue;
                    }
                }
                if (a == null)
                    a = new Argument();
                Expression e = getExpression(t);
                a.add(e);
            }
        }

        execution.add(f);
    }

    private Expression getExpression(TokenWord t) {
        if (t.token == WORD)  { // variable function array etc.
            if (end) {
                 // end of data 
            }
            
            
        } else if (t.token == AND)
            return new Expression(Operator.AND);
        else if (t.token == OR)
            return new Expression(Operator.OR);
        else if (t.token == XOR)
            return new Expression(Operator.XOR);
        else if (t.token == NOT)
            return new Expression(Operator.NOT);
        else if (t.token == LESS)
            return new Expression(Operator.LESS);
        else if (t.token == GREATER)
            return new Expression(Operator.GREATER);
        else if (t.token == LESS_EQUAL)
            return new Expression(Operator.LESS_EQUAL);
        else if (t.token == GREATER_EQUAL)
            return new Expression(Operator.GREATER_EQUAL);
        else if (t.token == EQUAL)// =
            return new Expression(Operator.EQUALS);
        else if (t.token == COMPARATOR_EQUALS)
            return new Expression(Operator.EQUALS);
        else if (t.token == NOT_EQUALS)
            return new Expression(Operator.NOT_EQUAL);
        else if (t.token == PLUS)
            return new Expression(Operator.PLUS);
        else if (t.token == MINUS)
            return new Expression(Operator.MINUS);
        else if (t.token == MULTIPLY)
            return new Expression(Operator.MULTIPLY);
        else if (t.token == DIV)
            return new Expression(Operator.DIVIDE);
        else if (t.token == INT_DIVIDE)
            return new Expression(Operator.INT_DIVIDE);
        else if (t.token == MODULO)
            return new Expression(Operator.MODULO);
        else if (t.token == BITW_AND)
            return new Expression(Operator.BITW_AND);
        else if (t.token == BITW_OR)
            return new Expression(Operator.BITW_OR);
        else if (t.token == XOR)
            return new Expression(Operator.BITW_XOR);
        else if (t.token == BITW_LEFT)
            return new Expression(Operator.BITW_LEFT);
        else if (t.token == BITW_RIGHT)
            return new Expression(Operator.BITW_RIGHT);
        return null;

    }

    private void error(String msg) {
        System.out.println(ANSI.RED + "Error in parser: " + msg + ANSI.BLACK);
        throw new ParseError(msg);
    }

}
