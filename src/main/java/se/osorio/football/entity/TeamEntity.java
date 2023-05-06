package se.osorio.football.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "team")
@Builder
@Data
@AllArgsConstructor
public class TeamEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(fetch = EAGER, mappedBy = "team")
    @JsonIgnore
    private List<PlayerEntity> players = new ArrayList<>();

    public TeamEntity() {
    }

    public TeamEntity(String name) {
        this.name = name;
    }

    public TeamEntity(Long id, String name) {
        this.id = id;
        this.name = name;

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

    public List<PlayerEntity> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerEntity> players) {
        this.players = players;
    }
}
