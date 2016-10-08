import java.awt.*;
import java.awt.event.*;
import java.util.EmptyStackException;

import javax.swing.*;

public class Gui extends JFrame{
	
	private static final Dimension ANSWERFIELDDIMENSION = new Dimension(450,50);
	private static final Font ANSWERFIELDFONT = new Font("SansSerif",Font.BOLD,20);
	private static final Dimension BUTTONDIMENSION = new Dimension(100,50);
	private static final Color CONTENTPANELCOLOR = Color.BLUE;
	
	private boolean lastResultCalculated = false;
	
	private JTextField answerField;
	private JButton zero,one,two,three,four,five,six,seven,eight,nine,
	add,subtract,multiply,divide,exponent,equals,leftParentheses,rightParentheses,dot,E,delete;
	private JPanel contentPanel;
	
	public Gui() {
		super("Calculator");
		
		answerField = new JTextField();
		answerField.setPreferredSize(ANSWERFIELDDIMENSION);
		answerField.setFont(ANSWERFIELDFONT);
		answerField.setBorder(BorderFactory.createEmptyBorder(0,0,0,50));
		
		zero = new JButton("0");
		one = new JButton("1");
		two = new JButton("2");
		three = new JButton("3");
		four = new JButton("4");
		five = new JButton("5");
		six = new JButton("6");
		seven = new JButton("7");
		eight = new JButton("8");
		nine = new JButton("9");
		
		add = new JButton("+");
		subtract = new JButton("-");
		multiply = new JButton("*");
		divide = new JButton("/");
		exponent = new JButton("^");
		
		equals = new JButton("=");
		
		leftParentheses = new JButton("(");
		rightParentheses = new JButton(")");
		
		dot = new JButton(".");
		
		E = new JButton("E");
		
		delete = new JButton("DEL");
		
		contentPanel = new JPanel();
		contentPanel.setBackground(CONTENTPANELCOLOR);
		contentPanel.setLayout(new FlowLayout());
		contentPanel.add(answerField, BorderLayout.NORTH);
		
		zero.setPreferredSize(BUTTONDIMENSION);
		one.setPreferredSize(BUTTONDIMENSION);
		two.setPreferredSize(BUTTONDIMENSION);
		three.setPreferredSize(BUTTONDIMENSION);
		four.setPreferredSize(BUTTONDIMENSION);
		five.setPreferredSize(BUTTONDIMENSION);
		six.setPreferredSize(BUTTONDIMENSION);
		seven.setPreferredSize(BUTTONDIMENSION);
		eight.setPreferredSize(BUTTONDIMENSION);
		nine.setPreferredSize(BUTTONDIMENSION);
		add.setPreferredSize(BUTTONDIMENSION);
		subtract.setPreferredSize(BUTTONDIMENSION);
		multiply.setPreferredSize(BUTTONDIMENSION);
		divide.setPreferredSize(BUTTONDIMENSION);
		exponent.setPreferredSize(BUTTONDIMENSION);
		equals.setPreferredSize(BUTTONDIMENSION);
		leftParentheses.setPreferredSize(BUTTONDIMENSION);
		rightParentheses.setPreferredSize(BUTTONDIMENSION);
		dot.setPreferredSize(BUTTONDIMENSION);
		E.setPreferredSize(BUTTONDIMENSION);
		delete.setPreferredSize(BUTTONDIMENSION);
		
		zero.addActionListener(new SymbolButtonOnClickListener());
		one.addActionListener(new SymbolButtonOnClickListener());
		two.addActionListener(new SymbolButtonOnClickListener());
		three.addActionListener(new SymbolButtonOnClickListener());
		four.addActionListener(new SymbolButtonOnClickListener());
		five.addActionListener(new SymbolButtonOnClickListener());
		six.addActionListener(new SymbolButtonOnClickListener());
		seven.addActionListener(new SymbolButtonOnClickListener());
		eight.addActionListener(new SymbolButtonOnClickListener());
		nine.addActionListener(new SymbolButtonOnClickListener());
		dot.addActionListener(new SymbolButtonOnClickListener());
		add.addActionListener(new OperatorButtonOnClickListener());
		subtract.addActionListener(new OperatorButtonOnClickListener());
		multiply.addActionListener(new OperatorButtonOnClickListener());
		divide.addActionListener(new OperatorButtonOnClickListener());
		exponent.addActionListener(new OperatorButtonOnClickListener());
		equals.addActionListener(new EqualButtonOnClickListener());
		leftParentheses.addActionListener(new SymbolButtonOnClickListener());
		rightParentheses.addActionListener(new SymbolButtonOnClickListener());
		E.addActionListener(new SymbolButtonOnClickListener());
		delete.addActionListener(new DeleteButtonOnClickListener());
		
		contentPanel.add(one);
		contentPanel.add(two);
		contentPanel.add(three);
		contentPanel.add(add);
		contentPanel.add(four);
		contentPanel.add(five);
		contentPanel.add(six);
		contentPanel.add(subtract);
		contentPanel.add(seven);
		contentPanel.add(eight);
		contentPanel.add(nine);
		contentPanel.add(multiply);
		contentPanel.add(leftParentheses);
		contentPanel.add(zero);
		contentPanel.add(rightParentheses);
		contentPanel.add(divide);
		contentPanel.add(exponent);
		contentPanel.add(dot);
		contentPanel.add(E);
		contentPanel.add(delete);
		contentPanel.add(equals);
		
		setContentPane(contentPanel);
	}
	
	private class SymbolButtonOnClickListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			JButton button = (JButton)event.getSource();
			if(lastResultCalculated)answerField.setText("");
			if(answerField.getText().length() > 0 && answerField.getCaretPosition() < answerField.getText().length()
					&& answerField.getCaretPosition() > 0) {
			    int caretPosition = answerField.getCaretPosition();
			    String newText = answerField.getText().substring(0, caretPosition).concat(button.getText())
					.concat(answerField.getText().substring(caretPosition, answerField.getText().length()));
			    answerField.setText(newText);
			}
			else if (answerField.getCaretPosition() == 0)answerField.setText(button.getText().
					concat(answerField.getText()));
			else {
				answerField.setText(answerField.getText().concat(button.getText()));
			}
			lastResultCalculated = false;
		}
		
	}
	
	private class OperatorButtonOnClickListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			JButton button = (JButton)event.getSource();
			//if(lastResultCalculated)answerField.setText("");
			if(answerField.getText().length() > 0 && answerField.getCaretPosition() < answerField.getText().length()
					&& answerField.getCaretPosition() > 0) {
			    int caretPosition = answerField.getCaretPosition();
			    String newText = answerField.getText().substring(0, caretPosition).concat(button.getText())
					.concat(answerField.getText().substring(caretPosition, answerField.getText().length()));
			    answerField.setText(newText);
			}
			else if (answerField.getCaretPosition() == 0)answerField.setText(button.getText().
					concat(answerField.getText()));
			else {
				answerField.setText(answerField.getText().concat(button.getText()));
			}
			lastResultCalculated = false;
		}
		
	}
	
	private class EqualButtonOnClickListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			Parser lexer = new Parser();
			try {
				double answer = lexer.evaluate(answerField.getText());
				if(answer % 1 == 0.0 && answer <= Integer.MAX_VALUE && answer >= Integer.MIN_VALUE) {
					answerField.setText(Integer.toString(((int)answer)));
				}
				else {
				    answerField.setText(Double.toString(answer));
				}
				lastResultCalculated = true;
			} catch (SyntaxException e) {
				answerField.setText(e.getMessage());
				lastResultCalculated = true;
			}
			  catch (EmptyStackException e1) {
				  answerField.setText("Malformed syntax.");
				  lastResultCalculated = true;
			  }
		}
		
	}
	
	private class DeleteButtonOnClickListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			answerField.setText("");
			lastResultCalculated = false;
		}
		
	}
	
}
