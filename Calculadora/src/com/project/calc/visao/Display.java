package com.project.calc.visao;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.project.calc.modelo.Memoria;
import com.project.calc.modelo.MemoriaObserver;

@SuppressWarnings("serial")
public class Display extends JPanel implements MemoriaObserver{
	
	private final JLabel label;
	
	public Display() {
		
		Memoria.getInstacia().adicionarObserver(this);
		
		setBackground(new Color(46, 49, 50));
		
		label = new JLabel(Memoria.getInstacia().getTexto());
		label.setForeground(Color.WHITE);
		label.setFont(new Font("courier", Font.PLAIN, 30));
		
		setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 20));
		
		add(label);

	}

	@Override
	public void valorAlterado(String valor) {
		
		label.setText(valor);
		
	}

}
