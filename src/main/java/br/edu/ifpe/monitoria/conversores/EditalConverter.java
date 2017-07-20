package br.edu.ifpe.monitoria.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.edu.ifpe.monitoria.entidades.Edital;

@FacesConverter(value = "editalConverter")
public class EditalConverter implements Converter{
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            return (Edital) component.getAttributes().get(value);
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity) {
        if (entity != null && entity instanceof Edital) {
            component.getAttributes().put(((Edital) entity).getId().toString(), entity);
            return ((Edital) entity).getId().toString();
        }

        return null;
    }
}