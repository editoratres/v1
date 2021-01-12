package editora3.controller;

 
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
 
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
 
import javax.inject.Inject;
import javax.inject.Named;
 
 
import org.primefaces.PrimeFaces;
import org.primefaces.context.PrimeFacesContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import editora3.entidades.Assinante;
import editora3.entidades.Auditoria;
import editora3.entidades.BandeiraCartao;
import editora3.entidades.Brinde;
 
import editora3.entidades.BrindeEstoqueEquipe;
 
import editora3.entidades.PontoDeVenda;
import editora3.entidades.Contrato;
import editora3.entidades.ContratoBrinde;
import editora3.entidades.ContratoPagamento;
import editora3.entidades.ContratoProduto;
import editora3.entidades.Equipe;
import editora3.entidades.Oferta;
import editora3.entidades.OfertaBrindes;
import editora3.entidades.OfertaIten;
import editora3.entidades.Produto;
import editora3.entidades.Relatorio;
import editora3.entidades.Subcanal;
import editora3.entidades.Vendedor;
import editora3.facade.AuditoriaFacade;
import editora3.facade.BandeiraCartaoFacade;
import editora3.facade.BrindeEstoqueFacade;
import editora3.facade.BrindeFacade;
import editora3.facade.PontoDeVendaFacade;
import editora3.facade.ContratoFacade;
import editora3.facade.EquipeFacade;
import editora3.facade.FiltrosLazyDataModel;
import editora3.facade.LazyObjetos;
import editora3.facade.OfertaFacade;
import editora3.facade.ProdutoFacade;
import editora3.facade.RelatorioFacade;
import editora3.facade.SubCanalFacade;
import editora3.facade.VendedorFacade;
import editora3.seguranca.AutorizacaoRecurso;
import editora3.seguranca.LoginInfo;
import editora3.util.CepWebService;
import editora3.util.JsfUtil;
import editora3.util.ValidacoesDiversas;
import editora3.util.CepWebService.Estados;
import editora3.util.CepWebService.Municipio;
import editora3.util.CepWebService.cep;

@Named("contratoController")
@RequestScoped
public class ContratoController implements AbstractController<Contrato> {

	@Inject
	private AutorizacaoRecurso autorizacaoRecurso; 
	
	@Inject
	private BrindeEstoqueFacade brindeEstoqueFacade;
	
	@Inject
	private LoginInfo loginInfo;
	
	@Inject
	private CepWebService cepWebService;
	
	private String mascaraCPF="999.999.999-99" ;
	
	@Inject
	private AuditoriaFacade auditoriaFacade;
	 
	
	@Inject
	FlashApp flashapp;

	private Boolean editar;
	
	@Inject
	private PontoDeVendaFacade pontoDeVendaFacade;
	
	 
	@Inject
	private ProdutoFacade produtoFacade;	
	
	@Inject
	private OfertaFacade ofertaFacade;	
	
	@Inject
	private BandeiraCartaoFacade bandeiraCartaoFacade;
	
	
	@Inject
	private BrindeFacade brindeFacade;	
	
	
	public ProdutoFacade getProdutoFacade() {
		return produtoFacade;
	}

	public void setProdutoFacade(ProdutoFacade produtoFacade) {
		this.produtoFacade = produtoFacade;
	}

	public BrindeFacade getBrindeFacade() {
		return brindeFacade;
	}

	public void setBrindeFacade(BrindeFacade brindeFacade) {
		this.brindeFacade = brindeFacade;
	}

	@Inject
	private EquipeFacade equipeFacade;
	
	private ArrayList<Municipio> municipio=null;
	private ArrayList<Estados> estados=null;
	
	 


	public ContratoFacade getProdutofacade() {
		return produtofacade;
	}

	public void setProdutofacade(ContratoFacade produtofacade) {
		this.produtofacade = produtofacade;
	}

	@Inject
	private VendedorFacade vendedorFacade;
	
	@Inject
	private ContratoFacade produtofacade;

	@Override
	public void excluir(Contrato item) {
		// TODO Auto-generated method stub
		String texto = item.getCodigocontrato().toString() + " - Assinante :  " + item.getAssinanteBean().getDescricao() + " - Valor : " + item.getPagamentoBean().getValor();
			
		if(getAutorizacaoRecurso().VerificarAcesso("ContratoDigit", "excluir",true,texto)) {
			getContratofacade().cancelarContrato(item);
			setItens(null);
			JsfUtil.addSuccessMessage("Contrato cancelado com sucesso", "Procedimento OK");
		}

	}

	@PostConstruct
	public void iniciar() {
		//getFlash().limparPorId("contratoForm");
		setItens(null);
	}

	@Override
	public void prepararEditar(Contrato item) {
		if(autorizacaoRecurso.VerificarAcesso("ContratoDigit", "editar",true,null,false)) {
			resetarList();
			item = getContratofacade().getContratoEager(item.getCodigo());
			setContratoCarregado(false);
			setItem(item);
		}
		 
		
		 
	}
	
	public void prepararConcederDesconto(Contrato item) {
		if(getAutorizacaoRecurso().VerificarAcesso("ConcederDesconto", "acessar",true, item.getCodigo().toString(),false)) {
			 
			resetarList();
			item = getContratofacade().getContratoEager(item.getCodigo());
			setContratoCarregado(false);
			setItem(item);
		 
		
		}else {
			FacesContext.getCurrentInstance().validationFailed();
		}
	}
	
	public void concederDesconto() {
		getContratofacade().ajustarContrato(getItem());
		auditoriaFacade.auditar("ConcederDesconto", "criar", "Contrato: "+ getItem().getCodigocontrato().toString() + " - Desconto  : " + getItem().getValorDesconto().toString());
		JsfUtil.addSuccessMessage("Desconto concedido com sucesso", "Procedimento OK");
	}
	public void prepararConsultar(Contrato item) {
		resetarList();
		item = getContratofacade().getContratoEager(item.getCodigo());
		setContratoCarregado(false);
		setItem(item);
		setEditar(true);
		 
		// TODO Auto-generated method stub

	}

	@Override
	public void atualizar() {
		// TODO Auto-generated method stub
		setItens(null);

	}

	@Override
	public void prepararNovo() {
		if(autorizacaoRecurso.VerificarAcesso("ContratoDigit", "criar",true,null,false)) {
			resetarList();
			setContratoCarregado(false);
			Contrato contrato = new Contrato();
			contrato.setAssinanteBean(new Assinante()); 
		//contrato.setInclusao(new Date());
		
			ContratoPagamento contratoPagamento = new ContratoPagamento();
			contratoPagamento.setCondposparcial(false);
			contratoPagamento.setCondpostotal(false);
			contrato.setPagamentoBean(contratoPagamento);
			setItem(contrato);
			setEditar(false);
		}
	
		// TODO Auto-generated method stub

	}
	private void resetarList() {
		setBrindesDisponiveis(null);
		setOfertasDisponiveis(null);
		setListaContratosDisponiveis(null);
		
	}

	@Override
	public void fecharDialogo() {
		PrimeFaces.current().resetInputs("contratoForm");
		//getFlash().limparPorId("contratoForm");
		// TODO Auto-generated method stub

	}
	private UIComponent findComponent(String id, UIComponent where) {
		if (where == null) {
		   return null;
		}
		else if (where.getId().equals(id)) {
		   return where;
		}
		else {
		   List<UIComponent> childrenList = where.getChildren();
		   if (childrenList == null || childrenList.isEmpty()) {
		      return null;
		   }
		   for (UIComponent child : childrenList) {
		      UIComponent result = null;
		      result = findComponent(id, child);
		      if(result != null) {
		         return result;
		      }
		   }
		   
		}
		return null;   
	}   
	
	private void invalidarComponente( String id,UIComponent where ) { 
		 
		
		UIInput c = (UIInput) findComponent(id,where);
		if(c!=null) {
		c.setValid(false);
			getCamposInvalidos().add(id);
		}
	}
	private List<String> camposInvalidos=new ArrayList<>();
	private boolean validarCamposObrigorios(Contrato item ) {
		getCamposInvalidos().clear();
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		boolean ret=true;
	/*	 
		if(item.getRelatorioBean()==null) {
			
			invalidarComponente("idrelatorio",viewRoot);
			ret=false;
		}*/
		 
		if(item.getDatavenda()==null ) {

			invalidarComponente("iddatavenda",viewRoot);
			ret=false;
		}
		if(item.getCodigocontrato()==null || (item.getCodigocontrato()!=null && item.getCodigocontrato()==0)) {

			invalidarComponente("idcontrato",viewRoot);
			ret=false;
		}
		if(item.getEquipeBean()==null) {
			
			invalidarComponente("idEquipe",viewRoot);
			ret=false;
		}
		
		
		if(item.getVendedorBean()==null) {
			
			invalidarComponente("idvendedor",viewRoot);
			ret=false;
		}
		if(item.getPontoDeVendaBean()==null) {
			
			invalidarComponente("idpontodevenda",viewRoot);
			ret=false;
		}
	/*	if(item.getSubcanlBean()==null) {
			
			invalidarComponente("idsubcanal",viewRoot);
			ret=false;
		}
		*/
		if(item.getAssinanteBean().getCnpjcpf().trim().length()==0) {
			
			invalidarComponente("cnpjcpf",viewRoot);
			ret=false;
		}
		
		if(item.getAssinanteBean().getDescricao().trim().length()==0) {
			
			invalidarComponente("nome",viewRoot);
			ret=false;
		}
		
		if(item.getPagamentoBean().getBandeiraBean()==null) {
			
			invalidarComponente("idbandeira",viewRoot);
			ret=false;
		}
		if(item.getPagamentoBean().getCartao().trim().length()==0) {
			
			invalidarComponente("idcartao",viewRoot);
			ret=false;
		}
		
		if(item.getPagamentoBean().getCodseguranca().trim().length()==0) {
			
			invalidarComponente("idcodseg",viewRoot);
			ret=false;
		}
		if(item.getPagamentoBean().getCodseguranca().trim().length()==0) {
			
			invalidarComponente("idvalidade",viewRoot);
			ret=false;
		}
		
		return ret;
	}
	private boolean validarProdutos(Contrato item) {
		if(item.getContratoProdutos()!=null && item.getContratoProdutos().isEmpty()) {
			JsfUtil.addErrorMessage("Contrato não pode ser gravado sem produtos", "Procedimento não realizado");
		 	return false;
		}
		List<ContratoProduto> contratoProdutos = item.getContratoProdutos();
		for (int i = 0; i < contratoProdutos.size(); i++) {
			if(contratoProdutos.get(i).getValortotal()<=0) {
				JsfUtil.addErrorMessage("O produto ["+ i +"] não possui valor", "Procedimento não realizado");
				return false;
			}
			
		}
		
		return true;
	}
	private boolean validarBrindes(Integer codigoEquipe, List<ContratoBrinde> contratoBrindes, Integer codigoPontoVenda) {
		HashMap<Integer, Double> quantidadesSumarizadas = new HashMap<>();
		Integer codigoBrinde = 0;
		Double qtArmazenada=0d;
		
		for (Iterator iterator = contratoBrindes.iterator(); iterator.hasNext();) {
			ContratoBrinde contratoBrinde = (ContratoBrinde) iterator.next();
			if(contratoBrinde.getId()==null) {
				codigoBrinde = contratoBrinde.getBrindBean().getCodigo();
				qtArmazenada = quantidadesSumarizadas.get(codigoBrinde)==null ? 0d : quantidadesSumarizadas.get(codigoBrinde); 
				if(quantidadesSumarizadas.containsKey(codigoBrinde)) {
					quantidadesSumarizadas.replace(codigoBrinde, qtArmazenada +contratoBrinde.getQuantidade());
				}else {
				   quantidadesSumarizadas.put(codigoBrinde, qtArmazenada +contratoBrinde.getQuantidade());
				}
			}
			
		}
		Integer codigoBrindeSumarizado=0;
		Double QuantidadeSumarizada=0d;
		Double QuantidadeEstoque=0d;
		if(!quantidadesSumarizadas.isEmpty()) {
			Set<Integer> keySet = quantidadesSumarizadas.keySet();
			for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
				codigoBrindeSumarizado = (Integer) iterator.next();
				 QuantidadeSumarizada = quantidadesSumarizadas.get(codigoBrindeSumarizado);
				Brinde findLocalizado = getBrindeFacade().find(codigoBrindeSumarizado);
				if(codigoEquipe==null) {
					QuantidadeEstoque =findLocalizado.getQuantidade()==null ? 0d : findLocalizado.getQuantidade();
				}else {
					List<BrindeEstoqueEquipe> retornarEstoqueEquipe = getBrindeEstoqueFacade().RetornarEstoqueEquipe(findLocalizado.getCodigo(),codigoEquipe,codigoPontoVenda);
					if(retornarEstoqueEquipe!=null && !retornarEstoqueEquipe.isEmpty()) {
						QuantidadeEstoque = retornarEstoqueEquipe.get(0).getQuantidade();
					}
				}
				if(QuantidadeEstoque<QuantidadeSumarizada) {
					JsfUtil.addErrorMessage("O Estoque ["+ QuantidadeEstoque +"] do brinde [ "+ findLocalizado.getDescricao()  +" ] não é suficiente para realizar esta operação", "Procedimento não realizado");
					return false;
				}
				
			}
		}
		
		return true;
	}
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		try {

			Contrato item = getItem();
			if(!validarCamposObrigorios(item)) {
				JsfUtil.addErrorMessage("Campo(s) obrigatorio(s) não foram preenchidos", "Procedimento não realizado");
				PrimeFacesContext.getCurrentInstance().validationFailed();
				return;
			}
			
			if(!validarProdutos(item)) {
				PrimeFacesContext.getCurrentInstance().validationFailed();
				return;
			}
			
			if(!validarBrindes((item.getEquipeBean()==null ? null : item.getEquipeBean().getCodigo()), item.getContratoBrindes(), item.getPontoDeVendaBean().getCodigo())) {
				PrimeFacesContext.getCurrentInstance().validationFailed();
				return;
			}
			if(item.getInclusao()==null) {
				List<Contrato> contratosDisponiveis = getContratofacade().contratosDisponiveis(null,item.getCodigocontrato());
				
				if(contratosDisponiveis.isEmpty()) {
					
					JsfUtil.addErrorMessage("O contrato digitado não foi localizado", "Contrato não Encontrado");
					FacesContext.getCurrentInstance().validationFailed();
					atualizar();
					return;
				}
			}
			
			//item.setDescricao(item.getDescricao().toUpperCase());

			Contrato localizarPorNome = getContratofacade().localizarPorCodigo(item.getCodigocontrato());
			if (localizarPorNome != null && localizarPorNome.getCodigo().intValue() != item.getCodigo().intValue()) {
				JsfUtil.addErrorMessage("O Contrato informado já foi cadastrado", "Procedimento não realizado");
				FacesContext.getCurrentInstance().validationFailed();
				atualizar();
				return;
			}
			
//			if(item.getCodigo()==null) {
//				if(!getAutorizacaoRecurso().VerificarAcesso("ContratoDigit", "criar",true, item.getCodigo().toString())) {
//					return ;
//				}
//			}else {
//				if(!getAutorizacaoRecurso().VerificarAcesso("ContratoDigit", "editar",true, item.getCodigo().toString())) {
//					return ;
//				}
//			}
				
			
			List<ContratoBrinde> contratoBrindes = item.getContratoBrindes();

			if(contratoBrindes!=null) {
				for (Iterator iterator = contratoBrindes.iterator(); iterator.hasNext();) {
					ContratoBrinde contratoBrinde = (ContratoBrinde) iterator.next();
					if(contratoBrinde.getId()<0) {
						contratoBrinde.setId(null);
					}
					
				}
			}
			
			List<ContratoProduto> contratoProdutos = item.getContratoProdutos();
			if(contratoProdutos!=null) {
				for (Iterator iterator = contratoProdutos.iterator(); iterator.hasNext();) {
					ContratoProduto contratoProduto = (ContratoProduto) iterator.next();
					if(contratoProduto.getId()<0) {
						contratoProduto.setId(null);
						contratoProduto.setContrato(item);
					}
				}
				
				
			}
		 	boolean contratoNovo =  (item.getCodigo()==null ? true : false);
		 	
			item.getPagamentoBean().setContrato(item);
			 
			getContratofacade().gravarContrato(item);
			String texto = item.getCodigocontrato().toString() + " - Assinante :  " + item.getAssinanteBean().getDescricao() + " - Valor : " + item.getPagamentoBean().getValor(); 
 			
			if(contratoNovo) {
				auditoriaFacade.auditar("ContratoDigit", "criar", texto);
			}else {
				auditoriaFacade.auditar("ContratoDigit", "editar", texto);
			}
			
			JsfUtil.addSuccessMessage("Contrato gravado com sucesso", "Procedimento OK");
		 
			atualizar();
		} catch (Exception e) {
			PrimeFacesContext.getCurrentInstance().validationFailed();
			JsfUtil.addErrorMessage(e, "create");
			// TODO: handle exception
		}

	}
	
	public Double EstoqueAtual(Brinde b) {
		Double ret =0d;
		
		Contrato item = getItem();
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
	}

	@Override
	public FlashApp getFlash() {
		// TODO Auto-generated method stub

		return this.flashapp;
	}

	@Override
	public void setFlash(FlashApp flash) {
		this.flashapp = flash;
		// TODO Auto-generated method stub

	}

	@Override
	public Contrato getItem() {
		// TODO Auto-generated method stub
		Contrato ret = (Contrato) getFlash().getValoresPorID("contratoForm").get("item");
		if(ret==null) {
			ret= new Contrato();
			setItem(ret);
		}
		return ret;
	}

	@Override
	public void setItem(Contrato item) {
		getFlash().getValoresPorID("contratoForm").put("item", item);
		// TODO Auto-generated method stub

	}

	@Override
	public List<Contrato> getItens() {
		ArrayList<Contrato> itens = (ArrayList<Contrato>) getFlash().getValoresPorID("contratoForm").get("itens");
		if (itens == null) {
			itens = (ArrayList<Contrato>) getContratofacade().findAllLazy(getLoginInfo().getCodigoEquipeVinculada());
			setItens(itens);
		}
		// TODO Auto-generated method stub
		return itens;
	}

	@Override
	public void setItens(List<Contrato> itens) {
		// TODO Auto-generated method stub
		getFlash().getValoresPorID("contratoForm").put("itens", itens);

	}
	 
	 
	public LazyDataModel<Contrato> getItensLazy() {

		LazyDataModel<Contrato> itens = (LazyDataModel<Contrato>) getFlash().getValoresPorID("contratoForm").get("itensLazy");
		try {
 
			if (itens==null) {
				itens = new LazyDataModel<Contrato>() {
					private static final long serialVersionUID = 1L;

					@Override
					public List<Contrato> load(int first, int pageSize, String sortField, SortOrder sortOrder,
							Map<String, Object> filters) {

						FiltrosLazyDataModel filtrosLazyDataModel = new FiltrosLazyDataModel(first, pageSize, sortField,
								sortOrder, filters);
						
						
						filtrosLazyDataModel.getMapeamentoCampoViewModel().put("codigocontrato", "c.codigocontrato");
						filtrosLazyDataModel.getMapeamentoCampoViewModel().put("assinanteBean.descricao","a.descricao");
						filtrosLazyDataModel.getMapeamentoCampoViewModel().put("assinanteBean.cnpjcpf","a.cnpjcpf");
						filtrosLazyDataModel.getMapeamentoCampoViewModel().put("pagamentoBean.cartao","cp.cartao");
						
						
						LazyObjetos<Contrato> findAllLazy = getContratofacade().findAllLazy(getLoginInfo().getCodigoEquipeVinculada(),filtrosLazyDataModel);

						setRowCount(findAllLazy.getTotalObjetos());

						return findAllLazy.getLista() ;
					}
				};
				// itens = (ArrayList<Contrato>)
				// getContratofacade().findAllLazy(getLoginInfo().getCodigoEquipeVinculada());
				setItensLazy(itens);
			}
		} catch (Exception ex) {
			JsfUtil.addErrorMessage(ex, "getItens");
			// TODO: handle exception
		}
		// TODO Auto-generated method stub
		return itens;
	}

	 
	public void setItensLazy(LazyDataModel<Contrato> itens) {
		// TODO Auto-generated method stub
		getFlash().getValoresPorID("contratoForm").put("itensLazy", itens);

	}
	 

	public ContratoFacade getContratofacade() {
		return produtofacade;
	}

	public void setContratofacade(ContratoFacade produtofacade) {
		this.produtofacade = produtofacade;
	}

	public Boolean getEditar() {
		return editar;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
	}

	public EquipeFacade getEquipeFacade() {
		return equipeFacade;
	}

	public void setEquipeFacade(EquipeFacade equipeFacade) {
		this.equipeFacade = equipeFacade;
	}

	public VendedorFacade getVendedorFacade() {
		return vendedorFacade;
	}

	public void setVendedorFacade(VendedorFacade vendedorFacade) {
		this.vendedorFacade = vendedorFacade;
	}

	private  List<Vendedor> vendedoresEquipe;
	public List<Vendedor> getVendedoresDaEquipe() {
		Contrato item = getItem(); 
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



	public void setMascaraCPF(String mascaraCPF) {
		this.mascaraCPF = mascaraCPF;
	}
	
	public String getMascaraPessoa() {
		Contrato selected = getItem();
        if (selected != null) {
            if (selected.getAssinanteBean().getTipopessoa() != null) {
                if (selected.getAssinanteBean().getTipopessoa().equalsIgnoreCase("fisica")) {
                    return "999.999.999-99";
                } else {
                    return "99.999.999/9999-99";
                }
            } else {
                return "999.999.999-99";
            }
        } else {
            return "";
        }
    }
	
	 public void ValidarCNPJ(AjaxBehaviorEvent event) {
	        try {

	        	Contrato selected = getItem();
	            String cnpj = selected.getAssinanteBean().getCnpjcpf();
	            if (cnpj != null) {
	                if (!Validacoes()) {
	                    selected.getAssinanteBean().setCnpjcpf(null);
	                    
	                    return;
	                }
	            }
	             
	        } catch (Exception e) {
	            JsfUtil.addErrorMessage("Falha na requisição\n\n" + e.getMessage());
	        }

	    }
	 private boolean Validacoes() {
	        boolean ret = false;
	        try {
	        	Contrato selected = getItem();
	        	
	            String cnpj = selected.getAssinanteBean().getCnpjcpf();
	            if (cnpj != null) {
	                if (!ValidacoesDiversas.ValidarCNPJ_Ou_CPF(cnpj)) {
	                    JsfUtil.addErrorMessage("CNPJ/CPF digitado é inválido", "CNPJ/CPF INVÁLIDO");
	                    return false;
	                }
	            }
	            
	            ret = true;

	        } catch (Exception e) {
	            JsfUtil.addErrorMessage("Falha na requisição\n\n" + e.getMessage());
	        }
	        return ret;
	    }

	public CepWebService getCepWebService() {
		return cepWebService;
	}

	public void setCepWebService(CepWebService cepWebService) {
		this.cepWebService = cepWebService;
	}

	public ArrayList<Estados> getEstados() {
		if(estados==null) {
			estados=cepWebService.getEstados();
			
		}
		return estados;
	}

	public void setEstados(ArrayList<Estados> estados) {
		this.estados = estados;
	}

	public ArrayList<Municipio> getMunicipios() {
		Contrato item = getItem(); 
		if(item!=null && item.getAssinanteBean().getEstado()!=null) {
			municipio = getCepWebService().getMunicipios(item.getAssinanteBean().getEstado());
		}
		return municipio;
	}

	public void setMunicipios(ArrayList<Municipio> municipio) {
		this.municipio = municipio;
	}
	public void pesquisarCep() {
		try {
			Contrato v = getItem();
			if(v.getAssinanteBean().getCep()!=null && !v.getAssinanteBean().getCep().isEmpty()) {
				cep cepconsultar = getCepWebService().PesquisarCEP(v.getAssinanteBean().getCep());
				if(cepconsultar!=null) {
					StringBuilder endereco = new StringBuilder();
					endereco.append(cepconsultar.getTipoLogradouro()!=null ? cepconsultar.getTipoLogradouro(): "");
					if(endereco.length()>0) {
						endereco.append(" ");
					}
					endereco.append(cepconsultar.getLogradouro()!=null ? cepconsultar.getLogradouro(): "");

					v.getAssinanteBean().setEndereco(endereco.toString()) ;
					
					 
					v.getAssinanteBean().setBairro(cepconsultar.getBairro());
					v.getAssinanteBean().setCidade(cepconsultar.getIbge());
					v.getAssinanteBean().setEstado(cepconsultar.getEstado());
					v.getAssinanteBean().setComplemento(cepconsultar.getComplemento()	);
					
					setMunicipios(getCepWebService().getMunicipios(cepconsultar.getEstado()));
					
				}else {
					JsfUtil.addErrorMessage("O Cep informado não foi localizado", "Pesquisa de CEP");
				}
				
			}
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "pesquisarCep");
			FacesContext.getCurrentInstance().validationFailed();
		}
	}
	public void validarNovoItem() {
		 
		try {
		
			Contrato item = getItem();
			
			if(item.getCodigocontrato()==null) {
				JsfUtil.addErrorMessage("Para inserir uma oferta primeiro informe um contrato", "Operação não permitida");
				FacesContext.getCurrentInstance().validationFailed();
				return;
			}
			if(item.getEquipeBean()==null) {
				JsfUtil.addErrorMessage("Para inserir uma oferta escolha informe uma equipe", "Operação não permitida");
				FacesContext.getCurrentInstance().validationFailed();
				return;
			}
			
			List<ContratoProduto> contratoProdutoItens = item.getContratoProdutos();
			
			if(contratoProdutoItens!=null && !contratoProdutoItens.isEmpty())
			{
				ContratoProduto contratoProdutoItem = contratoProdutoItens.get(contratoProdutoItens.size()-1);
				if(contratoProdutoItem!=null && 
						( contratoProdutoItem.getProdutoBean()==null || contratoProdutoItem.getQuantidade()==0d )) {
					JsfUtil.addErrorMessage("Já existe um brinde novo e ele esta vazio", "Operação não permitida");
					FacesContext.getCurrentInstance().validationFailed();
					return;
					
				}
			}
		
			for (int i = 0; i < contratoProdutoItens.size(); i++) {
			   if(contratoProdutoItens.get(i).getProdutoBean()==null) {
				   JsfUtil.addErrorMessage("O item ["+  i+1 +"] não possui um produto informado", "Operação não permitida");
					FacesContext.getCurrentInstance().validationFailed();
					return;
			   }
			}
			setOfertasDisponiveis(null);
			//item.setTotal(getTotalEntradaItens());
			
			setContratoProdutoConsultar(null);
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "prepararNovoItem");
			// TODO: handle exception
		}
		
		 
		// TODO Auto-generated method stub
		
	}
	public ContratoProduto getContratoProdutoConsultar() {
		return (ContratoProduto)getFlash().getValoresPorID("contratoForm").get("contratoProdutoConsultar");
		//return brindeConsultar;
	}
	public void setContratoProdutoConsultar(ContratoProduto contratoProduto) {
		 getFlash().getValoresPorID("contratoForm").put("contratoProdutoConsultar",contratoProduto);
		//this.brindeConsultar = brindeConsultar;
	}
 
	public void prepararConsultarProduto(ContratoProduto contratoProduto) {
		setContratoProdutoConsultar(contratoProduto);
	}
	public Double getQuantidadeTotalProdutos() {
		Double ret = 0d;
		
		List<ContratoProduto> contratoProdutoItens = getItem().getContratoProdutos();
		for (Iterator iterator = contratoProdutoItens.iterator(); iterator.hasNext();) {
			ContratoProduto contratoProduto = (ContratoProduto) iterator.next();
			ret+=  contratoProduto.getValortotal()  * contratoProduto.getQuantidade();
			
		}
		return ret;
	}
	
	public Double getTotalBrindes() {
		Double ret = 0d;
		
		List<ContratoBrinde> contratoBrindeItens = getItem().getContratoBrindes();
		for (Iterator iterator = contratoBrindeItens.iterator(); iterator.hasNext();) {
			ContratoBrinde contratoBrinde = (ContratoBrinde) iterator.next();
			ret+=  contratoBrinde.getValor() * contratoBrinde.getQuantidade() ;
			
		}
		return ret;
	}
	public Double getValorTotalParcelaProdutos() {
		Double ret = 0d;
		
		List<ContratoProduto> contratoProdutoItens = getItem().getContratoProdutos();
		for (Iterator iterator = contratoProdutoItens.iterator(); iterator.hasNext();) {
			ContratoProduto contratoProduto = (ContratoProduto) iterator.next();
			ret+=  contratoProduto.getValorparcela() * contratoProduto.getQuantidade();
			
		}
		return ret;
	}
	 
	 
	public void atualizarValorTotalProduto(ContratoProduto contratoProduto) {
		try {
			Double valorParcela =0d;
			if(contratoProduto.getParcelas()<1 || contratoProduto.getParcelas()>12) {
				JsfUtil.addErrorMessage("Número de parcelas informada inválida. Informe um valor entre [1-12]", "Informação informada inválida");
				contratoProduto.setParcelas(1);
			}
			if(contratoProduto.getQuantidade()<1 || contratoProduto.getQuantidade()>99) {
				JsfUtil.addErrorMessage("Quantidade informada inválida. Informe um valor entre [1-99]", "Informação informada inválida");
				contratoProduto.setQuantidade(1);
			}
			OfertaIten localizarOfertaItemPorEdicao = getOfertaFacade().localizarOfertaItemPorEdicao(contratoProduto.getEdicao(), contratoProduto.getOfertaBean().getCodigo());
		    if(contratoProduto.getParcelas()==1) {
				valorParcela=localizarOfertaItemPorEdicao.getVez1();
			}
			else if(contratoProduto.getParcelas()==2) {
				valorParcela=localizarOfertaItemPorEdicao.getVez2();
			}
			else if(contratoProduto.getParcelas()==3) {
				valorParcela=localizarOfertaItemPorEdicao.getVez3();
			}
			else if(contratoProduto.getParcelas()==4) {
				valorParcela=localizarOfertaItemPorEdicao.getVez4();
			}
			else if(contratoProduto.getParcelas()==5) {
				valorParcela=localizarOfertaItemPorEdicao.getVez5();
			}
			else if(contratoProduto.getParcelas()==6) {
				valorParcela=localizarOfertaItemPorEdicao.getVez6();
			}
			else if(contratoProduto.getParcelas()==7) {
				valorParcela=localizarOfertaItemPorEdicao.getVez7();
				
			}else if(contratoProduto.getParcelas()==8) {
				valorParcela=localizarOfertaItemPorEdicao.getVez8();
			}
			else if(contratoProduto.getParcelas()==9) {
				valorParcela=localizarOfertaItemPorEdicao.getVez9();
			}
			else if(contratoProduto.getParcelas()==10) {
				valorParcela=localizarOfertaItemPorEdicao.getVez10();
			}
			else if(contratoProduto.getParcelas()==11) {
				valorParcela=localizarOfertaItemPorEdicao.getVez11();
			}
			else if(contratoProduto.getParcelas()==12) {
				valorParcela=localizarOfertaItemPorEdicao.getVez12();
			}
			
		    contratoProduto.setValorparcela(valorParcela);
		    contratoProduto.setValortotal((valorParcela * contratoProduto.getParcelas()) * contratoProduto.getQuantidade());

		    getItem().getPagamentoBean().setValor(getQuantidadeTotalProdutos());
		    
		    List<ContratoBrinde> contratoBrindes = getItem().getContratoBrindes();
		    if(contratoBrindes!=null && !contratoBrindes.isEmpty()) {
		    	for (Iterator iterator = contratoBrindes.iterator(); iterator.hasNext();) {
					ContratoBrinde contratoBrinde = (ContratoBrinde) iterator.next();
					if(contratoBrinde.getContratoProdutoBean()!=null &&
							contratoBrinde.getContratoProdutoBean().getId()==contratoProduto.getId()) {
							//atualizar a quantidade de brindes
						contratoBrinde.setQuantidade(contratoProduto.getQuantidade().doubleValue());
						break;
					}
						
				}
		    }
		    
			
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "atualizarValorTotalProduto");
			// TODO: handle exception
		}
		
		
	}

	private List<Oferta> ofertasDisponiveis;
	
	public void atualizarProdutoConsultadoNoGrid(Produto produto, OfertaIten ofertaItem, Double valorparcela , Integer parcela) {
		ContratoProduto contratoProdutoConsultar = getContratoProdutoConsultar();
		if(getContratoProdutoConsultar()==null) {
			NovoItem();
			setContratoProdutoConsultar(getItem().getContratoProdutos().get(getItem().getContratoProdutos().size()-1));
			NovoItemBrinde(ofertaItem.getOferta().getOfertaBrindes(), getContratoProdutoConsultar());
			PrimeFaces.current().executeScript("scrollToBottom();");
			PrimeFaces.current().focus(":contratoForm:datalistitens:"+ (getItem().getContratoProdutos().size()-1) + ":idQuantidadeProduto");
		}else {
			FacesContext.getCurrentInstance().validationFailed();
		}
	
		getContratoProdutoConsultar().setProdutoBean(produto);
		getContratoProdutoConsultar().setEdicao(ofertaItem.getEdicao());
		getContratoProdutoConsultar().setOfertaBean(ofertaItem.getOferta());
		getContratoProdutoConsultar().setQuantidade(1);
		getContratoProdutoConsultar().setParcelas(parcela);
		getContratoProdutoConsultar().setValorparcela(valorparcela);
		getContratoProdutoConsultar().setValortotal((valorparcela==null ? 0d : valorparcela) * parcela);
		getItem().getPagamentoBean().setValor(getQuantidadeTotalProdutos());
	}
	public void NovoItem() {
		 
		try {
			Contrato item = getItem();
			//List<BrindeDevolucaoIten> brindaEntradaItens = item.getBrindeDevolucaoItens();
				
			ContratoProduto novoItem = new ContratoProduto();
			novoItem.setId(-(item.getContratoProdutos().size()+1));
			 
			item.getContratoProdutos().add(novoItem);
			
			setItem(item);
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "prepararNovoItem");
			// TODO: handle exception
		}
		
		 
		// TODO Auto-generated method stub
		
	}
	public void validarNovoItemBrinde() {
		try {
			Contrato item = getItem();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	public void NovoItemBrinde(List<OfertaBrindes> ofertaBrindes, ContratoProduto contratoproduto) {
		 
		try {
			if (ofertaBrindes != null && !ofertaBrindes.isEmpty()) {
				Contrato item = getItem();
				for (Iterator iterator = ofertaBrindes.iterator(); iterator.hasNext();) {
					OfertaBrindes ofertaBrinde = (OfertaBrindes) iterator.next();

					// List<BrindeDevolucaoIten> brindaEntradaItens =
					// item.getBrindeDevolucaoItens();

					Double quantidadeBrinde = ofertaBrinde.getQuantidade()==null ? 0d : ofertaBrinde.getQuantidade().doubleValue();
					Brinde brinde=ofertaBrinde.getBrindeBean();
					ContratoBrinde novoItem = new ContratoBrinde();
					novoItem.setContratoProdutoBean(contratoproduto);
					novoItem.setBrindBean(brinde);
					novoItem.setContrato(item);
					novoItem.setQuantidade(quantidadeBrinde);
					novoItem.setValor(brinde.getValor()*quantidadeBrinde);
					novoItem.setId(-(item.getContratoBrindes().size() + 1));

					item.getContratoBrindes().add(novoItem);

				}
				setItem(item);
			}
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "NovoItemBrinde");
			// TODO: handle exception
		}
		
		 
		// TODO Auto-generated method stub
		
	}
	
	public void excluirContratoProdutoItem(ContratoProduto item) {
		// TODO Auto-generated method stub
		try {
			
			List<ContratoBrinde> contratoBrindes = getItem().getContratoBrindes();
			
			contratoBrindes.removeIf(brinde -> brinde.getContratoProdutoBean()!=null && brinde.getContratoProdutoBean().equals(item));
			
			getItem().getContratoProdutos().remove(item);
	
			getItem().getPagamentoBean().setValor(getQuantidadeTotalProdutos());
			
			
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "excluirContratoProdutoItem");
			// TODO: handle exception
		}
		
		 
		
	}

	public OfertaFacade getOfertaFacade() {
		return ofertaFacade;
	}

	public void setOfertaFacade(OfertaFacade ofertaFacade) {
		this.ofertaFacade = ofertaFacade;
	}

	//private LazyDataModel<Oferta> ofertasDisponiveisLazy=null; 
	public LazyDataModel<Oferta> getOfertasDisponiveis() {
		LazyDataModel<Oferta> ofertasDisponiveisLazy = (LazyDataModel<Oferta>) getFlash().getValoresPorID("contratoForm").get("ofertasDisponiveisLazy");

		if (ofertasDisponiveisLazy == null) {
			ofertasDisponiveisLazy = new LazyDataModel<Oferta>() {
				private static final long serialVersionUID = 1L;
				@Override
				public List<Oferta> load(int first, int pageSize, String sortField, SortOrder sortOrder,
						Map<String, Object> filters) {

					FiltrosLazyDataModel filtrosLazyDataModel = new FiltrosLazyDataModel(first, pageSize, sortField,
							sortOrder, filters);
					
					filtrosLazyDataModel.getMapeamentoCampoViewModel().put("codigo", "o.codigo");
					filtrosLazyDataModel.getMapeamentoCampoViewModel().put("produtoBean.codigo", "p.codigo");
					filtrosLazyDataModel.getMapeamentoCampoViewModel().put("produtoBean.descricao", "p.descricao");

					LazyObjetos<Oferta> findAllLazy = getOfertaFacade().findAllLazy("ativos", getLoginInfo().getCodigoEquipeVinculada(),filtrosLazyDataModel);

					setRowCount(findAllLazy.getTotalObjetos());

					return findAllLazy.getLista();
				}
				
			};
			setOfertasDisponiveis(ofertasDisponiveisLazy);
		}
		return ofertasDisponiveisLazy;
	}
	public void setOfertasDisponiveis(LazyDataModel<Oferta> itens) {
		// TODO Auto-generated method stub
		getFlash().getValoresPorID("contratoForm").put("ofertasDisponiveisLazy",itens);
		
	}
	/*public List<Oferta> getOfertasDisponiveis() {
		if(ofertasDisponiveis==null) {
			ofertasDisponiveis=getOfertaFacade().findAllLazy("ativos");
		}
		return ofertasDisponiveis;
	}

	public void setOfertasDisponiveis(List<Oferta> ofertasDisponiveis) {
		this.ofertasDisponiveis = ofertasDisponiveis;
	}*/
	public List<Brinde> getBrindesDisponiveis() {
		if(brindesDisponiveis==null) {
			brindesDisponiveis=getBrindeFacade().findAll("ativos");
		}
		return brindesDisponiveis;
	}
	 
	
	public void definiContratoDisponivel() {
		System.out.println("");
	}
	
	public void CarregarContratoDisponivel(Contrato contratodisponivelSelecionado) {
		
		 
		try {
			contratodisponivelSelecionado= getContratofacade().getContratoEager(contratodisponivelSelecionado.getCodigo());
			//contratodisponivelSelecionado.setInclusao(new Date());
			setItem(contratodisponivelSelecionado);
			setContratoCarregado(true);
			resetarList();
			
			 

		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "carregarContrato");
			// TODO: handle exception
		}
		
	}
 
	
	
	public void prepararConsultarContratoDisponivel() {
		try {
			//setListaContratosDisponiveis(listaContratosDisponiveis);
			//setListaContratosDisponiveis( getContratofacade().contratosDisponiveis(null));
			if(getContratoCarregado()) {
				setContratoCarregado(false);
				prepararNovo();
				FacesContext.getCurrentInstance().validationFailed();
			}else {
				Contrato c = getItem();
				if(c.getCodigocontrato()!=null) {
					List<Contrato> contratosDisponiveis = getContratofacade().contratosDisponiveis(null,c.getCodigocontrato());
					if(contratosDisponiveis!=null && !contratosDisponiveis.isEmpty()) {
						CarregarContratoDisponivel(contratosDisponiveis.get(0));
						 
					}else {
						JsfUtil.addErrorMessage("O contrato digitado não foi localizado", "Contrato não Encontrado");
						c.setCodigocontrato(null);
						
						
					}
					FacesContext.getCurrentInstance().validationFailed();
				}else {
					setListaContratosDisponiveis(null);
				}
			}
				 
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "carregarContrato");
			// TODO: handle exception
		} 
		
		 
	}
	
	public void setBrindesDisponiveis(List<Brinde> brindesDisponiveis) {
		this.brindesDisponiveis = brindesDisponiveis;
	}

	private List<Brinde> brindesDisponiveis;
	
	public void excluirContratoBrindeItem(ContratoBrinde item) {
		// TODO Auto-generated method stub
		try {
			if(item.getContratoProdutoBean()==null) {
				getItem().getContratoBrindes().remove(item);
			}else {
				JsfUtil.addErrorMessage("Este brinde não pode ser excluido devido estar vinculado a uma oferta deste contrato", "Procedimento não realizado");
			}
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "excluirContratoBrindeItem");
			// TODO: handle exception
		}
	
		 
		
	}
	
	public List<BandeiraCartao> getBandeiraCartaos() {
		if(bandeiraCartaos==null) {
			bandeiraCartaos = getBandeiraCartaoFacade().findAll("ativos");
		}
		return bandeiraCartaos;
	}

	public void setBandeiraCartaos(List<BandeiraCartao> bandeiraCartaos) {
		this.bandeiraCartaos = bandeiraCartaos;
	}

	public BandeiraCartaoFacade getBandeiraCartaoFacade() {
		 
		return bandeiraCartaoFacade;
	}

	public void setBandeiraCartaoFacade(BandeiraCartaoFacade bandeiraCartaoFacade) {
		this.bandeiraCartaoFacade = bandeiraCartaoFacade;
	}
	 
	public List<Equipe> getEquipesDisponiveis() {
		if(equipesDisponiveis==null) {
			equipesDisponiveis = getEquipeFacade().findAllEquipes(getLoginInfo().getCodigoEquipeVinculada());
		}
		return equipesDisponiveis;
	}
	

	public void setEquipesDisponiveis(List<Equipe> equipesDisponiveis) {
		this.equipesDisponiveis = equipesDisponiveis;
	}

	public List<PontoDeVenda> getPontosDeVendadisponiveis() {
		if(pontosDeVendadisponiveis==null) {
			Contrato item = getItem();
			if(item.getEquipeBean()!=null) {
				pontosDeVendadisponiveis=getPontoDeVendaFacade().findAllPorEquipe(item.getEquipeBean().getCodigo());
			}
		}
		return pontosDeVendadisponiveis;
	}

	public void setPontosDeVendadisponiveis(List<PontoDeVenda> pontosDeVendadisponiveis) {
		this.pontosDeVendadisponiveis = pontosDeVendadisponiveis;
	}

	 
	public void setSubcanaisdisponiveis(List<Subcanal> subcanaisdisponiveis) {
		this.subcanaisdisponiveis = subcanaisdisponiveis;
	}

 
	public List<String> getCamposInvalidos() {
		return camposInvalidos;
	}

	public void setCamposInvalidos(List<String> camposInvalidos) {
		this.camposInvalidos = camposInvalidos;
	}

	public Boolean getContratoCarregado() {
		return (Boolean)getFlash().getValoresPorID("contratoForm").get("contratoCarregado");
		 
	}

	public void setContratoCarregado(Boolean contratoCarregado) {
		 
		getFlash().getValoresPorID("contratoForm").put("contratoCarregado",contratoCarregado);
	}

	public void atualizarGridContratosDisponiveis() {
		System.out.println("");
	}
	public LazyDataModel<Contrato> getListaContratosDisponiveis() {
		 
		LazyDataModel<Contrato> listaContratosDisponiveis = (LazyDataModel<Contrato>) getFlash().getValoresPorID("contratoForm").get("ListaContratosDisponiveis");
		if (listaContratosDisponiveis == null) {
			listaContratosDisponiveis = new LazyDataModel<Contrato>() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public List<Contrato> load(int first, int pageSize, String sortField, SortOrder sortOrder,
						Map<String, Object> filters) {

					FiltrosLazyDataModel filtrosLazyDataModel = new FiltrosLazyDataModel(first, pageSize, sortField,
							sortOrder, filters);

					LazyObjetos<Contrato> findAllLazy = getContratofacade().contratosDisponiveisLazy(getLoginInfo().getCodigoEquipeVinculada(), null, filtrosLazyDataModel);

					setRowCount(findAllLazy.getTotalObjetos());
					findAllLazy.getLista().sort(new Comparator<Contrato>() {

						@Override
						public int compare(Contrato o1, Contrato o2) {
							// TODO Auto-generated method stub
							return o1.getCodigo().compareTo(o2.getCodigo());
						}
						
					});	
					return findAllLazy.getLista();

				}
			};
			setListaContratosDisponiveis(listaContratosDisponiveis);
		}
		return listaContratosDisponiveis;
	}

	public void setListaContratosDisponiveis(LazyDataModel<Contrato> listaContratosDisponiveis) {
		getFlash().getValoresPorID("contratoForm").put("ListaContratosDisponiveis",listaContratosDisponiveis);
	}

	//private List<Contrato> listaContratosDisponiveis;

	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	public PontoDeVendaFacade getPontoDeVendaFacade() {
		return pontoDeVendaFacade;
	}

	public void setPontoDeVendaFacade(PontoDeVendaFacade pontoDeVendaFacade) {
		this.pontoDeVendaFacade = pontoDeVendaFacade;
	}

	public BrindeEstoqueFacade getBrindeEstoqueFacade() {
		return brindeEstoqueFacade;
	}

	public void setBrindeEstoqueFacade(BrindeEstoqueFacade brindeEstoqueFacade) {
		this.brindeEstoqueFacade = brindeEstoqueFacade;
	}

	public AutorizacaoRecurso getAutorizacaoRecurso() {
		return autorizacaoRecurso;
	}

	public void setAutorizacaoRecurso(AutorizacaoRecurso autorizacaoRecurso) {
		this.autorizacaoRecurso = autorizacaoRecurso;
	}

 

	private List<BandeiraCartao> bandeiraCartaos;
	
	private List<Equipe> equipesDisponiveis;
	
	private List<PontoDeVenda> pontosDeVendadisponiveis;
	
	private List<Subcanal> subcanaisdisponiveis;
	/*private final static List<String> VALID_COLUMN_KEYS = Arrays.asList("vez1", "vez2", "vez3");
	private List<ColumnModel> columns=new ArrayList<>();
	private String colunasVisiveis = "";
	private void createDynamicColumns() {
        String[] columnKeys = getColunasVisiveis().split(" ");
        columns = new ArrayList<ColumnModel>();   
         
        for(String columnKey : columnKeys) {
            String key = columnKey.trim();
             
            if(VALID_COLUMN_KEYS.contains(key)) {
                columns.add(new ColumnModel(columnKey.toUpperCase(), columnKey));
            }
        }
    }
	
	 public void updateColumns() {
	        //reset table state
	        UIComponent table = FacesContext.getCurrentInstance().getViewRoot().findComponent(":idFormPesquisarProduto:datalistProdutos");
	        //table.setValueExpression("sortBy", null);
	         
	        //update columns
	        createDynamicColumns();
	    }
	public String getColunasVisiveis() {
		return colunasVisiveis;
	}

	public void setColunasVisiveis(String colunasVisiveis) {
		this.colunasVisiveis = colunasVisiveis;
	}
	static public class ColumnModel implements Serializable {
		 
        private String header;
        private String property;
 
        public ColumnModel(String header, String property) {
            this.header = header;
            this.property = property;
        }
 
        public String getHeader() {
            return header;
        }
 
        public String getProperty() {
            return property;
        }
    }
*/	


}
