package facades;

import dtos.SpotDTO;
import dtos.TimelineDTO;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import entities.Spot;
import entities.Timeline;
import entities.User;

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
    //YES
    public List<TimelineDTO> getAllTimelinesFromUser(String username){
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Timeline> query = em.createQuery("SELECT t FROM Timeline t WHERE t.user.userName = :username", Timeline.class);
            query.setParameter("username", username);
            List<Timeline> timelines = query.getResultList();
            List<TimelineDTO> timelineDTOS = new ArrayList<>();
            for (Timeline tl : timelines) {
                timelineDTOS.add(new TimelineDTO(tl));
            }
            return timelineDTOS;

        }finally {
            em.close();
        }
    }

    public TimelineDTO getTimeline(Long id){
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Timeline> query = em.createQuery("SELECT t FROM Timeline t WHERE t.id = :id", Timeline.class);
            query.setParameter("id", id);
            Timeline timeline = query.getSingleResult();
            return new TimelineDTO(timeline);
        }
        finally {
            em.close();
        }
    }

}

