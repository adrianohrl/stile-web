/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.production;

import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.bean.production.services.ProductionOrderService;
import br.com.ceciliaprado.cmp.control.dao.production.io.ProductionOrdersReaderDAO;
import br.com.ceciliaprado.cmp.model.production.ProductionOrder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author adrianohrl
 */
@ManagedBean
@ViewScoped
public class ProductionOrderImportBean implements Serializable {
    
    @ManagedProperty(value = "#{productionOrderService}")
    private ProductionOrderService service;
    private final List<ProductionOrder> productionOrders = new ArrayList<>();
    
    public void upload(FileUploadEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        UploadedFile file = event.getFile();
        EntityManager em = DataSource.createEntityManager();
        try {
            ProductionOrdersReaderDAO readerDAO = new ProductionOrdersReaderDAO(em);
            readerDAO.readFile(file.getInputstream());
            productionOrders.addAll(readerDAO.getRegisteredProductionOrders());
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso no upload", 
                    "O arquivo " + event.getFile().getFileName() + " foi importado para a aplicação!!!");
            update();
        } catch (java.io.IOException | br.com.ceciliaprado.cmp.exceptions.IOException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no upload", 
                    "Não foi possível fazer o upload do arquivo " + event.getFile().getFileName() + "!!!");
            System.out.println("IOException catched during importation: " + e.getMessage());
        }
        em.close();
        context.addMessage(null, message);
    }
    
    public void update() {
        service.update();
    }

    public void setService(ProductionOrderService service) {
        this.service = service;
    }

    public List<ProductionOrder> getProductionOrders() {
        return productionOrders;
    }
    
}
