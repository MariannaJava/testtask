package com.game.controller;


import com.game.entity.Player;
import com.game.service.PlayerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class MyRestController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/players")
    public List<Player> showAllPlayers(@RequestParam (value = "name",required = false) String name,
                                       @RequestParam (value = "title",required = false) String title,
                                       @RequestParam (value = "race",required = false) String race,
                                       @RequestParam (value = "profession",required = false) String profession,
                                       @RequestParam (value = "after",required = false) Long after,
                                       @RequestParam (value = "before",required = false) Long before,
                                       @RequestParam (value = "banned",required = false) Boolean banned,
                                       @RequestParam (value = "minExperience",required = false) Integer minExperience,
                                       @RequestParam (value = "maxExperience",required = false) Integer maxExperience,
                                       @RequestParam (value = "minLevel",required = false) Integer minLevel,
                                       @RequestParam (value = "maxLevel",required = false) Integer maxLevel,
                                       @RequestParam (value = "order",required = false) PlayerOrder order,
                                       @RequestParam (value = "pageNumber",required = false) Integer pageNumber,
                                       @RequestParam (value = "pageSize",required = false) Integer pageSize) {

    return playerService.getAllPlayers(name, title, race, profession,
            after, before, banned, minExperience, maxExperience, minLevel, maxLevel,
            order, pageNumber, pageSize);
    }


@GetMapping("/players/count")
      public int playerCount(@RequestParam (value = "name",required = false) String name,
                                       @RequestParam (value = "title",required = false) String title,
                                       @RequestParam (value = "race",required = false) String race,
                                       @RequestParam (value = "profession",required = false) String profession,
                                       @RequestParam (value = "after",required = false) Long after,
                                       @RequestParam (value = "before",required = false) Long before,
                                       @RequestParam (value = "banned",required = false) Boolean banned,
                                       @RequestParam (value = "minExperience",required = false) Integer minExperience,
                                       @RequestParam (value = "maxExperience",required = false) Integer maxExperience,
                                       @RequestParam (value = "minLevel",required = false) Integer minLevel,
                                       @RequestParam (value = "maxLevel",required = false) Integer maxLevel){

        List<Player> allPlayers=playerService.getAllPlayers(name, title,race, profession,
                after, before, banned, minExperience, maxExperience,minLevel, maxLevel);
        return allPlayers.size();
    }

    @GetMapping("/players/{id}")
    public Player getPlayer(@PathVariable String id)  {

     return playerService.getPlayer(id);
    }


    @PostMapping("/players")
    public Player addNewPlayer(@RequestBody(required = false) Player player) {


        if(!playerService.addPlayer(player))
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Player not valid");
        else
        return player;
    }

    @PostMapping("/players/{id}")
    public Player updatePlayer(@PathVariable String id, @RequestBody(required = false)  Player player){

        return playerService.updatePlayer(id, player);
    }

        @DeleteMapping ("/players/{id}")
    public String deletePlayer(@PathVariable String id){
        playerService.deletePlayer(id);

        return "Player "+id+" was deleted!";
    }
}
