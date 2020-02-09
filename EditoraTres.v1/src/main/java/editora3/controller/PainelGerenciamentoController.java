package editora3.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import editora3.entidades.Brinde;
import editora3.entidades.Canal;
import editora3.entidades.Contrato;
import editora3.entidades.ContratoEntrada;
import editora3.entidades.InfraUsuario;
import editora3.facade.ContratoEntradaFacade;
import editora3.facade.ContratoFacade;
import editora3.seguranca.LoginInfo;
import editora3.facade.BrindeFacade;
import editora3.facade.CanalFacade;
import editora3.util.JsfUtil;
@Named("painelGerenciamentoController") 
@RequestScoped
public class PainelGerenciamentoController   {
	
	@Inject
	private LoginInfo loginInfo;
	
	@Inject
	private ContratoFacade contratoFacade;
	
	@Inject
	private	BrindeFacade brindefacade;
	private ArrayList<ContratoEntrada> itens;
	@Inject
	private
	FlashApp flashapp;
	
	@Inject
	private ContratoEntradaFacade contratoEntradaFacade;
	
	@PostConstruct
	public void iniciar() {
		setItens(null);
		setBrindesDisponiveis(null);
		setContratosEfetivados(null);
		createBarModel();
	}
	
	private BarChartModel barModel=new BarChartModel();
	private void createBarModel() {
		try {
			 setBarModel(initBarModel());
			 

		} catch (Exception ex) {
			// TODO: handle exception
			JsfUtil.addErrorMessage(ex, "createBarModel");
		}
           }
	private BarChartModel initBarModel() {
		 BarChartModel model = new BarChartModel();
		try {
			

	        getBarModel().setTitle("Valores efetivados no período");
	        getBarModel().setLegendPosition("ne");
	 
	        Axis xAxis = getBarModel().getAxis(AxisType.X);
	        xAxis.setLabel("Meses");
	 
	        Axis yAxis = getBarModel().getAxis(AxisType.Y);
	        yAxis.setLabel("Valores");
	        yAxis.setMin(0);
	       	
		
       // BarChartModel model = new BarChartModel();
			ChartSeries boys = new ChartSeries();
			boys.setLabel("");
	    Double ValorMaximo = 0d;   
	    Double valor =0d;
	    Double mes=0d;
	    Double ano =0d;
		List contratosEfetivadosUltimos6Meses = getContratoFacade().ContratosEfetivadosUltimos6Meses(getLoginInfo().getCodigoEquipeVinculada());
		for (Iterator iterator = contratosEfetivadosUltimos6Meses.iterator(); iterator.hasNext();) {
			Object[] object = (Object[]) iterator.next();
			if(object!=null && object.length>0) {
				 ano = (object[0]!= null ? (Double)object[0]  : 0d);
				 mes = (object[0]!= null ? (Double)object[1]  : 0d);
				 valor = (object[0]!= null ? (Double)object[2]  : 0d);
				boys.set((mes<10 ? "0": "")+mes.intValue() + "/" + ano.intValue(), valor);
			}
			ValorMaximo=(valor>ValorMaximo ?  valor :ValorMaximo );
			
			
		}
		 yAxis.setMax(ValorMaximo);
        model.addSeries(boys);
        
		} catch (Exception e) {
			// TODO: handle exception
		}
        return model;
    }
	public BarChartModel getBarModel() {
		return barModel;
	}
	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}
	public ContratoEntradaFacade getContratoEntradaFacade() {
		return contratoEntradaFacade;
	}
	public void setContratoEntradaFacade(ContratoEntradaFacade contratoEntradaFacade) {
		this.contratoEntradaFacade = contratoEntradaFacade;
	}
	
	public List<ContratoEntrada> getItens() {
		ArrayList<ContratoEntrada> itens = (ArrayList<ContratoEntrada>) getFlashapp().getValoresPorID("painelGerenciamentoForm").get("itens");
		if(itens==null) {
			itens = (ArrayList<ContratoEntrada>) getContratoEntradaFacade().findAll();
			setItens(itens);
		}
		// TODO Auto-generated method stub
		return itens;
	}
	
	public void setItens(List<ContratoEntrada> itens) {
		// TODO Auto-generated method stub
		getFlashapp().getValoresPorID("painelGerenciamentoForm").put("itens",itens);
		
	}
	public FlashApp getFlashapp() {
		return flashapp;
	}
	public void setFlashapp(FlashApp flashapp) {
		this.flashapp = flashapp;
	}
	private List<Brinde> brindesDisponiveis;
	
	
	public BrindeFacade getBrindefacade() {
		return brindefacade;
	}
	public void setBrindefacade(BrindeFacade brindefacade) {
		this.brindefacade = brindefacade;
	}
	public List<Brinde> getBrindesDisponiveis() {
		ArrayList<Brinde> brindesDisponiveis = (ArrayList<Brinde>) getFlashapp().getValoresPorID("painelGerenciamentoForm").get("brindesDisponiveis");
		if(brindesDisponiveis==null) {
			brindesDisponiveis = (ArrayList<Brinde>) getBrindefacade().findAllDisponivel(getLoginInfo().getCodigoEquipeVinculada());
			setBrindesDisponiveis(brindesDisponiveis);
		}
		// TODO Auto-generated method stub
		return brindesDisponiveis;
	}
	public void setBrindesDisponiveis(List<Brinde> brindesDisponiveis) {
		getFlashapp().getValoresPorID("painelGerenciamentoForm").put("brindesDisponiveis",brindesDisponiveis);
	}

	public List<Contrato> getContratosEfetivados() {
		ArrayList<Contrato> contratosDisponiveis = (ArrayList<Contrato>) getFlashapp().getValoresPorID("painelGerenciamentoForm").get("contratosEfetivados");
		if(contratosDisponiveis==null) {
			contratosDisponiveis = (ArrayList<Contrato>) getContratoFacade().totalContratosEfetivados(getLoginInfo().getCodigoEquipeVinculada());
			setBrindesDisponiveis(brindesDisponiveis);
		}
		return contratosDisponiveis;
	}
	
	
	public void setContratosEfetivados(List<Contrato> contratosDisponiveis) {
		getFlashapp().getValoresPorID("painelGerenciamentoForm").put("contratosEfetivados",contratosDisponiveis);
	}

	public ContratoFacade getContratoFacade() {
		
		return contratoFacade;
	}
	public void setContratoFacade(ContratoFacade contratoFacade) {
		this.contratoFacade = contratoFacade;
	}
	public Integer getTotalBrindesEmEstoque() {
		Integer ret =0;
		try {
			List<Brinde> brindesDisponiveis2 = getBrindesDisponiveis();
			for (Iterator iterator = brindesDisponiveis2.iterator(); iterator.hasNext();) {
				Brinde brinde = (Brinde) iterator.next();
				ret  += (brinde.getQuantidade()==null ? 0 : brinde.getQuantidade());
				if(getLoginInfo().getCodigoEquipeVinculada()==null) {
					 ret = brinde.getQuantidade();
					}else {
						if(brinde.getBrindeEstoqueEquipe()!=null && !brinde.getBrindeEstoqueEquipe().isEmpty()) {
						   Double estoque =  brinde.getBrindeEstoqueEquipe().get(0).getQuantidade();
						   ret = estoque.intValue();
						}
					}
			}
		} catch (Exception ex) {
			JsfUtil.addErrorMessage(ex, "getTotalContratosDisponiveis");
			// TODO: handle exception
		}
		
		
		
		return ret;
	}
	
	public Integer EstoqueAtual(Brinde b) {
	
		Integer ret =0;
		
		try {
			
			if(getLoginInfo().getCodigoEquipeVinculada()==null) {
			 ret = b.getQuantidade();
			}else {
				if(b.getBrindeEstoqueEquipe()!=null && !b.getBrindeEstoqueEquipe().isEmpty()) {
				   Double estoque =  b.getBrindeEstoqueEquipe().get(0).getQuantidade();
				   ret = estoque.intValue();
				}
			}
			
		} catch (Exception ex) {
			JsfUtil.addErrorMessage(ex, "EstoqueAtual");
			// TODO: handle exception
			// TODO: handle exception
		}
		
		return ret;
	}
	public Integer getTotalContratosDisponiveis() {
		Integer ret =0;
		try {
			ret = getContratoFacade().totalContratosDisponiveis(getLoginInfo().getCodigoEquipeVinculada(),null);
			 
			
		} catch (Exception ex) {
			JsfUtil.addErrorMessage(ex, "getTotalContratosDisponiveis");
			// TODO: handle exception
		}
		
		 
		
		return ret;
	}
	public LoginInfo getLoginInfo() {
		return loginInfo;
	}
	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	 
	 

}
