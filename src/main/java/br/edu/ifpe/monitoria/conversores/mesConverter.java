package br.edu.ifpe.monitoria.conversores;

import java.util.GregorianCalendar;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "mesConverter")
public class mesConverter implements Converter{
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            return (GregorianCalendar) component.getAttributes().get(value);
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity) {
        if (entity != null && entity instanceof GregorianCalendar) {
            component.getAttributes().put(((GregorianCalendar) entity).toString(), entity);
            return ((GregorianCalendar) entity).toString();
        }

        return null;
    }
}