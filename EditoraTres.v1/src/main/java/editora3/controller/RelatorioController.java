package editora3.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import editora3.entidades.Relatorio;
import editora3.facade.RelatorioFacade;
import editora3.util.JsfUtil;

@Named("relatorioController")
@RequestScoped
public class RelatorioController implements AbstractController<Relatorio> {

	@Inject
	FlashApp flashapp;

	private Boolean editar;
	
	@Inject
	private RelatorioFacade produtofacade;

	@Override
	public void excluir(Relatorio item) {
		// TODO Auto-generated method stub
		getRelatoriofacade().remove(item);
		setItens(null);

	}

	@PostConstruct
	public void iniciar() {
		setItens(null);
	}

	@Override
	public void prepararEditar(Relatorio item) {
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
		Relatorio relatorio = new Relatorio();
		relatorio.setData(new Date());
		setItem(relatorio);
	
		// TODO Auto-generated method stub

	}

	@Override
	public void fecharDialogo() {
		PrimeFaces.current().resetInputs("relatorioForm");
		getFlash().limparPorId("relatorioForm");
		// TODO Auto-generated method stub

	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		try {

			Relatorio item = getItem();
			item.setDescricao(item.getDescricao().toUpperCase());

			Relatorio localizarPorNome = getRelatoriofacade().localizarPorNome(item.getIdentificacao());
			if (localizarPorNome != null && localizarPorNome.getCodigo() != item.getCodigo()) {
				JsfUtil.addErrorMessage("O Relatorio informado já foi cadastrado", "Procedimento não realizado");
				FacesContext.getCurrentInstance().validationFailed();
				atualizar();
				return;
			}

			if (item.getCodigo() == null) {
				getRelatoriofacade().create(item);
				JsfUtil.addSuccessMessage("Relatorio criado com sucesso", "Procedimento OK");
			} else {
				getRelatoriofacade().edit(item);
				JsfUtil.addSuccessMessage("Relatorio alterado com sucesso", "Procedimento OK");
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
		this.flashapp = flash;
		// TODO Auto-generated method stub

	}

	@Override
	public Relatorio getItem() {
		// TODO Auto-generated method stub
		return (Relatorio) getFlash().getValoresPorID("relatorioForm").get("item");
	}

	@Override
	public void setItem(Relatorio item) {
		getFlash().getValoresPorID("relatorioForm").put("item", item);
		// TODO Auto-generated method stub

	}

	@Override
	public List<Relatorio> getItens() {
		ArrayList<Relatorio> itens = (ArrayList<Relatorio>) getFlash().getValoresPorID("relatorioForm").get("itens");
		if (itens == null) {
			itens = (ArrayList<Relatorio>) getRelatoriofacade().findAll();
			setItens(itens);
		}
		// TODO Auto-generated method stub
		return itens;
	}

	@Override
	public void setItens(List<Relatorio> itens) {
		// TODO Auto-generated method stub
		getFlash().getValoresPorID("relatorioForm").put("itens", itens);

	}
	 

	public RelatorioFacade getRelatoriofacade() {
		return produtofacade;
	}

	public void setRelatoriofacade(RelatorioFacade produtofacade) {
		this.produtofacade = produtofacade;
	}

	public Boolean getEditar() {
		return editar;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
	}

}
