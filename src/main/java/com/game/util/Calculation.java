package com.game.util;

public class Calculation {

    public static Integer levelCalc(Integer experience){
        return (int)Math.floor (((Math.sqrt(2500.+200.*experience))-50.)/100.);
    }

    public static Integer untilNextLevelCalc(Integer level,Integer experience){
        return (50*(level+1)*(level+2))-experience;

    }

}
