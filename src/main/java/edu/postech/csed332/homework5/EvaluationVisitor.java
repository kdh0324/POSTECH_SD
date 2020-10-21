package edu.postech.csed332.homework5;

import edu.postech.csed332.homework5.expression.BinaryExp;
import edu.postech.csed332.homework5.expression.NumberExp;
import edu.postech.csed332.homework5.expression.VariableExp;

import java.util.Map;

/**
 * A visitor to evaluate a value of an expression, given a valuation for each variable
 */
public class EvaluationVisitor implements ExpVisitor<Double> {
    private final Map<Integer, Double> valuation;

    public EvaluationVisitor(Map<Integer, Double> valuation) {
        this.valuation = valuation;
    }

    @Override
    public Double visitBinary(BinaryExp binaryExp, String operator) {
        Double left = binaryExp.getLeft().eval(valuation);
        Double right = binaryExp.getRight().eval(valuation);
        switch (operator) {
            case "/" :
                return left / right;
            case "^" :
                return Math.pow(left, right);
            case "-" :
                return left - right;
            case "*" :
                return left * right;
            case "+" :
                return left + right;
        }
        return null;
    }

    @Override
    public Double visitNumber(NumberExp numberExp) {
        return numberExp.getValue();
    }

    @Override
    public Double visitVariable(VariableExp variableExp) {
        return valuation.get(variableExp.getName());
    }
}
