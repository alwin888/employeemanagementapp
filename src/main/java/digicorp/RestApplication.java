package digicorp;

import digicorp.rest.*;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;
/**
 * Configures the JAX-RS application for the DigiCorp REST API.
 *
 * This class extends {@link Application} and defines the base URI for all REST
 * endpoints using the {@link ApplicationPath} annotation. In this case, all
 * resources are available under the base path {@code /api}.
 *
 * It also registers the REST resource classes that are part of this application:
 * <ul>
 *     <li>{@link EmployeeResource} – endpoints for employee operations</li>
 *     <li>{@link DepartmentResource} – endpoints for department operations</li>
 * </ul>
 * Any additional REST resources or providers should be added to the
 * {@link #getClasses()} method to ensure they are included in the application.
 * 
 */
@ApplicationPath("/api")
public class RestApplication extends Application {
    /**
     * Returns a set of classes that make up the REST application.
     * <p>
     * This includes all resource classes that should be exposed via JAX-RS.
     *
     * @return a set of registered JAX-RS classes
     */
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<>();
        s.add(EmployeeResource.class);
        s.add(DepartmentResource.class);
        return s;
    }
}