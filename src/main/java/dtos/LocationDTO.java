package dtos;

import entities.Location;
import entities.Spot;
import entities.User;

import java.util.ArrayList;
import java.util.List;

public class LocationDTO {
    private Long id;
    private String wikiId;
    private String name;
    private String type;


    public LocationDTO() {
    }

    public LocationDTO(String name, String type, String wikiId) {
        this.name = name;
        this.type = type;
        this.wikiId = wikiId;
    }
    public LocationDTO(Long id, String name, String type, String wikiId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.wikiId = wikiId;
    }

    public LocationDTO(Location location) {
        if (location.getId() != null) {
            this.id = location.getId();
        }
        this.name = location.getName();
        this.type = location.getType();
        this.wikiId = location.getWikiId();
    }



    public static List<LocationDTO> getDtos(List<Location> locations) {
        List <LocationDTO> locationDTOS = new ArrayList<>();
        if (locations != null){
            locations.forEach(l -> locationDTOS.add(new LocationDTO(l)));
        }
        return locationDTOS;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getWikiId() { return wikiId; }
    public void setWikiId(String wikiId) { this.wikiId = wikiId;}
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }


    @Override
    public String toString() {
        return "LocationDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +

                '}';
    }
}