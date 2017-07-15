package br.edu.ifpe.monitoria.conversores;

import br.edu.ifpe.monitoria.entidades.Professor;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "professorConverter")
public class ProfessorConverter implements Converter{
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            return (Professor) component.getAttributes().get(value);
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity) {
        if (entity != null && entity instanceof Professor) {
            component.getAttributes().put(((Professor) entity).getId().toString(), entity);
            return ((Professor) entity).getId().toString();
        }

        return null;
    }
}