package com.oyo.topscoreranking.controller;

import java.util.*;
import javax.validation.Valid;
import java.text.ParseException;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.oyo.topscoreranking.service.ScroreService;
import com.oyo.topscoreranking.dto.PlayerDto;
import com.oyo.topscoreranking.dto.PlayerHistoryDto;

@Validated
@RestController
@RequestMapping("api/oyo/")
public class ScoreController {
    
    private static final String DATA_PATTERN = "yyyy-MM-dd";

    @Autowired
    private ScroreService scoreService;

    public ScoreController() {
      // TODO document why this constructor is empty
    }

    // Create a new Player
    @PostMapping("player")
    public ResponseEntity<PlayerDto> addPlayer(@Valid @RequestBody PlayerDto playerDto) throws ParseException {
        return ResponseEntity.ok().body(scoreService.addPlayer(playerDto));
    }

     // Delete a Player using id
     @DeleteMapping("player/delete/{id}")
     public ResponseEntity<String> deletePlayer(@Valid @PathVariable(value = "id") int id) {
         return ResponseEntity.ok().body( scoreService.deletePlayerById(id));
     }

    // Get a player by id
    @GetMapping("player/{id}")
    public ResponseEntity<PlayerDto> getPlayer(@PathVariable(value = "id") @Validated int id) {
        PlayerDto player = scoreService.getPlayerById(id);
        return ResponseEntity.ok().body(player);
    }

     //Get a History By name Player name
     @GetMapping("playerHistory/{player}")
     public ResponseEntity<PlayerHistoryDto> getPlayer(@PathVariable(value = "player") String player) {
         return ResponseEntity.ok().body(scoreService.getPlayerHistory(player));
     }

    //Get Score by Player Name, fromDate, toDate parameter
    @GetMapping(value = "playerList")
    public ResponseEntity<List<PlayerDto>> getList(
        @RequestParam(value = "name", required = false) Set<String> player,
            @RequestParam(value="fromDate",required = false)
            @DateTimeFormat(pattern = DATA_PATTERN) Date fromDate,
            @RequestParam(value="toDate",required = false)
            @DateTimeFormat(pattern = DATA_PATTERN) Date toDate,Pageable pageable)
    {
        List<PlayerDto> playerDataList = scoreService.getListPlayers(player, fromDate, toDate, pageable);
        return ResponseEntity.ok().body(playerDataList);
    }
}
