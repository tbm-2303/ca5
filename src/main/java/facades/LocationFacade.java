package facades;

import dtos.LocationDTO;
import entities.Location;
import entities.User;
import errorhandling.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class LocationFacade {

    private static LocationFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private LocationFacade() {
    }

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
    public LocationDTO findLocation(String locationID){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            TypedQuery<Location> query = em.createQuery("SELECT l FROM Location l WHERE l.id = :id", Location.class);
            query.setParameter("id", locationID);
            Location location = query.getSingleResult();
            em.getTransaction().commit();
            return new LocationDTO(location);
        }
        finally {
            em.close();
        }
    }

    public LocationDTO createLocation(String id, String name, String type){
        Location location = new Location(id, name, type);
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(location);
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
        return new LocationDTO(location);
    }

    public String deleteLocation(String id) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Location> query = em.createQuery("DELETE FROM Location l WHERE l.id=:id", Location.class);
        query.setParameter("id", id);
        return ("Deleted location with id: " + id);
    }

}
