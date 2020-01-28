package editora3.seguranca;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
import java.io.IOException;
import java.util.Enumeration;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import editora3.entidades.InfraUsuario;
import editora3.facade.InfraUsuarioFacade;
import editora3.util.TratamentoNulos;

/**
 *
 * @author Fernando
 */
@WebFilter
public class Autenticacao implements Filter {

    @Inject
    private LoginInfo _LoginInfo;
    
    @Inject 
    InfraUsuarioFacade infraUsuarioFacade;

    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {
        try {

            HttpServletRequest request = (HttpServletRequest) sr;
            HttpServletResponse response = (HttpServletResponse) sr1;
            //HttpSession session = req.getSession();
            
            String EstagioDoProjeto = request.getServletContext().getInitParameter("javax.faces.PROJECT_STAGE");
            String UsuarioPadrao = TratamentoNulos.getTratarString().Tratar(request.getServletContext().getInitParameter("usuario.padrao"),"");


	            if (UsuarioPadrao.equalsIgnoreCase("@Desenvolvedor@")) {
	            	/*InfraUsuario localizarPeloUsuario = infraUsuarioFacade.LocalizarPeloUsuario(UsuarioPadrao);
	            	if(localizarPeloUsuario!=null) {
	            		_LoginInfo.setUsuario_logado(localizarPeloUsuario);
	            		_LoginInfo.setUsuariologado(localizarPeloUsuario.getUsuario());
	            	}*/
	            	fc.doFilter(sr, sr1);
	            	
	            }else {

	                String URI = request.getRequestURI();
	                String Path = request.getContextPath();
	                String URL = request.getRequestURL().toString();
	                String Sigla = _LoginInfo.getUsuariologado();
	                String URL_Padrao = Path + "/";
	                System.out.println("USUARIO : " + (Sigla == null ? "NAO LOGADO" : Sigla));
	                String Metodo = request.getMethod();
	                //String urldestino = getNextURL(request);
	                if (Sigla == null) {
                        if (URL.endsWith(Path + "/") || URL.contains("/javax.faces.resource/") || URL.endsWith("/login.jsf") || URL.endsWith("/signin.jsf") ) {
                        	fc.doFilter(sr, sr1);
	                    } else if (Metodo.equalsIgnoreCase("post")) {
	                        fc.doFilter(sr, sr1);
	                    } else {
	                        response.sendRedirect(URL_Padrao);
	                    }
	                	 
	                } else if (URL.endsWith(Path + "/")) {
	                    //response.sendRedirect(Path + "/ui/infra/inicio.jsf");                     
	                    request.getRequestDispatcher("/ui/dash/index.jsf").forward(request, response);
	                } else {
	                    fc.doFilter(sr, sr1);	
	                }

	            }
             

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}
