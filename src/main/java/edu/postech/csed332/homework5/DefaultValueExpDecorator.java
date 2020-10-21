package edu.postech.csed332.homework5;

import edu.postech.csed332.homework5.expression.Exp;
import edu.postech.csed332.homework5.expression.VariableExp;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * When evaluating the expression, use the default value for variables not present in the valuation.
 */
public class DefaultValueExpDecorator extends ExpDecorator {
    @NotNull
    private final Double defaultValue;

    public DefaultValueExpDecorator(@NotNull Exp e, @NotNull Double defaultValue) {
        super(e);
        this.defaultValue = defaultValue;
    }

    @Override
    @NotNull
    public Double eval(@NotNull Map<Integer, Double> valuation) {
        return accept(new EvaluationVisitor(valuation) {
            @Override
            public Double visitVariable(VariableExp variableExp) {
                if (!valuation.containsKey(variableExp.getName())) return defaultValue;
                return super.visitVariable(variableExp);
            }
        });
    }
}
