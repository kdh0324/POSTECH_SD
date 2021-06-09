package edu.postech.csed332.homework2;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A Boolean expression whose top-level operator is && (and).
 */
public class Conjunction implements Exp {
    private final List<Exp> subexps;

    /**
     * Builds a conjunction of a given list of Boolean expressions
     *
     * @param exps
     */
    public Conjunction(Exp... exps) {
        subexps = new ArrayList<>(Arrays.asList(exps));
    }

    public Conjunction(List<Exp> expList, Exp... exps) {
        subexps = new ArrayList<>(Arrays.asList(exps));
        subexps.addAll(expList);
    }

    /**
     * Returns the immediate sub-expressions of this expression.
     *
     * @return a list of sub-expressions
     */
    public List<Exp> getSubexps() {
        return subexps;
    }

    @Override
    public Set<Integer> vars() {
        Set<Integer> vars = new HashSet<>();
        for (Exp exp : subexps)
            vars.addAll(exp.vars());
        return vars;
    }

    @Override
    public Boolean evaluate(Map<Integer, Boolean> assignment) {
        boolean result = true;
        for (Exp exp : subexps)
            result &= exp.evaluate(assignment);
        return result;
    }

    @Override
    public Exp simplify() {
        for (int i = 0; i < subexps.size(); i++) {
            Exp exp = subexps.get(i);
            subexps.set(i, exp.simplify());
        }

        Exp exp = identityDomination();
        if (subexps.size() == 0)
            return new Constant(true);
        if (exp != this)
            return exp;

        return distributive();
    }

    private Exp identityDomination() {
        Set<Integer> variableSet = new HashSet<>();
        Set<Integer> negationSet = new HashSet<>();
        List<Exp> removed = new ArrayList<>();
        for (Exp exp : subexps) {
            if (exp instanceof Constant) {
                if (((Constant) exp).getValue())
                    removed.add(exp);
                else
                    return new Constant(false);
            }
            if (exp instanceof Variable) {
                if (variableSet.contains(((Variable) exp).getIdentifier()))
                    removed.add(exp);
                else if (negationSet.contains(((Variable) exp).getIdentifier()))
                    return new Constant(false);
                else
                    variableSet.add(((Variable) exp).getIdentifier());
            }
            if (exp instanceof Negation) {
                Exp subExp = ((Negation) exp).getSubexp();
                if (variableSet.contains(((Variable) subExp).getIdentifier()))
                    return new Constant(false);
                else
                    negationSet.add(((Variable) subExp).getIdentifier());
            }
        }
        absorption(variableSet);
        subexps.removeAll(removed);

        if (subexps.size() == 1)
            return subexps.get(0).simplify();
        return this;
    }

    private void absorption(Set<Integer> variableSet) {
        List<Exp> removed = new ArrayList<>();
        for (Exp exp : subexps)
            if (exp instanceof Disjunction) {
                List<Exp> subExps = ((Disjunction) exp).getSubexps();
                for (Exp subExp : subExps)
                    if (subExp instanceof Variable && variableSet.contains(((Variable) subExp).getIdentifier()))
                        removed.add(exp);
            }

        subexps.removeAll(removed);
    }

    private Exp distributive() {
        for (Exp exp : subexps)
            if (exp instanceof Disjunction) {
                List<Exp> expList = subexps;
                expList.remove(exp);

                List<Exp> disjunctions = new ArrayList<>();
                for (Exp subExp : ((Disjunction) exp).getSubexps())
                    disjunctions.add(new Disjunction(expList, subExp));

                return new Conjunction(disjunctions);
            }
        return this;
    }

    @Override
    public String toString() {
        return "(" + subexps.stream().map(Object::toString).collect(Collectors.joining(" && ")) + ")";
    }
}