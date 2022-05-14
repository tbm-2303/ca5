package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.TimelineDTO;
import dtos.UserDTO;
import entities.Role;
import entities.Timeline;
import entities.User;
import facades.TimelineFacade;
import facades.UserFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.Time;

/**
 * @author lam@cphbusiness.dk
 */
@Path("timeline")
public class TimelineResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final TimelineFacade FACADE = TimelineFacade.getTimelineFacade(EMF_Creator.createEntityManagerFactory());
    private static final UserFacade userfacade = UserFacade.getUserFacade(EMF_Creator.createEntityManagerFactory());

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String ping() {
        return "{\"msg\":\"timeline endpoint\"}";
    }


//YES
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("setup")
    public String setup(){
        EntityManager em = EMF.createEntityManager();
        UserDTO found = userfacade.getUserByName("timmy");//userDTO by name
        User user = em.find(User.class, found.getUserName());//user from db
        Timeline timeline = new Timeline("description",user);

        em.getTransaction().begin();
        em.persist(timeline);
        em.getTransaction().commit();
        return "{\"msg\":\"setup all good\"}";
    }
//YES
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("create")
    public String createTimeline(String timeline){
        TimelineDTO timelineDTO = GSON.fromJson(timeline, TimelineDTO.class);
        TimelineDTO createdTimeline = FACADE.createTimeline(timelineDTO);
        return GSON.toJson(createdTimeline);

    }

}
