package facades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


/**
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class LocationFacade {

    private static LocationFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private LocationFacade() {
    }

    /**
     * @param _emf
     * @return an instance of this facade class.
     */
    public static LocationFacade getLocationFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new LocationFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }




}


