"use strict";

const cnst = value => () => value;
const one = cnst(1);
const two = cnst(2);
const constList = {
    "one": one,
    "two": two
}

const variableList = ["x", "y", "z"];
const getVariable = id => (...args) => args[id];
const variable = value => {
    return getVariable(variableList.indexOf(value));
}

const getOperandsCount = []
const saveArgsCount = (names, arity) => {
    for (const item of names) {
        getOperandsCount[item] = arity;
    }
}
const abstractOperation = (operation, names, arity) => {
    saveArgsCount(names, arity);
    return (...args) => (...variables) => operation(...args.map(val => val(...variables)));
}
const add = abstractOperation((a, b) => a + b, ["+"], 2);
const subtract = abstractOperation((a, b) => a - b, ["-"], 2);
const multiply = abstractOperation((a, b) => a * b, ["*"], 2);
const divide = abstractOperation((a, b) => a / b, ["/"], 2);
const negate = abstractOperation(a => -a, ["negate"], 1);
const madd = abstractOperation((a, b, c) => a * b + c, ["madd", "*+"], 3);
const floor = abstractOperation(Math.floor, ["floor", "_"], 1);
const ceil = abstractOperation(Math.ceil, ["ceil", "^"], 1);
const operationList = {
    "+": add,
    "-": subtract,
    "*": multiply,
    "/": divide,
    "negate": negate,
    "madd": madd,
    "*+": madd,
    "floor": floor,
    "_": floor,
    "ceil": ceil,
    "^": ceil
}

const parse = expression => {
    let stack = [];
    for (const item of expression.trim().split(/\s+/)) {
        if (variableList.includes(item)) {
            stack.push(variable(item));
        } else if (item in operationList) {
            stack.push(operationList[item](...stack.splice(-getOperandsCount[item])));
        } else if (item in constList) {
            stack.push(constList[item]);
        } else {
            stack.push(cnst(+item));
        }
    }
    return stack.pop();
}

const test = () => {
    let expr = add(subtract(multiply(variable("x"), variable("x")),
        multiply(cnst(2), variable("x"))), cnst(1));
    for (let x = 0; x < 10; ++x) {
        console.log("expr(" + x + ") = " + expr(x, 0, 0));
    }
    console.log("parse(\"x x 2 - * x * 1 +\")(5) = " + parse("x x 2 - * x * 1 +")(5));
}

// test();