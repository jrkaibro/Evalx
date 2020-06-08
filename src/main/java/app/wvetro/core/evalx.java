package app.wvetro.core;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.udojava.evalex.AbstractFunction;
import com.udojava.evalex.Expression;

public class evalx {
	
	 private JSONObject jsonreturn; 
	 

	public String eval(String expressionoriginal, String variable, String title, String description) {
	    
	   String varToString =  "{\"variavel\":"+ variable.toString() +"}";
	   
	   expressionoriginal = expressionoriginal.toUpperCase();
		
	   String expression =  adjust(expressionoriginal);
	   varToString        = adjust(varToString);
	   
	   if (!expression.isEmpty()) {
		
	   Expression exp = new Expression(expression);
	   
	   
	   exp.addFunction(new AbstractFunction("INT", 1) {
		 	public BigDecimal eval(List<BigDecimal> parameters) {
						
		    			if (parameters.size() == 0) {
							System.out.println("INT requires at least one parameter");
						}
						
						BigDecimal value = new BigDecimal(0);
						
						for (BigDecimal parameter : parameters) {
							value = value.add(parameter);
						}					
					     			    
						BigDecimal retorno          = new BigDecimal(0);
						
						retorno = value.setScale(0, RoundingMode.DOWN);
								
					    return retorno;
		    }
		});
	   
	   
	   exp.addFunction(new AbstractFunction("ARRED50", 1) {
		 	public BigDecimal eval(List<BigDecimal> parameters) {
						
		    			if (parameters.size() == 0) {
							System.out.println("ARRED50 requires at least one parameter");
						}
						
						BigDecimal value = new BigDecimal(0);
						
						for (BigDecimal parameter : parameters) {
							value = value.add(parameter);
						}					
					     			    
						BigDecimal medida 		    = value;
						BigDecimal arredondamento   = new BigDecimal("50");
						
						BigDecimal divisor 		    = new BigDecimal(0);
						BigDecimal resto            = new BigDecimal(0);
						BigDecimal retorno          = new BigDecimal(0);
						BigDecimal r          		= new BigDecimal(0);
						
						divisor = medida.divide(arredondamento).setScale(0, RoundingMode.DOWN);
						resto   = medida.divide(arredondamento).subtract(divisor);
						
						if (resto.compareTo(r) > 0) {
							divisor = divisor.add(new BigDecimal ("1"));
						}
						
						retorno = divisor.multiply(arredondamento);					
					    return retorno;
		    }
		});
	   
	   
	   exp.addFunction(new AbstractFunction("TRUNC", 1) {
		    public BigDecimal eval(List<BigDecimal> parameters) {
						if (parameters.size() == 0) {
							System.out.println("TRUNC requires at least one parameter");
						}
						BigDecimal value = new BigDecimal(0);
						for (BigDecimal parameter : parameters) {
							value = value.add(parameter);
						}					
						
						 BigDecimal bd = new BigDecimal((value.toString()));
					     bd = bd.setScale(0,BigDecimal.ROUND_DOWN);
					   
					     return bd;
		    }
		});
	   
	   exp.addFunction(new AbstractFunction("ARREDMAIOR", 1) {
		    public BigDecimal eval(List<BigDecimal> parameters) {
						if (parameters.size() == 0) {
							System.out.println("ARREDMAIOR requires at least one parameter");
						}
						BigDecimal arredmaior = new BigDecimal(0);
						for (BigDecimal parameter : parameters) {
								arredmaior = arredmaior.add(parameter);
						}
						
						Double rounded = Math.ceil(arredmaior.doubleValue());
						BigDecimal calculed = new BigDecimal(rounded);
						
						return calculed;
		    }
		});
	 	  
	   JSONObject obj = new JSONObject(varToString);	   
	   JSONArray arr  = obj.getJSONArray("variavel");
	   
	   
		for (int i = 0; i < arr.length(); i++)
		{
			String name  = arr.getJSONObject(i).getString("name");
			String value = arr.getJSONObject(i).getString("value");
	
			if ( value != null ){
				exp.setVariable(name.trim(),value.trim());	
			} else {
				System.out.println(name + ":" + value);
			}
			
		}
		
		try {
			
			BigDecimal resultmath = exp.eval();	
			
			jsonreturn = returnjson(title,
									resultmath.toEngineeringString(),
									description,
									expressionoriginal,
									exp.getExpression(),
									"0",
									"SUCCESS");
			
			return jsonreturn.toString();
			
		} catch (Exception e) {
			
			
			System.out.println("------------------------ERROR---------------------------");
			System.out.println("Codigo              : " + title);
			System.out.println("Descriçãoo          : " + description);
			System.out.println("Expression original : " + expressionoriginal);
			System.out.println("Expression runtime  : " + exp.getExpression());
			System.out.println("Variable used       : " + exp.getUsedVariables());
			System.out.println("Variable declared   : " + exp.getDeclaredVariables());
			System.out.println("ERROR               : " + e.getMessage());
			System.out.println("---------------------------------------------------------");
			
			
			jsonreturn = returnjson(title,
						"0.00",
						description,
						expressionoriginal,
						exp.getExpression(),
						"1",
						e.getMessage());
			
		  return jsonreturn.toString();
		}
	   } else {
		   
		   jsonreturn = returnjson(title,
									"0.00",
									description,
									expressionoriginal,
									"Expression not found",
									"1",
									"is empty");
		
		   return jsonreturn.toString();
		   
	   }
	   
	}
	
	private String adjust(String varaibleadjusted) {
		
			varaibleadjusted = varaibleadjusted.replaceAll("([IIF]{3})", "IF");
			varaibleadjusted = varaibleadjusted.replaceAll("([AND]{3})","&&");
			varaibleadjusted = varaibleadjusted.replaceAll("(\\W([OR]{2})\\W)","||");
			varaibleadjusted = varaibleadjusted.replaceAll("([@]{1})","XXX");
			varaibleadjusted = varaibleadjusted.replaceAll("([#]{1})","XXXX");
			
		
		return varaibleadjusted;
	}

	
	private JSONObject returnjson(String title, 
								  String value, 
								  String description, 
								  String OriginalExpression, 
								  String ExpresseonUsed, 
								  String Error,
								  String ErrorMessage) {
		
		
		JSONObject item = new JSONObject();
		
		item.put("title",  title);
		item.put("value",  value.toString());
		item.put("description",  description);
		item.put("originalexpression",  OriginalExpression);
		item.put("usedexpresseon",  ExpresseonUsed);
		item.put("error", Error);
		item.put("errormessage", ErrorMessage);
		
		return item;
	}
	
	public boolean isNumeric(String str, Class<? extends Number> clazz)
	{
		try
		{
			if (clazz.equals(Byte.class)) {
				Byte.parseByte(str);
			}
			else if (clazz.equals(Double.class)) {
				Double.parseDouble(str);
			}
			else if (clazz.equals(Float.class)) {
				Float.parseFloat(str);
			}
			else if (clazz.equals(Integer.class)) {
				Integer.parseInt(str);
			}
			else if (clazz.equals(Long.class)) {
				Long.parseLong(str);
			}
			else if (clazz.equals(Short.class)) {
				Short.parseShort(str);
			}
		}
		catch (NumberFormatException nfe) {
			System.out.println(str + " is not a valid number.");
			return false;
		}
	 	return true;
	}
	
}
