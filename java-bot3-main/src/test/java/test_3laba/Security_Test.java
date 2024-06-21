package test_3laba;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import jav.bot.JavaBot2Application;
import jav.bot.entity.Joke;
import jav.bot.repository.JokeRepository;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = JavaBot2Application.class)
@TestPropertySource(locations = "classpath:application.properties")
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class Security_Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JokeRepository jokeRepository;

    @BeforeEach
    public void setup() {
        jokeRepository.deleteAll();
        Joke joke = new Joke();
        joke.setText("This is a test joke.");
        jokeRepository.save(joke);
    }

    @Test
    public void testAccessRegisterWithoutAuth() throws Exception {
        // Проверяет доступ к /register без авторизации
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testAccessJokesWithUserRole() throws Exception {
        // Проверяет доступ к /jokes с ролью USER
        mockMvc.perform(get("/jokes"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void testAccessJokesWithModeratorRole() throws Exception {
        // Проверяет доступ к /jokes с ролью MODERATOR
        mockMvc.perform(get("/jokes"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void testModifyJokesWithModeratorRole() throws Exception {
        // Проверяет возможность изменения шутки с ролью MODERATOR
        Optional<Joke> jokeOptional = jokeRepository.findAll().stream().findFirst();
        if (jokeOptional.isPresent()) {
            Long jokeId = jokeOptional.get().getId();
            mockMvc.perform(put("/jokes/" + jokeId)
                            .content("{\"text\": \"This is an updated test joke.\"}")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } else {
            throw new IllegalStateException("Joke not found in setup");
        }
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void testDeleteJokesWithModeratorRole() throws Exception {
        // Проверяет возможность удаления шутки с ролью MODERATOR
        Optional<Joke> jokeOptional = jokeRepository.findAll().stream().findFirst();
        if (jokeOptional.isPresent()) {
            Long jokeId = jokeOptional.get().getId();
            mockMvc.perform(delete("/jokes/" + jokeId))
                    .andExpect(status().isOk());
        } else {
            throw new IllegalStateException("Joke not found in setup");
        }
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testDeleteJokesWithUserRole() throws Exception {
        // Проверяет, что пользователь с ролью USER не может удалять шутки
        Optional<Joke> jokeOptional = jokeRepository.findAll().stream().findFirst();
        if (jokeOptional.isPresent()) {
            Long jokeId = jokeOptional.get().getId();
            mockMvc.perform(delete("/jokes/" + jokeId))
                    .andExpect(status().isForbidden());
        } else {
            throw new IllegalStateException("Joke not found in setup");
        }
    }
}
