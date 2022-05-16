package entities;

import dtos.LocationDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Location")
@NamedQueries(@NamedQuery(name = "Location.deleteAllRows", query = "DELETE FROM Location "))
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @OneToMany(mappedBy = "location", cascade = {CascadeType.PERSIST})
    private List<Spot> spotList = new ArrayList<>();

    public Location() {
    }

    public Location(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Location(LocationDTO locationDTO) {
        this.id = locationDTO.getId();
        this.name = locationDTO.getName();
        this.type = locationDTO.getType();
    }

    public void addSpot(Spot spot){
        this.spotList.add(spot);
        spot.setLocation(this);
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public List<Spot> getSpotList() { return spotList; }
    public void setSpotList(List<Spot> spotList) { this.spotList = spotList; }

    @Override
    public String toString() {
        return "Location{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
