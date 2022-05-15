package dtos;

import entities.Spot;
import java.util.ArrayList;
import java.util.List;

public class SpotDTO {
    private Long id;
    private String description;

    public SpotDTO() {
    }

    public SpotDTO(Spot spot) {
        this.id = spot.getId();
        this.description = spot.getDescription();
    }

    public static List<SpotDTO> getDTOS(List<Spot> spot) {
        List<SpotDTO> spotDTOS = new ArrayList<>();
        if (spot != null) {
            spot.forEach(s -> spotDTOS.add(new SpotDTO(s)));
        }
        return spotDTOS;
    }

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