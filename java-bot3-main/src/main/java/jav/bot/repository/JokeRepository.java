package jav.bot.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import jav.bot.entity.Joke;

@Repository
public interface JokeRepository extends JpaRepository<Joke, Long> {
    @Query(value = "SELECT * FROM jokes ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Joke findRandomJoke();

    @Query(value = "SELECT j.*, COUNT(jc.joke_id) AS num_calls FROM jokes j LEFT JOIN joke_calls jc ON j.id = jc.joke_id GROUP BY j.id ORDER BY num_calls DESC LIMIT 5", nativeQuery = true)
    List<Joke> findTop5Jokes();
}
