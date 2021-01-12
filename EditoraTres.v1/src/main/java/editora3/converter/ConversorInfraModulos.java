package editora3.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import editora3.controller.InfraModulosController;
import editora3.entidades.InfraModulo;
  
 
@FacesConverter("editora3.converter.conversorInfraModulos")
public  class ConversorInfraModulos implements Converter  { 

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
    	InfraModulo find = null;
		try {				
			if (value == null || value.length() == 0) {
				return null;
			} else {
				InfraModulosController controller = (InfraModulosController) facesContext.getApplication().getELResolver()
						.getValue(facesContext.getELContext(), null, "infraModulosController");
				if(value!=null && !value.equalsIgnoreCase("Todos os módulos")) {	
					find = controller.getInfraModulosFacade().find(value != null ? Integer.valueOf(value) : 0);
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
        if (object instanceof InfraModulo) {
        	InfraModulo o = (InfraModulo) object;
            return getStringKey(o.getIdmodulo());
        } else {
            //Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), InfraUsuario.class.getName()});
            return null;
        }
    }

}
