package editora3.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import editora3.entidades.ContratoEntrada;
import editora3.entidades.ContratoSaida;
import editora3.facade.ContratoEntradaFacade;
import editora3.util.JsfUtil;
@Named("contratoEntradaController") 
@RequestScoped
public class ContratoEntradaController implements AbstractController<ContratoEntrada> {

	@Inject
	FlashApp flashapp;
	
	@Inject
	private
	ContratoEntradaFacade contratoEntradaFacade;
	
	@Override
	public void excluir(ContratoEntrada item) {
		// TODO Auto-generated method stub
		try {
	
			Integer consultarContratosEmUso = getContratoEntradaFacade().consultarContratosEmUso(item.getFaixainicial(), item.getFaixafinal());
			if(consultarContratosEmUso>0) {
				JsfUtil.addErrorMessage("Não é possivel excluir. Já existem contratos em uso nessa faixa", "Procedimento não realizado");
		    	FacesContext.getCurrentInstance().validationFailed();
		    	return;
			}else {
				getContratoEntradaFacade().excluirEntradaContrato(item);
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
	public void prepararEditar(ContratoEntrada item) {
		setItem(item);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void atualizar() {
		// TODO Auto-generated method stub
		infoFaixa.clear();
		setItens(null);
		
	}

	@Override
	public void prepararNovo() {
		setItem(new ContratoEntrada()); 
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void fecharDialogo() {
		PrimeFaces.current().resetInputs("contratoEntradaForm");
		getFlash().limparPorId("contratoEntradaForm");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		try {
			
			ContratoEntrada item = getItem();
			
			
		 
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
		    List<ContratoEntrada> localizarPorFaixa = getContratoEntradaFacade().localizarPorFaixa(item.getFaixainicial(),item.getFaixafinal());
			if(localizarPorFaixa!=null && !localizarPorFaixa.isEmpty()) {
				JsfUtil.addErrorMessage("A faixa informada não pode possuir valores contidos em outras faixas já cadastradas", "Procedimento não realizado");
		    	FacesContext.getCurrentInstance().validationFailed();
		    	return;
			}
		    
		    if(item.getCodigo()==null) {
		    	item.setData(new Date());
		    	getContratoEntradaFacade().criarEntrada(item);
		    	JsfUtil.addSuccessMessage("Entrada de contrato criada com sucesso", "Procedimento OK");
		    	
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
	public ContratoEntrada getItem() {
		// TODO Auto-generated method stub
		ContratoEntrada ret = (ContratoEntrada)getFlash().getValoresPorID("contratoEntradaForm").get("item");
		if(ret==null) {
			ret=new ContratoEntrada();
		}
		return (ContratoEntrada)getFlash().getValoresPorID("contratoEntradaForm").get("item");
	}

	@Override
	public void setItem(ContratoEntrada item) {
		getFlash().getValoresPorID("contratoEntradaForm").put("item",item);
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ContratoEntrada> getItens() {
		ArrayList<ContratoEntrada> itens = (ArrayList<ContratoEntrada>) getFlash().getValoresPorID("contratoEntradaForm").get("itens");
		if(itens==null) {
			itens = (ArrayList<ContratoEntrada>) getContratoEntradaFacade().findAll();
			setItens(itens);
		}
		// TODO Auto-generated method stub
		return itens;
	}

	@Override
	public void setItens(List<ContratoEntrada> itens) {
		// TODO Auto-generated method stub
		getFlash().getValoresPorID("contratoEntradaForm").put("itens",itens);
		
	}

	public Integer consultarDisponibilidade(ContratoEntrada item) {
		Integer ret =0;
		
		ret = (item.getFaixafinal()-item.getFaixainicial()+1) - getContratoEntradaFacade().consultarDisponibilidadeDaFaixa(item.getFaixainicial(), item.getFaixafinal());
		
		return ret;
	}
	private HashMap<Integer, ArrayList<Double>> infoFaixa=new HashMap<>();
		

	public ArrayList<Double> informacaoFaixa(ContratoEntrada item){
		ArrayList<Double> ret=new ArrayList<>();
		try {
			
		
		 ret = (infoFaixa.get(item.getCodigo()));
		if(ret==null) {
			ret = new ArrayList<>();
			
			HashMap<String, Integer> situacaoDosContratosNaFaixa = getContratoEntradaFacade().situacaoDosContratosNaFaixa(item.getFaixainicial(), item.getFaixafinal());
			Double calcularTamanhoFaixa =Double.valueOf(calcularTamanhoFaixa(item));
		
			Double saidas =  Double.valueOf(situacaoDosContratosNaFaixa.get("saidas"));
			Double cancelamentos = Double.valueOf(situacaoDosContratosNaFaixa.get("cancelados")); 
			Double qtDisponibilidade =Double.valueOf(situacaoDosContratosNaFaixa.get("disponivel"));
			 
			
			ret.add(Double.valueOf(calcularTamanhoFaixa));
			ret.add(Double.valueOf(qtDisponibilidade));
			ret.add(Double.valueOf(saidas));
			ret.add(Double.valueOf(cancelamentos));
			ret.add(Double.valueOf(qtDisponibilidade/calcularTamanhoFaixa)*100d);
			ret.add(Double.valueOf(saidas/calcularTamanhoFaixa)*100d);
			ret.add(Double.valueOf(cancelamentos/calcularTamanhoFaixa)*100d);
			
			infoFaixa.put(item.getCodigo(), ret);
		}	
		
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "informacaoFaixa");
			// TODO: handle exception
		}
		return ret;
	}
	public ContratoEntradaFacade getContratoEntradaFacade() {
		return contratoEntradaFacade;
	}
	public void setContratoEntradaFacade(ContratoEntradaFacade contratoEntradaFacade) {
		this.contratoEntradaFacade = contratoEntradaFacade;
	}
	
	
	public int calcularTamanhoFaixa(ContratoEntrada contratoEntrada) {
		int totalFaixa =0;
		try {
			
			if(contratoEntrada!=null && contratoEntrada.getFaixafinal()!=null && contratoEntrada.getFaixainicial()!=null) {
			   totalFaixa = (contratoEntrada.getFaixafinal()==null ? 0 :contratoEntrada.getFaixafinal())  - (contratoEntrada.getFaixainicial()==null ? 0 : contratoEntrada.getFaixainicial())+1;
			}
			
			return totalFaixa;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return totalFaixa; 
		
	}

}
