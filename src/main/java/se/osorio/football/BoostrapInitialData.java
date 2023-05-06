package se.osorio.football;

import se.osorio.football.entity.PlayerEntity;
import se.osorio.football.entity.TeamEntity;
import se.osorio.football.repository.PlayersRepository;
import se.osorio.football.repository.TeamRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BoostrapInitialData implements CommandLineRunner {

    private final PlayersRepository playerRepository;
    private final TeamRepository teamRepository;

    public BoostrapInitialData(PlayersRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public void run(String... args) {
        TeamEntity teamEntity = new TeamEntity("Argentina");
        teamRepository.save(teamEntity);

        playerRepository.save(new PlayerEntity("Lionel Messi", "RW", "PSG", teamEntity));
        playerRepository.save(new PlayerEntity("Emiliano Martinez", "GK", "Aston Villa", teamEntity));
        playerRepository.save(new PlayerEntity("Julian Alvarez", "CF", "Manchester City", teamEntity));

    }
}
