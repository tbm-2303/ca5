package entities;

import dtos.LocationDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Location")
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "wikiId")
    private String wikiId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @OneToMany(mappedBy = "location", cascade = {CascadeType.PERSIST})
    private List<Spot> spotList = new ArrayList<>();

    public Location() {
    }

    public Location(String wikiId, String name, String type) {
        this.wikiId = wikiId;
        this.name = name;
        this.type = type;
    }

    public Location(LocationDTO locationDTO) {
        this.id = locationDTO.getId();
        this.wikiId = locationDTO.getWikiId();
        this.name = locationDTO.getName();
        this.type = locationDTO.getType();
    }

    public void addSpot(Spot spot){
        this.spotList.add(spot);
        spot.setLocation(this);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getWikiId() { return wikiId; }
    public void setWikiId(String wikiId) { this.wikiId = wikiId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public List<Spot> getSpotList() { return spotList; }
    public void setSpotList(List<Spot> spotList) { this.spotList = spotList; }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
