package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name ="Timeline")
public class Timeline implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "id")
    private Long id;


    @Basic(optional = false)
    @NotNull
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;


    public Timeline() {}

    public Timeline(String description, User user){
        this.description = description;
        this.user = user;
    }

}
