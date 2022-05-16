package entities;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "Spot")
@NamedQueries(@NamedQuery(name = "Spot.deleteAllRows", query = "DELETE FROM Spot"))
public class Spot {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @ManyToOne
    @JoinColumn(name = "timeline_id")
    private Timeline timeline;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    public Spot(){

    }
    public Spot(String description, String name, Timestamp timestamp){
        this.description = description;
        this.name = name;
        this.timestamp = timestamp;
    }
    public Spot(String description, String name, Timestamp timestamp, Location location){
        this.description = description;
        this.name = name;
        this.timestamp = timestamp;
    }


    public Timestamp getTimeStamp() { return timestamp; }
    public void setTimeStamp(Timestamp timeStamp) { this.timestamp = timestamp; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Timeline getTimeline() { return timeline; }
    public void setTimeline(Timeline timeline) { this.timeline = timeline; }
    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
}
