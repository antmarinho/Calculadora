package com.project.calc.visao;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Calculadora extends JFrame{
	
	public Calculadora() {
		
		organizarLayout();
		
		setSize(232, 322);
		//setUndecorated(true); tira barra 
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void organizarLayout() {
		
		setLayout(new BorderLayout());
		
		Display dp = new Display();
		dp.setPreferredSize(new Dimension(233,60));
		add(dp, BorderLayout.NORTH);
		
		Teclado tc = new Teclado();
		add(tc, BorderLayout.CENTER);
		
	}

	public static void main(String[] args) {
		
		new Calculadora();
		
	}

}
