package facades;

import dtos.RenameMeDTO;
import dtos.TimelineDTO;
import dtos.UserDTO;
import entities.RenameMe;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;

//import errorhandling.RenameMeNotFoundException;
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

    //YES
    public TimelineDTO createTimeline(TimelineDTO timelineDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            User user = em.find(User.class, timelineDTO.getUsername());
            if (user == null) {
                throw new NotFoundException("No user with this name exists");
            }
            Timeline timeline = new Timeline(timelineDTO.getDescription(), user);
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

