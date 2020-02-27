package editora3.facade;

import java.util.List;

public class LazyObjetos<T> {

	private Integer TotalObjetos;
	private List<T> Lista;
	public Integer getTotalObjetos() {
		return TotalObjetos;
	}
	public void setTotalObjetos(Integer totalObjetos) {
		TotalObjetos = totalObjetos;
	}
	public List<T> getLista() {
		return Lista;
	}
	public void setLista(List<T> lista) {
		Lista = lista;
	}
}
