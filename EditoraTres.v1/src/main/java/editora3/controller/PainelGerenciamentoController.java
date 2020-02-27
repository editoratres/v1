package editora3.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
 
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import editora3.entidades.Brinde;
import editora3.entidades.BrindeEstoqueEquipe;
 
import editora3.entidades.Contrato;
import editora3.entidades.ContratoEntrada;
 
import editora3.facade.ContratoEntradaFacade;
import editora3.facade.ContratoFacade;
import editora3.facade.FiltrosLazyDataModel;
import editora3.facade.LazyObjetos;
import editora3.seguranca.LoginInfo;
import editora3.facade.BrindeEstoqueFacade;
import editora3.facade.BrindeFacade;
 
import editora3.util.JsfUtil;
@Named("painelGerenciamentoController") 
@SessionScoped
public class PainelGerenciamentoController  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4083060794500396066L;

	@Inject
	private LoginInfo loginInfo;
	
	@Inject
	private ContratoFacade contratoFacade;
	
	@Inject
	private BrindeEstoqueFacade brindeEstoqueFacade;
	
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

		try {

			if (itens == null) {
				itens = (ArrayList<ContratoEntrada>) getContratoEntradaFacade().findAllDisponiveis();
				itens.sort(new Comparator<ContratoEntrada>() {

					@Override
					public int compare(ContratoEntrada o1, ContratoEntrada o2) {
						// TODO Auto-generated method stub
						return o1.getCodigo().compareTo(o2.getCodigo());
					}
				});
				setItens(itens);
			}
			// TODO Auto-generated method stub
		} catch (Exception ex) {
			// TODO: handle exception
			JsfUtil.addErrorMessage(ex, "getItens");
		}
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
	public LazyDataModel<Brinde> getBrindesDisponiveis() {
		LazyDataModel<Brinde> brindesDisponiveis = (LazyDataModel<Brinde>) getFlashapp().getValoresPorID("painelGerenciamentoForm").get("brindesDisponiveis");
		if(brindesDisponiveis==null) {
			brindesDisponiveis=new LazyDataModel<Brinde>() {
				
				private static final long serialVersionUID = 1L;

				@Override
				public List<Brinde> load(int first, int pageSize, String sortField, SortOrder sortOrder,
						Map<String, Object> filters) {

					FiltrosLazyDataModel filtrosLazyDataModel = new FiltrosLazyDataModel(first, pageSize, sortField,
							sortOrder, filters);

					LazyObjetos<Brinde> findAllLazy =getBrindefacade().findAllDisponivelLazy(getLoginInfo().getCodigoEquipeVinculada(),true,filtrosLazyDataModel);

				 
					setRowCount(findAllLazy.getTotalObjetos());

					return findAllLazy.getLista();
				
				}
			};
			//brindesDisponiveis = (ArrayList<Brinde>) getBrindefacade().findAllDisponivel(getLoginInfo().getCodigoEquipeVinculada(),true);
			setBrindesDisponiveis(brindesDisponiveis);
		}
		// TODO Auto-generated method stub
		return brindesDisponiveis;
	}
	public void setBrindesDisponiveis(LazyDataModel<Brinde> brindesDisponiveis) {
		getFlashapp().getValoresPorID("painelGerenciamentoForm").put("brindesDisponiveis",brindesDisponiveis);
	}
	private Integer totalContratosEfetivados;
	public LazyDataModel<Contrato> getContratosEfetivados() {
		LazyDataModel<Contrato> contratosDisponiveis = (LazyDataModel<Contrato>) getFlashapp().getValoresPorID("painelGerenciamentoForm").get("contratosEfetivados");
		
		try {

			if (contratosDisponiveis == null) {
				contratosDisponiveis = new LazyDataModel<Contrato>() {
					private static final long serialVersionUID = 1L;

					@Override
					public List<Contrato> load(int first, int pageSize, String sortField, SortOrder sortOrder,
							Map<String, Object> filters) {

						FiltrosLazyDataModel filtrosLazyDataModel = new FiltrosLazyDataModel(first, pageSize, sortField,
								sortOrder, filters);

						LazyObjetos<Contrato> findAllLazy = getContratoFacade()
								.findAllLazy(getLoginInfo().getCodigoEquipeVinculada(), filtrosLazyDataModel);

					 
						setRowCount(findAllLazy.getTotalObjetos());

						return findAllLazy.getLista();
					}
				};
				setContratosEfetivados(contratosDisponiveis);
			}
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "getContratosEfetivados");
			// TODO: handle exception
		}
		return contratosDisponiveis;
	}
	
	
	public void setContratosEfetivados(LazyDataModel<Contrato> contratosDisponiveis) {
		getFlashapp().getValoresPorID("painelGerenciamentoForm").put("contratosEfetivados",contratosDisponiveis);
	}

	public ContratoFacade getContratoFacade() {
		
		return contratoFacade;
	}
	public void setContratoFacade(ContratoFacade contratoFacade) {
		this.contratoFacade = contratoFacade;
	}
	public Double getTotalBrindesEmEstoque() {
		 
		try {
			
			return getBrindeEstoqueFacade().RetornarEstoqueEquipeGeral(getLoginInfo().getCodigoEquipeVinculada());
			
		} catch (Exception ex) {
			JsfUtil.addErrorMessage(ex, "getTotalContratosDisponiveis");
			// TODO: handle exception
		}
		return 0d;
	}

	 
	public Double EstoqueAtual(Brinde b) {
	
		Double ret =0d;
		
		try {
			
			if(getLoginInfo().getCodigoEquipeVinculada()==null) {
				ret=b.getQuantidade();
			}else {
				List<BrindeEstoqueEquipe> retornarEstoqueEquipe = getBrindeEstoqueFacade().RetornarEstoqueEquipe(b.getCodigo(),getLoginInfo().getCodigoEquipeVinculada(),null);
				if(retornarEstoqueEquipe!=null && !retornarEstoqueEquipe.isEmpty()) {
					for (Iterator iterator = retornarEstoqueEquipe.iterator(); iterator.hasNext();) {
						BrindeEstoqueEquipe brindeEstoqueEquipe = (BrindeEstoqueEquipe) iterator.next();
						ret+=brindeEstoqueEquipe.getQuantidade();
					}
					//ret =  retornarEstoqueEquipe.get(0).getQuantidade();
				}
			}
			
			return ret;
			
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
	public BrindeEstoqueFacade getBrindeEstoqueFacade() {
		return brindeEstoqueFacade;
	}
	public void setBrindeEstoqueFacade(BrindeEstoqueFacade brindeEstoqueFacade) {
		this.brindeEstoqueFacade = brindeEstoqueFacade;
	}
	public Integer totalContratosEfetivados() {
		return getContratoFacade().findAllLazyCount(getLoginInfo().getCodigoEquipeVinculada());
		 
	}
	 
	 
	 

}
