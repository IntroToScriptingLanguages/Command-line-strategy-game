/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 *  Program written by Mark Sheehan
 *  02/24/14
 *  CS 101 Section 2
 */
package youcantrunforever_test;

/**
 *
 * @author PSTogether
 */
public class Player_Marine extends Player
{
    public Player_Marine(String name, int distanceFromWall, String victoryMessage)
    {
        super(90, 10, new Gun_Marine_Rifle(), name, distanceFromWall, victoryMessage);
        description = "Marines are the backbone of the human resistance.  Equipped with state-of-the-art burst rifles and armor, they hit hard and are hard to kill, serving as superb meat shields.";
    }
    
    @Override
    public void Attack(Enemy e) {
        int range = (Math.abs(getDistance(this)-getDistance(e)));
        gun.shoot(e, gun.getAccuracy()*(25-range));
    }

    @Override public void takeAction(Player[] players, Enemy[] enemies, char action)
    {
        //put all logic here
        
        if (action == 'a') //ATTACK!!!!
        {
            if(gun.isEmpty())
            {   
                System.out.println(name+"'s weapon is empty!");
                gun.reload();
            }
            else
            {
                Enemy target = target(this, enemies, gun.getAccuracy());
                Attack(target);
            }
        }   
        else if (action == 'd')//Hold this position!
        {
            this.defend();
            System.out.println(name+" hunkers down!");
        }
        else if (action == 'r')//Reloading!  Cover me!
        {
            gun.reload();
        }
        else if (action == 'm')//Fall back!  There's too many of them!
        {
            run();
        }
        else
        {
            System.out.println("Invalid command.");
        }
    }
    
}
