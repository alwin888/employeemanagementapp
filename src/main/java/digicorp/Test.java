package digicorp;

import digicorp.entity.*;
import digicorp.services.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    static final String DBNAME = "employees";

    public static void main(String[] args) {
        Map<String,String> persistenceMap = new HashMap<>();
        persistenceMap.put("jakarta.persistence.jdbc.url",
                "jdbc:mariadb://localhost:3306/"+DBNAME);

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("EmployeeService", persistenceMap);
        EntityManager em = emf.createEntityManager();
        EmployeeService service = new EmployeeService(em);
        Employee emp = null;

        em.getTransaction().begin();

        emp = service.findEmployee(10001);
        System.out.println("Found " + emp);





    }

}