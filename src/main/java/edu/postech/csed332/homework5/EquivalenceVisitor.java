package edu.postech.csed332.homework5;

import edu.postech.csed332.homework5.expression.BinaryExp;
import edu.postech.csed332.homework5.expression.Exp;
import edu.postech.csed332.homework5.expression.NumberExp;
import edu.postech.csed332.homework5.expression.VariableExp;
import org.jetbrains.annotations.NotNull;

/**
 * A visitor to check whether a given expression is syntactically equivalent to another expression.
 */
public class EquivalenceVisitor implements ExpVisitor<Boolean> {

    Exp other;

    public EquivalenceVisitor(@NotNull Exp other) {
        this.other = other;
    }

    @Override
    public Boolean visitBinary(BinaryExp binaryExp, String operator) {
        return binaryExp.equiv(other);
    }

    @Override
    public Boolean visitNumber(NumberExp numberExp) {
        return numberExp.equiv(other);
    }

    @Override
    public Boolean visitVariable(VariableExp variableExp) {
        return variableExp.equiv(other);
    }
}
