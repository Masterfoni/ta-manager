package br.edu.ifpe.monitoria.conversores;

import br.edu.ifpe.monitoria.entidades.Servidor;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "servidorConverter")
public class ServidorConverter implements Converter{
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            return (Servidor) component.getAttributes().get(value);
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity) {
        if (entity != null && entity instanceof Servidor) {
            component.getAttributes().put(((Servidor) entity).getId().toString(), entity);
            return ((Servidor) entity).getId().toString();
        }

        return null;
    }
}