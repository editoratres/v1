package editora3.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import editora3.controller.CanalController;
 
import editora3.entidades.PontoDeVenda;
 

 
 
 
@FacesConverter("editora3.converter.ConversorPontoDeVenda")
public  class ConversorPontoDeVenda implements Converter  { 

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
    	PontoDeVenda find = null;
		try {				
			if (value == null || value.length() == 0) {
				return null;
			} else {
				CanalController controller = (CanalController) facesContext.getApplication().getELResolver()
						.getValue(facesContext.getELContext(), null, "canalController");
				if(value!=null && !value.equalsIgnoreCase("selecione")) {	
					find = controller.getCanalfacade().find(value != null ? Integer.valueOf(value) : 0);
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
        if (object instanceof PontoDeVenda) {
        	PontoDeVenda o = (PontoDeVenda) object;
            return getStringKey(o.getCodigo());
        } else {
            //Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), InfraUsuario.class.getName()});
            return null;
        }
    }

}
