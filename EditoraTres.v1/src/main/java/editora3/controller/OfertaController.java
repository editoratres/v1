package editora3.controller;

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

import org.apache.commons.lang.SerializationUtils;
import org.primefaces.PrimeFaces;

import com.sun.mail.imap.protocol.Item;

import editora3.entidades.Brinde;
import editora3.entidades.Equipe;
import editora3.entidades.Oferta;
import editora3.entidades.OfertaIten;
import editora3.entidades.Produto;
import editora3.facade.BrindeFacade;
import editora3.facade.OfertaFacade;
import editora3.facade.ProdutoFacade;
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
	public BrindeFacade getBrindeFacade() {
		return brindeFacade;
	}
	private OfertaIten ofertaItenGrid;
	public void setBrindeFacade(BrindeFacade brindeFacade) {
		this.brindeFacade = brindeFacade;
	}

	@PostConstruct
	public void iniciar() {
		setItens(null);
	}
	public ProdutoFacade getProdutoFacade() {
		return produtoFacade;
	}

	public void setProdutoFacade(ProdutoFacade produtoFacade) {
		this.produtoFacade = produtoFacade;
	}

	@Inject
	ProdutoFacade produtoFacade;
	
	 
	public List<Brinde> getBrindes() {
		return getBrindeFacade().findAll();
	}

	 
	public List<Produto> getProdutos() {
		 
		return getProdutoFacade().findAll();
	}

	
	@Override
	public void excluir(Oferta item) {
		// TODO Auto-generated method stub
		getOfertafacade().remove(item);
		setItens(null);
		
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
		setItem(new Oferta()); 
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void fecharDialogo() {
		PrimeFaces.current().resetInputs("ofertaForm");
		getFlash().limparPorId("ofertaForm");
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
		   
		    
		    if(item.getCodigo()==null) {
		    	getOfertafacade().create(item);
		    	JsfUtil.addSuccessMessage("Oferta criado com sucesso", "Procedimento OK");
		    }else {
		    	getOfertafacade().edit(item);
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
	public List<Oferta> getItens() {
		ArrayList<Oferta> itens = (ArrayList<Oferta>) getFlash().getValoresPorID("ofertaForm").get("itens");
		if(itens==null) {
			itens = (ArrayList<Oferta>) getOfertafacade().findAll();
			setItens(itens);
		}
		// TODO Auto-generated method stub
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
			
			setOfertaItensSelecionado(new OfertaIten());
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
			for (Iterator iterator = ofertaItens.iterator(); iterator.hasNext();) {
				OfertaIten ofertaIten = (OfertaIten) iterator.next();
				if(ofertaItensSelecionado.getCodigo()!=ofertaIten.getCodigo() &&  ofertaIten.getEdicao().equalsIgnoreCase(ofertaItensSelecionado.getEdicao())) {
					Total++;
				}
			}
			if( Total>0) {
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


}
