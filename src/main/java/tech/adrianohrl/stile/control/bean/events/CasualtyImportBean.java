package tech.adrianohrl.stile.control.bean.events;

import tech.adrianohrl.stile.control.bean.DataSource;
import tech.adrianohrl.stile.control.bean.events.services.CasualtyService;
import tech.adrianohrl.stile.control.dao.events.io.CasualtiesReaderDAO;
import tech.adrianohrl.stile.model.events.Casualty;
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
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@ManagedBean
@ViewScoped
public class CasualtyImportBean implements Serializable {
    
    @ManagedProperty(value = "#{casualtyService}")
    private CasualtyService service;
    private final List<Casualty> casualties = new ArrayList<>();
    
    public void upload(FileUploadEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message;
        UploadedFile file = event.getFile();
        EntityManager em = DataSource.createEntityManager();
        try {
            CasualtiesReaderDAO readerDAO = new CasualtiesReaderDAO(em);
            readerDAO.readFile(file.getInputstream());
            casualties.addAll(readerDAO.getRegisteredCasualties());
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso no upload", 
                    "O arquivo " + event.getFile().getFileName() + " foi importado para a aplicação!!!");
            update();
        } catch (java.io.IOException | tech.adrianohrl.stile.exceptions.IOException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no upload", 
                    "Não foi possível fazer o upload do arquivo " + event.getFile().getFileName() + ": " + e.getMessage());
            System.out.println("IOException catched during importation: " + e.getMessage());
        }
        em.close();
        context.addMessage(null, message);
    }
    
    public void update() {
        service.update();
    }

    public void setService(CasualtyService service) {
        this.service = service;
    }

    public List<Casualty> getCasualties() {
        return casualties;
    }
    
}
