package editora3.controller;

import java.util.ArrayList;import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
 
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import editora3.entidades.Equipe;
import editora3.entidades.Fornecedor;
import editora3.facade.FornecedorFacade;
import editora3.util.CepWebService;
import editora3.util.CepWebService.Estados;
import editora3.util.CepWebService.Municipio;
import editora3.util.CepWebService.cep;
import editora3.util.JsfUtil;
import editora3.util.ValidacoesDiversas;

@Named("fornecedorController")
@RequestScoped
public class FornecedorController implements AbstractController<Fornecedor>{

	
	@Inject
	private CepWebService cepWebService;
	
	private String mascaraCPF="999.999.999-99" ;
	@Inject
	private FornecedorFacade fornecedorFacade; 
	
	 

	private ArrayList<Municipio> municipio=null;
	private ArrayList<Estados> estados=null;
	public void pesquisarCep() {
		try {
			Fornecedor e = getItem();
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
	public void excluir(Fornecedor item) {
		// TODO Auto-generated method stub
		 try {
				getFornecedorFacade().remove(item);
				atualizar();
			 
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "excluir");
			// TODO: handle exception
		}
		
		
	}

	@Override
	public void prepararEditar(Fornecedor item) {
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
		setItem(new Fornecedor());
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
			
			Fornecedor equipe = getItem();
			
			if(equipe.getCnpjcpf()!=null && !equipe.getCnpjcpf().trim().isEmpty()) {
				Fornecedor localizarCPF = getFornecedorFacade().localizarCPF(equipe.getCnpjcpf());
				if(		localizarCPF!=null &&
						localizarCPF.getCodigo().intValue() != (equipe.getCodigo()==null ? 0 : equipe.getCodigo().intValue())) {
					
					    JsfUtil.addErrorMessage("O cnpj/cpf informado já foi utilizado por ["+ localizarCPF.getDescricao() +"]","Procedimento NÃO REALIZADO");
					    FacesContext.getCurrentInstance().validationFailed();
					    return;
				}
			}
			
			if(equipe.getCodigo()==null) {
				getFornecedorFacade().create(equipe);
				JsfUtil.addSuccessMessage("Fornecedor criado com sucesso", "Procedimento OK");
				
			}else {
				getFornecedorFacade().edit(equipe);	
				JsfUtil.addSuccessMessage("Fornecedor alterado com sucesso", "Procedimento OK");
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
	public Fornecedor getItem() {
		// TODO Auto-generated method stub
		
		return  (Fornecedor) getFlash().getValoresPorID("fornecedorForm").get("fornecedor");
	}

	@Override
	public void setItem(Fornecedor item) {
		 getFlash().getValoresPorID("fornecedorForm").put("fornecedor",item);
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Fornecedor> getItens() {
		// TODO Auto-generated method stub
		List<Fornecedor> ret = (List<Fornecedor>) getFlash().getValoresPorID("fornecedorForm").get("fornecedores");
		if(ret==null) {
		 ret = 	getFornecedorFacade().findAll();
		}
		return ret; 
	}

	@Override
	public void setItens(List<Fornecedor> itens) {
		// TODO Auto-generated method stub
		getFlash().getValoresPorID("fornecedorForm").put("fornecedores",itens);
		
	}

	public String getMascaraCPF() {
		return mascaraCPF;
	}

	public void setMascaraCPF(String mascaraCPF) {
		this.mascaraCPF = mascaraCPF;
	}
	
	public String getMascaraPessoa() {
		Fornecedor selected = getItem();
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

	        	Fornecedor selected = getItem();
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
	        	Fornecedor selected = getItem();
	        	
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
		Fornecedor item = getItem(); 
		if(item!=null && item.getEstado()!=null) {
			municipio = getCepWebService().getMunicipios(item.getEstado());
		}
		return municipio;
	}

	public void setMunicipios(ArrayList<Municipio> municipio) {
		this.municipio = municipio;
	}

	public FornecedorFacade getFornecedorFacade() {
		return fornecedorFacade;
	}

	public void setFornecedorFacade(FornecedorFacade fornecedorFacade) {
		this.fornecedorFacade = fornecedorFacade;
	}
}
