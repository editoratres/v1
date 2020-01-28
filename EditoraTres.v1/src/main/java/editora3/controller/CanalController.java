package editora3.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import editora3.entidades.Canal;
import editora3.facade.CanalFacade;
import editora3.util.JsfUtil;
@Named("canalController") 
@RequestScoped
public class CanalController implements AbstractController<Canal> {

	@Inject
	FlashApp flashapp;
	
	@Inject
	private
	CanalFacade canalfacade;
	
	@Override
	public void excluir(Canal item) {
		// TODO Auto-generated method stub
		getCanalfacade().remove(item);
		setItens(null);
		
	}
	@PostConstruct
	public void iniciar() {
		setItens(null);
	}

	@Override
	public void prepararEditar(Canal item) {
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
		setItem(new Canal()); 
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fecharDialogo() {
		PrimeFaces.current().resetInputs("canalForm");
		getFlash().limparPorId("canalForm");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		try {
			
		    Canal item = getItem();
		    item.setDescricao(item.getDescricao().toUpperCase());
		    
		    Canal localizarPorNome = getCanalfacade().localizarPorNome(item.getDescricao());
		    if(localizarPorNome!=null && localizarPorNome.getCodigo()!=item.getCodigo()) {
		    	JsfUtil.addErrorMessage("O canal informado já foi cadastrado", "Procedimento não realizado");
		    	FacesContext.getCurrentInstance().validationFailed();
		    	atualizar();
		    	return;
		    }
		    
		   
		    
		    if(item.getCodigo()==null) {
		    	getCanalfacade().create(item);
		    	JsfUtil.addSuccessMessage("Canal criado com sucesso", "Procedimento OK");
		    }else {
		    	getCanalfacade().edit(item);
		    	JsfUtil.addSuccessMessage("Canal alterado com sucesso", "Procedimento OK");
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
	public Canal getItem() {
		// TODO Auto-generated method stub
		return (Canal)getFlash().getValoresPorID("canalForm").get("item");
	}

	@Override
	public void setItem(Canal item) {
		getFlash().getValoresPorID("canalForm").put("item",item);
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Canal> getItens() {
		ArrayList<Canal> itens = (ArrayList<Canal>) getFlash().getValoresPorID("canalForm").get("itens");
		if(itens==null) {
			itens = (ArrayList<Canal>) getCanalfacade().findAll();
			setItens(itens);
		}
		// TODO Auto-generated method stub
		return itens;
	}

	@Override
	public void setItens(List<Canal> itens) {
		// TODO Auto-generated method stub
		getFlash().getValoresPorID("canalForm").put("itens",itens);
		
	}

	public CanalFacade getCanalfacade() {
		return canalfacade;
	}

	public void setCanalfacade(CanalFacade canalfacade) {
		this.canalfacade = canalfacade;
	}

}
