package facades;

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

import entities.Location;
import entities.Spot;
import entities.Timeline;


/**
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class SpotFacade {

    private static SpotFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private SpotFacade() {
    }

    /**
     * @param _emf
     * @return an instance of this facade class.
     */
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
    public SpotDTO createSpot(SpotDTO spotDTO, Long timeline_id) {
        EntityManager em = emf.createEntityManager();
        try {
            Timeline timeline = em.find(Timeline.class, timeline_id);
            if (timeline == null) {
                throw new NotFoundException("No timeline with this id exists");
            }
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
            //String date = spotDTO.getTimestamp();
            //LocalDate localDate = LocalDate.parse(date, formatter);
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());
            Spot spot = new Spot(spotDTO.getDescription(),spotDTO.getName(), spotDTO.getTimestamp());
            TypedQuery<Location> query
                    = em.createQuery("SELECT l FROM Location l where l.name = :country", Location.class);
            query.setParameter("country", spotDTO.getLocationDTO().getName());
            Location location = query.getSingleResult();
            location.addSpot(spot);
            timeline.addSpot(spot);

            em.getTransaction().begin();
            em.persist(spot);
            em.getTransaction().commit();

            return new SpotDTO(spot);
        } finally {
            em.close();
        }
    }

    //yes
    public SpotDTO createSpot2(SpotDTO spotDTO, Long timeline_id) {
        EntityManager em = emf.createEntityManager();
        try {
            Timeline timeline = em.find(Timeline.class, timeline_id);
            if (timeline == null) {
                throw new NotFoundException("No timeline with this id exists");
            }
            //Date date = new Date();
            //Timestamp ts = new Timestamp(date.getTime());
            Spot spot = new Spot(spotDTO.getDescription(),spotDTO.getName(), spotDTO.getTimestamp());
            Location location = new Location(spotDTO.getLocationDTO().getWikiId(),spotDTO.getLocationDTO().getName(),spotDTO.getLocationDTO().getType());

            location.addSpot(spot);
            timeline.addSpot(spot);

            em.getTransaction().begin();
            em.persist(location);
            em.persist(spot);
            em.getTransaction().commit();

            return new SpotDTO(spot);
        } finally {
            em.close();
        }
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



}

