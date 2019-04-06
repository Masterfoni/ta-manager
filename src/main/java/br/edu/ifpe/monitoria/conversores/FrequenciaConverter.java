package br.edu.ifpe.monitoria.conversores;

import br.edu.ifpe.monitoria.entidades.Frequencia;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "frequenciaConverter")
public class FrequenciaConverter implements Converter {
	
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            return (Frequencia) component.getAttributes().get(value);
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity) {
        if (entity != null && entity instanceof Frequencia) {
            component.getAttributes().put(((Frequencia) entity).getId().toString(), entity);
            return ((Frequencia) entity).getId().toString();
        }

        return null;
    }

}
