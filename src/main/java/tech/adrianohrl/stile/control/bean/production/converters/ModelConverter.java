/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.bean.production.converters;

import tech.adrianohrl.stile.control.bean.Converter;
import tech.adrianohrl.stile.model.production.Model;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author adrianohrl
 */
@FacesConverter("modelConverter")
public class ModelConverter extends Converter<Model> {

    @Override
    public String getErrorMessage() {
        return "Modelo inválido!!!";
    }

    @Override
    public String toString(Model model) {
        return model.getReference();
    }
    
}