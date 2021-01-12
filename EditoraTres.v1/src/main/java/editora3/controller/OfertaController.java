package editora3.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.SerializationUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.sun.mail.imap.protocol.Item;

import editora3.entidades.Brinde;
import editora3.entidades.Contrato;
import editora3.entidades.Equipe;
import editora3.entidades.InfraTipoPerfilUsuario;
import editora3.entidades.InfraUsuarioPerfil;
import editora3.entidades.Oferta;
import editora3.entidades.OfertaBrindes;
import editora3.entidades.OfertaEquipe;
import editora3.entidades.OfertaIten;
import editora3.entidades.Produto;
import editora3.facade.AuditoriaFacade;
import editora3.facade.BrindeFacade;
import editora3.facade.EquipeFacade;
import editora3.facade.FiltrosLazyDataModel;
import editora3.facade.LazyObjetos;
import editora3.facade.OfertaFacade;
import editora3.facade.ProdutoFacade;
import editora3.seguranca.AutorizacaoRecurso;
import editora3.util.JsfUtil;
@Named("ofertaController") 
@RequestScoped
public class OfertaController implements AbstractController<Oferta> {

	@Inject
	FlashApp flashapp;
	
	@Inject
	OfertaFacade ofertafacade;
	@Inject
	BrindeFacade brindeFacade;
	

	@Inject
	private AuditoriaFacade auditoriaFacade;  
	
	@Inject
	private AutorizacaoRecurso autorizacaoRecurso; 
	
	public BrindeFacade getBrindeFacade() {
		return brindeFacade;
	}
	private OfertaIten ofertaItenGrid;
	public void setBrindeFacade(BrindeFacade brindeFacade) {
		this.brindeFacade = brindeFacade;
	}

	@PostConstruct
	public void iniciar() {
		//setItensLazy(null);
	}
	public ProdutoFacade getProdutoFacade() {
		return produtoFacade;
	}

	public void setProdutoFacade(ProdutoFacade produtoFacade) {
		this.produtoFacade = produtoFacade;
	}

	@Inject
	ProdutoFacade produtoFacade;
	
	@Inject
	private
	EquipeFacade equipeFacade;
	
	 
	public List<Brinde> getBrindes() {
		return getBrindeFacade().findAll();
	}

	 
	public List<Produto> getProdutos() {
		
		List<Produto> findAll = getProdutoFacade().findAll();
		findAll.sort(new Comparator<Produto>() {

			@Override
			public int compare(Produto o1, Produto o2) {
				// TODO Auto-generated method stub
				return o1.getDescricao().compareTo(o2.getDescricao());
			}
		});
		 
		return findAll;
	}

	
	
	public List<Equipe> getEquipes() {
		return getEquipeFacade().findAll();
	}

	 
	@Override
	public void excluir(Oferta item) {
		// TODO Auto-generated method stub
		Integer totalContratosComAOferta = getOfertafacade().totalContratosComAOferta(item.getCodigo());
		if(totalContratosComAOferta>0) {
			JsfUtil.addErrorMessage("A oferta está vinculada a ["+ totalContratosComAOferta +"] contrato(s)", "Procedimento não permitido");
			FacesContext.getCurrentInstance().validationFailed();
			return;
		}
		if(autorizacaoRecurso.VerificarAcesso("Oferta", "excluir",true,item.getCodigo().toString() + " - "+ item.getProdutoBean().getDescricao(),true)) {
			getOfertafacade().remove(item);
			setItensLazy(null);
		}
		
	}
	public void excluirItemBrinde(OfertaBrindes OfertaBrindes ) {
		// TODO Auto-generated method stubtr
		try {
			 
			Oferta item = getItem();
			item.getOfertaBrindes().remove(OfertaBrindes);
				
			 
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "excluirItemBrinde");
			// TODO: handle exception
		}
		
		
	}
	public void atualizarTipoAssinatura(Produto p) {
		try {
			Oferta item = getItem();
			item.setTipoassinatura(item.getProdutoBean().getTipoassinatura());
			
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "atualizarTipoAssinatura");
			// TODO: handle exception
		}
	}
	public void incluirBrindeOferta(Brinde b) {
		try {
			
			
			Oferta item = getItem();
			OfertaBrindes ofertaBrindes=new OfertaBrindes();
			ofertaBrindes.setBrindeBean(b);
			ofertaBrindes.setOferta(item);
			ofertaBrindes.setQuantidade(1);
			item.getOfertaBrindes().add(ofertaBrindes);
			
			
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "incluirBrindeOferta");
			// TODO: handle exception
		}
	}
	
	List<Brinde> brindesDisponiveis=null;
	public List<Brinde> getBrindesDisponiveis() {
		if(brindesDisponiveis==null) {
			brindesDisponiveis=getBrindeFacade().findAll("todos");
		}
		return brindesDisponiveis;
	}
	 
	public void excluirItemOferta(OfertaIten ofertaIten) {
		// TODO Auto-generated method stubtr
		try {
			ArrayList<OfertaIten> ofertaItens = getOfertaItens();
			ofertaItens.remove(ofertaIten);
			setOfertaItens(ofertaItens);
				
			 
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "excluirItemOferta");
			// TODO: handle exception
		}
		
		
	}

	@Override
	public void prepararEditar(Oferta item) {
		if(autorizacaoRecurso.VerificarAcesso("Oferta", "editar",true,null,false)) {
			setItem(getOfertafacade().findEager(item.getCodigo()));
			IniciarLista();
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public void atualizar() {
		// TODO Auto-generated method stub
		setItensLazy(null);
		
	}

	@Override
	public void prepararNovo() {
		if(autorizacaoRecurso.VerificarAcesso("Oferta", "criar",true,null,false)) {
		Oferta oferta = new Oferta();
		oferta.setAtiva(false);
		oferta.setDesconto(0d);
		oferta.setVigencia(0);
		setItem(oferta); 
		IniciarLista();
		}
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void fecharDialogo() {
		PrimeFaces.current().resetInputs("ofertaForm");
		//getFlash().limparPorId("ofertaForm");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		try {
			
			Oferta item = getItem();
			if(item.getProdutoBean()==null) {
				JsfUtil.addErrorMessage("O Produto não foi informado", "Procedimento não realizado");
				FacesContext.getCurrentInstance().validationFailed();
				return;
			}
			
			 List<Equipe> target = getDualListModelofertaEquipe().getTarget();

			
			if(target.isEmpty()) {
				JsfUtil.addErrorMessage("Nenhuma equipe foi informada", "Procedimento não realizado");
				FacesContext.getCurrentInstance().validationFailed();
				return;
			}
			
		  /*  item.setDescricao(item.getDescricao().toUpperCase());
		    
		    Oferta localizarPorNome = getProdutofacade().localizarPorNome(item.getDescricao());
		    if(localizarPorNome!=null && localizarPorNome.getCodigo()!=item.getCodigo()) {
		    	JsfUtil.addErrorMessage("O Oferta informado já foi cadastrado", "Procedimento não realizado");
		    	FacesContext.getCurrentInstance().validationFailed();
		    	atualizar();
		    	return;
		    }*/
			ArrayList<OfertaIten> ofertaItens = getOfertaItens();
			for (OfertaIten ofertaIten : ofertaItens) {
				if(ofertaIten.getCodigo()<0) {
					ofertaIten.setCodigo(null);
				}
			} 
		   item.setOfertaItens(ofertaItens);
		   
		 //  List<Equipe> target = getDualListModelofertaEquipe().getTarget();

			List<OfertaEquipe> listOfertaEquipe =new ArrayList<>();
			if (item.getOfertaEquipe() == null) {
				item.setOfertaEquipe(new ArrayList<>());
			}
			int i = 0;
			item.getOfertaEquipe().clear();
			for (Equipe next : target) {
				//if (!item.getOfertaEquipe().contains(next)) {
					OfertaEquipe ofertaEquipe = new OfertaEquipe();
					ofertaEquipe.setOferta(item);
					ofertaEquipe.setEquipeBean(next);
					// perfilusuario.setIdperfil(i);
					item.getOfertaEquipe().add(ofertaEquipe);
				//}
				//i++;
			}
			 
		   
		    
		    if(item.getCodigo()==null) {
		    	getOfertafacade().create(item);
		    	auditoriaFacade.auditar("Oferta", "criar", item.getCodigo().toString() + " - " + item.getProdutoBean().getDescricao());
		    	JsfUtil.addSuccessMessage("Oferta criado com sucesso", "Procedimento OK");
		    }else {
		    	getOfertafacade().edit(item);
		    	auditoriaFacade.auditar("Oferta", "editar", item.getCodigo().toString() + " - " + item.getProdutoBean().getDescricao());
		    	JsfUtil.addSuccessMessage("Oferta alterado com sucesso", "Procedimento OK");
		    }
		    //setOfertaItens(null);
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
	public Oferta getItem() {
		// TODO Auto-generated method stub
		Oferta oferta = (Oferta) getFlash().getValoresPorID("ofertaForm").get("item");
		if(oferta==null) {
		   oferta = new Oferta();
		   setItem(oferta);
		}
		return oferta;
	}

	@Override
	public void setItem(Oferta item) {
		getFlash().getValoresPorID("ofertaForm").put("item",item);
		// TODO Auto-generated method stub
		
	}
	@Override
	public ArrayList<Oferta> getItens() {
			return null;
	}
	//@Override
	public LazyDataModel<Oferta> getItensLazy() {
		LazyDataModel<Oferta> itens = (LazyDataModel<Oferta>) getFlash().getValoresPorID("ofertaForm").get("itens");

		if (itens == null) {
			itens = new LazyDataModel<Oferta>() {
				private static final long serialVersionUID = 1L;
				@Override
				public List<Oferta> load(int first, int pageSize, String sortField, SortOrder sortOrder,
						Map<String, Object> filters) {

					FiltrosLazyDataModel filtrosLazyDataModel = new FiltrosLazyDataModel(first, pageSize, sortField,
							sortOrder, filters);
						
					filtrosLazyDataModel.getMapeamentoCampoViewModel().put("produtoBean.descricao", "p.descricao");
					
					LazyObjetos<Oferta> findAllLazy = getOfertafacade().findAllLazy("todos",null, filtrosLazyDataModel);

					setRowCount(findAllLazy.getTotalObjetos());

					return findAllLazy.getLista();
				}
			};
			setItensLazy(itens);
		}
		return itens;
	}
 
	public void prepareCreateItemOferta() {
		try {
			Oferta item = getItem();
			if(item.getProdutoBean()==null) {
				JsfUtil.addErrorMessage("Para acrescentar edições informe primeiro o Produto", "Procedimento não realizado");
				FacesContext.getCurrentInstance().validationFailed();
				return;
			}
			OfertaIten ofertaIten = new OfertaIten();
			ofertaIten.setAtiva(false);
			setOfertaItensSelecionado(ofertaIten);
			getOfertaItensSelecionado().setOferta(item);
			
			
			
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "prepareCreateItemOferta");
			// TODO: handle exception
		}
	}
	public void prepareEditarItemOferta(OfertaIten item) {
		try {
			
			 
			setOfertaItensSelecionado((OfertaIten) SerializationUtils.clone(item));
			
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "prepareEditarItemOferta");
			// TODO: handle exception
		}
	}
	public void createItemOferta() {
		try {
			
			
			OfertaIten ofertaItensSelecionado = getOfertaItensSelecionado();
			Oferta item = getItem();
			List<OfertaIten> ofertaItens = item.getOfertaItens();
			int Total=0;
			boolean repetido=false;
			for (Iterator iterator = ofertaItens.iterator(); iterator.hasNext();) {
				OfertaIten ofertaIten = (OfertaIten) iterator.next();
				if((ofertaItensSelecionado.getCodigo()==null && ofertaItensSelecionado.getCodigo()!=ofertaIten.getCodigo()) &&   
						ofertaIten.getEdicao().equalsIgnoreCase(ofertaItensSelecionado.getEdicao()) ) {
					repetido=true;
					break;
				}
			}
			if(repetido) {
				PrimeFaces.current().resetInputs(":ofertaFormItens");
				JsfUtil.addErrorMessage("A Edição ja foi incluida nessa oferta", "Procedimento não realizado");
				setOfertaItensSelecionado(null);
				setOfertaItens(item.getOfertaItens());
				FacesContext.getCurrentInstance().validationFailed();
				return;
			}
			
			if(ofertaItensSelecionado.getCodigo()==null) {
				ofertaItensSelecionado.setCodigo(-(item.getOfertaItens().size()+1));
			}
			if(!item.getOfertaItens().contains(ofertaItensSelecionado)) {
				ofertaItensSelecionado.setOferta(getItem());
				item.getOfertaItens().add(ofertaItensSelecionado);
			}else {
				ofertaItensSelecionado.setOferta(getItem());
				int indexOf = item.getOfertaItens().indexOf(ofertaItensSelecionado);
				if(indexOf>=0) {
					item.getOfertaItens().set(indexOf, ofertaItensSelecionado);
				}
			}
			setOfertaItensSelecionado(null);
			
			
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "createItemOferta");
			// TODO: handle exception
		}
	}
	
	//private OfertaIten ofertaItensSelecionado;
	 
	public ArrayList<OfertaIten> getOfertaItens() {
		 
			return  new ArrayList<>(getItem().getOfertaItens());
		
	}

	public void setOfertaItens(List<OfertaIten>  ofertaItens) {
		 
			getItem().setOfertaItens(ofertaItens);
			
		 
		 
	}
	
	@Override
	public void setItens(List<Oferta> itens) {
		// TODO Auto-generated method stub
		getFlash().getValoresPorID("ofertaForm").put("itens",itens);
		
	}
	
	//@Override
	public void setItensLazy(LazyDataModel<Oferta> itens) {
		// TODO Auto-generated method stub
		getFlash().getValoresPorID("ofertaForm").put("itens",itens);
		
	}

	public OfertaFacade getOfertafacade() {
		return this.ofertafacade;
	}

	public void setOfertafacade(OfertaFacade ofertafacade) {
		this.ofertafacade = ofertafacade;
	}
	public OfertaIten getOfertaItensSelecionado() {
		 OfertaIten ret = (OfertaIten) getFlash().getValoresPorID("ofertaForm").get("OfertaIten");
		 if(ret==null) {
			 ret = new OfertaIten();
			 setOfertaItensSelecionado(ret);
		 }
		return ret;
	}

	public void setOfertaItensSelecionado(OfertaIten ofertaItensSelecionado) {
		getFlash().getValoresPorID("ofertaForm").put("OfertaIten", ofertaItensSelecionado);
	}
	public OfertaIten getOfertaItenGrid() {
		return ofertaItenGrid;
	}

	public void setOfertaItenGrid(OfertaIten ofertaItenGrid) {
		this.ofertaItenGrid = ofertaItenGrid;
	}
	public EquipeFacade getEquipeFacade() {
		return equipeFacade;
	}

	public void setEquipeFacade(EquipeFacade equipeFacade) {
		this.equipeFacade = equipeFacade;
	}
	
	 //private DualListModel<Equipe> dualListModelPerfilUsuario=new DualListModel<>();
	 public DualListModel<Equipe> getDualListModelofertaEquipe() {
		 DualListModel<Equipe> ret = null ;
		 
		 if(getFlash().getValores().get("dualListModelOfertaEquipe")==null) {
			ret = new DualListModel<>();
			
			setDualListModelofertaEquipe(ret);
		 }else {
			 ret=  (DualListModel<Equipe>) getFlash().getValores().get("dualListModelOfertaEquipe");
		 }
	        return ret; //
	    }

	    /**
	     * @param dualListModelPerfilUsuario the dualListModelPerfilUsuario to set
	     */
	    public void setDualListModelofertaEquipe(DualListModel<Equipe> dualListModelPerfilUsuario) {
	       
	        getFlash().getValores().put("dualListModelOfertaEquipe", dualListModelPerfilUsuario);
	    }

	    private void IniciarLista(){
	        try {
	            Oferta item = getItem();
	            List<Equipe> TodasAsEquipes = getEquipeFacade().findAll();
	            if (!item.getOfertaEquipe().isEmpty()) {
	                List<Equipe> equipesDaOferta = new ArrayList<>();
	                for (Iterator<OfertaEquipe> iterator = item.getOfertaEquipe().iterator(); iterator.hasNext();) {
	                	OfertaEquipe next = iterator.next();
	                	equipesDaOferta.add(next.getEquipeBean());
	                     
	                }
	                TodasAsEquipes.removeAll(equipesDaOferta);
	                setDualListModelofertaEquipe(new DualListModel<>(
	                		TodasAsEquipes,
	                		equipesDaOferta));
	            }else{             
	            	setDualListModelofertaEquipe( new DualListModel<>(
	            			TodasAsEquipes,
	                    new ArrayList<>() ));
	            }
	         

	        } catch (Exception e) {
	            JsfUtil.addErrorMessage("(IniciarLista) Falha na requisição\n\n" + e.getMessage());
	        }
	    }

	
	@FacesConverter(forClass = Produto.class)
    public static class ProdutoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        	Produto find = null;
			try {				
				if (value == null || value.length() == 0) {
					return null;
				} else {
					OfertaController controller = (OfertaController ) facesContext.getApplication().getELResolver()
							.getValue(facesContext.getELContext(), null, "ofertaController");

					find = controller.getProdutoFacade().find(value != null ? Integer.valueOf(value) : 0);

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
            if (object instanceof Produto) {
            	Produto o = (Produto) object;
                return getStringKey(o.getCodigo());
            } else {
                //Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), InfraUsuario.class.getName()});
                return null;
            }
        }

    }

	
	@FacesConverter(forClass = Brinde.class)
    public static class BrindeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        	Brinde find = null;
			try {				
				if (value == null || value.length() == 0) {
					return null;
				} else {
					OfertaController controller = (OfertaController ) facesContext.getApplication().getELResolver()
							.getValue(facesContext.getELContext(), null, "ofertaController");

					find = controller.getBrindeFacade().find(value != null ? Integer.valueOf(value) : 0);

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
            if (object instanceof Brinde) {
            	Brinde o = (Brinde) object;
                return getStringKey(o.getCodigo());
            } else {
                //Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), InfraUsuario.class.getName()});
                return null;
            }
        }

    }

	  @FacesConverter("editora3.controller.EquipeDualListConverter")
	    public static class PerfilUsuarioConverter implements Converter {
	        @Override
	        public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
	               Object ret = null;
	               if (arg1 instanceof PickList) {
	                   Object dualList = ((PickList) arg1).getValue();
	                   DualListModel dl = (DualListModel) dualList;
	                   for (Object o : dl.getSource()) {
	                       String id = "" + ((Equipe) o).getCodigo();
	                       if (arg2.equals(id)) {
	                           ret = o;
	                           break;
	                       }
	                   }
	                   if (ret == null) {
	                       for (Object o : dl.getTarget()) {
	                           String id = "" + ((Equipe) o).getCodigo();
	                           if (arg2.equals(id)) {
	                               ret = o;
	                               break;
	                           }
	                       }
	                   }
	               }
	               return ret;
	           }
	         @Override
	        public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
	             String str = "";
	             if (arg2 instanceof Equipe) {
	                 str = "" + ((Equipe) arg2).getCodigo();
	             }
	             return str;
	        }

	    }

}
