package expression.operations;

import expression.GenericExpression;
import expression.generic.calculators.AbstractCalculator;

public class Square extends UnaryOperation {
    public Square(GenericExpression expression) {
        super(expression);
    }

    @Override
    protected String getOperator() {
        return "square";
    }

    @Override
    protected <T extends Number> T calculate(AbstractCalculator<T> calculator, T a) {
        return calculator.square(a);
    }
}
