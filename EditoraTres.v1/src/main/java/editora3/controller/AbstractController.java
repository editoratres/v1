package editora3.controller;

import java.util.List;

public interface AbstractController<T> {
	public void excluir(T item) ; 
	public void prepararEditar(T item) ; 
	public void atualizar() ; 
	public void prepararNovo();
	public void fecharDialogo();
	public void create() ;
	public FlashApp getFlash();
	public void setFlash(FlashApp flash);
	public T getItem();  
	public void setItem(T item);
	public List<T> getItens() ;
	public void setItens(List<T> itens);
}
