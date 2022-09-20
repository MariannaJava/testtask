package com.game.repository;

import com.game.entity.Player;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PlayerDAOImpl implements PlayerDAO {
    //@Autowired


    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Player getPlayer(int id){
        Session session=entityManager.unwrap(Session.class);
        Player player=session.get(Player.class,id);
        return player;
    }

    @Override
    public void savePlayer(Player player){

        Session session=entityManager.unwrap(Session.class);
        session.saveOrUpdate(player);

    }

    @Override
    public void deletePlayer(int id){
        Session session=entityManager.unwrap(Session.class);
        Player player=session.get(Player.class,id);
        session.delete(player);

    }
}
