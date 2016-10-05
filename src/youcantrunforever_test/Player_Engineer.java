/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 *  Program written by Mark Sheehan
 *  02/24/14
 *  CS 101 Section 2
 */
package youcantrunforever_test;
import java.util.Scanner;
/**
 *
 * @author PSTogether
 */
public class Player_Engineer extends Player
{
    private Ability_Switch switchGun; //We add this so we can activate it.
    
    public Player_Engineer(String name, int distanceFromWall, String victoryMessage)
    {
        super(55, 12, new Gun_Laser(), name, distanceFromWall, victoryMessage);
        switchGun = new Ability_Switch(this);
        giveAbility(switchGun);
        hadAbility = true;
        description = "A tactical unit armed with two experimental weapons: a laser pistol with advanced target-acquisition systems, and the deployable Sentry Gun, a stationary heavy machine-gun that can attack two targets at once.";
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
                if (gun.getName().equals("Sentry Gun"))
                {
                    System.out.println("Choose the first target.");
                    Enemy target = target(this, enemies, gun.getAccuracy());
                    Attack(target);
                    
                    int[][] targets = scanForEnemies(enemies, gun.getAccuracy()); //Checks to see if there are more enemies in range.
                    boolean hasEnemiesInRange = false;
                    for (int[] target_chest : targets)
                    {
                        int inRange = target_chest[0];
                        if (inRange == 1)
                        {
                            hasEnemiesInRange = true;
                        }
                    }
                    if (hasEnemiesInRange == true) //There is an enemy in range!
                    {
                        
                        Scanner scan = new Scanner(System.in);
                        System.out.println("** Press Enter to Continue. **");
                        scan.nextLine();
                        
                        System.out.println("Choose the second target.");
                        Enemy target2 = target(this, enemies, gun.getAccuracy());
                        Attack(target2);
                    }
                }
                else
                {
                    Enemy target = target(this, enemies, gun.getAccuracy());
                    Attack(target);
                }
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
        else if (action == 'f')//Deploying sentry gun!
        {
            if (gun.getName().equals("Laser Pistol")) //We have laser pistol!
            {
                switchGun.activate(this, new Gun_Sentry_Gun());
                deploy();
                System.out.println(name+" deployed his Sentry Gun!");
            }
            else //When in doubt, switch to Laser Pistol!
            {
                System.out.println(name+" packed his Sentry Gun!");
                switchGun.activate(this, new Gun_Laser());
                undeploy();
            }
        }
        else
        {
            System.out.println("Invalid command.");
        }
    }



}
