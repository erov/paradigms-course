package expression.parser;

import expression.*;
import expression.exceptions.*;
import expression.operations.*;

import java.util.Map;

public class ExpressionParser extends BaseParser implements GenericParser {
    private static final Map<String, Priorities> UNARY_OPERATIONS = Map.of(
            "-", Priorities.NEGATE,
            "abs", Priorities.NEGATE,
            "square", Priorities.NEGATE
    );
    private static final Map<String, Priorities> BINARY_OPERATIONS = Map.of(
            "+", Priorities.ADD,
            "-", Priorities.ADD,
            "*", Priorities.MUL,
            "/", Priorities.MUL,
            "mod", Priorities.MUL
    );
    private enum Arguments {
        FIRST, SECOND;
        public String get(Operations op) {
            return op == Operations.UNARY ? "" : this == FIRST ? "first " : "second ";
        }
    }
    private enum Operations {
        UNARY, BINARY;
        public String get() {
            return this == UNARY ? "unary" : "binary";
        }
    }

    @Override
    public GenericExpression parse(String expression) throws ParseException {
        makeSourse(expression);
        GenericExpression result = parse(Priorities.first(), Arguments.FIRST);
        if (ch != END) {
            throw ch == ')' ? new MissingBracketsException("missing opening bracket for closing bracket", getPosition()) :
                    new MissingOparationException(getPosition());
        }
        return result;
    }

    private GenericExpression parse(Priorities priority, Arguments argument) throws ParseException {
        if (priority.equals(Priorities.last())) {
            return parseHighestPriority(argument, Operations.BINARY);
        }

        GenericExpression left = parse(priority.next(), argument);
        while (checkAbstractOperator(BINARY_OPERATIONS, priority)) {
            int position = getPosition();
            String operator = getAbstractOperator(BINARY_OPERATIONS);
            GenericExpression right = parse(priority.next(), Arguments.SECOND);
            left = makeExpression(Operations.BINARY, left, right, operator, position);
        }
        return left;
    }

    private GenericExpression parseHighestPriority(Arguments argument, Operations operation) throws ParseException {
        GenericExpression expression;
        int position = getPosition();
        skipWhitespace();
        if (test('(')) {
            expression = parse(Priorities.first(), Arguments.FIRST);
            expect(')');
        } else if (isDigit()) {
            expression = parseConst("");
        } else if (checkAbstractOperator(UNARY_OPERATIONS, null)) {
            String operator = getAbstractOperator(UNARY_OPERATIONS);
            skipWhitespace();
            if (eof() || ch == ')') {
                throw new ItemNotFoundException("argument for unary operation",
                        (eof() ? "end of input" : "nothing"), getPosition());
            }
            expression = operator.equals("-") && isDigit() ? parseConst("-") :
                    makeExpression(Operations.UNARY, parseHighestPriority(Arguments.FIRST, Operations.UNARY),
                            null, operator, position);
        } else {
            StringBuilder sb = new StringBuilder();
            while ((isLetter() || isDigit()) && !isBracket() && !Character.isWhitespace(ch) && !eof()) {
                sb.append(ch);
                nextChar();
            }
            String item = sb.toString();
            if (BINARY_OPERATIONS.containsKey(item)) {
                throw new ItemNotFoundException(
                        String.format("%sargument for %s operation",
                                argument.get(operation), operation.get()), "nothing", position);
            }
            switch (item) {
                case "x":
                case "y":
                case "z":
                    expression = new Variable(item);
                    break;
                case "":
                    throw ch == ')' ? new ItemNotFoundException("variable/constant/unary operation inside brackets",
                            "nothing", getPosition() - 1) : new ItemNotFoundException(
                            String.format("%sargument for %s operation",
                                    argument.get(operation), operation.get()), "nothing", position);
                default:
                    throw new InvalidVariableNameException(item, position);
            }
        }
        skipWhitespace();
        return expression;
    }

    private Const parseConst(String sign) throws ParseException {
        StringBuilder current = new StringBuilder();
        current.append(sign);
        while (isDigit()) {
            current.append(ch);
            expect(ch);
        }

        int nextPosition = getPosition();
        skipWhitespace();
        if (isDigit()) {
            throw new SpaceInNumberException(nextPosition);
        }

        return new Const(current.toString());
    }

    private boolean checkAbstractOperator(Map<String, Priorities> operations, Priorities priority) {
        for (var current : operations.entrySet()) {
            if (test(current.getKey())) {
                if (priority == null) {
                    return true;
                }
                return current.getValue().equals(priority);
            }
        }
        return false;
    }

    private String getAbstractOperator(Map<String, Priorities> operations) throws ParseException {
        String operator = "";
        for (var current : operations.entrySet()) {
            if (test(current.getKey())) {
                operator = current.getKey();
                expect(operator);
                break;
            }
        }
        return operator;
    }

    private GenericExpression makeExpression(Operations type, GenericExpression left, GenericExpression right, String operator, int position) throws ParseException {
        if (type.equals(Operations.UNARY)) {
            switch (operator) {
                case "-":
                    return new Negate(left);
                case "abs":
                    return new Abs(left);
                case "square":
                    return new Square(left);
            }
        } else {
            switch (operator) {
                case "+":
                    return new Add(left, right);
                case "-":
                    return new Subtract(left, right);
                case "*":
                    return new Multiply(left, right);
                case "/":
                    return new Divide(left, right);
                case "mod":
                    return new Mod(left, right);
            }
        }
        throw new IllegalOperationException("unknown operation", position);
    }
}
