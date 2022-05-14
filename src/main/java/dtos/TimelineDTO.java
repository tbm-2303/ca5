package dtos;
import entities.Timeline;
import entities.User;

import java.util.ArrayList;
import java.util.List;

public class TimelineDTO {
    private Long id;
    private String description;
    private String username;

    public TimelineDTO() {
    }

    public TimelineDTO(Long id, String description, String username) {
        this.id = id;
        this.description = description;
        this.username = username;
    }

    public TimelineDTO(Timeline timeline) {
        this.id = timeline.getId();
        this.description = timeline.getDescription();
        this.username = timeline.getUser().getUserName();
    }

    public static List<TimelineDTO> getDtos(List<Timeline> timelines) {
        List <TimelineDTO> timelineDTOS = new ArrayList<>();
        if (timelines != null){
            timelines.forEach(t -> timelineDTOS.add(new TimelineDTO(t)));
        }
        return timelineDTOS;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getUsername() { return username; }
    public void setUser(String username) { this.username = username; }

    @Override
    public String toString() {
        return "TimelineDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", user=" + username +
                '}';
    }
}