/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel;

import br.com.ceciliaprado.cmp.control.bean.Service;
import br.com.ceciliaprado.cmp.control.dao.personnel.EmployeeDAO;
import br.com.ceciliaprado.cmp.model.personnel.Employee;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
@ManagedBean
@ApplicationScoped
public class EmployeeService extends Service<Employee> {

    @Override
    public String getErrorMessage() {
        return "Nenhum funcionário foi cadastrado ainda!!!";
    }

    public List<Employee> getEmployees() {
        return getElements();
    }

    @Override
    protected List<Employee> getElements(EntityManager em) {
        EmployeeDAO employeeDAO = new EmployeeDAO(em);
        return employeeDAO.findAll();
    }
    
    public String toString(String employeeType) {
        String str = "";
        switch (employeeType) {
            case "Subordinate":
                str = "Subordinado";
                break;
            case "Supervisor":
                str = "Supervisor";
                break;
            case "Manager":
                str = "Gerente";
                break;
        }
        return str;
    }
    
}
