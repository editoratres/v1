package editora3.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;

import javax.faces.context.FacesContext;
 
import javax.inject.Inject;
import javax.inject.Named;

 
import org.primefaces.PrimeFaces;

import org.primefaces.event.CellEditEvent;

import editora3.entidades.Brinde;
import editora3.entidades.BrindeSaida;
import editora3.entidades.BrindeSaidaIten;
import editora3.entidades.Vendedor;
 
import editora3.facade.BrindeFacade;
import editora3.facade.BrindeSaidaFacade;
import editora3.facade.CanalFacade;
import editora3.facade.EquipeFacade;
import editora3.facade.SubCanalFacade;
import editora3.facade.VendedorFacade;
import editora3.util.JsfUtil;
 
@Named("brindeSaidaController") 
@RequestScoped
public class BrindeSaidaController implements AbstractController<BrindeSaida> {

	@Inject
	FlashApp flashapp;

	@Inject
	private EquipeFacade equipeFacade; 

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
	public CanalFacade getCanalFacade() {
		return canalFacade;
	}
	public void setCanalFacade(CanalFacade canalFacade) {
		this.canalFacade = canalFacade;
	}
	public SubCanalFacade getSubcanalFacade() {
		return subcanalFacade;
	}
	public void setSubcanalFacade(SubCanalFacade subcanalFacade) {
		this.subcanalFacade = subcanalFacade;
	}

	@Inject
	private VendedorFacade vendedorFacade;	
	
	@Inject
	private CanalFacade canalFacade;	
	
	@Inject
	private SubCanalFacade subcanalFacade;	
	
	@Inject
	private BrindeSaidaFacade brindeSaidaFacade;
	@Inject
	private BrindeSaidaIten brindaSaidaIten;
	 

	@Inject
	private BrindeFacade brindeFacade; 
	
 
	
	private Boolean editar=false;
	
	private  List<Vendedor> vendedoresEquipe;
	public List<Vendedor> getVendedoresDaEquipe() {
		BrindeSaida item = getItem(); 
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
	
	
	@Override
	public void excluir(BrindeSaida item) {
		// TODO Auto-generated method stub
		getBrindeSaidaFacade().cancelarSaidaBrinde(item);
		setItens(null);
		
	}
	public Double getQuantidadeTotalSaidaItens() {
		Double ret = 0d;
		
		List<BrindeSaidaIten> brindaEntradaItens = getItem().getBrindeSaidaItens();
		for (Iterator iterator = brindaEntradaItens.iterator(); iterator.hasNext();) {
			BrindeSaidaIten BrindeDevolucaoItens = (BrindeSaidaIten) iterator.next();
			ret+=  BrindeDevolucaoItens.getQuantidade() ;
			
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
		     update(":brindeSaidaForm:datalistItens:idsubtotal");
		}
	 public void onCellEdit(CellEditEvent event) {
		 try {
 		 int rowIndex = 0;
 		 
 		int index = event.getRowIndex();
 		 
 		PrimeFaces.current().ajax().update(":brindeSaidaForm:datalistItens:idsubtotal");
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
	public void excluirItem(BrindeSaidaIten item) {
		// TODO Auto-generated method stub
		getItem().getBrindeSaidaItens().remove(item);
		if(getItem().getBrindeSaidaItens().isEmpty()) {
			getItem().getBrindeSaidaItens().add(new BrindeSaidaIten());
		}
	 
		
	}

	@Override
	public void prepararEditar(BrindeSaida item) {
		setEditar(true);
		setItem(item);
		 
	}
	
	 

	@Override
	public void atualizar() {
		// TODO Auto-generated method stub
		setItens(null);
		
	}

	@Override
	public void prepararNovo() {
		//setItem(new BrindeDevolucao()); 
		BrindeSaida item =new BrindeSaida();
		item.setData(new Date());
		//BrindeDevolucaoIten _brindaEntradaIten2 = new BrindeDevolucaoIten();
		//_brindaEntradaIten2.setId(-1);
    	//item.getBrindeDevolucaoItens().add(_brindaEntradaIten2);
		 
    
		 setItem(item);
		 setEditar(false);
		 
		
	} 
	public void validarNovoItem() {
		 
		try {
			BrindeSaida item = getItem();
			List<BrindeSaidaIten> brindaSaidaItens = item.getBrindeSaidaItens();
			
			if(brindaSaidaItens!=null && !brindaSaidaItens.isEmpty())
			{
				BrindeSaidaIten BrindeSaidaItem = brindaSaidaItens.get(brindaSaidaItens.size()-1);
				if(BrindeSaidaItem!=null && 
						( BrindeSaidaItem.getBrindeBean()==null || BrindeSaidaItem.getQuantidade()==0d )) {
					JsfUtil.addErrorMessage("Já existe um brinde novo e ele esta vazio", "Operação não permitida");
					FacesContext.getCurrentInstance().validationFailed();
					return;
					
				}
			}
		
			for (int i = 0; i < brindaSaidaItens.size(); i++) {
			   if(brindaSaidaItens.get(i).getBrindeBean()==null) {
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
			BrindeSaida item = getItem();
			//List<BrindeDevolucaoIten> brindaEntradaItens = item.getBrindeDevolucaoItens();
				
			BrindeSaidaIten novoItem = new BrindeSaidaIten();
			novoItem.setId(-(item.getBrindeSaidaItens().size()+1));
			 
			item.getBrindeSaidaItens().add(novoItem);
			
			setItem(item);
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "prepararNovoItem");
			// TODO: handle exception
		}
		
		 
		// TODO Auto-generated method stub
		
	}
	 
	public void prepararConsultarBrinde(BrindeSaidaIten brindeSaidaIten) {
		setBrindeConsultar(brindeSaidaIten);
	}

	public void atualizarBrindeConsultadoNoGrid(Brinde brinde) {
		if(getBrindeConsultar()==null) {
			NovoItem();
			setBrindeConsultar(getItem().getBrindeSaidaItens().get(getItem().getBrindeSaidaItens().size()-1));
			PrimeFaces.current().executeScript("scrollToBottom();");
			PrimeFaces.current().focus(":brindeSaidaForm:datalistItens:"+ (getItem().getBrindeSaidaItens().size()-1) + ":idQuantidadeBrinde");
		}else {
			FacesContext.getCurrentInstance().validationFailed();
		}
	
		getBrindeConsultar().setBrindeBean(brinde);
		
	}
	@Override
	public void fecharDialogo() {
		PrimeFaces.current().resetInputs("brindeSaidaForm");
		setItens(null);
		setItem(null);
		getFlash().limparPorId("brindeSaidaForm");
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		try {
			
			BrindeSaida item = getItem();
			
			if(item.getEquipeBean()==null) {
				JsfUtil.addErrorMessage("Informe a equipe", "Procedimento não realizado");
		    	FacesContext.getCurrentInstance().validationFailed();
		    	return;
			}
			
			/*if(item.getVendedorBean()==null) {
				JsfUtil.addErrorMessage("Informe o Vendedor", "Procedimento não realizado");
		    	FacesContext.getCurrentInstance().validationFailed();
		    	return;
			}*/
			if(getQuantidadeTotalSaidaItens()==0d) {
				JsfUtil.addErrorMessage("Saída sem brinde(s) não são permitidas", "Procedimento não realizado");
		    	FacesContext.getCurrentInstance().validationFailed();
		    	return;
			}
			 
		    //item.setDescricao(item.getDescricao().toUpperCase());
			/*if(item.getNotafiscal()!=null) {
				item.setNotafiscal(item.getNotafiscal().trim());
			
			    BrindeDevolucao localizarPorNome = getBrindeSaidaFacade().localizarPorNotaFiscal(item.getNotafiscal().trim());
			    if(localizarPorNome!=null && localizarPorNome.getCodigo()!=item.getCodigo()) {
			    	JsfUtil.addErrorMessage("A nota fiscal informado já foi cadastrada", "Procedimento não realizado");
			    	FacesContext.getCurrentInstance().validationFailed();
			    	atualizar();
			    	return;
			    }
			}*/
		    if(item.getBrindeSaidaItens()!=null && !item.getBrindeSaidaItens().isEmpty()) {
		    	for (int i = 0; i < item.getBrindeSaidaItens().size(); i++) {
		    		BrindeSaidaIten BrindeDevolucaoItem = item.getBrindeSaidaItens().get(i);
		    		if(BrindeDevolucaoItem.getBrindeBean()==null) {
		    			JsfUtil.addErrorMessage("Não foi especificao o brinde para o  item ["+ (i+1) + "]", "Procedimento não realizado");
		    			FacesContext.getCurrentInstance().validationFailed();
		    			return;
		    		}
		    		if(BrindeDevolucaoItem.getQuantidade()==0) {
		    			JsfUtil.addErrorMessage("Não foi especificao a quantidade do brinde para o  item ["+ (i+1) + "]", "Procedimento não realizado");
		    			FacesContext.getCurrentInstance().validationFailed();
		    			return;
		    		}
		    		if(BrindeDevolucaoItem.getId()<0) {
		    			BrindeDevolucaoItem.setId(null);
		    			BrindeDevolucaoItem.setBrindeSaida(item);
		    		}
					 
				}
		    }
		    
		   
		    
		    if(item.getCodigo()==null) {
		    	//item.setTotal(getTotalEntradaItens());
		    	getBrindeSaidaFacade().CriarSaida(item);
		    	JsfUtil.addSuccessMessage("Saida de brinde criada com sucesso", "Procedimento OK");
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
	public BrindeSaida getItem() {
		// TODO Auto-generated method stub
		
		BrindeSaida ret = (BrindeSaida)getFlash().getValoresPorID("brindeSaidaForm").get("item");
		
		if(ret==null) {
			ret = new BrindeSaida();
			setItem(ret);
		}
		
		return ret;
	}

	@Override
	public void setItem(BrindeSaida item) {
		getFlash().getValoresPorID("brindeSaidaForm").put("item",item);
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<BrindeSaida> getItens() {
		ArrayList<BrindeSaida> itens = (ArrayList<BrindeSaida>) getFlash().getValoresPorID("brindeSaidaForm").get("itens");
		if(itens==null) {
			itens = (ArrayList<BrindeSaida>) getBrindeSaidaFacade().findAll();
			setItens(itens);
		}
		// TODO Auto-generated method stub
		return itens;
	}

	@Override
	public void setItens(List<BrindeSaida> itens) {
		// TODO Auto-generated method stub
		getFlash().getValoresPorID("brindeSaidaForm").put("itens",itens);
		
	}

	 

	 
	 
	 

	 
	public BrindeFacade getBrindeFacade() {
		return brindeFacade;
	}
	public void setBrindeFacade(BrindeFacade brindeFacade) {
		this.brindeFacade = brindeFacade;
	}
	 
	public boolean isEditar() {
		return editar;
	}
	public void setEditar(boolean editar) {
		this.editar = editar;
	}
	 
/*	@FacesConverter(forClass = Fornecedor.class)
    public static class EquipeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        	Fornecedor find = null;
			try {				
				if (value == null || value.length() == 0) {
					return null;
				} else {
					BrindeDevolucaoController controller = (BrindeDevolucaoController) facesContext.getApplication().getELResolver()
							.getValue(facesContext.getELContext(), null, "brindeDevolucaoController");

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
*/
	public BrindeSaidaIten getBrindeConsultar() {
		return (BrindeSaidaIten)getFlash().getValoresPorID("brindeSaidaForm").get("brindeConsultar");
		//return brindeConsultar;
	}
	public void setBrindeConsultar(BrindeSaidaIten brindeConsultar) {
		 getFlash().getValoresPorID("brindeSaidaForm").put("brindeConsultar",brindeConsultar);
		//this.brindeConsultar = brindeConsultar;
	}
	public BrindeSaidaFacade getBrindeSaidaFacade() {
		return brindeSaidaFacade;
	}
	public void setBrindeSaidaFacade(BrindeSaidaFacade brindeSaidaFacade) {
		this.brindeSaidaFacade = brindeSaidaFacade;
	}
	public BrindeSaidaIten getBrindaSaidaIten() {
		return brindaSaidaIten;
	}
	public void setBrindaSaidaIten(BrindeSaidaIten brindaSaidaIten) {
		this.brindaSaidaIten = brindaSaidaIten;
	}
}
