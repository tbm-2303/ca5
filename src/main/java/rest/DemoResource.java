package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dtos.UserDTO;
import entities.Role;
import entities.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import facades.UserFacade;
import utils.EMF_Creator;
import utils.HttpUtils;

/**
 * @author lam@cphbusiness.dk
 */
@Path("info")
public class DemoResource {
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final UserFacade FACADE = UserFacade.getUserFacade(EMF_Creator.createEntityManagerFactory());

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    //YES
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("count")
    public String userCount() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery ("select u from User u",entities.User.class);
            List<User> users = query.getResultList();
            return "[" + users.size() + "]";
        } finally {
            em.close();
        }
    }

    //YES
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("setup")
    public String setup(){

        EntityManager em = EMF.createEntityManager();

        User user = new User("timmy", "timmy123", "test1");
        User admin = new User("james", "james123", "test2");
        User both = new User("kent", "kent123", "test3");

        em.getTransaction().begin();
        Role userRole = new Role("user");
        Role adminRole = new Role("admin");
        user.addRole(userRole);
        admin.addRole(adminRole);
        both.addRole(userRole);
        both.addRole(adminRole);
        em.persist(userRole);
        em.persist(adminRole);
        em.persist(user);
        em.persist(admin);
        em.persist(both);
        em.getTransaction().commit();
        return "{\"msg\":\"setup all good\"}";
    }

    //YES
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/allusers")
    public Response getAllUsers() throws EntityNotFoundException {
        List<UserDTO> userDTOS = FACADE.getAllUsers();
        return Response
                .ok()
                .entity(GSON.toJson(userDTOS))
                .build();
    }

    //YES
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("allUserNames")
    public String getAllUsersNames() throws EntityNotFoundException {
        List<String> usernames = FACADE.getAllUsernames();
        StringBuilder sb = new StringBuilder();
        for (String username : usernames) {
            sb.append(username).append("\n");
        }
        return sb.toString();
    }

    //YES
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("UserByName/{username}")
    public Response getUserByName(@PathParam("username") String username) throws EntityNotFoundException {
      UserDTO user = FACADE.getUserByName(username);
        return  Response.ok()
                .entity(GSON.toJson(user))
                .build();
    }

    //YES
    //update user
    @Path("{username}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("username") String username, String user) {
        UserDTO userDTO = GSON.fromJson(user, UserDTO.class);
        UserDTO updated = FACADE.update(userDTO);
        return Response.ok().entity(GSON.toJson(updated)).build();
    }























    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("catfactx")
    public Response test() throws IOException {
       return Response.ok().entity(GSON.toJson(FACADE.getRandomCatFact())).build();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("randomjokex")
    public Response test2() {
        return Response.ok().entity(GSON.toJson(FACADE.getRandomJoke())).build();
    }
//d

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }
}