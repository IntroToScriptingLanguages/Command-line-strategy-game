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
public class Player_Medic extends Player
{
    private Ability_Medipack medipack; //We add this so we can activate it.
    public Player_Medic(String name, int distanceFromWall, String victoryMessage)
    {
        super(50, 15, new Gun_Medic_Rifle(), name, distanceFromWall, victoryMessage);
        medipack = new Ability_Medipack(this);
        giveAbility(medipack);
        hadAbility = true;
        description = "A support unit armed with a precision battle rifle.  It has the ability to administer medpacks that can heal injured allies, greatly prolonging squad longevity.";
    }
    
    public @Override void Attack(Enemy e)
    {
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
        else if (action == 'f')//I'll heal you!
        {
            medipack.activate(players);
        }
        else
        {
            System.out.println("Invalid command.");
        }
    }



}
