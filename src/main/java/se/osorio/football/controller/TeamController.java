package se.osorio.football.controller;

import se.osorio.football.domain.Player;
import se.osorio.football.domain.Team;
import se.osorio.football.entity.PlayerEntity;
import se.osorio.football.entity.TeamEntity;
import se.osorio.football.repository.PlayersRepository;
import se.osorio.football.repository.TeamRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamRepository teamRepository;
    private final PlayersRepository playersRepository;

    public TeamController(TeamRepository teamRepository, PlayersRepository playersRepository) {
        this.teamRepository = teamRepository;
        this.playersRepository = playersRepository;
    }

    @GetMapping
    public List<Team> getTeams() {

        List<TeamEntity> teamEntities = teamRepository.findAll();

        List<Team> teams = teamEntities.stream().map
                        (teamEntity -> Team.builder()
                                .id(teamEntity.getId())
                                .name(teamEntity.getName())
                                .build())
                .collect(Collectors.toList());

        return teams;
    }

    @GetMapping("/{id}")
    public Team getTeam(@PathVariable Long id) {
        TeamEntity teamEntity = teamRepository.findById(id).orElseThrow(RuntimeException::new);

        Team team = Team.builder()
                .id(teamEntity.getId())
                .name(teamEntity.getName())
                .players(teamEntity.getPlayers().stream().map(playerEntity -> Player.builder()
                        .id(playerEntity.getId())
                        .name(playerEntity.getName())
                        .position(playerEntity.getPosition())
                        .club(playerEntity.getClub())
                        .team(playerEntity.getTeam().getId())
                        .build()).collect(Collectors.toList()))
                .build();

        return team;
    }

    @GetMapping("/{id}/players")
    public List<Player> getPlayersOfTeam(@PathVariable Long id) {
        System.out.println("getPlayersOfTeam");
        List<PlayerEntity> playerEntities = teamRepository.findById(id).orElseThrow(RuntimeException::new).getPlayers();

        List<Player> playerList = playerEntities.stream()
                .map(playerEntity -> Player.builder()
                        .id(playerEntity.getId())
                        .name(playerEntity.getName())
                        .position(playerEntity.getPosition())
                        .club(playerEntity.getClub())
                        .team(playerEntity.getTeam().getId())
                        .build())
                .collect(Collectors.toList());

        return playerList;
    }

    @PostMapping
    public ResponseEntity createTeam(@RequestBody Team team) throws URISyntaxException {

        TeamEntity teamEntity = TeamEntity.builder().id(team.getId()).name(team.getName()).build();

        TeamEntity savedTeamEntity = teamRepository.save(teamEntity);

        Team savedTeam = Team.builder()
                .id(savedTeamEntity.getId())
                .name(savedTeamEntity.getName())
                .build();

        return ResponseEntity.created(new URI("/teams/" + savedTeam.getId())).body(savedTeam);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateTeam(@PathVariable Long id, @RequestBody Team team) {
        TeamEntity teamEntity = teamRepository.findById(id).orElseThrow(RuntimeException::new);

        teamEntity.setName(team.getName());
        TeamEntity currentTeamEntity = teamRepository.save(teamEntity);

        Team currentTeam = Team.builder().id(currentTeamEntity.getId()).name(currentTeamEntity.getName()).build();

        return ResponseEntity.ok(currentTeam);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTeam(@PathVariable Long id) {
        teamRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{team}/players/{id}")
    public Player getPlayer(@PathVariable Long team, @PathVariable Long id) {

        System.out.println("TeamController.getPlayer: "+id);

        PlayerEntity playerEntity = playersRepository.findById(id).orElseThrow(RuntimeException::new);

        Player player = Player.builder().id(playerEntity.getId()).name(playerEntity.getName()).position(playerEntity.getPosition()).club(playerEntity.getClub()).team(playerEntity.getTeam().getId()).build();

        return player;
    }

    @PutMapping("/{team}/players/{id}")
    public ResponseEntity updatePlayer(@PathVariable Long team, @PathVariable Long id, @RequestBody Player player) {
        System.out.println("updatePlayer: "+player.getClub());
        TeamEntity teamEntity = teamRepository.findById(player.getTeam()).orElseThrow(RuntimeException::new);

        PlayerEntity currentPlayerEntity = teamEntity.getPlayers().stream()
                .filter(playerEntity -> playerEntity.getId() == player.getId())
                .findFirst().get();
        currentPlayerEntity.setName(player.getName());
        currentPlayerEntity.setPosition(player.getPosition());
        currentPlayerEntity.setClub(player.getClub());

        teamRepository.save(teamEntity);

        Player currentPlayer = Player.builder().id(currentPlayerEntity.getId()).name(currentPlayerEntity.getName()).position(currentPlayerEntity.getPosition()).club(currentPlayerEntity.getClub()).team(currentPlayerEntity.getTeam().getId()).build();
        System.out.println("currentPlayer.getClub(): "+currentPlayer.getClub());
        return ResponseEntity.ok(currentPlayer);
    }

    @PostMapping("/{team}/players")
    public ResponseEntity createPlayer(@PathVariable Long team, @RequestBody Player player) throws URISyntaxException {
        System.out.println("Player team id: "+team);

        TeamEntity teamEntity = teamRepository.findById(team).orElseThrow(RuntimeException::new);
        PlayerEntity playerEntity = PlayerEntity.builder().name(player.getName()).position(player.getPosition()).club(player.getClub()).team(teamEntity).build();
        PlayerEntity savedPlayerEntity = playersRepository.save(playerEntity);

        Player savedPlayer = Player.builder().id(savedPlayerEntity.getId()).name(savedPlayerEntity.getName()).position(savedPlayerEntity.getPosition()).club(savedPlayerEntity.getClub()).team(savedPlayerEntity.getTeam().getId()).build();

        return ResponseEntity.created(new URI("/teams/"+team+"/players/" + savedPlayer.getId())).body(savedPlayer);
    }

}
