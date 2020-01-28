package editora3.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import editora3.entidades.Brinde;
import editora3.facade.BrindeFacade;
import editora3.util.JsfUtil;
@Named("brindeController") 
@RequestScoped
public class BrindeController implements AbstractController<Brinde> {

	@Inject
	FlashApp flashapp;
	
	@Inject
	private
	BrindeFacade brindefacade;
	
	@Override
	public void excluir(Brinde item) {
		int ret = getBrindefacade().movimentacoesBrinde(item.getCodigo());
		if(ret==0) {
			// TODO Auto-generated method stub
			getBrindefacade().remove(item);
			setItens(null);
		}else {
			JsfUtil.addErrorMessage("O brinde possui movimentações realizadas", "Procedimento não realizado");
	    	FacesContext.getCurrentInstance().validationFailed();
	    	 
		}
		
	}

	@Override
	public void prepararEditar(Brinde item) {
		setItem(item);
		// TODO Auto-generated method stub
		
	}
	@PostConstruct
	public void iniciar() {
		setItens(null);
	}

	@Override
	public void atualizar() {
		// TODO Auto-generated method stub
		setItens(null);
		
	}

	@Override
	public void prepararNovo() {
		setItem(new Brinde()); 
		getItem().setQuantidade(0);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fecharDialogo() {
		PrimeFaces.current().resetInputs("brindeForm");
		getFlash().limparPorId("brindeForm");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		try {
			
			Brinde item = getItem();
		    item.setDescricao(item.getDescricao().toUpperCase());
		    
		    Brinde localizarPorNome = getBrindefacade().localizarPorNome(item.getDescricao());
		    if(localizarPorNome!=null && localizarPorNome.getCodigo()!=item.getCodigo()) {
		    	JsfUtil.addErrorMessage("O brinde informado já foi cadastrado", "Procedimento não realizado");
		    	FacesContext.getCurrentInstance().validationFailed();
		    	atualizar();
		    	return;
		    } 
		    if(item.getCodigo()==null) {
		    	getBrindefacade().create(item);
		    	JsfUtil.addSuccessMessage("Brinde criado com sucesso", "Procedimento OK");
		    }else {
		    	getBrindefacade().edit(item);
		    	JsfUtil.addSuccessMessage("Brinde alterado com sucesso", "Procedimento OK");
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
	public Brinde getItem() {
		// TODO Auto-generated method stub
		return (Brinde)getFlash().getValoresPorID("brindeForm").get("item");
	}

	@Override
	public void setItem(Brinde item) {
		getFlash().getValoresPorID("brindeForm").put("item",item);
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Brinde> getItens() {
		ArrayList<Brinde> itens = (ArrayList<Brinde>) getFlash().getValoresPorID("brindeForm").get("itens");
		if(itens==null) {
			itens = (ArrayList<Brinde>) getBrindefacade().findAll();
			setItens(itens);
		}
		// TODO Auto-generated method stub
		return itens;
	}

	@Override
	public void setItens(List<Brinde> itens) {
		// TODO Auto-generated method stub
		getFlash().getValoresPorID("brindeForm").put("itens",itens);
		
	}

	public BrindeFacade getBrindefacade() {
		return brindefacade;
	}

	public void setCanalfacade(BrindeFacade brindefacade) {
		this.brindefacade = brindefacade;
	}

}
