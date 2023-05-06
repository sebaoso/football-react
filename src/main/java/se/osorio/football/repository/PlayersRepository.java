package se.osorio.football.repository;

import se.osorio.football.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayersRepository extends JpaRepository<PlayerEntity, Long> {
}
