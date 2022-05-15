package facades;

import dtos.LocationDTO;
import entities.Location;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;


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

    public List<String> getAllLocationNames() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Location> query = em.createQuery("SELECT l FROM Location l", Location.class);
        List<String> countries = new ArrayList<>();
        for (Location l : query.getResultList()) {
            countries.add(l.getName());
        }
        return countries;
    }

    public LocationDTO findLocationByWikiID(String locationID) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<Location> query = em.createQuery("SELECT l FROM Location l WHERE l.id = :id", Location.class);
            query.setParameter("id", locationID);
            Location location = query.getSingleResult();
            em.getTransaction().commit();
            return new LocationDTO(location);
        } finally {
            em.close();
        }
    }

}
