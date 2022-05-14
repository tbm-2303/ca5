package dtos;

import entities.Role;
import entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    private String userName;
    private String password;
    private List<Role> roleList;
    private String test;

    public UserDTO(){

    }
    public UserDTO(String userName, String password, String test){
        this.userName = userName;
        this.password = password;
        this.test = test;
    }

    public UserDTO(User user){
        this.userName = user.getUserName();
        this.password = user.getUserPass();
        this.test = user.getTest();
    }

    public static List<UserDTO> getDtos(List<User> user) {
        List <UserDTO> userDTOS = new ArrayList<>();
        if (user != null){
            user.forEach(u -> userDTOS.add(new UserDTO(u)));
        }
        return userDTOS;
    }

    public String getTest() { return test; }
    public void setTest(String test) { this.test = test; }
    public List<Role> getRoleList() { return roleList; }
    public void setRoleList(List<Role> roleList) { this.roleList = roleList; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }


    public String toString(){
        return "UserDTO{" +
                "username:" + userName +
                ", password:" + password +
                "}";
    }
}