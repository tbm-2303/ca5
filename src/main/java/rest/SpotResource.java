package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.SpotDTO;
import facades.SpotFacade;
import utils.EMF_Creator;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

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
        return "{\"msg\":\"spot endpoint\"}";
    }


    //YES
    //create spot with location from db
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("spot/{id}")
    public String createSpot(@PathParam("id") Long timeline_id, String spot){
        SpotDTO spotDTO = GSON.fromJson(spot, SpotDTO.class);
        SpotDTO createdSpot = FACADE.createSpot2(spotDTO,timeline_id);
        return GSON.toJson(createdSpot);
    }
    //yes
    //create spot with new location
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("spot2/{id}")
    public String createSpot2(@PathParam("id") Long timeline_id, String spot){
        SpotDTO spotDTO = GSON.fromJson(spot, SpotDTO.class);
        SpotDTO createdSpot = FACADE.createSpot2(spotDTO,timeline_id);
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

    //test
    //virker men createspot længere oppe er ens med denne
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("spot2/{id}")
    public String createSpot2(@PathParam("id") Long timeline_id, String spot){
        SpotDTO spotDTO = GSON.fromJson(spot, SpotDTO.class);
        SpotDTO createdSpot = FACADE.createSpot2(spotDTO,timeline_id);
        return GSON.toJson(createdSpot);
    }

}
