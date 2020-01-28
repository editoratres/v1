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

import editora3.entidades.ContratoCancelamento;
import editora3.entidades.ContratoSaida;
import editora3.facade.ContratoCancelamentoFacade;
 
import editora3.facade.EquipeFacade;
import editora3.util.JsfUtil;
@Named("contratoCancelamentoController") 
@RequestScoped
public class ContratoCancelamentoController implements AbstractController<ContratoCancelamento> {

	@Inject
	FlashApp flashapp;
	
	@Inject
	private
	ContratoCancelamentoFacade contratoCancelamentoFacade;
	
	private Boolean editar=false;
	
	@Override
	public void excluir(ContratoCancelamento item) {
		// TODO Auto-generated method stub
		try {
	
			Integer consultarContratosEmUso = getContratoCancelamentoFacade().consultarContratosEmUso(item.getFaixainicial(), item.getFaixafinal());
			if(consultarContratosEmUso>0) {
				JsfUtil.addErrorMessage("Não é possivel excluir. Já existem contratos em uso nessa faixa", "Procedimento não realizado");
		    	FacesContext.getCurrentInstance().validationFailed();
		    	return;
			}else {
				getContratoCancelamentoFacade().excluirSaidaContrato(item);
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
	public void prepararEditar(ContratoCancelamento item) {
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
		setItem(new ContratoCancelamento()); 
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fecharDialogo() {
		PrimeFaces.current().resetInputs("contratoCancelamentoForm");
		getFlash().limparPorId("contratoCancelamentoForm");
		// TODO Auto-generated method stub
		
	}

	public int calcularTamanhoFaixa(ContratoCancelamento contratoSaida) {
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
			
			ContratoCancelamento item = getItem();
			
			 
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
			List<ContratoCancelamento> localizarPorFaixa = getContratoCancelamentoFacade().verificarFaixasDeCancelamentos(item.getFaixainicial(),item.getFaixafinal());
			if(localizarPorFaixa!=null && !localizarPorFaixa.isEmpty()) {
				JsfUtil.addErrorMessage("A faixa informada não pode possuir valores contidos em outro(s) cancelamento(s)  já cadastradas", "Procedimento não realizado");
		    	FacesContext.getCurrentInstance().validationFailed();
		    	return;
			}
		    
			List<ContratoSaida> localizarPorFaixaSida = getContratoCancelamentoFacade().verificarFaixasDeSaida(item.getFaixainicial(),item.getFaixafinal());
			if(localizarPorFaixaSida!=null && !localizarPorFaixaSida.isEmpty()) {
				JsfUtil.addErrorMessage("A faixa informada não pode possuir valores contidos em saida(s)  já cadastradas", "Procedimento não realizado");
		    	FacesContext.getCurrentInstance().validationFailed();
		    	return;
			}
			
		    int tamanhoFaixa =   item.getFaixafinal() -item.getFaixainicial()+1;
		    int contratosDisponiveisNaFaixa = getContratoCancelamentoFacade().verificarQuantidadeDeContratosDisponiveis(item.getFaixainicial(), item.getFaixafinal());
		    if (tamanhoFaixa > contratosDisponiveisNaFaixa) {
		    	JsfUtil.addErrorMessage("A faixa de contrato informada precisa de ["+ tamanhoFaixa +"] contratos disponíveis, porém existem apenas [ "+  contratosDisponiveisNaFaixa+" ] contratos disponiveis nessa faixa", "Procedimento não realizado");
		    	FacesContext.getCurrentInstance().validationFailed();
		    	return;
		    }
		    
		    if(item.getCodigo()==null) {
		    	item.setData(new Date());
		    	getContratoCancelamentoFacade().criarCancelamento(item);
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
	public ContratoCancelamento getItem() {
		// TODO Auto-generated method stub
		ContratoCancelamento ret = (ContratoCancelamento)getFlash().getValoresPorID("contratoCancelamentoForm").get("item");
		if(ret==null) {
			ret=new ContratoCancelamento();
		}
		return ret;
	}

	@Override
	public void setItem(ContratoCancelamento item) {
		getFlash().getValoresPorID("contratoCancelamentoForm").put("item",item);
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ContratoCancelamento> getItens() {
		ArrayList<ContratoCancelamento> itens = (ArrayList<ContratoCancelamento>) getFlash().getValoresPorID("contratoCancelamentoForm").get("itens");
		if(itens==null) {
			itens = (ArrayList<ContratoCancelamento>) getContratoCancelamentoFacade().findAll();
			setItens(itens);
		}
		// TODO Auto-generated method stub
		return itens;
	}

	@Override
	public void setItens(List<ContratoCancelamento> itens) {
		// TODO Auto-generated method stub
		getFlash().getValoresPorID("contratoCancelamentoForm").put("itens",itens);
		
	}
	@Inject
	private EquipeFacade equipeFacade; 

	public EquipeFacade getEquipeFacade() {
		return equipeFacade;
	}
	public void setEquipeFacade(EquipeFacade equipeFacade) {
		this.equipeFacade = equipeFacade;
	}
	public ContratoCancelamentoFacade getContratoCancelamentoFacade() {
		return contratoCancelamentoFacade;
	}
	public void setContratoCancelamenoFacade(ContratoCancelamentoFacade contratoCancelamentoFacade) {
		this.contratoCancelamentoFacade = contratoCancelamentoFacade;
	}
	public Boolean getEditar() {
		return editar;
	}
	public void setEditar(Boolean editar) {
		this.editar = editar;
	}

}
