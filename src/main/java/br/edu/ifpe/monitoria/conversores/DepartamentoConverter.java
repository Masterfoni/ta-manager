package br.edu.ifpe.monitoria.conversores;

import br.edu.ifpe.monitoria.entidades.Departamento;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "departamentoConverter")
public class DepartamentoConverter implements Converter{
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            return (Departamento) component.getAttributes().get(value);
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity) {
        if (entity != null && entity instanceof Departamento) {
            component.getAttributes().put(((Departamento) entity).getId().toString(), entity);
            return ((Departamento) entity).getId().toString();
        }

        return null;
    }
}