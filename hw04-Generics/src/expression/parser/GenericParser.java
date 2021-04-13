package expression.parser;

import expression.GenericExpression;
import expression.exceptions.*;

public interface GenericParser {
    GenericExpression parse(String expression) throws ParseException, CalculateException;
}
