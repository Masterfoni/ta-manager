package br.edu.ifpe.monitoria.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.edu.ifpe.monitoria.entidades.Aluno;

@FacesConverter(value = "alunoConverter")
public class AlunoConverter implements Converter {
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            return (Aluno) component.getAttributes().get(value);
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity) {
        if (entity != null && entity instanceof Aluno) {
            component.getAttributes().put(((Aluno) entity).getId().toString(), entity);
            return ((Aluno) entity).getId().toString();
        }

        return null;
    }
}