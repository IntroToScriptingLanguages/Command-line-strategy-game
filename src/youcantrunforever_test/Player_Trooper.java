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
public class Player_Trooper extends Player
{
    private Ability_RocketNet rocketnet; //We add this so we can activate it.
    public Player_Trooper(String name, int distanceFromWall, String victoryMessage)
    {
        super(50, 12, new Gun_Rocket(), name, distanceFromWall, victoryMessage);
        rocketnet = new Ability_RocketNet(this);
        giveAbility(rocketnet);
        hadAbility = true;
        description = "Heavy-weapons units equipped with long-range explosives designed to decimate alien hordes.  The Trooper also carries Rocket Nets, a fast-firing but inaccurate projective capable of entangling a single enemy for a short period.";
    }
    
    @Override
    public void Attack(Enemy e) {
        gun.shoot(e, 0);
    }
    
    public void areaAttack(Enemy target, Enemy[] enemies)
    {
        System.out.println(name+" fired the Rocket Launcher!");
        int range = (Math.abs(getDistance(this)-getDistance(target)));
        if (rand.nextInt(100) < 90) //Rocket hit!
        {
            System.out.println("Direct hit!");
            for (Enemy enemy : enemies)
            {
                if (enemy.isAlive() && Math.abs(enemy.getDistanceFromWall()-target.getDistanceFromWall()) <= 1) //Enemy within area of effect.
                {
                    Attack(enemy);
                }
            }
        }
        else
        {
            System.out.println("The rocket missed the target!"); //Rocket miss!
        }
        gun.reduceAmmo();
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
                areaAttack(target, enemies);
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
        else if (action == 'f')//Firing rocket net!
        {
            rocketnet.activate(enemies);
        }
        else
        {
            System.out.println("Invalid command.");
        }
    }
    
}
