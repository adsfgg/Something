package interpreters;

import exceptions.ParserException;
import exceptions.SyntaxException;
import exceptions.math.ArgumentException;
import exceptions.math.ConstantNotFoundException;
import exceptions.math.FunctionNotFoundException;
import exceptions.math.MismatchedParenthesisException;
import exceptions.math.OperatorMismatchException;
import helpers.MathHelper;
import postfix.EnumFunctions;

import java.util.HashMap;
import java.util.Stack;

public class PostfixInterpreter {
    private static final String SYMBOLS = "+-*/^(),!";
    private HashMap<String, Byte> precedenceTable = new HashMap<>();
    
    public PostfixInterpreter() {
        precedenceTable.put("+", (byte) 2);
        precedenceTable.put("-", (byte) 2);
        precedenceTable.put("*", (byte) 3);
        precedenceTable.put("/", (byte) 3);
        precedenceTable.put("^", (byte) 4);
    }
    
    public double parse(String infix) throws ParserException {
        infix = this.format(infix);
        String postfix = this.toPostfix(infix);
        
        return this.run(postfix);
    }
    
    private String format(String input) {
        for(char c : SYMBOLS.toCharArray()) {
            input = input.replace(String.valueOf(c), " " + c + " ");
        }
        
        input = input.replaceAll("[ ]+", " ");
        
        return input.trim();
    }
    
    private String toPostfix(String infix) throws OperatorMismatchException, MismatchedParenthesisException, SyntaxException {
        StringBuilder postfix = new StringBuilder();
        Stack<String> stack = new Stack<>();
        //        boolean negativeFunc = false;
        
        String[] split = infix.split(" ");
        
        boolean neg = false;
        
        for(int i = 0, splitLength = split.length; i < splitLength; i++) {
            String token = split[i];
            if(MathHelper.isNumber(token) || MathHelper.isConst(token)) {
                if(neg) postfix.append("-").append(token);
                else postfix.append(token);
                neg = false;
            }
            else if(MathHelper.isFunction(token)) {
                stack.push(token);
            }
            else if(token.equals(",")) {
                postfix.append(" ");
                while(!stack.empty() && !stack.peek().equals("(")) {
                    postfix.append(stack.pop()).append(" ");
                }
            }
            else if(MathHelper.isOperator(token)) {
                if(token.equals("-")) {
                    if(i == 0) {
                        neg = true;
                        continue;
                    }
                }
                
                postfix.append(" "); // add a space to split the numbers
                while(!stack.empty() && MathHelper.isOperator(stack.peek()) && ((!token.equals("^") && precedenceTable.get(token) <= precedenceTable.get(stack.peek())) || (token.equals("^") && precedenceTable.get(token) < precedenceTable.get(stack.peek())))) {
                    postfix.append(stack.pop()).append(" ");
                }
                stack.push(token);
            }
            else if(token.equals("!")) {
                if(i == 0) throw new OperatorMismatchException();
                postfix.append(" !");
            }
            else if(token.equals("(")) {
                stack.push(token);
            }
            else if(token.equals(")")) {
                if(stack.empty()) {
                    throw new MismatchedParenthesisException();
                }
                while(!stack.peek().equals("(")) {
                    postfix.append(" ").append(stack.pop());
                    if(stack.empty()) {
                        throw new MismatchedParenthesisException();
                    }
                }
                stack.pop(); // pop the opening parenthesis off the stack, but not on the output queue.
                if(!stack.empty() && MathHelper.isFunction(stack.peek())) {
                    postfix.append(" ").append(stack.pop());
                }
            }
            else {
                throw new SyntaxException(String.format("Unrecognised token \"%s\"", token));
            }
        }
        
        postfix.append(" ");
        
        while(!stack.empty()) {
            if((stack.peek()).matches("[(|)]")) throw new MismatchedParenthesisException();
            postfix.append(stack.pop()).append(" ");
        }
        
        return postfix.toString().trim();
    }
    
    private double run(String postfix) throws OperatorMismatchException, SyntaxException, ArgumentException, ConstantNotFoundException, FunctionNotFoundException {
        Stack<Double> stack = new Stack<>();
        
        if(postfix.equals("")) throw new IllegalArgumentException("Postfix cannot be empty");
        
        for(String s : postfix.split(" ")) {
            if(MathHelper.isNumber(s)) {
                stack.push(Double.parseDouble(s));
            }
            else if(MathHelper.isConst(s)) {
                stack.push(MathHelper.getConst(s).getValue());
            }
            else if(MathHelper.isOperator(s)) {
                if(stack.size() < 2) throw new OperatorMismatchException();
                
                double num2 = stack.pop();
                double num1 = stack.pop();
                
                switch(s) {
                    case "+":
                        stack.push(num1 + num2);
                        break;
                    case "-":
                        stack.push(num1 - num2);
                        break;
                    case "*":
                        stack.push(num1 * num2);
                        break;
                    case "/":
                        stack.push(num1 / num2);
                        break;
                    case "^":
                        stack.push(Math.pow(num1, num2));
                        break;
                }
            }
            else if(MathHelper.isFunction(s)) {
                execFunc(stack, s, false);
            }
            else if(s.equals("!")) {
                long num = stack.pop().longValue();
                
                if(num < 0) {
                    stack.push((double) -MathHelper.factorial(-num));
                }
                else {
                    stack.push((double) MathHelper.factorial(num));
                }
            }
            else if(s.startsWith("-")) {
                s = s.substring(1);
                
                if(MathHelper.isFunction(s)) {
                    stack = execFunc(stack, s, true);
                }
                else if(MathHelper.isConst(s)) {
                    stack.push(-MathHelper.getConst(s).getValue());
                }
                else {
                    throw new SyntaxException(String.format("Unrecognised token \"%s\".", s));
                }
            }
            else {
                throw new SyntaxException(String.format("Unrecognised token \"%s\".", s));
            }
        }
        
        if(stack.size() > 1) throw new ArgumentException();
        
        double result = stack.pop();
        
        if(MathHelper.isInt(result)) result = Math.round(result);
        
        return result;
    }
    
    private Stack<Double> execFunc(Stack<Double> stack, String s, boolean negative) throws ArgumentException, FunctionNotFoundException {
        EnumFunctions func = MathHelper.getFunction(s);
        int argc = func.getArgc();
        double[] argv = new double[argc];
        
        for(int i = argc; i > 0; i--) {
            if(stack.empty()) throw new ArgumentException(String.format("function \"%s\"", s), argc, argc - i);
            argv[i - 1] = stack.pop(); // the array starts from zero.
        }
        
        if(negative) {
            stack.push(-func.run(argv));
        }
        else {
            stack.push(func.run(argv));
        }
        
        return stack;
    }
}
