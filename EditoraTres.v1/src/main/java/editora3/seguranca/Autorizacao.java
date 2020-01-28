package editora3.seguranca;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Inject;
 

import editora3.entidades.InfraTipoPerfilDet;
import editora3.facade.InfraUsuarioFacade;
import editora3.util.JsfUtil;
import editora3.util.TratamentoNulos;
 
/**
 *
 * @author Fernando
 */
public class Autorizacao implements PhaseListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
    private LoginInfo _LoginInfo;
    @Inject
    private InfraUsuarioFacade usuario;

    @Override
    public void afterPhase(PhaseEvent event) {

    }

    @Override
    public void beforePhase(PhaseEvent event) {

        String EstagioDoProjeto = event.getFacesContext().getExternalContext().getInitParameter("javax.faces.PROJECT_STAGE");
        String UsuarioPadrao = TratamentoNulos.getTratarString().Tratar(event.getFacesContext().getExternalContext().getInitParameter("usuario.padrao"),"");
        if (!UsuarioPadrao.equalsIgnoreCase("@Desenvolvedor@")) {
        	

            System.out.println(event.getPhaseId());
            FacesContext ctx = event.getFacesContext();
            //ExternalContext ec = ctx.getExternalContext();
            Integer IdUsuario = _LoginInfo.getIdusuariologado();
            if (ctx.getViewRoot() == null) {
                try {
                    ctx.getExternalContext().redirect("/Endereco/");
                } catch (IOException ex) {
                    JsfUtil.addErrorMessage(ex.getMessage());
                }
            } else {
                String paginaDestino = TratamentoNulos.getTratarString().Tratar(ctx.getViewRoot().getViewId(), "");
                if (IdUsuario != null
                        && !paginaDestino.contains("/login.xhtml")
                        && !paginaDestino.contains("/logged.xhtml")
                        && !paginaDestino.contains("/naoautorizado.xhtml")
                        && !paginaDestino.contains("/index.xhtml")) {

                    if (!ValidarAcesso(paginaDestino, IdUsuario)) {
                        try {
                            FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO, "Acesso Negado", "Usuario não autorizado");
                            ctx.addMessage("", mensagem);
                            NavigationHandler nh = ctx.getApplication().getNavigationHandler();
                            nh.handleNavigation(ctx, null, "inicio");
                            //ctx.getViewRoot().setViewId("/naoautorizado.xhtml");
                            //ctx.getExternalContext().redirect("/naoautorizado.xhtml");
                        } catch (Exception ex) {
                            Logger.getLogger(Autorizacao.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            }
        }else{
            _LoginInfo.setEstagioaplicacao(EstagioDoProjeto);
        }

    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }

    /**
     * @return the usuario
     */
    public InfraUsuarioFacade getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(InfraUsuarioFacade usuario) {
        this.usuario = usuario;
    }

    private boolean ValidarAcesso(String Recurso, Integer Usuario) {
        boolean Ret = false;
        try {

            List<InfraTipoPerfilDet> LocalizarRecurso = getUsuario().LocalizarRecurso(Usuario, Recurso);
            if (LocalizarRecurso.size() > 0) {
                Ret = LocalizarRecurso.get(0).getAcessar();
            }
            //Ret = true; // remover isso aqui
        } catch (Exception e) {
            JsfUtil.addErrorMessage("(ValidarAcesso) Falha na requisição\n\n" + e.getMessage());
        }
        return Ret;
    }

}

