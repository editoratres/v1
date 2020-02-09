package editora3.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;
 

import editora3.entidades.Equipe;
import editora3.entidades.Vendedor;
import editora3.facade.EquipeFacade;
import editora3.facade.VendedorFacade;
import editora3.seguranca.LoginInfo;
import editora3.util.CepWebService;
import editora3.util.JsfUtil;
import editora3.util.CepWebService.Estados;
import editora3.util.CepWebService.Municipio;
import editora3.util.CepWebService.cep;

@Named("vendedorController")
@RequestScoped
public class VendedorController implements Serializable, AbstractController<Vendedor>{
	
	/**
	 * 
	 */
	
	
	@Inject
	private LoginInfo loginInfor;
	
	
	private static final long serialVersionUID = -3093663958472481537L;
	@Inject
	private EquipeFacade equipeFacade;
	
	@Inject
	private VendedorFacade vendedorFacade;
	
	private List<Equipe> equipesDisponiveis;

	@Override
	public FlashApp getFlash() {
		return flash;
	}
	@Override
	public void setFlash(FlashApp flash) {
		this.flash = flash;
	}
	 
	@Inject
	private FlashApp flash ;

	@Override
	public void excluir(Vendedor item) {
		 getVendedorFacade().remove(item);
		 atualizar();
		
	}
	@Override
	public void prepararEditar(Vendedor item) {
		setItem(item);
		
	}
	@PostConstruct
	public void iniciar() {
		setItens(null);
	}

	
	@Override
	public void atualizar() {
		setItens(null);
		
	}
	@Override
	public void prepararNovo() {
		Vendedor vendedor = new Vendedor();
		vendedor.setComissao(0d);
		//vendedor.setEquipeBean(new Equipe());
		setItem(vendedor);
		 
		
	}
	@Override
	public void create() {
		try {
			
			Vendedor item = getItem();
			if(item.getCpf()!=null && !item.getCpf().trim().isEmpty()) {
				Vendedor localizarCPF = getVendedorFacade().localizarCPF(item.getCpf());
				if(		localizarCPF!=null &&
						localizarCPF.getCodigo().intValue() != (item.getCodigo()==null ? 0 : item.getCodigo().intValue())) {
					
					    JsfUtil.addErrorMessage("O cpf informado já foi utilizado por ["+ localizarCPF.getNome() +"]","Procedimento NÃO REALIZADO");
					    FacesContext.getCurrentInstance().validationFailed();
					    return;
				}
			}
			
			
			if(item.getCodigo()==null) {
				
				getVendedorFacade().create(item);
				JsfUtil.addSuccessMessage("Vendedor criado com sucesso","Procedimento OK");
				
			}else {
				getVendedorFacade().edit(item);
				JsfUtil.addSuccessMessage("Vendedor atualizado com sucesso","Procedimento OK");
			}
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().validationFailed();
			JsfUtil.addErrorMessage(e,"create");
		}
	
		
		
	}
	
	@Override
	public void fecharDialogo() {
		PrimeFaces.current().resetInputs("vendedorForm");
		//RequestContext.getCurrentInstance().reset("vendedorForm");
		getFlash().limpar();
		// TODO Auto-generated method stub
		
	}
	@Override
	public Vendedor getItem() {
		// TODO Auto-generated method stub
		 	return  (Vendedor) getFlash().getValores().get("vendedor");
	}
	@Override
	public void setItem(Vendedor item) {
		getFlash().getValores().put("vendedor", item);
		
	}
	@Override
	public List<Vendedor> getItens() {
		List<Vendedor>  ret = (List<Vendedor>) getFlash().getValores().get("vendedores");
		if(ret==null) {
			ret = getVendedorFacade().findAllPorEquipe(getLoginInfor().getCodigoEquipeVinculada());
		}
		return ret;
		 
	 
	}
	@Override
	public void setItens(List<Vendedor> itens) {
		getFlash().getValores().put("vendedores", itens);
		
	}
	public VendedorFacade getVendedorFacade() {
		return vendedorFacade;
	}
	public void setVendedorFacade(VendedorFacade vendedorFacade) {
		this.vendedorFacade = vendedorFacade;
	}
	 
	
    public EquipeFacade getEquipeFacade() {
    	
		return equipeFacade;
	}
	public void setEquipeFacade(EquipeFacade equipeFacade) {
		this.equipeFacade = equipeFacade;
	}


	@FacesConverter(forClass = Equipe.class)
    public static class EquipeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        	Equipe find = null;
			try {				
				if (value == null || value.length() == 0) {
					return null;
				} else {
					VendedorController controller = (VendedorController) facesContext.getApplication().getELResolver()
							.getValue(facesContext.getELContext(), null, "vendedorController");

					find = controller.getEquipeFacade().find(value != null ? Integer.valueOf(value) : 0);

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
            if (object instanceof Equipe) {
            	Equipe o = (Equipe) object;
                return getStringKey(o.getCodigo());
            } else {
                //Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), InfraUsuario.class.getName()});
                return null;
            }
        }

    }
	@Inject
	private CepWebService cepWebService;
	private ArrayList<Municipio> municipio=null;
	private ArrayList<Estados> estados=null;
	public void pesquisarCep() {
		try {
			Vendedor v = getItem();
			if(v.getCep()!=null && !v.getCep().isEmpty()) {
				cep cepconsultar = getCepWebService().PesquisarCEP(v.getCep());
				if(cepconsultar!=null) {
					StringBuilder endereco = new StringBuilder();
					endereco.append(cepconsultar.getTipoLogradouro()!=null ? cepconsultar.getTipoLogradouro(): "");
					if(endereco.length()>0) {
						endereco.append(" ");
					}
					endereco.append(cepconsultar.getLogradouro()!=null ? cepconsultar.getLogradouro(): "");

					v.setEndereco(endereco.toString()) ;
					
					 
					v.setBairro(cepconsultar.getBairro());
					v.setCidade(cepconsultar.getIbge());
					v.setEstado(cepconsultar.getEstado());
					v.setComplemento(cepconsultar.getComplemento()	);
					
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

	public void setEstados(List<String> estados) {
		estados = estados;
	}

	public ArrayList<Municipio> getMunicipios() {
		Vendedor item = getItem(); 
		if(item!=null && item.getEstado()!=null) {
			municipio = getCepWebService().getMunicipios(item.getEstado());
		}
		return municipio;
	}

	public void setMunicipios(ArrayList<Municipio> municipio) {
		this.municipio = municipio;
	}
	public LoginInfo getLoginInfor() {
		return loginInfor;
	}
	public void setLoginInfor(LoginInfo loginInfor) {
		this.loginInfor = loginInfor;
	}
	public List<Equipe> getEquipesDisponiveis() {
		if(equipesDisponiveis==null) {
			equipesDisponiveis = getEquipeFacade().findAllEquipes(getLoginInfor().getCodigoEquipeVinculada());
		}
		return equipesDisponiveis;
	}
	public void setEquipesDisponiveis(List<Equipe> equipesDisponiveis) {
		this.equipesDisponiveis = equipesDisponiveis;
	}


}
