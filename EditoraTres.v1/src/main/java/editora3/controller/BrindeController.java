package editora3.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;

import editora3.entidades.Brinde;
import editora3.entidades.BrindeEstoqueEquipe;
import editora3.entidades.Equipe;
import editora3.facade.BrindeEstoqueFacade;
import editora3.facade.BrindeFacade;
import editora3.facade.EquipeFacade;
import editora3.util.JsfUtil;
@Named("brindeController") 
@RequestScoped
public class BrindeController implements AbstractController<Brinde> {

	@Inject
	FlashApp flashapp;
	
	@Inject
	private
	BrindeFacade brindefacade;
	
	@Inject
	private EquipeFacade equipeFacade;
	
	@Inject
	private BrindeEstoqueFacade brindeEstoqueFacade;
	
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
		setEquipeDisponiveis(null);
	}

	@Override
	public void atualizar() {
		// TODO Auto-generated method stub
		setItens(null);
		
	}

	@Override
	public void prepararNovo() {
		setItem(new Brinde()); 
		getItem().setQuantidade(0d);
		getItem().setStatus(false);
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
			
			getSumario().clear();
			itens = (ArrayList<Brinde>) getBrindefacade().findAllDisponivel(null,false);
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
	public void setSumario(HashMap<Integer, Double> valores) {
		getFlash().getValoresPorID("brindeForm").put("valoresSumarios",valores);
	}
	public HashMap<Integer, Double> getSumario() {
		HashMap<Integer, Double> ret = (HashMap<Integer, Double>) getFlash().getValoresPorID("brindeForm").get("valoresSumarios");
		if(ret ==null) {
			ret = new HashMap<>();
			setSumario(ret);
		}
		return ret;
	}
	public Double totalEquipe(Integer codigoEquipe) {
		Double ret =0d;
		try {
			ret = getSumario().get(codigoEquipe);
		} catch (Exception ex) {
			JsfUtil.addErrorMessage(ex, "totalEquipe");
			
			// TODO: handle exception
		}
		return ret;
	}
	public Double sumarizarEstoqueEquipe(Integer codigoBrinde ,Integer equipeAtual) {
		Double ret =0d;
		try {
			List<BrindeEstoqueEquipe> brindeEstoqueEquipe = brindeEstoqueFacade.RetornarEstoqueEquipe(codigoBrinde, equipeAtual);
			//List<BrindeEstoqueEquipe> brindeEstoqueEquipe = getItem().getBrindeEstoqueEquipe();
			if(brindeEstoqueEquipe!=null && !brindeEstoqueEquipe.isEmpty()) {
				for (Iterator iterator = brindeEstoqueEquipe.iterator(); iterator.hasNext();) {
					BrindeEstoqueEquipe itemEstoque = (BrindeEstoqueEquipe) iterator.next();
					//if(itemEstoque.getEquipeBean().getCodigo()==codigoBrinde) {
						if (!getSumario().containsKey(equipeAtual)) {
							getSumario().put(equipeAtual, itemEstoque.getQuantidade());
						} else {
							getSumario().replace(equipeAtual, getSumario().get(equipeAtual) + itemEstoque.getQuantidade());
						}
				//	}
				}
			}
			 
			
		} catch (Exception ex) {
			JsfUtil.addErrorMessage(ex, "sumarizarEstoqueEquipe");
			
			// TODO: handle exception
		}
		return ret;
	}
	//private List<Equipe> equipeDisponiveis;

	public EquipeFacade getEquipeFacade() {
		return equipeFacade;
	}

	public void setEquipeFacade(EquipeFacade equipeFacade) {
		this.equipeFacade = equipeFacade;
	}

	public List<Equipe> getEquipeDisponiveis() {
		 List<Equipe> ret = (List<Equipe>)getFlash().getValoresPorID("brindeForm").get("equipesDisponiveis");
		if(ret==null) {
			ret = getEquipeFacade().findAllEquipes(null);
			setEquipeDisponiveis(ret);
		}
		return ret;
	}

	public void setEquipeDisponiveis(List<Equipe> equipeDisponiveis) {
		getFlash().getValoresPorID("brindeForm").put("equipesDisponiveis",equipeDisponiveis);
		//this.equipeDisponiveis = equipeDisponiveis;
	}

	public List<BrindeEstoqueEquipe> retornaEstoquePorEquipe(Integer codigoBrinde, Integer equipeAtual){
		List<BrindeEstoqueEquipe> ret=null;
		
		ret = brindeEstoqueFacade.RetornarEstoqueEquipe(codigoBrinde, equipeAtual);
		 
		return ret;
	}

	public BrindeEstoqueFacade getBrindeEstoqueFacade() {
		return brindeEstoqueFacade;
	}

	public void setBrindeEstoqueFacade(BrindeEstoqueFacade brindeEstoqueFacade) {
		this.brindeEstoqueFacade = brindeEstoqueFacade;
	}
 
}
