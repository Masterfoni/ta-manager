package br.edu.ifpe.monitoria.conversores;

import br.edu.ifpe.monitoria.entidades.ComponenteCurricular;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "componenteConverter")
public class ComponenteConverter implements Converter{
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            return (ComponenteCurricular) component.getAttributes().get(value);
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity) {
        if (entity != null && entity instanceof ComponenteCurricular) {
            component.getAttributes().put(((ComponenteCurricular) entity).getId().toString(), entity);
            return ((ComponenteCurricular) entity).getId().toString();
        }

        return null;
    }
}