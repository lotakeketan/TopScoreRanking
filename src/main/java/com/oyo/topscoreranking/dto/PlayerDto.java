package com.oyo.topscoreranking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto {
    
    private Integer id;
    @Pattern(regexp = "^[A-Za-z]*$", message = "Player name must contain only letters")
    private String player;
    @Min(1)
    private int score;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;

    public PlayerDto(@Pattern(regexp = "^[A-Za-z]*$", message = "Player name must contain only letters") String player, @Min(1) int score, LocalDateTime time) {
        this.player = player;
        this.score = score;
        this.time = time;
    }

}
