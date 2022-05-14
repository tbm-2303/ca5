package facades;

import com.google.gson.JsonObject;
import dtos.UserDTO;
import entities.Role;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;

import security.errorhandling.AuthenticationException;
import utils.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lam@cphbusiness.dk
 */
public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }
    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public List<UserDTO> getAllUsers() throws EntityNotFoundException {
        EntityManager em = emf.createEntityManager();
        TypedQuery<User> typedQueryUser
                = em.createQuery("SELECT u FROM User u", User.class);
        List<User> userList = typedQueryUser.getResultList();

        List<UserDTO> userDTOS = new ArrayList<>();
        for (User u : userList) {
            userDTOS.add(new UserDTO(u));
        }
        return userDTOS;
    }

    public List<String> getAllUsernames() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
        List<String> usernames = new ArrayList<>();
        for (User u : query.getResultList()) {
            usernames.add(u.getUserName());
        }
        return usernames;
    }

    public UserDTO getUserByName(String username) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null) {
                throw new NotFoundException("No user with this name exists");
            }
        } finally {
            em.close();
        }
        return new UserDTO(user);
    }








































    public JsonObject getRandomCatFact() {
        return Utility.fetchData("https://catfact.ninja/fact");
    }

    public JsonObject getRandomJoke() {
        return Utility.fetchData("https://api.chucknorris.io/jokes/random");
    }

    public boolean usernameExists(String username) {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
        } finally {
            em.close();
        }
        return user != null;
    }

}
