package edu.postech.csed332.homework2;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A Boolean expression whose top-level operator is || (or).
 */
public class Disjunction implements Exp {
    private final List<Exp> subexps;

    /**
     * Builds a disjunction of a given list of Boolean expressions
     *
     * @param exps
     */
    public Disjunction(Exp... exps) {
        subexps = new ArrayList<>(Arrays.asList(exps));
    }

    public Disjunction(List<Exp> expList, Exp... exps) {
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
        boolean result = false;
        for (Exp exp : subexps)
            result |= exp.evaluate(assignment);
        return result;
    }

    @Override
    public Exp simplify() {
        boolean flag = true;
        while (flag) {
            flag = false;
            Iterator<Exp> iter = subexps.listIterator();
            List<Exp> expList = new ArrayList<>();
            while (iter.hasNext()) {
                Exp exp = iter.next();
                if (exp instanceof Disjunction) {
                    expList.addAll(((Disjunction) exp).getSubexps());
                    flag = true;
                }
            }
            subexps.addAll(expList);
        }
        for (int i = 0; i < subexps.size(); i++) {
            Exp exp = subexps.get(i);
            subexps.set(i, exp.simplify());
        }

        Exp exp = identityDomination();
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
                    return new Constant(true);
                else
                    removed.add(exp);
            }
            if (exp instanceof Variable) {
                if (variableSet.contains(((Variable) exp).getIdentifier()))
                    removed.add(exp);
                else if (negationSet.contains(((Variable) exp).getIdentifier()))
                    return new Constant(true);
                else
                    variableSet.add(((Variable) exp).getIdentifier());
            }
            if (exp instanceof Negation) {
                Exp subExp = ((Negation) exp).getSubexp();
                if (subExp instanceof Constant) {
                    if (((Constant) subExp).getValue())
                        removed.add(exp);
                    else
                        return new Constant(true);
                }
                if (subExp instanceof Variable) {
                    if (variableSet.contains(((Variable) subExp).getIdentifier()))
                        return new Constant(true);
                    else if (negationSet.contains(((Variable) subExp).getIdentifier()))
                        removed.add(exp);
                    else
                        negationSet.add(((Variable) subExp).getIdentifier());
                }
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
            if (exp instanceof Conjunction) {
                List<Exp> subExps = ((Conjunction) exp).getSubexps();
                for (Exp subExp : subExps)
                    if (subExp instanceof Variable && variableSet.contains(((Variable) subExp).getIdentifier()))
                        removed.add(exp);
            }

        subexps.removeAll(removed);
    }

    private Exp distributive() {
        for (Exp exp : subexps)
            if (exp instanceof Conjunction) {
                List<Exp> expList = new ArrayList<>(subexps);
                expList.remove(exp);
                if (expList.get(0) instanceof Disjunction || expList.get(0) instanceof Conjunction)
                    return this;

                List<Exp> conjunctions = new ArrayList<>();
                for (Exp subExp : ((Conjunction) exp).getSubexps())
                    conjunctions.add(new Conjunction(expList, subExp));

                return new Disjunction(conjunctions);
            }
        return this;
    }

    @Override
    public String toString() {
        return "(" + subexps.stream().map(Object::toString).collect(Collectors.joining(" || ")) + ")";
    }
}