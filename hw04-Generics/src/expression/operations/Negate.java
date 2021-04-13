package expression.operations;

import expression.GenericExpression;
import expression.generic.calculators.AbstractCalculator;

public class Negate extends UnaryOperation {
    public Negate(GenericExpression expression) {
        super(expression);
    }

    @Override
    protected String getOperator() {
        return "-";
    }

    @Override
    protected <T extends Number> T calculate(AbstractCalculator<T> calculator, T a) {
        return calculator.negate(a);
    }
}
