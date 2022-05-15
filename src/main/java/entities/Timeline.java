package entities;

import dtos.TimelineDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "timeline", cascade = {CascadeType.PERSIST})
    private List<Spot> spotList = new ArrayList<>();



    public Timeline() {}

    public Timeline(String description, User user){
        this.description = description;
        this.user = user;
    }

    public Timeline(TimelineDTO dto){
        if (dto.getId() != null) {
            this.id = dto.getId();
        }
        this.description = dto.getDescription();
    }

    public void addSpot(Spot spot){
        spotList.add(spot);
        spot.setTimeline(this);
    }
    public List<Spot> getSpotList() { return spotList; }
    public void setSpotList(List<Spot> spotList) { this.spotList = spotList; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
