"use strict";

function createNewExpression(Expression, evaluate, toString, diff, prefix = toString, postfix = toString) {
    Expression.prototype.evaluate = evaluate;
    Expression.prototype.toString = toString;
    Expression.prototype.diff = diff;
    Expression.prototype.prefix = prefix;
    Expression.prototype.postfix = postfix;
    Expression.prototype.constructor = Expression;
    return Expression;
}

const Const = createNewExpression(
    function (value) {
        this._value = value;
    },
    function () {
        return this._value;
    },
    function () {
        return this._value.toString();
    },
    () => Const.ZERO
);
Const.ZERO = new Const(0);
Const.ONE = new Const(1);
Const.TWO = new Const(2);

const validVariableNames = ["x", "y", "z"];
const Variable = createNewExpression(
    function (name) {
        this._id = validVariableNames.indexOf(name);
    },
    function (...variables) {
        return variables[this._id];
    },
    function () {
        return validVariableNames[this._id];
    },
    function (variable) {
        return this.toString() === variable ? Const.ONE : Const.ZERO;
    }
);
const variableList = [];
validVariableNames.forEach(name => variableList[name] = new Variable(name));

const operationList = [];
const AbstractOperation = createNewExpression(
    function (...args) {
        this.args = args;
        this.derivatives = {};
    },
    function (...variables) {
        return this._operation(...this.args.map(a => a.evaluate(...variables)));
    },
    function () {
        return this.args.map(a => a.toString()).join(' ') + ' ' + this.operator;
    },
    function (variable) {
        if (!(variable in this.derivatives)) {
            this.derivatives[variable] = [];
            this.args.forEach(item => this.derivatives[variable].push(item.diff(variable)));
        }
        return this._diffRule(this.derivatives[variable]);
    },
    function () {
        return `(${this.operator} ${this.args.map(a => a.prefix()).join(' ')})`;
    },
    function () {
        return `(${this.args.map(a => a.postfix()).join(' ')} ${this.operator})`;
    }
);

function createNewOperation(operation, operator, diffRule) {
    const CurrentOperation = function(...args) {
        return AbstractOperation.call(this, ...args);
    };

    CurrentOperation.arity = operation.length ? operation.length : Infinity;
    operationList[operator] = CurrentOperation;

    CurrentOperation.prototype = Object.create(AbstractOperation.prototype);
    CurrentOperation.prototype._operation = operation;
    CurrentOperation.prototype.operator = operator;
    CurrentOperation.prototype._diffRule = diffRule;
    CurrentOperation.prototype.constructor = CurrentOperation;
    return CurrentOperation;
}

const Add = createNewOperation((a, b) => a + b, "+",
    (dArgs) => new Add(dArgs[0], dArgs[1]));
const Subtract = createNewOperation((a, b) => a - b, "-",
    (dArgs) => new Subtract(dArgs[0], dArgs[1]));
const Multiply = createNewOperation((a, b) => a * b, "*",
    function (dArgs) {
        return new Add(new Multiply(dArgs[0], this.args[1]), new Multiply(this.args[0], dArgs[1]));
    });
const Divide = createNewOperation((a, b) => a / b, "/",
    function (dArgs) {
        return new Divide(new Subtract(new Multiply(dArgs[0], this.args[1]), new Multiply(this.args[0], dArgs[1])),
            new Multiply(this.args[1], this.args[1]));
    });
const Negate = createNewOperation(a => -a, "negate",
    (dArgs) => new Negate(dArgs[0]));
const Hypot = createNewOperation((a, b) => a * a + b * b, "hypot",
    function (dArgs) {
        return new Add(new Multiply(new Multiply(Const.TWO, this.args[0]), dArgs[0]),
            new Multiply(new Multiply(Const.TWO, this.args[1]), dArgs[1]));
    });
const HMean = createNewOperation((a, b) => 2 / (1 / a + 1 / b), "hmean",
    function (dArgs) {
        const denominator = new Add(this.args[0], this.args[1]);
        return new Divide(new Multiply(Const.TWO, new Add(
            new Multiply(new Multiply(this.args[1], this.args[1]), dArgs[0]),
            new Multiply(new Multiply(this.args[0], this.args[0]), dArgs[1]))), new Multiply(denominator, denominator));
    });

const _Sign = createNewOperation(a => Math.sign(a), "_sign",
    () => Const.ZERO);
const ArithMean = createNewOperation((...args) => args.reduce((a, b) => a + b) / args.length,
    "arith-mean",
    function (dArgs) {
        return new Multiply(new Const(1 / this.args.length), dArgs.reduce((a, b) => new Add(a, b)));
    });
const GeomMean = createNewOperation((...args) => Math.pow(Math.abs(args.reduce((a, b) => a * b)), 1 / args.length),
    "geom-mean",
    function (dArgs) {
        const pref = [];
        const len = this.args.length;
        this.args.forEach(a => pref.push(pref.length > 0 ? new Multiply(pref[pref.length - 1], a) : a));
        let numerator;
        let suf;
        for (let i = len - 1; i >= 0; --i) {
            let cur = dArgs[i];
            cur = i !== 0 ? new Multiply(pref[i - 1], cur) : cur;
            cur = i !== len - 1 ? new Multiply(cur, suf) : cur;
            suf = i !== len - 1 ? new Multiply(this.args[i], suf) : this.args[i];
            numerator = i === len - 1 ? cur : new Add(numerator, cur);
        }

        let denominator = Const.ONE;
        for (let i = 0; i !== len - 1; ++i) {
            denominator = i === 0 ? this : new Multiply(denominator, this);
        }

        return new Divide(
            new Multiply(new _Sign(suf), numerator),
            new Multiply(
                new Const(len),
                denominator
            )
        )
    });
const HarmMean = createNewOperation((...args) => args.length / (args.reduce((a, b) => a + 1 / b, 0)),
    "harm-mean",
    function (dArgs) {
        const len = this.args.length;
        let result = new Const(len);
        const inv = [];
        for (let i = 0; i !== len; ++i) {
            inv[i] = new Divide(Const.ONE, this.args[i]);
        }
        const denominator = new Divide(Const.ONE, inv.reduce((a, b) => new Add(a, b)));
        result = new Multiply(result, new Multiply(denominator, denominator));
        for (let i = 0; i !== len; ++i) {
            inv[i] = new Multiply(new Multiply(inv[i], inv[i]), dArgs[i]);
        }
        return new Multiply(result, inv.reduce((a, b) => new Add(a, b)));
    });


const parse = expression => {
    const stack = [];
    for (const item of expression.trim().split(/\s+/)) {
        if (item in variableList) {
            stack.push(variableList[item]);
        } else if (item in operationList) {
            stack.push(new operationList[item](...stack.splice(-operationList[item].arity)));
        } else {
            stack.push(new Const(+item));
        }
    }
    return stack.pop();
};



function createNewError(CurrentError, name) {
    CurrentError.prototype = Object.create(Error.prototype);
    CurrentError.prototype.name = name;
    CurrentError.prototype.constructor = CurrentError;
    return CurrentError;
}

function makeErrorMessage(position, message) {
    return `at position ${position + 1}: ${message}`;
}

function makeMismatchMessage(expected, found) {
    return `${expected} expected, but ${found} found`;
}

const InvalidItemError = createNewError(
    function (position, isItemExpected, found) {
        this.message = makeErrorMessage(position,
            isItemExpected ? makeMismatchMessage("Const/Variable/Expression", `'${found}'`)
                : `found unexpected item '${found}'`);
    },
    "InvalidItemError"
);

const MissingBracketError = createNewError(
    function (position, isOpening) {
        this.message = makeErrorMessage(position, `expected ${isOpening ? "opening" : "closing"} bracket`);
    },
    "MissingBracketError"
);

const UnsupportedOperationError = createNewError(
    function (position, name) {
        this.message = makeErrorMessage(position, `found unsupported operation '${name}'`);
    },
    "UnsupportedOperationError"
);

const ArityMismatchError = createNewError(
    function (position, operator, expected, found) {
        this.message = makeErrorMessage(position,
            makeMismatchMessage(`for '${operator}' operation ${expected === 0 ? "any" : expected} arguments`, found))
    },
    "ArityMismatchError"
);


function isStartOfExpression(ch) {
    return ch === '(';
}
function isEndOfExpression(ch) {
    return ch === ')';
}
function isBracket(ch) {
    return isStartOfExpression(ch) || isEndOfExpression(ch);
}

function prepareExpression(expression) {
    function isWhitespace(ch) {
        return /\s/.test(ch);
    }
    const result = [];
    let current = [];
    for (const it of (expression + ' ')) {
        if (isBracket(it) || isWhitespace(it)) {
            if (current.length > 0) {
                result.push(current.join(''));
            }
            if (isBracket(it)) {
                result.push(it);
            }
            while(current.length > 0) {
                current.pop();
            }
        } else {
            current.push(it);
        }
    }
    return result;
}

function ItemSource(expression) {
    this._expression = prepareExpression(expression);
    this._id = 0;
}
ItemSource.prototype.get = function (delta = 0) {
    return !this.isEOF(delta) ? this._expression[this._id + delta] : '';
};
ItemSource.prototype.getPosition = function () {
    return this._id;
};
ItemSource.prototype.next = function () {
    ++this._id;
};
ItemSource.prototype.isEOF = function (delta = 0) {
    return this._id + delta >= this._expression.length;
};
ItemSource.prototype.isStartOfExpression = function (delta = 0) {
    return isStartOfExpression(this.get(delta));
};
ItemSource.prototype.isEndOfExpression = function (delta = 0) {
    return isEndOfExpression(this.get(delta));
};
ItemSource.prototype.isOperation = function () {
    return this.get() in operationList;
};
ItemSource.prototype.isNumber = function () {
    return isFinite(this.get()) && this.get().length > 0;
}

const parseOperation = (source, _) => {
    if (source.isEOF() || source.isEndOfExpression()) {
        throw new InvalidItemError(source.getPosition(), true, source.isEOF() ? '' : source.get());
    }
    if (!source.isOperation()) {
        throw new UnsupportedOperationError(source.getPosition(), source.get());
    }
    const operation = operationList[source.get()];
    source.next();
    return operation;
};

const parseArgs = (source, mode) => {
    const args = [];
    while (!source.isEOF() && !source.isOperation() && !source.isEndOfExpression(mode)) {
        args.push(parseExpression(source, mode));
    }
    return args;
};

const itemOrder = [parseOperation, parseArgs];
/*
    Pred: mode - id of first parsed item in itemOrder[]
    Post: [operation, args]
*/
function getExpression(mode, source) {
    const result = [];
    result[mode] = itemOrder[mode](source, mode);
    result[mode ^ 1] = itemOrder[mode ^ 1](source, mode);
    return result;
}

const parseExpression = (source, mode) => {
    let result;
    if (source.isStartOfExpression()) {
        source.next();
        const [operation, args] = getExpression(mode, source);
        if (isFinite(operation.arity) && args.length !== operation.arity) {
            throw new ArityMismatchError(source.getPosition(), operation.prototype.operator, operation.arity, args.length);
        }
        if (!source.isEndOfExpression()) {
            throw new InvalidItemError(source.getPosition(), false, source.get());
        }
        result = new operation(...args);
    } else if (source.get() in variableList) {
        result = variableList[source.get()];
    } else if (source.isNumber()) {
        result = new Const(+source.get());
    } else {
        throw source.isOperation() ? new MissingBracketError(source.getPosition(), true) :
            new InvalidItemError(source.getPosition(), true, source.get());
    }
    source.next();
    return result;
};

const parseAbstract = (expression, mode) => {
    const source = new ItemSource(expression);
    const result = parseExpression(source, mode);
    if (!source.isEOF()) {
        throw new InvalidItemError(source.getPosition(), false, source.get());
    }
    return result;
};

const parsePrefix = expression => parseAbstract(expression, 0);
const parsePostfix = expression => parseAbstract(expression, 1);
