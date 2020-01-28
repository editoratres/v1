package editora3.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

 
import editora3.entidades.Subcanal;
import editora3.facade.SubCanalFacade;
import editora3.util.JsfUtil;
@Named("subcanalController") 
@RequestScoped
public class SubCanalController implements AbstractController<Subcanal> {

	@Inject
	FlashApp flashapp;
	
	@Inject
	private
	SubCanalFacade subcanalfacade;
	
	@Override
	public void excluir(Subcanal item) {
		// TODO Auto-generated method stub
		getSubCanalfacade().remove(item);
		setItens(null);
		
	}
	@PostConstruct
	public void iniciar() {
		setItens(null);
	}

	@Override
	public void prepararEditar(Subcanal item) {
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
		setItem(new Subcanal()); 
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fecharDialogo() {
		PrimeFaces.current().resetInputs("subcanalForm");
		getFlash().limparPorId("subcanalForm");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		try {
			
			Subcanal item = getItem();
		    item.setDescricao(item.getDescricao().toUpperCase());
		    
		    Subcanal localizarPorNome = getSubCanalfacade().localizarPorNome(item.getDescricao());
		    if(localizarPorNome!=null && localizarPorNome.getCodigo()!=item.getCodigo()) {
		    	JsfUtil.addErrorMessage("O Subcanal informado já foi cadastrado", "Procedimento não realizado");
		    	FacesContext.getCurrentInstance().validationFailed();
		    	atualizar();
		    	return;
		    }
		    
		   
		    
		    if(item.getCodigo()==null) {
		    	getSubCanalfacade().create(item);
		    	JsfUtil.addSuccessMessage("SubCanal criado com sucesso", "Procedimento OK");
		    }else {
		    	getSubCanalfacade().edit(item);
		    	JsfUtil.addSuccessMessage("SubCanal alterado com sucesso", "Procedimento OK");
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
	public Subcanal getItem() {
		// TODO Auto-generated method stub
		return (Subcanal)getFlash().getValoresPorID("subcanalForm").get("item");
	}

	@Override
	public void setItem(Subcanal item) {
		getFlash().getValoresPorID("subcanalForm").put("item",item);
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Subcanal> getItens() {
		ArrayList<Subcanal> itens = (ArrayList<Subcanal>) getFlash().getValoresPorID("subcanalForm").get("itens");
		if(itens==null) {
			itens = (ArrayList<Subcanal>) getSubCanalfacade().findAll();
			setItens(itens);
		}
		// TODO Auto-generated method stub
		return itens;
	}

	@Override
	public void setItens(List<Subcanal> itens) {
		// TODO Auto-generated method stub
		getFlash().getValoresPorID("subcanalForm").put("itens",itens);
		
	}

	public SubCanalFacade getSubCanalfacade() {
		return subcanalfacade;
	}

	public void setCanalfacade(SubCanalFacade subcanalfacade) {
		this.subcanalfacade = subcanalfacade;
	}

}
