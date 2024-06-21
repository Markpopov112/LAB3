package jav.bot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "joke_call"
)
public class JokeCall {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "call_seq"
    )
    @SequenceGenerator(
            name = "call_seq",
            sequenceName = "call_seq",
            allocationSize = 1
    )
    private Long id;
    @Column(
            name = "call_time"
    )
    private LocalDateTime callTime;
    @Column(
            name = "user_id"
    )
    private Long userId;
    @ManyToOne
    @JoinColumn(
            name = "joke_id"
    )
    private Joke joke;

    public JokeCall() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCallTime() {
        return this.callTime;
    }

    public void setCallTime(LocalDateTime callTime) {
        this.callTime = callTime;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Joke getJoke() {
        return this.joke;
    }

    public void setJoke(Joke joke) {
        this.joke = joke;
    }
}
