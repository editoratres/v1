package editora3.util;

public class TratarNulos<T> {
	 public T Tratar(T Valor, T ValorPadrao)
	    {
	        T Retorno=null;

	        if(Valor==null)        
	            Retorno = ValorPadrao;        
	        else        
	            Retorno=Valor;
	        
	       return Retorno;
	    }
}
