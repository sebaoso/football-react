package se.osorio.football.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class Player {

    private Long id;
    private String name;
    private String position;
    private String club;
    private Long team;

    public Player() {
    }

    public Player(String name, String position, String club, Long team) {
        this.name = name;
        this.position = position;
        this.club = club;
        this.team = team;
    }

    public Player(Long id, String name, String position, String club) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.club = club;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public Long getTeam() {
        return team;
    }

    public void setTeam(Long team) {
        this.team = team;
    }
}
