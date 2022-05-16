package entities;

import dtos.TimelineDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="Timeline")
@NamedQueries(@NamedQuery(name = "Timeline.deleteAllRows", query = "DELETE FROM Timeline"))
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

    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "startDate", nullable = false, length = 30)
    private String startDate;
    @Column(name = "endDate", nullable = false, length = 30)
    private String endDate;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    @OneToMany(mappedBy = "timeline", cascade = {CascadeType.PERSIST})
    private List<Spot> spotList = new ArrayList<>();

    public Timeline() {}

    public Timeline(String description, User user, String startDate, String endDate, String name){
        this.description = description;
        this.user = user;
        this.endDate = endDate;
        this.startDate = startDate;
        this.name = name;
    }

    public Timeline(TimelineDTO dto){
        if (dto.getId() != null) {
            this.id = dto.getId();
        }
        this.description = dto.getDescription();
        this.endDate = dto.getEndDate();
        this.startDate = dto.getStartDate();
        this.name = dto.getName();
    }

    public void addSpot(Spot spot){
        spotList.add(spot);
        spot.setTimeline(this);
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
    public List<Spot> getSpotList() { return spotList; }
    public void setSpotList(List<Spot> spotList) { this.spotList = spotList; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
