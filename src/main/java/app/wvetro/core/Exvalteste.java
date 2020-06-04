package app.wvetro.core;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Exvalteste {

	public Exvalteste() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		evalx eval = new evalx();	
		String valido = eval.eval("(IIF(T=1,((L-X-26-((N-1)*26))/N)-40,((L-X-40-((N-1)*27))/N))-16+32)>1000-(IF(T=1,((L-X-26-((N-1)*26))/N)-40,((L-X-40-((N-1)*27))/N))-16+32)>1000", "[{\"name\":\"L\",\"value\":\"1\"},{\"name\":\"H\",\"value\":\"10\"},{\"name\":\"T\",\"value\":\"1\"},{\"name\":\"X\",\"value\":\"1\"},{\"name\":\"CMM\",\"value\":\"1\"},{\"name\":\"N\",\"value\":\"1\"},{\"name\":\"S\",\"value\":\"1\"}]","CODIGO DO ITEM","DESCRICAO DO ITEM");
		System.out.println(valido);
	
		
		/*
		String varaibleadjusted = "(@+H )";
		varaibleadjusted = varaibleadjusted.replaceAll("([@]{1})", "XXX");
		System.out.println(varaibleadjusted);
		*/

	
	}

}
