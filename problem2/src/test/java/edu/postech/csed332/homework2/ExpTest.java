package edu.postech.csed332.homework2;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ExpTest {

    @Test
    void testParserOK() {
        Exp exp = ExpParser.parse("p1 || p2 && ! p3 || true");
        assertEquals(exp.toString(), "((p1 || (p2 && (! p3))) || true)");
    }

    @Test
    void testParserError() {
        assertThrows(IllegalStateException.class, () -> {
            Exp exp = ExpParser.parse("p1 || p2 && ! p0 || true");
        });
    }

    @Test
    public void testVars() {
        Exp exp = ExpParser.parse("p1 && p3 && p4 && p1 && true");
        Set<Integer> vars = Set.of(1, 3, 4);

        assertEquals(vars, exp.vars());
    }

    @Test
    public void testConstantEvaluate() {
        Exp exp = ExpParser.parse("true");
        Map<Integer, Boolean> assignment = new HashMap<>();
        assertTrue(exp.evaluate(assignment));

        exp = ExpParser.parse("false");
        assertFalse(exp.evaluate(assignment));
    }

    @Test
    public void testVariableEvaluate() {
        Exp exp = ExpParser.parse("p1");
        Map<Integer, Boolean> assignment = new HashMap<>();
        assignment.put(1, true);
        assertTrue(exp.evaluate(assignment));

        exp = ExpParser.parse("p2");
        assignment.put(2, false);
        assertFalse(exp.evaluate(assignment));
    }

    @Test
    public void testNegationEvaluate() {
        Exp exp = ExpParser.parse("!p1");
        Map<Integer, Boolean> assignment = new HashMap<>();
        assignment.put(1, false);
        assertTrue(exp.evaluate(assignment));

        exp = ExpParser.parse("!p2");
        assignment.put(2, true);
        assertFalse(exp.evaluate(assignment));
    }

    @Test
    public void testIdentity() {
        Exp exp = ExpParser.parse("p1 && true");
        Exp simplified = exp.simplify();
        assertEquals("p1", simplified.toString());

        exp = ExpParser.parse("p1 || false");
        simplified = exp.simplify();
        assertEquals("p1", simplified.toString());

        exp = ExpParser.parse("p1 && p1");
        simplified = exp.simplify();
        assertEquals("p1", simplified.toString());

        exp = ExpParser.parse("p1 || p1");
        simplified = exp.simplify();
        assertEquals("p1", simplified.toString());
    }

    @Test
    public void testDomination() {
        Exp exp = ExpParser.parse("p1 && false");
        Exp simplified = exp.simplify();
        assertEquals("false", simplified.toString());

        exp = ExpParser.parse("p1 || true");
        simplified = exp.simplify();
        assertEquals("true", simplified.toString());

        exp = ExpParser.parse("p1 && !p1");
        simplified = exp.simplify();
        assertEquals("false", simplified.toString());

        exp = ExpParser.parse("p1 || !p1");
        simplified = exp.simplify();
        assertEquals("true", simplified.toString());
    }

    @Test
    public void testDeMorgan() {
        Exp exp = ExpParser.parse("!(p1 && p2)");
        Exp simplified = exp.simplify();
        assertEquals("((! p1) || (! p2))", simplified.toString());

        exp = ExpParser.parse("!(p1 || p2)");
        simplified = exp.simplify();
        assertEquals("((! p1) && (! p2))", simplified.toString());
    }

    @Test
    public void testAbsorption() {
        Exp exp = ExpParser.parse("p1 || (p1 && p2)");
        Exp simplified = exp.simplify();
        assertEquals("p1", simplified.toString());

        exp = ExpParser.parse("p1 && (p1 || p2)");
        simplified = exp.simplify();
        assertEquals("p1", simplified.toString());
    }

    @Test
    public void testDoubleNegation() {
        Exp exp = ExpParser.parse("!(!p1)");
        Exp simplified = exp.simplify();
        assertEquals("p1", simplified.toString());
    }

    @Test
    public void testDistributive() {
        Exp exp = ExpParser.parse("p1 || (p2 && p3)");
        Exp simplified = exp.simplify();
        assertEquals("((p2 && p1) || (p3 && p1))", simplified.toString());

        exp = ExpParser.parse("p1 && (p2 || p3)");
        simplified = exp.simplify();
        assertEquals("((p2 || p1) && (p3 || p1))", simplified.toString());
    }

    @Test
    public void testEvaluate() {
        Map<Integer, Boolean> assignment = new HashMap<>();
        assignment.put(1, true);
        assignment.put(2, false);
        assignment.put(3, true);
        assignment.put(4, true);

        Exp exp = ExpParser.parse("p1 || (p2 && p3)");
        assertTrue(exp.evaluate(assignment));

        exp = ExpParser.parse("p1 && p3 && p4 && p1 && true");
        assertTrue(exp.evaluate(assignment));

        exp = ExpParser.parse("(p1 || p2) && !p2 && p3");
        assertTrue(exp.evaluate(assignment));
    }
}
