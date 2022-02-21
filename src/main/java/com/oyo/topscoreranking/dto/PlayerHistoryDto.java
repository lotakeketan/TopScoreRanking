package com.oyo.topscoreranking.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class PlayerHistoryDto {
    private Double average;
    private ScroreDto low;
    private ScroreDto top;
    private List<ScroreDto> scoreDtoList =new ArrayList<>();

    public PlayerHistoryDto(Double average, ScroreDto low, ScroreDto top, List<ScroreDto> scoreDtoList) {
        this.average = average;
        this.low = low;
        this.top = top;
        this.scoreDtoList = scoreDtoList;
    }
}