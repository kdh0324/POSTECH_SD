package edu.postech.csed332.homework5;

import edu.postech.csed332.homework5.expression.*;
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

        boolean result;
        Exp origin = other;
        other = ((BinaryExp) origin).getLeft();
        result = left.accept(this);
        other = ((BinaryExp) origin).getRight();
        result &= right.accept(this);

        boolean isInstanceOf;
        switch (operator) {
            case "/" :
                isInstanceOf = origin instanceof DivideExp;
                break;
            case "^" :
                isInstanceOf = origin instanceof ExponentiationExp;
                break;
            case "-" :
                isInstanceOf = origin instanceof MinusExp;
                break;
            case "*" :
                isInstanceOf = origin instanceof MultiplyExp;
                break;
            case "+" :
                isInstanceOf = origin instanceof PlusExp;
                break;
            default:
                isInstanceOf = false;
        }

        return isInstanceOf && result;
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
