package dtos;

import entities.Role;
import entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    private String username;
    private String password;
    private List<Role> roleList;

    public UserDTO(){

    }
    public UserDTO(String userName, String password, String test){
        this.username = userName;
        this.password = password;

    }

    public UserDTO(User user){
        this.username = user.getUserName();
        this.password = user.getUserPass();
    }

    public static List<UserDTO> getDtos(List<User> user) {
        List <UserDTO> userDTOS = new ArrayList<>();
        if (user != null){
            user.forEach(u -> userDTOS.add(new UserDTO(u)));
        }
        return userDTOS;
    }
    
    public List<Role> getRoleList() { return roleList; }
    public void setRoleList(List<Role> roleList) { this.roleList = roleList; }
    public String getUserName() { return username; }
    public void setUserName(String userName) { this.username = userName; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }


    public String toString(){
        return "UserDTO{" +
                "username:" + username +
                ", password:" + password +
                "}";
    }
}