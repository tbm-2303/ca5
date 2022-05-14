package facades;

import dtos.RenameMeDTO;
import dtos.TimelineDTO;
import entities.RenameMe;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;

//import errorhandling.RenameMeNotFoundException;
import entities.Timeline;
import entities.User;
import utils.EMF_Creator;

/**
 *
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


    public TimelineDTO createTimeline(TimelineDTO timelineDTO){
        EntityManager em = emf.createEntityManager();
        try {
        User user = em.find(User.class, timelineDTO.getUsername());
            if (user == null) {
                throw new NotFoundException("No user with this name exists");
            }
        Timeline timeline = new Timeline(timelineDTO.getDescription(),user);
        em.getTransaction().begin();
        em.persist(timeline);
        em.getTransaction().commit();
        } finally {
            em.close();
        }
        return timelineDTO;
    }




}
       /*

        CityInfo cityInfo = new CityInfo(personDTO.getAddressDTO().getCityInfoDTO());
        Address address = new Address(personDTO.getAddressDTO());
        Person person = new Person(personDTO);
        cityInfo.addAddress(address);
        address.addPerson(person);
        for (PhoneDTO phoneDTO : personDTO.getPhoneList()) {
            person.addPhone(new Phone(phoneDTO));
        }
        try {
            em.getTransaction().begin();
            em.persist(cityInfo);
            em.persist(address);
            em.persist(person);
            for (Phone phone : person.getPhoneList()) {
                em.persist(phone);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }


         */

