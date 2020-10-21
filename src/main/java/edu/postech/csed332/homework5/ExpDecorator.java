package edu.postech.csed332.homework5;

import edu.postech.csed332.homework5.expression.Exp;
import org.jetbrains.annotations.NotNull;

/**
 * A base decorator class
 */
public class ExpDecorator extends Exp {
    private final Exp expression;

    ExpDecorator(Exp e) {
        expression = e;
    }

    @Override
    public <T> @NotNull T accept(ExpVisitor<T> visitor) {
        return expression.accept(visitor);
    }
    // TODO implement all the methods of ExpDecorator
}
