package editora3.controller;

import java.util.ArrayList;import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import editora3.entidades.Equipe;
import editora3.entidades.Vendedor;
import editora3.facade.EquipeFacade;
import editora3.util.CepWebService;
import editora3.util.CepWebService.Estados;
import editora3.util.CepWebService.Municipio;
import editora3.util.CepWebService.cep;
import editora3.util.JsfUtil;
import editora3.util.ValidacoesDiversas;

@Named("equipeController")
@RequestScoped
public class EquipeController implements AbstractController<Equipe>{

	
	@Inject
	private CepWebService cepWebService;
	
	private String mascaraCPF="999.999.999-99" ;
	@Inject
	private EquipeFacade EquipeFacade; 
	
	public EquipeFacade getEquipeFacade() {
		return EquipeFacade;
	}

	@PostConstruct
	public void iniciar() {
		setItens(null);
	}
	public void setEquipeFacade(EquipeFacade equipeFacade) {
		EquipeFacade = equipeFacade;
	}

	private ArrayList<Municipio> municipio=null;
	private ArrayList<Estados> estados=null;
	public void pesquisarCep() {
		try {
			Equipe e = getItem();
			if(e.getCep()!=null && !e.getCep().isEmpty()) {
				cep cepconsultar = getCepWebService().PesquisarCEP(e.getCep());
				if(cepconsultar!=null) {
					StringBuilder endereco = new StringBuilder();
					endereco.append(cepconsultar.getTipoLogradouro()!=null ? cepconsultar.getTipoLogradouro(): "");
					if(endereco.length()>0) {
						endereco.append(" ");
					}
					endereco.append(cepconsultar.getLogradouro()!=null ? cepconsultar.getLogradouro(): "");

					e.setEndereco(endereco.toString()) ;
					
					 
					e.setBairro(cepconsultar.getBairro());
					e.setCidade(cepconsultar.getIbge());
					e.setEstado(cepconsultar.getEstado());
					e.setComplemento(cepconsultar.getComplemento()	);
					
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
	
	@Inject
	private FlashApp flash ;
	
	@Override
	public void excluir(Equipe item) {
		// TODO Auto-generated method stub
		try {
			item = getEquipeFacade().FindEager(item.getCodigo());
			if(item.getVendedors()!=null && !item.getVendedors().isEmpty()) {
				JsfUtil.addErrorMessage("Somente equipes sem vendedores podem ser excluidas", "Exclusão não permitida");
				FacesContext.getCurrentInstance().validationFailed();
			}else {
				getEquipeFacade().remove(item);
				atualizar();
			}
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "excluir");
			// TODO: handle exception
		}
		
		
	}

	@Override
	public void prepararEditar(Equipe item) {
		// TODO Auto-generated method stub
		setItem(item);
		
	}

	@Override
	public void atualizar() {
		// TODO Auto-generated method stub
		setItens(null);
		
	}

	@Override
	public void prepararNovo() {
		// TODO Auto-generated method stub
		setItem(new Equipe());
		getItem().setTipopessoa("fisica");
		
	}

	@Override
	public void fecharDialogo() {
		// TODO Auto-generated method stub
		//getFlash().limparPorId("equipeForm");
		
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		try {
			
			Equipe equipe = getItem();
			
			if(equipe.getCnpjcpf()!=null && !equipe.getCnpjcpf().trim().isEmpty()) {
				Equipe localizarCPF = getEquipeFacade().localizarCPF(equipe.getCnpjcpf());
				if(		localizarCPF!=null &&
						localizarCPF.getCodigo().intValue() != (equipe.getCodigo()==null ? 0 : equipe.getCodigo().intValue())) {
					
					    JsfUtil.addErrorMessage("O cnpj/cpf informado já foi utilizado por ["+ localizarCPF.getDescricao() +"]","Procedimento NÃO REALIZADO");
					    FacesContext.getCurrentInstance().validationFailed();
					    return;
				}
			}
			
			if(equipe.getCodigo()==null) {
				getEquipeFacade().create(equipe);
				JsfUtil.addSuccessMessage("Equipe criada com sucesso", "Procedimento OK");
				
			}else {
				getEquipeFacade().edit(equipe);	
				JsfUtil.addSuccessMessage("Equipe alterada com sucesso", "Procedimento OK");
			}
			
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "create");
			FacesContext.getCurrentInstance().validationFailed();
			// TODO: handle exception
		}
		
		
	}

	@Override
	public FlashApp getFlash() {
		// TODO Auto-generated method stub
		return this.flash;
	}

	@Override
	public void setFlash(FlashApp flash) {
		// TODO Auto-generated method stub
		this.flash=flash;
		
	}

	@Override
	public Equipe getItem() {
		// TODO Auto-generated method stub
		
		return  (Equipe) getFlash().getValoresPorID("equipeForm").get("equipe");
	}

	@Override
	public void setItem(Equipe item) {
		 getFlash().getValoresPorID("equipeForm").put("equipe",item);
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Equipe> getItens() {
		// TODO Auto-generated method stub
		List<Equipe> ret = (List<Equipe>) getFlash().getValoresPorID("equipeForm").get("equipes");
		if(ret==null) {
		 ret = 	getEquipeFacade().findAll();
		}
		return ret; 
	}

	@Override
	public void setItens(List<Equipe> itens) {
		// TODO Auto-generated method stub
		getFlash().getValoresPorID("equipeForm").put("equipes",itens);
		
	}

	public String getMascaraCPF() {
		return mascaraCPF;
	}

	public void setMascaraCPF(String mascaraCPF) {
		this.mascaraCPF = mascaraCPF;
	}
	
	public String getMascaraPessoa() {
		Equipe selected = getItem();
        if (selected != null) {
            if (selected.getTipopessoa() != null) {
                if (selected.getTipopessoa().equalsIgnoreCase("fisica")) {
                    return "999.999.999-99";
                } else {
                    return "99.999.999/9999-99";
                }
            } else {
                return "";
            }
        } else {
            return "";
        }
    }
	
	 public void ValidarCNPJ(AjaxBehaviorEvent event) {
	        try {

	        	Equipe selected = getItem();
	            String cnpj = selected.getCnpjcpf();
	            if (cnpj != null) {
	                if (!Validacoes()) {
	                    selected.setCnpjcpf(null);
	                    
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
	        	Equipe selected = getItem();
	        	
	            String cnpj = selected.getCnpjcpf();
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

	public void setEstados(List<String> estados) {
		estados = estados;
	}

	public ArrayList<Municipio> getMunicipios() {
		Equipe item = getItem(); 
		if(item!=null && item.getEstado()!=null) {
			municipio = getCepWebService().getMunicipios(item.getEstado());
		}
		return municipio;
	}

	public void setMunicipios(ArrayList<Municipio> municipio) {
		this.municipio = municipio;
	}
}
