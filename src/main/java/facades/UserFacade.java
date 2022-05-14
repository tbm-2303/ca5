package facades;

import com.google.gson.JsonObject;
import entities.Role;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;

import security.errorhandling.AuthenticationException;
import utils.Utility;

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

    public JsonObject getRandomCatFact() {
        return Utility.fetchData("https://catfact.ninja/fact");
    }

    public JsonObject getRandomJoke() {
        return Utility.fetchData("https://api.chucknorris.io/jokes/random");
    }

    //todo: make more facade methods for persisting and updating data.
    public User create(User user) {
        EntityManager em = getEntityManager();
        Role role = em.find(Role.class, "user");
        user.addRole(role);
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
        return user;
    }


    public List<User> getAllUsers() throws EntityNotFoundException {
        EntityManager em = emf.createEntityManager();
        TypedQuery<User> typedQueryUser
                = em.createQuery("SELECT u FROM User u", User.class);
        List<User> userList = typedQueryUser.getResultList();
        return userList;
    }




}
