package com.oyo.topscoreranking.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name="PLAYERDATA")
@Getter
@Setter
@NoArgsConstructor
public class Player implements Serializable {

    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Pattern(regexp="^[A-Za-z]*$",message = "Player name must contain only letters")
    private String player;
    @NonNull
    @Min(value=1, message="Score strictly superior to 0")
    private int score;

    @Column(nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;

    public Player(@NonNull String player, int score, LocalDateTime time) {
        this.player = player;
        this.score = score;
        this.time = time;
    }

    public Player(int id, @NonNull String player, int score, LocalDateTime time) {
        this.id = id;
        this.player = player;
        this.score = score;
        this.time = time;
    }
    
}
