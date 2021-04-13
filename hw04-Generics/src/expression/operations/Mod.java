package expression.operations;

import expression.GenericExpression;
import expression.generic.calculators.AbstractCalculator;

public class Mod extends BinaryOperation {
    public Mod(GenericExpression left, GenericExpression right) {
        super(left, right);
    }

    @Override
    protected String getOperator() {
        return "mod";
    }

    @Override
    protected <T extends Number> T calculate(AbstractCalculator<T> calculator, T a, T b) {
        return calculator.mod(a, b);
    }
}
