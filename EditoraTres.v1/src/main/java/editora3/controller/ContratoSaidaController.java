package editora3.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

 
import editora3.entidades.ContratoSaida;
import editora3.entidades.Vendedor;
import editora3.facade.ContratoSaidaFacade;
import editora3.facade.EquipeFacade;
import editora3.util.JsfUtil;
@Named("contratoSaidaController") 
@RequestScoped
public class ContratoSaidaController implements AbstractController<ContratoSaida> {

	@Inject
	FlashApp flashapp;
	
	@Inject
	private
	ContratoSaidaFacade contratoSaidaFacade;
	
	private Boolean editar=false;
	
	@Override
	public void excluir(ContratoSaida item) {
		// TODO Auto-generated method stub
		try {
	
			Integer consultarContratosEmUso = getContratoSaidaFacade().consultarContratosEmUso(item.getFaixainicial(), item.getFaixafinal());
			if(consultarContratosEmUso>0) {
				JsfUtil.addErrorMessage("Não é possivel excluir. Já existem contratos em uso nessa faixa", "Procedimento não realizado");
		    	FacesContext.getCurrentInstance().validationFailed();
		    	return;
			}else {
				getContratoSaidaFacade().excluirSaidaContrato(item);
				setItens(null);
			}
		
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "excluir");
			 
		}
		
	}
	@PostConstruct
	public void iniciar() {
		setItens(null);
	}

	@Override
	public void prepararEditar(ContratoSaida item) {
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
		setItem(new ContratoSaida()); 
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fecharDialogo() {
		PrimeFaces.current().resetInputs("contratoSaidaForm");
		getFlash().limparPorId("contratoSaidaForm");
		// TODO Auto-generated method stub
		
	}

	public int calcularTamanhoFaixa(ContratoSaida contratoSaida) {
		int totalFaixa =0;
		try {
			
			if(contratoSaida.getFaixafinal()!=null && contratoSaida.getFaixainicial()!=null) {
			   totalFaixa = (contratoSaida.getFaixafinal()==null ? 0 :contratoSaida.getFaixafinal())  - (contratoSaida.getFaixainicial()==null ? 0 : contratoSaida.getFaixainicial())+1;
			}
			
			return totalFaixa;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return totalFaixa; 
		
	}
	@Override
	public void create() {
		// TODO Auto-generated method stub
		try {
			
			ContratoSaida item = getItem();
			
			if(item.getEquipeBean()==null) {
				JsfUtil.addErrorMessage("A equipe é obrigatória", "Procedimento não realizado");
		    	FacesContext.getCurrentInstance().validationFailed();
		    	return;
			}
			
			/*if(item.getVendedorBean()==null) {
				JsfUtil.addErrorMessage("O vendedor é obrigatório", "Procedimento não realizado");
		    	FacesContext.getCurrentInstance().validationFailed();
		    	return;
			}*/
			
			if(item.getFaixainicial()==null) {
		    	JsfUtil.addErrorMessage("A Faixa inicial não pode ser vazia", "Procedimento não realizado");
		    	FacesContext.getCurrentInstance().validationFailed();
		    	return;
		    }
			if(item.getFaixafinal()==null) {
		    	JsfUtil.addErrorMessage("A Faixa final não pode ser vazia", "Procedimento não realizado");
		    	FacesContext.getCurrentInstance().validationFailed();
		    	return;
		    }
			
			if(item.getFaixafinal()-item.getFaixainicial()+1<1) {
		    	JsfUtil.addErrorMessage("A Faixa de contratos informada não é valida. A quantidade não pode ser menor que 1(um)", "Procedimento não realizado");
		    	FacesContext.getCurrentInstance().validationFailed();
		    	return;
		    }
		   if(item.getFaixafinal()-item.getFaixainicial()+1>500) {
		    	JsfUtil.addErrorMessage("Não é permitido a criação de mais de 500 contratos de uma só vez", "Procedimento não realizado");
		    	FacesContext.getCurrentInstance().validationFailed();
		    	return;
		    }
			List<ContratoSaida> localizarPorFaixa = getContratoSaidaFacade().verificarFaixasDeSaida(item.getFaixainicial(),item.getFaixafinal());
			if(localizarPorFaixa!=null && !localizarPorFaixa.isEmpty()) {
				JsfUtil.addErrorMessage("A faixa informada não pode possuir valores contidos em outras faixas já cadastradas", "Procedimento não realizado");
		    	FacesContext.getCurrentInstance().validationFailed();
		    	return;
			}
		    
		    int tamanhoFaixa =   item.getFaixafinal() -item.getFaixainicial()+1;
		    
		    int contratosDisponiveisNaFaixa = getContratoSaidaFacade().verificarQuantidadeDeContratosDisponiveis(item.getFaixainicial(), item.getFaixafinal());
		    if (tamanhoFaixa > contratosDisponiveisNaFaixa) {
		    	JsfUtil.addErrorMessage("A faixa de contrato informada precisa de ["+ tamanhoFaixa +"] contratos disponíveis, porém existem apenas [ "+  contratosDisponiveisNaFaixa+" ] contratos disponiveis nessa faixa", "Procedimento não realizado");
		    	FacesContext.getCurrentInstance().validationFailed();
		    	return;
		    }
		    
		    
		    if(item.getCodigo()==null) {
		    	item.setData(new Date());
		    	getContratoSaidaFacade().criarSaida(item);
		    	JsfUtil.addSuccessMessage("Saida de contrato(s) criada com sucesso", "Procedimento OK");
		    	
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
	public ContratoSaida getItem() {
		// TODO Auto-generated method stub
		ContratoSaida ret = (ContratoSaida)getFlash().getValoresPorID("contratoSaidaForm").get("item");
		if(ret==null) {
			ret=new ContratoSaida();
		}
		return ret;
	}

	@Override
	public void setItem(ContratoSaida item) {
		getFlash().getValoresPorID("contratoSaidaForm").put("item",item);
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ContratoSaida> getItens() {
		ArrayList<ContratoSaida> itens = (ArrayList<ContratoSaida>) getFlash().getValoresPorID("contratoSaidaForm").get("itens");
		if(itens==null) {
			itens = (ArrayList<ContratoSaida>) getContratoSaidaFacade().findAll();
			setItens(itens);
		}
		// TODO Auto-generated method stub
		return itens;
	}

	@Override
	public void setItens(List<ContratoSaida> itens) {
		// TODO Auto-generated method stub
		getFlash().getValoresPorID("contratoSaidaForm").put("itens",itens);
		
	}
	@Inject
	private EquipeFacade equipeFacade; 

	public EquipeFacade getEquipeFacade() {
		return equipeFacade;
	}
	public void setEquipeFacade(EquipeFacade equipeFacade) {
		this.equipeFacade = equipeFacade;
	}
	private  List<Vendedor> vendedoresEquipe;
	public List<Vendedor> getVendedoresDaEquipe() {
		ContratoSaida item = getItem(); 
		if(item!=null && item.getEquipeBean()!=null) {
			vendedoresEquipe =getEquipeFacade().localizarVendedoresEquipe(item.getEquipeBean().getCodigo());
			 
		}else {
			vendedoresEquipe=null;
		}
		return vendedoresEquipe;
	}

	public void setVendedoresDaEquipe(List<Vendedor> vendedoresEquipe) {
		this.vendedoresEquipe = vendedoresEquipe;
	}
	
	public ContratoSaidaFacade getContratoSaidaFacade() {
		return contratoSaidaFacade;
	}
	public void setContratoSaidaFacade(ContratoSaidaFacade contratoSaidaFacade) {
		this.contratoSaidaFacade = contratoSaidaFacade;
	}
	public Boolean getEditar() {
		return editar;
	}
	public void setEditar(Boolean editar) {
		this.editar = editar;
	}

}
