package editora3.seguranca;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import editora3.entidades.InfraTipoPerfilDet;
import editora3.facade.InfraUsuarioFacade;
import editora3.util.JsfUtil;
 

/**
 *
 * @author Fernando
 */
@SessionScoped
public class AutorizacaoRecurso implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
    private LoginInfo lusuario;
    @Inject
    private InfraUsuarioFacade infraUsuarioFacade;
     /**
     * @return the lusuario
     */
    public LoginInfo getLusuario() {
        return lusuario;
    }

    /**
     * @param lusuario the lusuario to set
     */
    public void setLusuario(LoginInfo lusuario) {
        this.lusuario = lusuario;
    }

    /**
     * @return the infraUsuarioFacade
     */
    public InfraUsuarioFacade getInfraUsuarioFacade() {
        return infraUsuarioFacade;
    }

    /**
     * @param infraUsuarioFacade the infraUsuarioFacade to set
     */
    public void setInfraUsuarioFacade(InfraUsuarioFacade infraUsuarioFacade) {
        this.infraUsuarioFacade = infraUsuarioFacade;
    }
    
	public boolean VerificarAcesso(String NomeClasse,String Acao){
        return VerificarAcesso(NomeClasse, Acao, true);
    }
    public boolean VerificarAcesso(String NomeClasse,String Acao,boolean ExibirMSG){
        boolean Ret = false;
        try {
            List<InfraTipoPerfilDet> LocalizarRecursoClasse = getInfraUsuarioFacade().LocalizarRecursoClasse(getLusuario().getUsuario_logado().getIdusuario(), NomeClasse);
            if(LocalizarRecursoClasse.size()>0){
               if(Acao.equalsIgnoreCase("editar")){
                    Ret = LocalizarRecursoClasse.get(0).getEditar();
               }else if(Acao.equalsIgnoreCase("criar")){
                   Ret = LocalizarRecursoClasse.get(0).getCriar();
               }else if(Acao.equalsIgnoreCase("excluir")){
                   Ret = LocalizarRecursoClasse.get(0).getExcluir();
               }
               if(!Ret && ExibirMSG){
                  JsfUtil.addSuccessMessage("Usuário não autorizado", "Acesso Negado");
                  FacesContext.getCurrentInstance().validationFailed();
               }
                   
            }else{
                Ret=true;
            }
        } catch (Exception e) {
             JsfUtil.addErrorMessage("(VerificarAcesso) Falha na requisição\n\n" + e.getMessage());
        }
        return Ret;
    }

}
