package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.repository.PlayerDAO;
import com.game.repository.PlayerRepositoryJPA;
import com.game.repository.specs.PlayerSpecification;
import com.game.repository.specs.SearchCriteria;
import com.game.repository.specs.SearchOperation;
import com.game.util.Calculation;
import com.game.util.Validation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class PlayerServiceImpl implements PlayerService{

   @Autowired
   private PlayerDAO playerDAO;

   @Autowired
   public PlayerRepositoryJPA playerRepositoryJPA;

    public PlayerServiceImpl(PlayerRepositoryJPA playerRepositoryJPA) {
        this.playerRepositoryJPA = playerRepositoryJPA;
    }

   @Override
    public List<Player> getAllPlayers(String name, String title, String race, String profession,
                                      Long after, Long before, Boolean banned,
                                      Integer minExperience, Integer maxExperience,
                                      Integer minLevel, Integer maxLevel,
                                      PlayerOrder order,
                                      Integer pageNumber, Integer pageSize) {

        if(order==null)
        order=PlayerOrder.ID;

        if(pageNumber==null)pageNumber=0;

        if(pageSize==null)pageSize=3;

        PlayerSpecification spec= new PlayerSpecification();
        if(name!=null )
            spec.add(new SearchCriteria("name",name, SearchOperation.MATCH));
        if(title!=null )
            spec.add(new SearchCriteria("title",title, SearchOperation.MATCH));
        if(race!=null )
            spec.add(new SearchCriteria("race",race, SearchOperation.EQUAL));
        if(profession!=null )
            spec.add(new SearchCriteria("profession",profession, SearchOperation.EQUAL));
        if(minExperience!=null)
            spec.add(new SearchCriteria("experience",minExperience, SearchOperation.GREATER_THAN_EQUAL));
        if(maxExperience!=null)
            spec.add(new SearchCriteria("experience",maxExperience, SearchOperation.LESS_THAN_EQUAL));
        if(minLevel!=null)
            spec.add(new SearchCriteria("level",minLevel, SearchOperation.GREATER_THAN_EQUAL));
        if(maxLevel!=null)
            spec.add(new SearchCriteria("level",maxLevel, SearchOperation.LESS_THAN_EQUAL));
        if(banned!=null)
            spec.add(new SearchCriteria("banned",banned, SearchOperation.EQUAL));
        if(after!=null)
            spec.add(new SearchCriteria("birthday",after, SearchOperation.DATE_GREATER_THAN_EQUAL));
        if(before!=null)
            spec.add(new SearchCriteria("birthday",before, SearchOperation.DATE_LETTER_THAN_EQUAL));

        Pageable pageable=PageRequest.of(pageNumber,pageSize,Sort.by(order.getFieldName()));
        return playerRepositoryJPA.findAll(spec,pageable).toList();
    }

    @Override
    public List<Player> getAllPlayers(String name, String title, String race, String profession,
                                      Long after, Long before, Boolean banned,
                                      Integer minExperience, Integer maxExperience,
                                      Integer minLevel, Integer maxLevel) {

        PlayerSpecification spec= new PlayerSpecification();
        if(name!=null)
            spec.add(new SearchCriteria("name",name, SearchOperation.MATCH));
        if(title!=null)
            spec.add(new SearchCriteria("title",title, SearchOperation.MATCH));
        if(race!=null)
            spec.add(new SearchCriteria("race",race, SearchOperation.EQUAL));
        if(profession!=null)
            spec.add(new SearchCriteria("profession",profession, SearchOperation.EQUAL));
        if(minExperience!=null)
            spec.add(new SearchCriteria("experience",minExperience, SearchOperation.GREATER_THAN_EQUAL));
        if(maxExperience!=null)
            spec.add(new SearchCriteria("experience",maxExperience, SearchOperation.LESS_THAN_EQUAL));
        if(minLevel!=null)
            spec.add(new SearchCriteria("level",minLevel, SearchOperation.GREATER_THAN_EQUAL));
        if(maxLevel!=null)
            spec.add(new SearchCriteria("level",maxLevel, SearchOperation.LESS_THAN_EQUAL));
        if(banned!=null)
            spec.add(new SearchCriteria("banned",banned, SearchOperation.EQUAL));
        if(after!=null)
            spec.add(new SearchCriteria("birthday",after, SearchOperation.DATE_GREATER_THAN_EQUAL));
        if(before!=null)
            spec.add(new SearchCriteria("birthday",before, SearchOperation.DATE_LETTER_THAN_EQUAL));

        return playerRepositoryJPA.findAll(spec);
    }

    @Override
    public Player getPlayer(String id){
        if(Validation.idIsValid(id)){
            Player player = playerDAO.getPlayer(Integer.parseInt(id));
            if (player == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found");
            } else
                return player;
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id not valid");
        }
    }

    @Override
    public void savePlayer(Player player){
        playerDAO.savePlayer(player);
    }

    public boolean addPlayer(Player player){

        if(player.getBanned()==null)player.setBanned(false);

        if (Validation.playerIsValid(player)) {
                player.setLevel(Calculation.levelCalc(player.getExperience()));
                player.setUntilNextLevel(Calculation.untilNextLevelCalc(player.getLevel(),
                        player.getExperience()));
                playerDAO.savePlayer(player);
                return true;
            } else
                return false;
    }

    @Override
    public void deletePlayer(String id){
        if(Validation.idIsValid(id)){
            Player player = getPlayer(id);
            if (player == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found");
            } else
                playerDAO.deletePlayer(Integer.parseInt(id)); ;
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id not valid");
        }
     }

    @Override
    public Player updatePlayer(String id, Player player){
        Player playerUpdate;

        if(Validation.idIsValid(id)){
            playerUpdate = getPlayer(id);

            if (playerUpdate == null ) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found");
            } else {
               if( Validation.playerIsEmpty(player))
                    return playerUpdate;

                player.setId(Integer.parseInt(id));
                BeanUtils.copyProperties(player, playerUpdate,
                        Validation.getNullPropertyNames(player));

                playerUpdate.setLevel(Calculation.levelCalc(playerUpdate.getExperience()));
                playerUpdate.setUntilNextLevel(Calculation.untilNextLevelCalc(playerUpdate.getLevel(),
                        playerUpdate.getExperience()));
                if(Validation.playerValuesIsValid(playerUpdate)) {
                    savePlayer(playerUpdate);

                }
                else
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Values not valid");
            }
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id not valid");
        }
        return playerUpdate;
    }

}

