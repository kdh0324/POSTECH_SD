package edu.postech.csed332.homework5;

import edu.postech.csed332.homework5.expression.BinaryExp;
import edu.postech.csed332.homework5.expression.NumberExp;
import edu.postech.csed332.homework5.expression.VariableExp;

/**
 * A visitor interface for expressions
 *
 * @param <T> type of return values
 */
public interface ExpVisitor<T> {
    T visitBinary(BinaryExp binaryExp, String operator);
    T visitNumber(NumberExp numberExp);
    T visitVariable(VariableExp variableExp);
}
