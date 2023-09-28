package com.project.calc.modelo;

import java.util.ArrayList;
import java.util.List;

public class Memoria {
	
	private enum TipoComando {
		
		ZERAR, NUMERO, DIV, MULT, SUB, SOMA, IGUAL, VIRGULA;
			
	};
	
	private static final Memoria instacia = new Memoria();
	
	private final List<MemoriaObserver> ob = new ArrayList<>();
	
	private String texto = "";
	
	private Memoria() {
		
		
	}
	
	public void adicionarObserver(MemoriaObserver o) {
		
		ob.add(o);
		
	}
	
	public void processarComando(String valor) {
		
		TipoComando tipo = dectarTipo(valor);
		
		
		if("AC".equals(valor))
			texto = "";
		else
			texto += valor;
		
		ob.forEach(o -> o.valorAlterado(getTexto()));
	
		
	}

	private TipoComando dectarTipo(String valor) {
		
		if(valor.isEmpty() && valor == "0")
			return null;
		
		try {
			
			Integer.parseInt(valor);
			return TipoComando.NUMERO;
				
		} catch (NumberFormatException e) {
			
			if("AC".equals(valor))
				return TipoComando.ZERAR;
			else if("/".equals(valor))
				return TipoComando.DIV;
			else if("*".equals(valor))
				return TipoComando.MULT;
			else if("-".equals(valor))
				return TipoComando.SUB;
			else if("+".equals(valor))
				return TipoComando.SOMA;
			else if("=".equals(valor))
				return TipoComando.IGUAL;
			else if(",".equals(valor))
				return TipoComando.VIRGULA;
			
		}
		
		return null;
		
	}

	public String getTexto() {
		
		return texto.isEmpty() ? "0" : texto;
	}

	public static Memoria getInstacia() {
		
		return instacia;
	}
	
}
