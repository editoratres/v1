package editora3.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import editora3.controller.VendedorController;
import editora3.entidades.Vendedor;

 
 
 
@FacesConverter("editora3.converter.ConversorVendedor")
public  class ConversorVendedor implements Converter  { 

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
    	Vendedor find = null;
		try {				
			if (value == null || value.length() == 0) {
				return null;
			} else {
				VendedorController controller = (VendedorController) facesContext.getApplication().getELResolver()
						.getValue(facesContext.getELContext(), null, "vendedorController");
				if(value!=null && !value.equalsIgnoreCase("selecione")) {	
					find = controller.getVendedorFacade().find(value != null ? Integer.valueOf(value) : 0);
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
        if (object instanceof Vendedor) {
        	Vendedor o = (Vendedor) object;
            return getStringKey(o.getCodigo());
        } else {
            //Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), InfraUsuario.class.getName()});
            return null;
        }
    }

}
