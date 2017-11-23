package com.evangel.lambda;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * This simple Swing program demonstrates how to use Lambda expressions in
 * action listener.
 *
 * @author www.codejava.net
 */
public class ListenerLambdaExample extends JFrame {
	private JButton button = new JButton("Click Me!");

	public ListenerLambdaExample() {
		super("Listener Lambda Example");
		getContentPane().setLayout(new FlowLayout());
		getContentPane().add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				System.out.println("Handled by anonymous class listener");
			}
		});
		button.addActionListener(e -> System.out
				.println("Handled by Lambda listener"));
		button.addActionListener(e -> {
			System.out.println("Handled Lambda listener");
			System.out.println("Have fun!");
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 100);
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new ListenerLambdaExample().setVisible(true);
			}
		});
	}
}