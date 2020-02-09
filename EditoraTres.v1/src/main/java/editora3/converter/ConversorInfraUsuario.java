package editora3.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import editora3.controller.CanalController;
import editora3.controller.InfraUsuarioController;
import editora3.entidades.Canal;
import editora3.entidades.InfraUsuario;
 

 
 
 
@FacesConverter("editora3.converter.ConversorInfraUsuario")
public  class ConversorInfraUsuario implements Converter  { 

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
    	InfraUsuario find = null;
		try {				
			if (value == null || value.length() == 0) {
				return null;
			} else {
				InfraUsuarioController controller = (InfraUsuarioController) facesContext.getApplication().getELResolver()
						.getValue(facesContext.getELContext(), null, "infraUsuarioController");
				if(value!=null && !value.equalsIgnoreCase("selecione")) {	
					find = controller.getFacade().find(value != null ? Integer.valueOf(value) : 0);
				}

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
        if (object instanceof InfraUsuario) {
        	InfraUsuario o = (InfraUsuario) object;
            return getStringKey(o.getIdusuario());
        } else {
            //Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), InfraUsuario.class.getName()});
            return null;
        }
    }

}
