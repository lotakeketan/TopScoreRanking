package com.oyo.topscoreranking.service;

import com.oyo.topscoreranking.dto.PlayerDto;
import com.oyo.topscoreranking.dto.PlayerHistoryDto;

import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ScroreService 
{
    
    PlayerDto addPlayer(PlayerDto playerDto) throws ParseException;

    PlayerDto getPlayerById(int id);

    String deletePlayerById(int id);

    PlayerHistoryDto getPlayerHistory(String player);

    List<PlayerDto> getListPlayers(Set<String> player, Date fromDate, Date toDate, Pageable pageable);
}
