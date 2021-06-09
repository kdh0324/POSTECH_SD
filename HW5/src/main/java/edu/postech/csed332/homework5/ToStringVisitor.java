package edu.postech.csed332.homework5;

import edu.postech.csed332.homework5.expression.BinaryExp;
import edu.postech.csed332.homework5.expression.NumberExp;
import edu.postech.csed332.homework5.expression.VariableExp;

/**
 * A visitor to compute the string expression of a given expression
 */
public class ToStringVisitor implements ExpVisitor<String> {
    @Override
    public String visitBinary(BinaryExp binaryExp, String operator) {
        String left = binaryExp.getLeft().toString();
        String right = binaryExp.getRight().toString();
        return "(" + left + " " + operator + " " + right + ")";
    }

    @Override
    public String visitNumber(NumberExp numberExp) {
        String value = numberExp.getValue().toString();
        if (numberExp.getValue() < 0) return "(" + value + ")";
        return value;
    }

    @Override
    public String visitVariable(VariableExp variableExp) {
        return "x" + variableExp.getName();
    }
}
