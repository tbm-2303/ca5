package dtos;
import entities.Spot;
import entities.Timeline;
import entities.User;

import java.util.ArrayList;
import java.util.List;

public class TimelineDTO {
    private Long id;
    private String description;
    private String username;
    private String name;
    private String startDate;
    private String endDate;
    private List<SpotDTO> spotList = new ArrayList<>();

    public TimelineDTO() {
    }

    public TimelineDTO(Long id, String description, String username, String name, String startDate, String endDate) {
        this.id = id;
        this.description = description;
        this.username = username;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public TimelineDTO(Timeline timeline) {
        this.id = timeline.getId();
        this.description = timeline.getDescription();
        this.username = timeline.getUser().getUserName();
        this.name = timeline.getName();
        this.startDate = timeline.getStartDate();
        this.endDate = timeline.getEndDate();
        for (Spot spot : timeline.getSpotList()) {
            this.spotList.add((new SpotDTO(spot)));
        }
    }

    public static List<TimelineDTO> getDtos(List<Timeline> timelines) {
        List <TimelineDTO> timelineDTOS = new ArrayList<>();
        if (timelines != null){
            timelines.forEach(t -> timelineDTOS.add(new TimelineDTO(t)));
        }
        return timelineDTOS;
    }

    public List<SpotDTO> getSpotList() { return spotList; }
    public void setSpotList(List<SpotDTO> spotList) { this.spotList = spotList; }
    public void setUsername(String username) { this.username = username; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
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