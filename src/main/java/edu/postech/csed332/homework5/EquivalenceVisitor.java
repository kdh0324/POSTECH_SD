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
        Exp left = binaryExp.getLeft();
        Exp right = binaryExp.getRight();

        return binaryExp.getClass().equals(other.getClass()) && left.accept(this) && right.accept(this);
    }

    @Override
    public Boolean visitNumber(NumberExp numberExp) {
        return other instanceof NumberExp && numberExp.getValue().equals(((NumberExp) other).getValue());
    }

    @Override
    public Boolean visitVariable(VariableExp variableExp) {
        return other instanceof VariableExp && variableExp.getName() == ((VariableExp) other).getName();
    }
}
