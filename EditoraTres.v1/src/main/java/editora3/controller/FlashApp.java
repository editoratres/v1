package editora3.controller;

import java.io.Serializable;
import java.util.HashMap;
 

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
@Named("flashApp")
@SessionScoped
public class FlashApp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<String, Object> valores = new HashMap<>();
	private HashMap<String,  HashMap<String, Object>> valoresPorID = new HashMap<>();

	public HashMap<String, Object> getValores() {
		return valores;
	}

	public void setValores(HashMap<String, Object> valores) {
		this.valores = valores;
	}
	public void limpar() {
		this.valores.clear();
		this.valoresPorID.clear();
	}

	public HashMap<String, Object> getValoresPorID(String idValores) {
		if(!valoresPorID.containsKey(idValores)) {
			valoresPorID.put(idValores, new HashMap<>());
		}
		return valoresPorID.get(idValores);
	}

	public void limparPorId(String idValores) {
		this.valoresPorID.get(idValores).clear();
	}
	public void setValoresPorID(HashMap<String,  HashMap<String, Object>> valoresPorID) {
		this.valoresPorID = valoresPorID;
	}
	
	
	
	
	
}
