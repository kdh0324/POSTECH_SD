package edu.postech.csed332.homework5;

import edu.postech.csed332.homework5.expression.BinaryExp;
import edu.postech.csed332.homework5.expression.Exp;
import edu.postech.csed332.homework5.expression.VariableExp;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * This expression is equivalent to another expression that is syntactically identical up to renaming.
 * For example, "(x1 + x2) * x3 + 1.0 * x1" is equivalent to "(x3 + x1) * x2 + 1.0 * x3", but not
 * equivalent to "(x3 + x1) * x2 + 1.0 * x1".
 */
public class RenamingEquivDecorator extends ExpDecorator {

    public RenamingEquivDecorator(Exp e) {
        super(e);
    }

    @Override
    public boolean equiv(@NotNull Exp other) {
        return accept(new EquivalenceVisitor(other) {
            final HashMap<Integer, Integer> key = new HashMap<>();

            @Override
            public Boolean visitVariable(VariableExp variableExp) {
                if (!(other instanceof VariableExp)) return false;
                if (key.containsKey(variableExp.getName()))
                    return key.get(variableExp.getName()) == ((VariableExp) other).getName();
                key.put(variableExp.getName(), ((VariableExp) other).getName());
                return true;
            }
        });
    }
}
