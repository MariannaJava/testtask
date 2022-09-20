package com.game.util;

import com.game.entity.Player;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Validation {



    public static boolean idIsValid(String str){
        Double id;

        try {
            id= Double.parseDouble(str);
        }
        catch(Exception e){
            return false;
        }

        if(id<=0 || id%1!=0){
            return false;
        }

        return true;
    }
    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static boolean playerIsValid(Player player){
        if(player.getName()==null||
                player.getTitle()==null||
                player.getRace()==null||
                player.getProfession()==null||
                player.getExperience()==null||
                player.getBirthday()==null)
            return false;
           if(  player.getName().length()>12||
                player.getTitle().length()>30||
                player.getName().isEmpty() ||
                player.getBirthday().getTime()<new Date(100,0,1).getTime()||
                player.getBirthday().getTime()>new Date(1100,0,1).getTime()||
                player.getBirthday().getTime()<0||
                player.getExperience()>10000000||
                player.getExperience()<0)
            return false;

    return true;
    }

    public static boolean playerValuesIsValid(Player player){

        if(  player.getName().length()>12||
                player.getTitle().length()>30||
                player.getName().isEmpty() ||
                player.getBirthday().getTime()<new Date(100,0,1).getTime()||
                player.getBirthday().getTime()>new Date(1100,0,1).getTime()||
                player.getBirthday().getTime()<0||
                player.getExperience()>10000000||
                player.getExperience()<0)
            return false;

        return true;
    }
    public static boolean playerIsEmpty(Player player){
        return player.getName() == null &&
                player.getTitle() == null &&
                player.getRace() == null &&
                player.getProfession() == null &&
                player.getExperience() == null &&
                player.getBirthday() == null &&
                player.getBanned() == null &&
                player.getLevel() == null &&
                player.getUntilNextLevel() == null;
    }

}