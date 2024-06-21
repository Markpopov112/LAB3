package jav.bot.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jav.bot.entity.Joke;
import jav.bot.entity.JokeCall;
import jav.bot.repository.JokeRepository;

@Service
public class JokeService {
    private final JokeRepository jokeRepository;

    @Autowired
    public JokeService(JokeRepository jokeRepository) {
        this.jokeRepository = jokeRepository;
    }

    public List<Joke> findAllJokes() {
        return this.jokeRepository.findAll();
    }

    public List<Joke> findTop5Jokes() {
        return this.jokeRepository.findTop5Jokes();
    }

    public Joke saveJoke(Joke joke) {
        joke.setCreatedAt(LocalDateTime.now());
        return this.jokeRepository.save(joke);
    }

    public Joke getRandomJoke() {
        return this.jokeRepository.findRandomJoke();
    }

    @Transactional
    public Joke getRandomJoke(Long userId) {
        Joke joke = jokeRepository.findRandomJoke();
        if (joke != null) {
            JokeCall jokeCall = new JokeCall();
            jokeCall.setJoke(joke);
            jokeCall.setCallTime(LocalDateTime.now());
            jokeCall.setUserId(userId);
            joke.getCalls().add(jokeCall);
            jokeRepository.save(joke);
        }
        return joke;
    }


    public Joke updateJoke(Long id, Joke joke) {
        return this.jokeRepository.findById(id)
                .map(existingJoke -> {
                    existingJoke.setText(joke.getText());
                    existingJoke.setUpdatedAt(LocalDateTime.now());
                    return this.jokeRepository.save(existingJoke);
                }).orElseThrow(() -> new IllegalArgumentException("Joke not found with id " + id));
    }

    public void deleteJoke(Long id) {
        this.jokeRepository.deleteById(id);
    }
}
