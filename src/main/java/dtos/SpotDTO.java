package dtos;

import entities.Spot;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SpotDTO {
    private Long id;
    private String description;
    private String name;
    private TimelineDTO timelineDTO;
    private String country;

    private Timestamp timestamp;

    public SpotDTO() {
    }

    public SpotDTO(Spot spot) {
        this.id = spot.getId();
        this.description = spot.getDescription();
        this.name = spot.getName();
        this.country = spot.getLocation().getName();
    }

    public static List<SpotDTO> getDTOS(List<Spot> spot) {
        List<SpotDTO> spotDTOS = new ArrayList<>();
        if (spot != null) {
            spot.forEach(s -> spotDTOS.add(new SpotDTO(s)));
        }
        return spotDTOS;
    }

    public String getCountry() {return country; }
    public void setCountry(String country) { this.country = country; }
    public TimelineDTO getTimelineDTO() { return timelineDTO; }
    public void setTimelineDTO(TimelineDTO timelineDTO) { this.timelineDTO = timelineDTO; }
    public Timestamp getTimestamp() { return timestamp; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "SpotDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}