package expression.generic;

import expression.GenericExpression;
import expression.exceptions.CalculateException;
import expression.exceptions.ParseException;
import expression.generic.calculators.*;
import expression.parser.ExpressionParser;

import java.util.Map;

public class GenericTabulator implements Tabulator {
    private static final Map<String, AbstractCalculator<?>> MODE = Map.of(
            "i", new CheckedIntegerCalculator(),
            "d", new DoubleCalculator(),
            "bi", new BigIntegerCalculator(),
            "u", new IntegerCalculator(),
            "p", new ModularIntegerCalculator(),
            "b", new ByteCalculator()
    );

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws ParseException {
        AbstractCalculator<?> calculator = MODE.get(mode);
        GenericExpression expr = new ExpressionParser().parse(expression);
        return makeTable(calculator, expr, x1, x2, y1, y2, z1, z2);
    }

    <T extends Number> Object[][][] makeTable(AbstractCalculator<T> calculator, GenericExpression expr,
                               int x1, int x2, int y1, int y2, int z1, int z2) {
        Object[][][] result = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        for (int x = 0; x <= x2 - x1; x++) {
            for (int y = 0; y <= y2 - y1; y++) {
                for (int z = 0; z <= z2 - z1; z++) {
                    try {
                        result[x][y][z] = expr.evaluate(calculator, calculator.valueOf(x1 + x),
                                calculator.valueOf(y1 + y), calculator.valueOf(z1 + z));
                    } catch (CalculateException e) {
                        // null
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: GenericTabulator MODE EXPRESSION");
            return;
        }
        int l = -2, r = 2;
        Object[][][] ans;
        try {
            ans = new GenericTabulator().tabulate(args[0].substring(1), args[1], l, r, l, r, l, r);
        } catch (ParseException e) {
            System.out.println("Parse error: " + e.getMessage());
            return;
        }
        System.out.printf("f(x,y,z) = %s%nmode: %s%n==========%n", args[1], args[0].substring(1));
        for (int x = 0; x <= r - l; x++) {
            for (int y = 0; y <= r - l; y++) {
                for (int z = 0; z <= r - l; z++) {
                    System.out.printf("f(%d,%d,%d) = %s%n", l + x, l + y, l + z,
                            ans[x][y][z] == null ? "null" : ans[x][y][z].toString());
                }
            }
        }
    }
}
