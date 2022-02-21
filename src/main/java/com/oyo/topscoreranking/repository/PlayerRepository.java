package com.oyo.topscoreranking.repository;

import java.util.List;

import com.oyo.topscoreranking.dto.PlayerDto;
import com.oyo.topscoreranking.dto.ScroreDto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

import com.oyo.topscoreranking.entity.Player;
import com.oyo.topscoreranking.dto.PlayerDto;

public interface PlayerRepository extends JpaRepository<Player,Integer>,JpaSpecificationExecutor<Player> {
  
    List<Player> findByPlayerIn(List<String> player);

    String deletePlayerById(int id);

    @Query("SELECT new com.oyo.topscoreranking.dto.ScroreDto(data.time,data.score) FROM Player data WHERE data.player= ?1")
    List<ScroreDto> findByPlayer(String player);
}
