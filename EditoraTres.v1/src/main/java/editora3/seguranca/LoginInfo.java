package editora3.seguranca;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 

import java.io.Serializable;
import java.sql.Timestamp;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import editora3.entidades.InfraUsuario;
 

/**
 *
 * @author Fernando
 */
@Named
@SessionScoped
public class LoginInfo implements Serializable {

    private static final long serialVersionUID = 4582254362630538481L;
    private String IdSessao;
    private String usuariologado;
    private Integer idusuariologado;
    private Integer idrepresentante;
    private InfraUsuario usuario_logado;
    private Timestamp logadodesde;
    private String estagioaplicacao="Development";

    public LoginInfo() {
        System.out.println("Criou objeto logininfo");
    }

    public String getUsuariologado() {
        return usuariologado;
    }

    public void setUsuariologado(String _usuariologado) {
        this.usuariologado = _usuariologado;
    }

    /**
     * @return the idusuariologado
     */
    public Integer getIdusuariologado() {
        return idusuariologado;
    }

    /**
     * @param idusuariologado the idusuariologado to set
     */
    public void setIdusuariologado(Integer idusuariologado) {
        this.idusuariologado = idusuariologado;
    }

    /**
     * @return the idrepresentante
     */
    public Integer getIdrepresentante() {
        return idrepresentante;
    }

    /**
     * @param idrepresentante the idrepresentante to set
     */
    public void setIdrepresentante(Integer idrepresentante) {
        this.idrepresentante = idrepresentante;
    }

    /**
     * @return the usuario_logado
     */
    public InfraUsuario getUsuario_logado() {
        return usuario_logado;
    }

    /**
     * @param usuario_logado the usuario_logado to set
     */
    public void setUsuario_logado(InfraUsuario usuario_logado) {
        this.usuario_logado = usuario_logado;
    }

    /**
     * @return the logadodesde
     */
    public Timestamp getLogadodesde() {
        return logadodesde;
    }

    /**
     * @param logadodesde the logadodesde to set
     */
    public void setLogadodesde(Timestamp logadodesde) {
        this.logadodesde = logadodesde;
    }

    /**
     * @return the IdSessao
     */
    public String getIdSessao() {
        return IdSessao;
    }

    /**
     * @param IdSessao the IdSessao to set
     */
    public void setIdSessao(String IdSessao) {
        this.IdSessao = IdSessao;
    }

    /**
     * @return the estagioaplicacao
     */
    public String getEstagioaplicacao() {
        return estagioaplicacao;
    }

    /**
     * @param estagioaplicacao the estagioaplicacao to set
     */
    public void setEstagioaplicacao(String estagioaplicacao) {
        this.estagioaplicacao = estagioaplicacao;
    }

}
