package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.SpotDTO;
import dtos.TimelineDTO;
import facades.SpotFacade;
import utils.EMF_Creator;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

/**
 * @author lam@cphbusiness.dk
 */
@Path("spot")
public class SpotResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final SpotFacade FACADE = SpotFacade.getSpotFacade(EMF_Creator.createEntityManagerFactory());

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
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("spot/{id}")
    public String createSpot(String spot, String name, String description, Timestamp timeStamp, String locationID){
        SpotDTO spotDTO = GSON.fromJson(spot, SpotDTO.class);
        SpotDTO createdSpot = FACADE.createSpot(name, description, timeStamp, locationID);
        return GSON.toJson(createdSpot);
    }

    //YES
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("spots/{id}")
    public Response getSpotsFromTimeline(@PathParam("id") Long timeline_id) throws EntityNotFoundException {
        List<SpotDTO> spotDTOS = FACADE.getSpotsFromTimeline(timeline_id);
        return Response
                .ok()
                .entity(GSON.toJson(spotDTOS))
                .build();
    }

    //YES
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("test")
    public Response test() throws EntityNotFoundException {
        List<SpotDTO> spotDTOS = FACADE.test();
        return Response
                .ok()
                .entity(GSON.toJson(spotDTOS))
                .build();
    }

}
