package entities;

import javax.persistence.*;

@Entity
@Table(name = "Spot")
public class Spot {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "timeline_id")
    private Timeline timeline;


    public Spot(){

    }
    public Spot(String description ){
        this.description = description;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Timeline getTimeline() { return timeline; }
    public void setTimeline(Timeline timeline) { this.timeline = timeline; }
}
