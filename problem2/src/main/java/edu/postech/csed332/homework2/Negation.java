package edu.postech.csed332.homework2;

import java.util.Map;
import java.util.Set;

/**
 * A Boolean expression whose top-level operator is ! (not).
 */
public class Negation implements Exp {
    private final Exp subexp;

    /**
     * Builds a negated expression of a given Boolean expression.
     *
     * @param exp a Boolean expression
     */
    public Negation(Exp exp) {
        subexp = exp;
    }

    /**
     * Returns the immediate sub-expression of this expression.
     *
     * @return a sub-expression
     */
    public Exp getSubexp() {
        return subexp;
    }

    @Override
    public Set<Integer> vars() {
        return subexp.vars();
    }

    @Override
    public Boolean evaluate(Map<Integer, Boolean> assignment) {
        if (subexp instanceof Constant)
            return !((Constant) subexp).getValue();
        if (subexp instanceof Variable)
            return !assignment.get(((Variable) subexp).getIdentifier());

        return !subexp.evaluate(assignment);
    }

    @Override
    public Exp simplify() {
        if (subexp instanceof Negation)
            return ((Negation) subexp).subexp;
        if (subexp instanceof Conjunction)
            return new Disjunction(
                    new Negation(((Conjunction) subexp).getSubexps().get(0)),
                    new Negation(((Conjunction) subexp).getSubexps().get(1)));
        if (subexp instanceof Disjunction)
            return new Conjunction(
                    new Negation(((Disjunction) subexp).getSubexps().get(0)),
                    new Negation(((Disjunction) subexp).getSubexps().get(1)));
        return this;
    }

    @Override
    public String toString() {
        return "(! " + subexp + ")";
    }
}
