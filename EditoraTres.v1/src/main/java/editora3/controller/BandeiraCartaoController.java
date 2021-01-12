package editora3.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import editora3.entidades.BandeiraCartao;
import editora3.facade.AuditoriaFacade;
import editora3.facade.BandeiraCartaoFacade;
import editora3.seguranca.AutorizacaoRecurso;
import editora3.util.JsfUtil;
@Named("bandeiraCartaoController") 
@RequestScoped
public class BandeiraCartaoController implements AbstractController<BandeiraCartao> {

	@Inject
	FlashApp flashapp;
	
	@Inject
	private
	BandeiraCartaoFacade canalfacade;
	
	@Inject
	private AuditoriaFacade auditoriaFacade;  
	
	@Inject
	private AutorizacaoRecurso autorizacaoRecurso; 
	
	@Override
	public void excluir(BandeiraCartao item) {
		// TODO Auto-generated method stub
		if(autorizacaoRecurso.VerificarAcesso("BandeiraCartao", "excluir",true, item.getCodigo().toString()+ " - " + item.getDescricao())) {
			getBandeiraCartaofacade().remove(item);
			setItens(null);
		}
		
	}
	@PostConstruct
	public void iniciar() {
		setItens(null);
	}
	 
	@Override
	public void prepararEditar(BandeiraCartao item) {
		if(autorizacaoRecurso.VerificarAcesso("BandeiraCartao", "editar",true,null,false)) {
			setItem(item);
		}else {
			FacesContext.getCurrentInstance().validationFailed();
		}
		 
		// TODO Auto-generated method stub
		
	}

	@Override
	public void atualizar() {
		// TODO Auto-generated method stub
		setItens(null);
		
	}

	@Override
	public void prepararNovo() {
		if(autorizacaoRecurso.VerificarAcesso("BandeiraCartao", "criar",true,null,false)) {
					 
		   setItem(new BandeiraCartao());
		}
	 
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fecharDialogo() {
		PrimeFaces.current().resetInputs("bandeiraCartaoForm");
		getFlash().limparPorId("bandeiraCartaoForm");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		try {
			
			
			
		    BandeiraCartao item = getItem();
		    item.setDescricao(item.getDescricao().toUpperCase());
		    
		    BandeiraCartao localizarPorNome = getBandeiraCartaofacade().localizarPorNome(item.getDescricao());
		    if(localizarPorNome!=null && localizarPorNome.getCodigo()!=item.getCodigo()) {
		    	JsfUtil.addErrorMessage("Bandeira informada já foi cadastrado", "Procedimento não realizado");
		    	FacesContext.getCurrentInstance().validationFailed();
		    	atualizar();
		    	return;
		    }
		    
		   
		    
		    if(item.getCodigo()==null) {
		    	
		    		getBandeiraCartaofacade().create(item);
		    		auditoriaFacade.auditar("BandeiraCartao", "criar", item.getCodigo().toString() + " - " + item.getDescricao());
		    		JsfUtil.addSuccessMessage("Bandeira criada com sucesso", "Procedimento OK");
		    	
		    }else {
		    		getBandeiraCartaofacade().edit(item);
		    		auditoriaFacade.auditar("BandeiraCartao", "editar", item.getCodigo().toString() + " - " + item.getDescricao());
		    		JsfUtil.addSuccessMessage("Bandeira alterada com sucesso", "Procedimento OK");
		    	
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
	public BandeiraCartao getItem() {
		// TODO Auto-generated method stub
		return (BandeiraCartao)getFlash().getValoresPorID("bandeiraCartaoForm").get("item");
	}

	@Override
	public void setItem(BandeiraCartao item) {
		getFlash().getValoresPorID("bandeiraCartaoForm").put("item",item);
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<BandeiraCartao> getItens() {
		ArrayList<BandeiraCartao> itens = (ArrayList<BandeiraCartao>) getFlash().getValoresPorID("bandeiraCartaoForm").get("itens");
		if(itens==null) {
			itens = (ArrayList<BandeiraCartao>) getBandeiraCartaofacade().findAll();
			setItens(itens);
		}
		// TODO Auto-generated method stub
		return itens;
	}

	@Override
	public void setItens(List<BandeiraCartao> itens) {
		// TODO Auto-generated method stub
		getFlash().getValoresPorID("bandeiraCartaoForm").put("itens",itens);
		
	}

	public BandeiraCartaoFacade getBandeiraCartaofacade() {
		return canalfacade;
	}

	public void setBandeiraCartaofacade(BandeiraCartaoFacade canalfacade) {
		this.canalfacade = canalfacade;
	}

}
