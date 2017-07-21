package br.edu.ifpe.monitoria.conversores;

import br.edu.ifpe.monitoria.entidades.Coordenacao;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "coordenacaoConverter")
public class CoordenacaoConverter implements Converter{
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            return (Coordenacao) component.getAttributes().get(value);
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity) {
        if (entity != null && entity instanceof Coordenacao) {
            component.getAttributes().put(((Coordenacao) entity).getId().toString(), entity);
            return ((Coordenacao) entity).getId().toString();
        }

        return null;
    }
}