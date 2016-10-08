import java.util.*;
import java.util.regex.*;

public class Parser {
	
	private static Map<String,OperatorInfo> operators = new HashMap<String,OperatorInfo>();
	
	public Parser() {
		operators.put("^",new OperatorInfo(4,false));
		operators.put("*",new OperatorInfo(3,true));
		operators.put("/",new OperatorInfo(3,true));
		operators.put("+",new OperatorInfo(2,true));
		operators.put("-",new OperatorInfo(2,true));
	}
	
	private boolean isValidInput(String input) {
		Pattern p = Pattern.compile(TokenType.NUMBER.pattern);
		Matcher m = p.matcher(input);
		if(!m.find()) {
			return false;
		}
		Pattern p1 = Pattern.compile(TokenType.OPERATOR.pattern);
		Matcher m1 = p1.matcher(input);
		if(m1.find(input.length()-1)) {
			return false;
		}
		return true;
	}
	
	public double evaluate(String input) throws SyntaxException{
		
		if(!isValidInput(input))throw new SyntaxException();
		
		if(input.length() > 1 && (input.startsWith("-") || input.startsWith("+")) || input.startsWith(".")) {
			input = "0".concat(input);
		}
		/*Dealing with negation.*/
		String[] arr = input.split("\\(-");
		String updatededInput = "";
		for (int i = 0; i < arr.length - 1; i++) {
			arr[i] = arr[i].concat("(0-");
			updatededInput = updatededInput.concat(arr[i]);
		}
		/**/
		updatededInput = updatededInput.concat(arr[arr.length - 1]);
		input = updatededInput;
		Queue<Parser.Token> tokens = getTokens(input);
		Stack<Double> tokenStack = new Stack<>();
		for (Token token : tokens) {
			if(token.type == TokenType.NUMBER)tokenStack.push((double)token.value);
			else if (token.type == TokenType.OPERATOR){
			    if(tokenStack.size() >= 2) {
			    	double num1 = tokenStack.pop();
			    	double num2 = tokenStack.peek();
			    	double retVal = 0.0;
			    	switch((String)token.value){
			    	    case "^":
			    	    	retVal = Math.pow(num2, num1);
			    	        break;
			    	    case "*":
			    	    	retVal = num2 * num1;
			    	    	break;
			    	    case "/":
			    	    	retVal = num2/num1;
			    	    	break;
			    	    case "+":
			    	    	retVal = num2 + num1;
			    	    	break;
			    	    case "-":
			    	    	retVal = num2 - num1;
			    	    	break;
			    	}
			    	tokenStack.pop();
			    	tokenStack.push(retVal);
			    }
			}
		}
		return tokenStack.pop();
	}
	
	private Queue getTokens(String input) throws SyntaxException {
		String regExp = "";
		for(TokenType tokenType : TokenType.values()) {
			regExp = regExp.concat("(?<" + tokenType.name() + ">"  + tokenType.pattern + ")|");
		}
		
		final Pattern tokenPattern = Pattern.compile(regExp);
		Matcher matcher = tokenPattern.matcher(input);
		boolean lastTokenOperator = false;//Two consecutive operators not allowed.
		/* Shunting-yard algorithm. https://www.wikiwand.com/en/Shunting-yard_algorithm#/The_algorithm_in_detail
		  */
		Queue<Token> output = new ArrayDeque();
		Stack<Token> operatorStack = new Stack<Token>();
		while(matcher.find()) {
			System.out.println("Another token");
			if(matcher.group(TokenType.NUMBER.name()) != null) {
				lastTokenOperator = false;
				System.out.println("NUMBER:" + matcher.group());
				output.add(new Token(Double.parseDouble(matcher.group()),TokenType.NUMBER));
			}
			else if(matcher.group(TokenType.OPERATOR.name()) != null) {
				if(lastTokenOperator)throw new SyntaxException();
				lastTokenOperator = true;
				Token t = new Token(matcher.group(),TokenType.OPERATOR);
				while(!operatorStack.isEmpty() && operatorStack.peek().type == TokenType.OPERATOR) {
					Token lastOperator = operatorStack.peek();
					if ((operators.get(t.value).isLeftAssociative() &&
						operators.get(t.value).precedence <= operators.get(lastOperator.value).precedence) ||
						(!operators.get(t.value).isLeftAssociative() && 
						 operators.get(t.value).getPrecedence() < operators.get(lastOperator.value).getPrecedence())) {
						    output.add(operatorStack.pop());
					}
					else break;
				}
				operatorStack.push(t);
			}
			else if(matcher.group(TokenType.LEFTPARENTHESES.name()) != null) {
				lastTokenOperator = false;
				operatorStack.push(new Token(matcher.group(),TokenType.LEFTPARENTHESES));
			}
			else if(matcher.group(TokenType.RIGHTPARENTHESES.name()) != null) {
				lastTokenOperator = false;
				Token t = new Token(matcher.group(TokenType.RIGHTPARENTHESES.name()),TokenType.RIGHTPARENTHESES);
				while(!operatorStack.isEmpty() && operatorStack.peek().type != TokenType.LEFTPARENTHESES) {
					output.add(operatorStack.pop());
				}
				if(operatorStack.empty())throw new SyntaxException();
				if (operatorStack.peek().type == TokenType.LEFTPARENTHESES)operatorStack.pop();
			}
			else if (!matcher.hitEnd()) {
				throw new SyntaxException();
			}
		}
		while(!operatorStack.isEmpty()) {
			if(operatorStack.peek().type == TokenType.LEFTPARENTHESES || 
					operatorStack.peek().type == TokenType.RIGHTPARENTHESES)throw new SyntaxException();
			output.add(operatorStack.pop());
		}
		for(Token t : output) {
			System.out.println(t.value);
		}
		return output;
	}
		
	public class Token{
		private Object value;
		private TokenType type;
		
		public Token(Object value, TokenType type) {
			this.value = value;
			this.type = type;
		}
	}
	
	private enum TokenType{
		NUMBER("[0-9]+(?:\\.[0-9]+)?(?:E-?[0-9]+(?://.[0-9]+)?)?"),
		OPERATOR("[\\^|\\*|/|+|-]"),
		LEFTPARENTHESES("\\("),
		RIGHTPARENTHESES("\\)");
		
		private final String pattern;
		
		private TokenType(String pattern) {
			this.pattern = pattern;
		}
	}
	
	private class OperatorInfo{
		private int precedence;
		private boolean leftAssociative;
		private OperatorInfo(int precedence, boolean leftAssociative) {
			this.precedence = precedence;
			this.leftAssociative = leftAssociative;
		}		
		private int getPrecedence(){return precedence;}
		private boolean isLeftAssociative(){return leftAssociative;}
	}

}
