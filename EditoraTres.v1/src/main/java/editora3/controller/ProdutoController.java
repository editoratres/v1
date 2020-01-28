package editora3.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import editora3.entidades.Produto;
import editora3.facade.ProdutoFacade;
import editora3.util.JsfUtil;
@Named("produtoController") 
@RequestScoped
public class ProdutoController implements AbstractController<Produto> {

	@Inject
	FlashApp flashapp;
	
	@Inject
	private
	ProdutoFacade produtofacade;
	
	@Override
	public void excluir(Produto item) {
		// TODO Auto-generated method stub
		getProdutofacade().remove(item);
		setItens(null);
		
	}
	@PostConstruct
	public void iniciar() {
		setItens(null);
	}

	@Override
	public void prepararEditar(Produto item) {
		setItem(item);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void atualizar() {
		// TODO Auto-generated method stub
		setItens(null);
		
	}

	@Override
	public void prepararNovo() {
		setItem(new Produto()); 
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fecharDialogo() {
		PrimeFaces.current().resetInputs("produtoForm");
		getFlash().limparPorId("produtoForm");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		try {
			
			Produto item = getItem();
		    item.setDescricao(item.getDescricao().toUpperCase());
		    
		    Produto localizarPorNome = getProdutofacade().localizarPorNome(item.getDescricao());
		    if(localizarPorNome!=null && localizarPorNome.getCodigo()!=item.getCodigo()) {
		    	JsfUtil.addErrorMessage("O Produto informado já foi cadastrado", "Procedimento não realizado");
		    	FacesContext.getCurrentInstance().validationFailed();
		    	atualizar();
		    	return;
		    }
		    
		   
		    
		    if(item.getCodigo()==null) {
		    	getProdutofacade().create(item);
		    	JsfUtil.addSuccessMessage("Produto criado com sucesso", "Procedimento OK");
		    }else {
		    	getProdutofacade().edit(item);
		    	JsfUtil.addSuccessMessage("Produto alterado com sucesso", "Procedimento OK");
		    }
		    atualizar();
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "create");
			// TODO: handle exception
		}
		
	}

	@Override
	public FlashApp getFlash() {
		// TODO Auto-generated method stub
		
		return this.flashapp;
	}

	@Override
	public void setFlash(FlashApp flash) {
		this.flashapp=flash;
		// TODO Auto-generated method stub
		
	}

	@Override
	public Produto getItem() {
		// TODO Auto-generated method stub
		return (Produto)getFlash().getValoresPorID("produtoForm").get("item");
	}

	@Override
	public void setItem(Produto item) {
		getFlash().getValoresPorID("produtoForm").put("item",item);
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Produto> getItens() {
		ArrayList<Produto> itens = (ArrayList<Produto>) getFlash().getValoresPorID("produtoForm").get("itens");
		if(itens==null) {
			itens = (ArrayList<Produto>) getProdutofacade().findAll();
			setItens(itens);
		}
		// TODO Auto-generated method stub
		return itens;
	}

	@Override
	public void setItens(List<Produto> itens) {
		// TODO Auto-generated method stub
		getFlash().getValoresPorID("produtoForm").put("itens",itens);
		
	}

	public ProdutoFacade getProdutofacade() {
		return produtofacade;
	}

	public void setProdutofacade(ProdutoFacade produtofacade) {
		this.produtofacade = produtofacade;
	}

}
