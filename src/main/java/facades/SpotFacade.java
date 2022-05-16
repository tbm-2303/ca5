package facades;

import dtos.LocationDTO;
import dtos.SpotDTO;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;

import dtos.TimelineDTO;
import entities.Location;
import entities.Spot;
import entities.Timeline;

public class SpotFacade {

    private static SpotFacade instance;
    private static EntityManagerFactory emf;
    LocationFacade locationFacade = LocationFacade.getLocationFacade(emf);

    //Private Constructor to ensure Singleton
    private SpotFacade() {
    }

    public static SpotFacade getSpotFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new SpotFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //YES
    public SpotDTO createSpot(String name, String des, Timestamp timestamp, String locationId){
        EntityManager em = getEntityManager();
        LocationDTO locationDTO = locationFacade.findLocation(locationId);
        Location spotLocation = new Location(locationDTO);
        Spot spot = new Spot(name, des, timestamp, spotLocation);
        try{
            em.getTransaction().begin();;
            em.persist(spot);
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
        return new SpotDTO(spot);
    }
    //YES
    public List<SpotDTO> getSpotsFromTimeline(Long timeline_id) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Spot> query
                = em.createQuery("SELECT s FROM Spot s where s.timeline.id = :timeline_id", Spot.class);
        query.setParameter("timeline_id", timeline_id);
        List<Spot> spotList = query.getResultList();

        List<SpotDTO> spotDTOS = new ArrayList<>();
        for (Spot spot : spotList) {
            spotDTOS.add(new SpotDTO(spot));
        }
        spotDTOS.sort(Comparator.comparing(SpotDTO::getTimestamp));
        return new ArrayList<>(spotDTOS);
    }

    //YES
    public List<SpotDTO>  test(){
        EntityManager em = emf.createEntityManager();
        Long timeline_ID = 1L;
        Timeline timeline = em.find(Timeline.class,timeline_ID);
        List<Spot> spotList = timeline.getSpotList();
        List<SpotDTO> spotDTOS = new ArrayList<>();
        for (Spot spot : spotList) {
            spotDTOS.add(new SpotDTO(spot));
        }
        return spotDTOS;
    }

/*
    public List<String> seeSpot(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            //find spot where id =
            TypedQuery <Spot> query = em.createQuery("SELECT s FROM Spot s WHERE s.id = :id", Spot.class);
            query.setParameter("id", id);
            Spot spot = query.getSingleResult();
            String name = spot.getName();
            String description = spot.getDescription();
            String timeStamp = spot.getTimeStamp().toString();
            String location = spot.getLocation().toString();
            List<String> spotData = new ArrayList<>();
            spotData.add(name);
            spotData.add(description);
            spotData.add(timeStamp);
            spotData.add(location);

            return spotData;
        }finally {
            em.close();
        }
    }

    public synchronized SpotDTO editSpot(SpotDTO spotDTO){
        EntityManager em = emf.createEntityManager();
        Spot spotUpdated = em.find(Spot.class, spotDTO.getId());
        try{
            em.getTransaction().begin();
            spotUpdated.setName(spotDTO.getName());
            spotUpdated.setDescription(spotDTO.getDescription());
            spotUpdated.setTimeStamp(spotDTO.getTimestamp());
            spotUpdated.setLocation(spotDTO.getLocation());
            //spotUpdated.setId(spotDTO.getId());
            //spotUpdated.setTimeline(spotDTO.getTimeline());
            em.merge(spotUpdated);
            em.getTransaction().commit();
            return new SpotDTO(spotUpdated);
        }finally {
            em.close();
        }

    }*/
    //test virker, og endpoint mangler
    public String deleteSpot(Long id){
        EntityManager em = emf.createEntityManager();
        Spot spot = em.find(Spot.class, id);
        if(spot == null){
            throw new WebApplicationException("The timeline does not exist");
        }
        else{
            try{
                em.getTransaction().begin();
                TypedQuery<Spot> query = em.createQuery("DELETE FROM Spot s WHERE s.id = :id", Spot.class);
                query.setParameter("id", id);
                query.executeUpdate();
                em.getTransaction().commit();
            }
            finally {
                em.close();
            }
        }
        return "The spot with id: " + id + " has been deleted";
    }


}
