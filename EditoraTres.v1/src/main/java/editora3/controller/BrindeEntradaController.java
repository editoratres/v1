package editora3.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
 
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

 
import org.primefaces.PrimeFaces;
 
import org.primefaces.event.CellEditEvent;
 

import editora3.entidades.BrindeEntradaItens;
import editora3.entidades.Brinde;
 
import editora3.entidades.BrindeEntrada;
 
import editora3.entidades.Fornecedor;
import editora3.facade.AuditoriaFacade;
import editora3.facade.BrindeEntradaFacade;
import editora3.facade.BrindeFacade;
import editora3.facade.FornecedorFacade;
import editora3.seguranca.AutorizacaoRecurso;
import editora3.util.JsfUtil;
@Named("brindeEntradaController") 
@RequestScoped
public class BrindeEntradaController implements AbstractController<BrindeEntrada> {

	@Inject
	FlashApp flashapp;
	
	@Inject
	private BrindeEntradaFacade brindeEntradafacade;
	@Inject
	private FornecedorFacade fornecedorFacade; 
	@Inject
	private BrindeEntradaItens brindaEntradaIten;
	private String pesquisarProduto;

	@Inject
	private BrindeFacade brindeFacade; 
	
	@Inject
	private AuditoriaFacade auditoriaFacade;  
	
	@Inject
	private AutorizacaoRecurso autorizacaoRecurso; 
	
	private List<BrindeEntradaItens> brindaEntradaItensGrid;
	
	private Boolean editar=false;
	
	@Override
	public void excluir(BrindeEntrada item) {
		// TODO Auto-generated method stub
		if(autorizacaoRecurso.VerificarAcesso("BrindeEntrada", "excluir",true,item.getCodigo().toString() ,true)) {
			getBrindeEntradafacade().cancelarEntradaBrinde(item);
			setItens(null);
		}
		
	}
	public Double getTotalEntradaItens() {
		Double ret = 0d;
		
		List<BrindeEntradaItens> brindaEntradaItens = getItem().getBrindaEntradaItens();
		for (Iterator iterator = brindaEntradaItens.iterator(); iterator.hasNext();) {
			BrindeEntradaItens brindeEntradaItens = (BrindeEntradaItens) iterator.next();
			ret+=brindeEntradaItens.getQuantidade() * brindeEntradaItens.getValorunitario();
			
		}
		return ret;
	}
	 
	@PostConstruct
	public void iniciar() {
		setItens(null);
	}
	public static void update(String id) {
		   PrimeFaces pf = PrimeFaces.current(); //RequestContext.getCurrentInstance() for <PF 6.2
		   if(pf.isAjaxRequest()) pf.ajax().update(id);
		}

		/** Whether onCellEdit changed the value */
		boolean onCellEditChange;

		public void onCellEditRemote() { 
		     update(":brindeEntradaForm:datalistItens:idsubtotal");
		}
	 public void onCellEdit(CellEditEvent event) {
		 try {
 		 int rowIndex = 0;
 		 
 		int index = event.getRowIndex();
 		 
 		PrimeFaces.current().ajax().update(":brindeEntradaForm:datalistItens:idsubtotal");
 		//PrimeFaces.current().ajax().update("badSsnForm:badSsnsList:" + index + ":dispName");
				 
		} catch (Exception e) {
			// TODO: handle exception
			JsfUtil.addErrorMessage(e, "onCellEdit");
		}
	      
	         
	      /*  if(newValue != null && !newValue.equals(oldValue)) {
	            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
	            FacesContext.getCurrentInstance().addMessage(null, msg);
	        }*/
	    }
	public  List<String> localizarProduto(String produto) {
		List<String> ret=new ArrayList<>();
		try {
			
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "localizarProduto");
			// TODO: handle exception
		}
		return ret;
	}
	public void excluirItem(BrindeEntradaItens item) {
		// TODO Auto-generated method stub
		getItem().getBrindaEntradaItens().remove(item);
		 
	 
		
	}

	@Override
	public void prepararEditar(BrindeEntrada item) {
		if(autorizacaoRecurso.VerificarAcesso("BrindeEntrada", "editar",true,null,false)) {
			setEditar(true);
			setItem(item);
		}
	}
	
	 

	@Override
	public void atualizar() {
		// TODO Auto-generated method stub
		setItens(null);
		
	}

	@Override
	public void prepararNovo() {
		
		//setItem(new BrindeEntrada());
		if(autorizacaoRecurso.VerificarAcesso("BrindeEntrada", "criar",true,null,false)) {

			BrindeEntrada item =new BrindeEntrada();
			item.setData(new java.util.Date());
			 
			 setItem(item);
			 setEditar(false);
		}
		
	} 
	public void prepararNovoItem() {
		 
		try {
			BrindeEntrada item = getItem();
			List<BrindeEntradaItens> brindaEntradaItens = item.getBrindaEntradaItens();
			
			if(brindaEntradaItens!=null && !brindaEntradaItens.isEmpty())
			{
				BrindeEntradaItens brindeEntradaItem = brindaEntradaItens.get(brindaEntradaItens.size()-1);
				if(brindeEntradaItem!=null && brindeEntradaItem.getBrindeBean()==null) {
					JsfUtil.addErrorMessage("Ja existe um brinde novo e ele esta vazio", "Operação não permitida");
					FacesContext.getCurrentInstance().validationFailed();
					return;
					
				}
			}
		
			for (int i = 0; i < brindaEntradaItens.size(); i++) {
			   if(brindaEntradaItens.get(i).getBrindeBean()==null) {
				   JsfUtil.addErrorMessage("O item ["+  i+1 +"] não possui um brinde informado", "Operação não permitida");
					FacesContext.getCurrentInstance().validationFailed();
					return;
			   }
			}
			 
			item.setTotal(getTotalEntradaItens());
			
			BrindeEntradaItens novoItem = new BrindeEntradaItens();
			novoItem.setId(-(item.getBrindaEntradaItens().size()+1));
			 
			item.getBrindaEntradaItens().add(novoItem);
			
			setItem(item);
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "prepararNovoItem");
			// TODO: handle exception
		}
		
		 
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fecharDialogo() {
		PrimeFaces.current().resetInputs("brindeEntradaForm");
		setItens(null);
		setItem(null);
		getFlash().limparPorId("brindeEntradaForm");
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		try {
			
			BrindeEntrada item = getItem();
		    //item.setDescricao(item.getDescricao().toUpperCase());
			if(item.getNotafiscal()!=null) {
				item.setNotafiscal(item.getNotafiscal().trim());
			
			    BrindeEntrada localizarPorNome = getBrindeEntradafacade().localizarPorNotaFiscal(item.getNotafiscal().trim());
			    if(localizarPorNome!=null && localizarPorNome.getCodigo()!=item.getCodigo()) {
			    	JsfUtil.addErrorMessage("A nota fiscal informado já foi cadastrada", "Procedimento não realizado");
			    	FacesContext.getCurrentInstance().validationFailed();
			    	atualizar();
			    	return;
			    }
			}
			
			
		    if(item.getBrindaEntradaItens()!=null && !item.getBrindaEntradaItens().isEmpty()) {
		    	for (int i = 0; i < item.getBrindaEntradaItens().size(); i++) {
		    		BrindeEntradaItens brindeEntradaItem = item.getBrindaEntradaItens().get(i);
		    		if(brindeEntradaItem.getBrindeBean()==null) {
		    			JsfUtil.addErrorMessage("Não foi especificao o brinde para o  item ["+ (i+1) + "]", "Procedimento não realizado");
		    			FacesContext.getCurrentInstance().validationFailed();
		    			return;
		    		}
		    		if(brindeEntradaItem.getQuantidade()==0) {
		    			JsfUtil.addErrorMessage("Não foi especificao a quantidade do brinde para o  item ["+ (i+1) + "]", "Procedimento não realizado");
		    			FacesContext.getCurrentInstance().validationFailed();
		    			return;
		    		}
		    		if(brindeEntradaItem.getId()<0) {
		    			brindeEntradaItem.setId(null);
		    			brindeEntradaItem.setBrindeEntrada(item);
		    		}
					 
				}
		    }
		    
		    /*if(getTotalEntradaItens()==0d) {
		    	JsfUtil.addErrorMessage("Entradas sem brinde(s) não são permitidas", "Procedimento não realizado");
    			FacesContext.getCurrentInstance().validationFailed();
    			return;
		    }*/
		    
		   
		    
		    if(item.getCodigo()==null) {
		    	item.setTotal(getTotalEntradaItens());
		    	getBrindeEntradafacade().CriarEntrada(item);
		    	auditoriaFacade.auditar("BrindeEntrada", "criar", item.getCodigo().toString());
		    	JsfUtil.addSuccessMessage("Entrada de brinde criada com sucesso", "Procedimento OK");
		    }
		    atualizar();
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "create");
			// TODO: handle exception
		}
		
	}
	public void prepararConsultarBrinde(BrindeEntradaItens brindeDevolucaoIten) {
		setBrindeConsultar(brindeDevolucaoIten);
	}

	public BrindeEntradaItens getBrindeConsultar() {
		return (BrindeEntradaItens)getFlash().getValoresPorID("brindeEntradaForm").get("brindeConsultar");
		//return brindeConsultar;
	}
	public void setBrindeConsultar(BrindeEntradaItens brindeConsultar) {
		 getFlash().getValoresPorID("brindeEntradaForm").put("brindeConsultar",brindeConsultar);
		//this.brindeConsultar = brindeConsultar;
	}
	public void atualizarBrindeConsultadoNoGrid(Brinde brinde) {
		if(getBrindeConsultar()==null) {
			NovoItem();
			setBrindeConsultar(getItem().getBrindaEntradaItens().get(getItem().getBrindaEntradaItens().size()-1));
			PrimeFaces.current().executeScript("scrollToBottom();");
			PrimeFaces.current().focus(":brindeEntradaForm:datalistItens:"+ (getItem().getBrindaEntradaItens().size()-1) + ":idQuantidadeBrinde");
		}else {
			FacesContext.getCurrentInstance().validationFailed();
		}
	
		getBrindeConsultar().setBrindeBean(brinde);
		
	}
	public void validarNovoItem() {
		 
		try {
			BrindeEntrada item = getItem();
			List<BrindeEntradaItens> brindaEntradaItens = item.getBrindaEntradaItens();
			
			if(brindaEntradaItens!=null && !brindaEntradaItens.isEmpty())
			{
				BrindeEntradaItens BrindeDevolucaoItem = brindaEntradaItens.get(brindaEntradaItens.size()-1);
				if(BrindeDevolucaoItem!=null && 
						( BrindeDevolucaoItem.getBrindeBean()==null || BrindeDevolucaoItem.getQuantidade()==0d )) {
					JsfUtil.addErrorMessage("Já existe um brinde novo e ele esta vazio", "Operação não permitida");
					FacesContext.getCurrentInstance().validationFailed();
					return;
					
				}
			}
		
			for (int i = 0; i < brindaEntradaItens.size(); i++) {
			   if(brindaEntradaItens.get(i).getBrindeBean()==null) {
				   JsfUtil.addErrorMessage("O item ["+  i+1 +"] não possui um brinde informado", "Operação não permitida");
					FacesContext.getCurrentInstance().validationFailed();
					return;
			   }
			}
			 
			//item.setTotal(getTotalEntradaItens());
			
			setBrindeConsultar(null);
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "prepararNovoItem");
			// TODO: handle exception
		}
		
		 
		// TODO Auto-generated method stub
		
	}
	
 
	public void NovoItem() {
		 
		try {
			BrindeEntrada item = getItem();
			//List<BrindeDevolucaoIten> brindaEntradaItens = item.getBrindeDevolucaoItens();
				
			BrindeEntradaItens novoItem = new BrindeEntradaItens();
			novoItem.setId(-(item.getBrindaEntradaItens().size()+1));
			 
			item.getBrindaEntradaItens().add(novoItem);
			
			setItem(item);
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "prepararNovoItem");
			// TODO: handle exception
		}
		
		 
		// TODO Auto-generated method stub
		
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
	public BrindeEntrada getItem() {
		// TODO Auto-generated method stub
		
		BrindeEntrada ret = (BrindeEntrada)getFlash().getValoresPorID("brindeEntradaForm").get("item");
		
		if(ret==null) {
			ret = new BrindeEntrada();
			setItem(ret);
		}
		
		return ret;
	}

	@Override
	public void setItem(BrindeEntrada item) {
		getFlash().getValoresPorID("brindeEntradaForm").put("item",item);
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<BrindeEntrada> getItens() {
		ArrayList<BrindeEntrada> itens = (ArrayList<BrindeEntrada>) getFlash().getValoresPorID("brindeEntradaForm").get("itens");
		if(itens==null) {
			itens = (ArrayList<BrindeEntrada>) getBrindeEntradafacade().findAll();
			setItens(itens);
		}
		// TODO Auto-generated method stub
		return itens;
	}

	@Override
	public void setItens(List<BrindeEntrada> itens) {
		// TODO Auto-generated method stub
		getFlash().getValoresPorID("brindeEntradaForm").put("itens",itens);
		
	}

	 

	public BrindeEntradaFacade getBrindeEntradafacade() {
		return brindeEntradafacade;
	}

	public void setBrindeEntradafacade(BrindeEntradaFacade brindeEntradafacade) {
		this.brindeEntradafacade = brindeEntradafacade;
	}
	public FornecedorFacade getFornecedorFacade() {
		return fornecedorFacade;
	}

	public void setFornecedorFacade(FornecedorFacade fornecedorFacade) {
		this.fornecedorFacade = fornecedorFacade;
	}
	public BrindeEntradaItens getBrindaEntradaIten() {
		return brindaEntradaIten;
	}

	public void setBrindaEntradaIten(BrindeEntradaItens brindaEntradaIten) {
		this.brindaEntradaIten = brindaEntradaIten;
	}
	public String getPesquisarProduto() {
		return pesquisarProduto;
	}

	public void setPesquisarProduto(String pesquisarProduto) {
		this.pesquisarProduto = pesquisarProduto;
	}
	public BrindeFacade getBrindeFacade() {
		return brindeFacade;
	}
	public void setBrindeFacade(BrindeFacade brindeFacade) {
		this.brindeFacade = brindeFacade;
	}
	public List<BrindeEntradaItens> getBrindaEntradaItensGrid() {
		return brindaEntradaItensGrid;
	}
	public void setBrindaEntradaItensGrid(List<BrindeEntradaItens> brindaEntradaItensGrid) {
		this.brindaEntradaItensGrid = brindaEntradaItensGrid;
	}
	public boolean isEditar() {
		return editar;
	}
	public void setEditar(boolean editar) {
		this.editar = editar;
	}
	@FacesConverter(forClass = Fornecedor.class)
    public static class EquipeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        	Fornecedor find = null;
			try {				
				if (value == null || value.length() == 0) {
					return null;
				} else {
					BrindeEntradaController controller = (BrindeEntradaController) facesContext.getApplication().getELResolver()
							.getValue(facesContext.getELContext(), null, "brindeEntradaController");

					find = controller.getFornecedorFacade().find(value != null ? Integer.valueOf(value) : 0);

				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return find;

        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Fornecedor) {
            	Fornecedor o = (Fornecedor) object;
                return getStringKey(o.getCodigo());
            } else {
                //Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), InfraUsuario.class.getName()});
                return null;
            }
        }

    }

}
