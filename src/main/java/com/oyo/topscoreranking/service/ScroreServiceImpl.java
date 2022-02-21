package com.oyo.topscoreranking.service;

import java.sql.Timestamp;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import com.oyo.topscoreranking.dto.PlayerDto;
import com.oyo.topscoreranking.dto.PlayerHistoryDto;
import com.oyo.topscoreranking.dto.ScroreDto;
import com.oyo.topscoreranking.entity.Player;
import com.oyo.topscoreranking.repository.PlayerRepository;
import com.oyo.topscoreranking.exception.BusinessException;
import static com.oyo.topscoreranking.helper.Converter.convertToPlayerDTO;
import static com.oyo.topscoreranking.helper.Converter.convertToPlayerEntity;
import static com.oyo.topscoreranking.helper.Converter.convertToPlayerDTOList;



//import static com.oyo.topscoreranking.GameConverter.convertToGame;
//import static com.oyo.topscoreranking.GameConverter.convertToGameDTO;

@Service
public class ScroreServiceImpl implements ScroreService {

    @Autowired
    private PlayerRepository playerRepository;


    @Override
    public PlayerDto addPlayer(PlayerDto playerDto) 
    {
        return convertToPlayerDTO(playerRepository.save(convertToPlayerEntity(playerDto)));
    }

    @Override
    public String deletePlayerById(int id)
    {
        playerRepository.deleteById(id);
        return "Player ID = " + id + " removed. !! ";
    }

    @Override
    public PlayerDto getPlayerById(int id) {
        Optional<Player> playerOptional = playerRepository.findById(id);
        PlayerDto playerDto = new PlayerDto();
        if (playerOptional.isPresent())
            playerDto = convertToPlayerDTO(playerOptional.get());
        return playerDto;
    }

    @Override
    public PlayerHistoryDto getPlayerHistory(String player) {
        List<ScroreDto> scoreDtoList = playerRepository.findByPlayer(player);
        if (!scoreDtoList.isEmpty())
            return new PlayerHistoryDto(scoreDtoList.stream()
                    .mapToDouble(ScroreDto::getScore)
                    .average()
                    .orElse(Double.NaN),
                    Collections.min(scoreDtoList, Comparator.comparing(ScroreDto::getScore)),
                    Collections.max(scoreDtoList, Comparator.comparing(ScroreDto::getScore)),
                    scoreDtoList);

        return new PlayerHistoryDto();
    }

    @Override
    public List<PlayerDto> getListPlayers(Set<String> player, Date fromDate, Date toDate, Pageable pageable)
    {
     
        if(Objects.nonNull(fromDate) && Objects.nonNull(toDate) && fromDate.after(toDate))
        {
            throw new BusinessException("001","Input correct fromDate and toDate values");
        }
        List<Player> playerList= playerRepository.findAll(new Specification<Player>() {
           @Override
           public Predicate toPredicate(Root<Player> root,CriteriaQuery<?> criteriaQuery,CriteriaBuilder criteriaBuilder )
           {
               Predicate predicate = criteriaBuilder.conjunction(); 
              if(Objects.nonNull(fromDate))
              {
                predicate=criteriaBuilder.and(predicate,criteriaBuilder.greaterThanOrEqualTo
                (root.get("time"), new Timestamp(fromDate.getTime()).toLocalDateTime()));                    
               }
               if(Objects.nonNull(toDate))
              {
                predicate=criteriaBuilder.and(predicate,criteriaBuilder.lessThanOrEqualTo
                (root.get("time"), new Timestamp(toDate.getTime()).toLocalDateTime()));                    
               }
               if(!StringUtils.isEmpty(player))
               {
                   predicate=criteriaBuilder.and(predicate,root.get("player").in(player));
               }          
               criteriaQuery.orderBy(criteriaBuilder.desc(root.get("time")));
               return predicate;
            }
       },pageable).getContent();
       return convertToPlayerDTOList(playerList);
    }
    
}
