package editora3.util;



import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class JsfUtil {
    private static Logger log = org.apache.log4j.Logger.getLogger("application");
    public static String idSessao(){        

            FacesContext fCtx = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
            return  session.getId();
    }

    public static SelectItem[] getSelectItems(List<?> entities, boolean selectOne) {
        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) {
            items[0] = new SelectItem("", "---");
            i++;
        }
        for (Object x : entities) {
            items[i++] = new SelectItem(x, x.toString());
        }
        return items;
    }

    public static boolean isValidationFailed() {
        return FacesContext.getCurrentInstance().isValidationFailed();
    }

    public static void addErrorMessage(Exception ex, String defaultMsg) {
        String msg = ex.getLocalizedMessage();
        log.info(defaultMsg);
        log.error(ex,ex.fillInStackTrace());
        if (msg != null && msg.length() > 0) {
            addErrorMessage(msg);
        } else {
            if(defaultMsg!=null){
                addErrorMessage(defaultMsg);
            }else{
                if(ex!=null){
                    addErrorMessage(ex.getMessage());
                }
            }
        }
    }

    public static void addErrorMessages(List<String> messages) {
        for (String message : messages) {
            addErrorMessage(message);
        }
    }
     public static void addErrorMessage(String msg) {
         addErrorMessage(msg, "");
     }

    public static void addErrorMessage(String msg,String titulo) {
        
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo,msg );
        FacesContext.getCurrentInstance().addMessage("", facesMsg);
    }

    public static void addSuccessMessage(String msg) {
        addSuccessMessage(msg,"");
        
    }
    public static void addSuccessMessage(String msg,String Titulo) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,Titulo,msg);
        //RequestContext.getCurrentInstance().showMessageInDialog(facesMsg);
        FacesContext.getCurrentInstance().addMessage("", facesMsg);
    }

    public static String getRequestParameter(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }

    public static Object getObjectFromRequestParameter(String requestParameterName, Converter converter, UIComponent component) {
        String theId = JsfUtil.getRequestParameter(requestParameterName);
        return converter.getAsObject(FacesContext.getCurrentInstance(), component, theId);
    }

    public static enum PersistAction {
        CREATE,
        DELETE,
        UPDATE
    }
    public static String NomeAplicacao() {
        
       String Ret = "";
       
       
       
       return Ret;
    }
    public static String RetornarCaminhoAbsoluto(String relativePath) {
    	//String relativePath="/resources/temp/";
    	String absolutePath= FacesContext.getCurrentInstance().getExternalContext().getRealPath(relativePath);
	    return absolutePath;	
	    	
    }
    public static ServletContext getServletContext() {
    	ServletContext ret = null;
    	
    	ret =  (ServletContext) FacesContext
    		    .getCurrentInstance().getExternalContext().getContext();
    	
    	return ret;
    }
    public static String UrlServidor(){
        StringBuilder ret=new StringBuilder();
        try {
            ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
            ret
            .append(ectx.getRequestScheme())
            .append("://")
            .append(ectx.getRequestServerName())
            .append(ectx.getRequestServerPort() != 0 ? ":" + ectx.getRequestServerPort() : "");       
                   

        } catch (Exception ex) {
        	JsfUtil.addErrorMessage(ex, "UrlServidor");
        }
      
       
        return ret.toString();
    }
    public static String UrlContexto(){
        String ret="";
        try {
            ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
            ret = ectx.getRequestScheme()
                    + "://" + ectx.getRequestServerName()
                    + ":" + ectx.getRequestServerPort()
                    + "/" + ectx.getRequestContextPath().replace("/", "");

        } catch (Exception e) {
        }
      
       
        return ret;
    }
}
