package facades;

import dtos.RenameMeDTO;
import dtos.SpotDTO;
import dtos.TimelineDTO;
import dtos.UserDTO;
import entities.RenameMe;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;

//import errorhandling.RenameMeNotFoundException;
import entities.Spot;
import entities.Timeline;
import entities.User;
import utils.EMF_Creator;

/**
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class TimelineFacade {

    private static TimelineFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private TimelineFacade() {
    }

    /**
     * @param _emf
     * @return an instance of this facade class.
     */
    public static TimelineFacade getTimelineFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TimelineFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public SpotDTO createSpot(SpotDTO spotDTO, Long timeline_id) {
        EntityManager em = emf.createEntityManager();
        try {
            Timeline timeline = em.find(Timeline.class, timeline_id);
            if (timeline == null) {
                throw new NotFoundException("No timelie with this id exists");
            }
            Spot spot = new Spot(spotDTO.getDescription());
            timeline.addSpot(spot);

            em.getTransaction().begin();
            em.persist(spot);
            em.merge(timeline);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return spotDTO;
    }

    //YES
    public TimelineDTO createTimeline(TimelineDTO timelineDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            User user = em.find(User.class, timelineDTO.getUsername());
            if (user == null) {
                throw new NotFoundException("No user with this name exists");
            }
            Timeline timeline = new Timeline(timelineDTO.getDescription(), user, timelineDTO.getStartDate(),timelineDTO.getEndDate(),timelineDTO.getName());
            user.addTimeline(timeline);
            em.getTransaction().begin();
            em.merge(user);
            em.persist(timeline);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return timelineDTO;
    }
//YES
    public List<TimelineDTO> getAllTimelines() throws EntityNotFoundException {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Timeline> typedQueryUser
                = em.createQuery("SELECT t FROM Timeline t", Timeline.class);
        List<Timeline> timelineList = typedQueryUser.getResultList();

        List<TimelineDTO> timelineDTOS = new ArrayList<>();
        for (Timeline t : timelineList) {
            timelineDTOS.add(new TimelineDTO(t));
        }
        return timelineDTOS;
    }









}

