package com.project.calc.modelo;

import java.util.ArrayList;
import java.util.List;

public class Memoria {
	
	private enum TipoComando {
		
		ZERAR, NUMERO, DIV, MULT, SUB, SOMA, IGUAL, VIRGULA, PORCENT, INVERTER;
			
	};
	
	private static final Memoria instacia = new Memoria();
	
	private final List<MemoriaObserver> ob = new ArrayList<>();
	
	private TipoComando ultimaOp = null;
	private boolean substituir = false;
	private String texto = "";
	private String textoBuffer = "";
	
	private Memoria() {
		
		
	}
	
	public void adicionarObserver(MemoriaObserver o) {
		
		ob.add(o);
		
	}
	
	public void processarComando(String valor) {
		
		TipoComando tipo = dectarTipo(valor);
		
		if(tipo == null)
			return;
		else if(tipo == TipoComando.ZERAR) {
			
			texto = "";
			textoBuffer = "";
			substituir = false;
			ultimaOp = null;
			
		}
		else if(tipo == TipoComando.INVERTER && texto.contains("-"))
			texto = texto.substring(1);
		
		else if(tipo == TipoComando.INVERTER && !texto.contains("-")) 
			texto = "-" + texto;
		
		else if(tipo == TipoComando.NUMERO || tipo == TipoComando.VIRGULA) {
			
			texto = substituir ? valor : texto + valor;
			substituir = false;
			
		}
		else {
			
			substituir = true;
			texto = obterResulOp();
			textoBuffer = texto;
			ultimaOp = tipo;
			
		}
		
		ob.forEach(o -> o.valorAlterado(getTexto()));
	
		
	}

	private String obterResulOp() {
		
		String resulStr;
		
		if(ultimaOp == null || ultimaOp == TipoComando.IGUAL)
			return texto;
		
		double numeroBuffer = Double.parseDouble(textoBuffer.replace(",", "."));
		
		double numeroAtual = Double.parseDouble(texto.replace(",", "."));
		
		double resul = 0;
		
		if(ultimaOp == TipoComando.SOMA)
			resul = numeroAtual + numeroBuffer;
		else if(ultimaOp == TipoComando.SUB)
			resul = numeroBuffer - numeroAtual;
		else if(ultimaOp == TipoComando.MULT)
			resul = numeroBuffer * numeroAtual;
		else if(ultimaOp == TipoComando.DIV)
			resul = numeroBuffer / numeroAtual;
		
		resulStr = Double.toString(resul).replace(".", ",");
		
		boolean n = resulStr.endsWith(",0");
		
		return n ? resulStr.replace(",0", "") : resulStr;
				
	}

	private TipoComando dectarTipo(String valor) {
		
		if(valor.isEmpty() && valor == "0")
			return null;
		else if(texto.equals("0") && valor == "0")
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
			else if(",".equals(valor) && !texto.contains(","))
				return TipoComando.VIRGULA;
			else if("+/-".equals(valor) && !texto.contains(","))
				return TipoComando.INVERTER;
			/*else if("%".equals(valor) && !texto.contains(","))
				return TipoComando.PORCENT;*/
			
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
