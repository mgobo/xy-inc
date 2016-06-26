/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zup.br.commons;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author mateusgobo
 */
@ManagedBean(name="beanCommons")
@SessionScoped
public class BeanCommons extends FacesUtil implements Serializable{
    
    private StringBuilder tituloPagina;
    private String[] areas;
    private String tipo;
    
    public void navegar(String root, String header){
        page(root, header);
        setTituloPagina(header);
        this.addValueSession("areaAtiva", root);
        this.setAreaAtiva(this.getSessionValue("areaAtiva").toString());
    }
    
    public String getTituloPagina() {
        if(this.tituloPagina == null){
            this.tituloPagina = new StringBuilder();
        }
        return tituloPagina.toString();
    }
    public void setTituloPagina(String tituloPagina) {
        if(this.tituloPagina == null){
            this.tituloPagina = new StringBuilder();
        }
        this.tituloPagina.delete(0, this.tituloPagina.length());
        this.tituloPagina.append(tituloPagina);
    }

    public void setAreas(String[] areas){
        this.areas = areas;
    }
    public String[] getAreas() {
        return areas;
    }

    public String getTipo() {
        if(this.tipo == null){
            this.tipo = "";
        }
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
