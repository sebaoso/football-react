package se.osorio.football.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "player")
@Builder
@Data
@AllArgsConstructor
public class PlayerEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String position;
    private String club;

    @ManyToOne
    @JoinColumn(name ="team_id", nullable = false)
    private TeamEntity team;

    public PlayerEntity() {
    }

    public PlayerEntity(String name, String position, String club, TeamEntity team) {
        this.name = name;
        this.position = position;
        this.club = club;
        this.team = team;
    }

    public PlayerEntity(Long id, String name, String position, String club) {
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

    public TeamEntity getTeam() {
        return team;
    }

    public void setTeam(TeamEntity team) {
        this.team = team;
    }
}
