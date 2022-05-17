package facades;

import dtos.LocationDTO;
import dtos.UserDTO;
import entities.Location;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
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

    //yes
    public List<String> getAllLocationNames() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Location> query = em.createQuery("SELECT l FROM Location l", Location.class);
        List<String> countries = new ArrayList<>();
        for (Location l : query.getResultList()) {
            countries.add(l.getName());
        }
        return countries;
    }

    //no endpoint
    public LocationDTO findLocationByWikiID(String wikiId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<Location> query = em.createQuery("SELECT l FROM Location l WHERE l.wikiId = :id", Location.class);
            query.setParameter("id", wikiId);
            Location location = query.getSingleResult();
            em.getTransaction().commit();
            return new LocationDTO(location);
        } finally {
            em.close();
        }
    }

    public List<LocationDTO> getAllLocation() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Location> query = em.createQuery("SELECT l FROM Location l", Location.class);
        List<Location> locations = query.getResultList();

        List<LocationDTO> locationDTOS = new ArrayList<>();
        for (Location l : locations) {
            locationDTOS.add(new LocationDTO(l));
        }
        return locationDTOS;
    }

}
