package com.game.repository;

import com.game.entity.Player;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;


public interface PlayerRepositoryJPA extends CrudRepository<Player,Integer>,
        JpaSpecificationExecutor<Player> {

}