package app.wvetro.core;

public class Exvalteste {

	public Exvalteste() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		evalx eval = new evalx();	
		String valido = eval.eval("ARRED50(1460)", "[{\"name\":\"L\",\"value\":\"1\"},{\"name\":\"H\",\"value\":\"10\"},{\"name\":\"T\",\"value\":\"1\"},{\"name\":\"X\",\"value\":\"1\"},{\"name\":\"CMM\",\"value\":\"1\"},{\"name\":\"N\",\"value\":\"1\"},{\"name\":\"S\",\"value\":\"1\"}]","CODIGO DO ITEM","DESCRICAO DO ITEM");
		System.out.println(valido);
			
	}

}
