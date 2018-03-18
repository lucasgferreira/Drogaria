package br.com.drogaria.comverter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("upperCaseConverter")
public class UpperCaseConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent uiComponent, String value) {
		// TODO Auto-generated method stub
		if (value == null) {
			return null;
		} else {
			return value.toUpperCase();
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent uiComponent, Object value) {
		// TODO Auto-generated method stub
		if (value == null) {
			return "";
		} else {
			return value.toString();
		}

	}

}
