package digicorp.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class responsible for creating and providing access to the application's
 * singleton {@link EntityManagerFactory}. This ensures consistent configuration and
 * efficient resource usage across the DigiCorp HR system.
 * <p>
 * The factory is initialized once at class load time and reused throughout the
 * application for creating request-scoped {@link jakarta.persistence.EntityManager}
 * instances in REST resources and service classes.
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *     <li>Configure JPA connection properties</li>
 *     <li>Initialize the JPA persistence unit {@code EmployeeService}</li>
 *     <li>Expose a globally accessible and thread-safe {@code EntityManagerFactory}</li>
 * </ul>
 *
 * <p>
 * This class is declared {@code final} and contains a private constructor to
 * prevent instantiation, following standard utility-class design patterns.
 * </p>
 */
public final class JPAUtil {
    /**
     * Singleton instance of the application's {@link EntityManagerFactory}.
     * Created at class initialization to guarantee availability and thread safety.
     */
    private static final EntityManagerFactory emf = buildEMF();
    /**
     * Builds and configures the application's {@link EntityManagerFactory}.
     * <p>
     * This method programmatically sets JPA properties—such as the JDBC URL—
     * before invoking {@link Persistence#createEntityManagerFactory(String, Map)}
     * using the {@code EmployeeService} persistence unit.
     *
     * @return a fully initialized {@code EntityManagerFactory}
     */
    private static EntityManagerFactory buildEMF() {
        Map<String, String> props = new HashMap<>();
        props.put("jakarta.persistence.jdbc.url",
                "jdbc:mariadb://localhost:3306/employees");

        return Persistence.createEntityManagerFactory("EmployeeService", props);
    }
    /**
     * Private constructor to prevent instantiation.
     * <p>
     * This class functions purely as a static utility holder and should never
     * be instantiated.
     */
    private JPAUtil() {}

    /**
     * Returns the application's shared {@link EntityManagerFactory}.
     * <p>
     * This factory should be used to create request-level {@link jakarta.persistence.EntityManager}
     * instances for database operations.
     *
     * @return the singleton {@code EntityManagerFactory}
     */
    public static EntityManagerFactory getEMF() {
        return emf;
    }
}