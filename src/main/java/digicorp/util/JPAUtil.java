package digicorp.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public final class JPAUtil {

    private static final EntityManagerFactory emf = buildEMF();

    private static EntityManagerFactory buildEMF() {
        Map<String, String> props = new HashMap<>();
        props.put("jakarta.persistence.jdbc.url",
                "jdbc:mariadb://localhost:3306/employees");

        return Persistence.createEntityManagerFactory("EmployeeService", props);
    }

    private JPAUtil() {}  // prevent instantiation

    public static EntityManagerFactory getEMF() {
        return emf;
    }
}