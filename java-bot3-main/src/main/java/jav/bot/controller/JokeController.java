package jav.bot.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jav.bot.entity.Joke;
import jav.bot.service.JokeService;

@RestController
@RequestMapping("/jokes")
public class JokeController {
    private final JokeService jokeService;

    @Autowired
    public JokeController(JokeService jokeService) {
        this.jokeService = jokeService;
    }

    @GetMapping
    public ResponseEntity<List<Joke>> getAllJokes() {
        List<Joke> jokes = jokeService.findAllJokes();
        return ResponseEntity.ok(jokes);
    }

    @PostMapping
    public ResponseEntity<Joke> createJoke(@RequestBody Joke joke) {
        Joke newJoke = jokeService.saveJoke(joke);
        return ResponseEntity.ok(newJoke);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Joke> updateJoke(@PathVariable Long id, @RequestBody Joke joke) {
        Joke updatedJoke = jokeService.updateJoke(id, joke);
        return ResponseEntity.ok(updatedJoke);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJoke(@PathVariable Long id) {
        jokeService.deleteJoke(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/top5")
    public ResponseEntity<List<Joke>> getTop5Jokes() {
        List<Joke> jokes = jokeService.findTop5Jokes();
        return ResponseEntity.ok(jokes);
    }

    @GetMapping("/random")
    public ResponseEntity<Joke> getRandomJoke() {
        return ResponseEntity.ok(jokeService.getRandomJoke());
    }
}
