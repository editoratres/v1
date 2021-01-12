package editora3.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import editora3.entidades.Equipe;
import editora3.entidades.PontoDeVenda;
import editora3.entidades.Vendedor;
import editora3.facade.AuditoriaFacade;
import editora3.facade.EquipeFacade;
import editora3.facade.PontoDeVendaFacade;
import editora3.seguranca.AutorizacaoRecurso;
import editora3.seguranca.LoginInfo;
import editora3.util.CepWebService;
import editora3.util.JsfUtil;
import editora3.util.CepWebService.Estados;
import editora3.util.CepWebService.Municipio;
import editora3.util.CepWebService.cep;
@Named("canalController") 
@RequestScoped
public class CanalController implements AbstractController<PontoDeVenda> {

	@Inject
	FlashApp flashapp;
	@Inject
	private EquipeFacade equipeFacade;
	@Inject
	private LoginInfo loginInfor;
	
	@Inject
	private
	PontoDeVendaFacade canalfacade;
	
	@Inject
	private AuditoriaFacade auditoriaFacade;  
	
	@Inject
	private AutorizacaoRecurso autorizacaoRecurso; 
	
	@Override
	public void excluir(PontoDeVenda item) { 
		// TODO Auto-generated method stub
		int total = canalfacade.totalContratosDoPontoDeVenda(item.getCodigo());
		if(total!=0) {
			JsfUtil.addErrorMessage("Existem ["+ total +"] contrato(s) vinculados ao ponto de venda", "Procedimento não permitido");
			FacesContext.getCurrentInstance().validationFailed();
			return;
		}
		
		total = canalfacade.totalSaidasBrindesParaPontoDeVenda(item.getCodigo());
		if(total!=0) {
			JsfUtil.addErrorMessage("Existem ["+ total +"] saidas(s) de brindes vinculados ao ponto de venda", "Procedimento não permitido");
			FacesContext.getCurrentInstance().validationFailed();
			return;
		}
		if(autorizacaoRecurso.VerificarAcesso("Pdv", "excluir",true,item.getCodigo().toString() +" - " + item.getDescricao(),true)) {
			
			getCanalfacade().remove(item);
			setItens(null);
		}
	}	
	
	@PostConstruct
	public void iniciar() {
		setItens(null);
	}

	@Override
	public void prepararEditar(PontoDeVenda item) {
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
		setItem(new PontoDeVenda()); 
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fecharDialogo() {
		PrimeFaces.current().resetInputs("canalForm");
		getFlash().limparPorId("canalForm");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		try {
			
		    PontoDeVenda item = getItem();
		    item.setDescricao(item.getDescricao().toUpperCase());
		    
		    PontoDeVenda localizarPorNome = getCanalfacade().localizarPorNome(item.getDescricao());
		    if(localizarPorNome!=null && localizarPorNome.getCodigo()!=item.getCodigo()) {
		    	JsfUtil.addErrorMessage("O ponto de venda informado já foi cadastrado", "Procedimento não realizado");
		    	FacesContext.getCurrentInstance().validationFailed();
		    	atualizar();
		    	return;
		    }
		    
		   
		    
		    if(item.getCodigo()==null) {
		    	getCanalfacade().create(item);
		    	JsfUtil.addSuccessMessage("Ponto de venda criado com sucesso", "Procedimento OK");
		    }else {
		    	getCanalfacade().edit(item);
		    	JsfUtil.addSuccessMessage("Ponto de venda alterado com sucesso", "Procedimento OK");
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
	public PontoDeVenda getItem() {
		// TODO Auto-generated method stub
		PontoDeVenda ret = (PontoDeVenda)getFlash().getValoresPorID("canalForm").get("item");
		if(ret==null) {
			ret =new PontoDeVenda();
			setItem(ret);
		}
		return ret;
	}

	@Override
	public void setItem(PontoDeVenda item) {
		getFlash().getValoresPorID("canalForm").put("item",item);
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PontoDeVenda> getItens() {
		ArrayList<PontoDeVenda> itens = (ArrayList<PontoDeVenda>) getFlash().getValoresPorID("canalForm").get("itens");
		if(itens==null) {
			itens = (ArrayList<PontoDeVenda>) getCanalfacade().findAll();
			setItens(itens);
		}
		// TODO Auto-generated method stub
		return itens;
	}

	@Override
	public void setItens(List<PontoDeVenda> itens) {
		// TODO Auto-generated method stub
		getFlash().getValoresPorID("canalForm").put("itens",itens);
		
	}

	public PontoDeVendaFacade getCanalfacade() {
		return canalfacade;
	}

	public void setCanalfacade(PontoDeVendaFacade canalfacade) {
		this.canalfacade = canalfacade;
	}
	 List<Equipe> equipesDisponiveis=null;
	public List<Equipe> getEquipesDisponiveis() {
		if(equipesDisponiveis==null) {
			equipesDisponiveis = getEquipeFacade().findAllEquipes(getLoginInfor().getCodigoEquipeVinculada());
		}
		return equipesDisponiveis;
	}
	public void setEquipesDisponiveis(List<Equipe> equipesDisponiveis) {
		this.equipesDisponiveis = equipesDisponiveis;
	}
	public EquipeFacade getEquipeFacade() {
		return equipeFacade;
	}
	public void setEquipeFacade(EquipeFacade equipeFacade) {
		this.equipeFacade = equipeFacade;
	}
	public LoginInfo getLoginInfor() {
		return loginInfor;
	}
	public void setLoginInfor(LoginInfo loginInfor) {
		this.loginInfor = loginInfor;
	}
	@Inject
	private CepWebService cepWebService;
	private ArrayList<Municipio> municipio=null;
	private List<Estados> estados=null;
	public void pesquisarCep() {
		try {
			PontoDeVenda v = getItem();
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

	public List<Estados> getEstados() {
		if(estados==null) {
			estados=cepWebService.getEstados();
			
		}
		return estados;
	}

	public void setEstados(List<Estados> estados) {
		this.estados = estados;
	}

	public ArrayList<Municipio> getMunicipios() {
		PontoDeVenda item = getItem(); 
		if(item!=null && item.getEstado()!=null) {
			municipio = getCepWebService().getMunicipios(item.getEstado());
		}
		return municipio;
	}

	public void setMunicipios(ArrayList<Municipio> municipio) {
		this.municipio = municipio;
	}

}
