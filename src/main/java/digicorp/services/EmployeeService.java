package digicorp.services;

import digicorp.entity.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Date;

public class EmployeeService {
    protected EntityManager em;

    public EmployeeService(EntityManager em) {
        this.em = em;
    }

    public Employee createEmployee(int emp_no, Date birth_date, String first_name, String last_name, String gender, Date hiredate) {
        Employee emp = new Employee(emp_no);
        emp.setBirthDate(birth_date);
        emp.setFirstName(first_name);
        emp.setLastName(last_name);
        emp.setGender(gender);
        emp.setHireDate(hiredate);
        em.persist(emp);
        return emp;
    }

    public void removeEmployee(int id) {
        Employee emp = findEmployee(id);
        if (emp != null) {
            em.remove(emp);
        }
    }

    public Employee findEmployee(int id) {
        return em.find(Employee.class, id);
    }

    public List<Employee> findAllEmployees() {
        TypedQuery<Employee> query = em.createQuery(
                "SELECT e FROM Employee e", Employee.class);
        return query.getResultList();
    }
}
