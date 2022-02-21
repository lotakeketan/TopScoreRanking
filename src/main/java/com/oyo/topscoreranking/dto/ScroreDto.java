package com.oyo.topscoreranking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ScroreDto {
    @JsonFormat(pattern="HH:mm:ss")
    private LocalDateTime time;
    private int score;

    public ScroreDto(LocalDateTime time, int score) {
        this.time = time;
        this.score = score;
    }

}
