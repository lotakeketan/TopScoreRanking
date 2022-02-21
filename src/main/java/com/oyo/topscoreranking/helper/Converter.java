package com.oyo.topscoreranking.helper;

import com.oyo.topscoreranking.dto.PlayerDto;
import com.oyo.topscoreranking.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class Converter {
    
    private Converter() {
    }

    public static PlayerDto convertToPlayerDTO(Player player) {
        return PlayerDto.builder()
                .id(player.getId())
                .player(player.getPlayer())
                .score(player.getScore())
                .time(player.getTime())
                .build();
    }

    public static List<PlayerDto> convertToPlayerDTOList(List<Player> player) {
        return player.stream().map(s ->
               PlayerDto.builder()
                        .id(s.getId())
                        .player(s.getPlayer())
                        .score(s.getScore())
                        .time(s.getTime())
                        .build()
        ).collect(Collectors.toList());
    }

    public static Player convertToPlayerEntity(PlayerDto playerDto) {
        Player player = new Player();
        player.setId(playerDto.getId());
        player.setPlayer(playerDto.getPlayer());
        player.setScore(playerDto.getScore());
        player.setTime(playerDto.getTime());
        return player;
    }
}
