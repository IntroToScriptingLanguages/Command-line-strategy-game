/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 *  Program written by Mark Sheehan
 *  02/24/14
 *  CS 101 Section 2
 */
package youcantrunforever_test;

import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Player implements GameAgent
{
    //====
    //Data
    //====
    
    protected int MAX_HITPOINTS;
    protected int hitPoints;
    protected int runSpeed;
    protected Gun gun;
    protected SprayGun spray; //adding abilities of, for instance, one unit refilling another unit's ammo or jamming weapons, will have to change this value.
    protected String name;
    protected int distanceFromWall;
    protected String victoryMessage;
    protected Random rand = new Random();
    protected boolean isDefending = false;
    protected boolean deployed = false;
    protected boolean hasAbility;
    protected boolean hadAbility = false;
    protected Ability ability;
    protected String description;
    
    //===========
    //Constructor
    //===========
    
    public Player(int MAX_HITPOINTS, int runSpeed, Gun gun, String name, int distanceFromWall, String victoryMessage)
    {
        this.MAX_HITPOINTS = MAX_HITPOINTS;
        this.hitPoints = MAX_HITPOINTS;
        this.runSpeed = runSpeed;
        this.gun = gun;
        this.name = name;
        this.distanceFromWall = distanceFromWall;
        this.victoryMessage = victoryMessage;
    }
    
       public Player(int MAX_HITPOINTS, int runSpeed, SprayGun gun, String name, int distanceFromWall, String victoryMessage)
    {
        this.MAX_HITPOINTS = MAX_HITPOINTS;
        this.hitPoints = MAX_HITPOINTS;
        this.runSpeed = runSpeed;
        this.spray = gun;
        this.name = name;
        this.distanceFromWall = distanceFromWall;
        this.victoryMessage = victoryMessage;
    }
    
    //================
    //Abstract methods (Concrete classes must implement these functions)
    //================
    
    //used with each action
    public abstract void takeAction(Player[] players, Enemy[] enemies, char action);
    
    //used to attack a particular enemy
    
    public abstract void Attack(Enemy e);   
    

    
    //======================================
    //Functions from the GameAgent interface
    //======================================
    
    

    
    //returns true if player is still alive (hp > 0)
    @Override
    public boolean isAlive()
    {
        if(hitPoints > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    //returns the name of the player
    
    @Override
    public String getName()
    {
        return name;
    }
    
    //prints out the current status of the player including hp, distance, and name
    
    @Override
    public void printStatus()
    {
        if(isAlive())
        {
            String ammoCount, gunname;
            if (gun != null)
            {
                ammoCount = ""+gun.getRemainingShots()+"/"+gun.getMaxShots();
                gunname = gun.getName();
            }
            else
            {
                ammoCount = ""+spray.getRemainingShots()+"/"+spray.getMaxShots();
                gunname = spray.getName();
            }
            System.out.print(name + " : " + "\t\tHP: " + hitPoints + " / " + MAX_HITPOINTS + ";\t Weapon: "+gunname+";\t\t Distance: " +
                    distanceFromWall+";\t Ammo: "+ammoCount+";\t Ability: "+getAbilityName()+" ");
            
            if (isDefending)
                System.out.print("(Defending) ");
            if (hadAbility && hasAbility == false)
                System.out.print("(Ability disabled) ");
            if (deployed)
                System.out.print("(Deployed) ");
            
            System.out.println();
        }
        else
        {
            System.out.println("** " + name + " is DEAD **");
        }
    }
    

    
    //=====================
    //Implemented Functions
    //=====================
    
    public String getAbilityName()
    {
        if (hadAbility)
            return ability.getName();
        return "N/A";
    }   
    
    public void addDamage(int damage) 
    {
        hitPoints -= damage;
        if (hitPoints > MAX_HITPOINTS)
            hitPoints = MAX_HITPOINTS;
        if(!isAlive())
        {
            Die();
        }
    }
    
    
    //is called whenever the player dies (hp < 0)
    public void Die() 
    {
        System.out.println(name + " was killed!");
    }
    
    public Gun getGun()
    {
            return gun;
    }
    
    public SprayGun getSpray()
    {
        return spray;
    }
    
    
    public void run()
    {
        if(distanceFromWall == 0)
        {
            System.out.println(name + " is up against the wall.  Can't run anymore!");
        }
        else if(distanceFromWall - runSpeed > 0)
        {
            distanceFromWall -= runSpeed;
            System.out.println(name + " runs toward wall by "+runSpeed+"!");
            System.out.println(name+"'s distance is now "+distanceFromWall);
        }
        else if(distanceFromWall - runSpeed < 0)
        {
            distanceFromWall = 0;
            System.out.println(name + " has reached the wall!");
        }
    }
    
    public int getRunSpeed()
    {
        return runSpeed;
    }
    
    public int getDistanceFromWall()
    {
        return distanceFromWall;
    }

    //Retrieves the nearest alive enemy - used for targeting attacks
    
    public Enemy targetNearestEnemy(Enemy[] enemies)
    {
        //should do defensive programming here but we'll take care of that later
        
        Enemy nearestEnemy = null;  //assumed that at least one enemy is alive
        
        //find first enemy in array that is alive
        for(int i = 0; i < enemies.length; i++)
        {
            if(enemies[i].isAlive())
            {
                nearestEnemy = enemies[i];
                break;
            }
        }
        
        //find nearest enemy
        for(int i = 1; i < enemies.length; i++)
        {
            if(enemies[i].isAlive() && (getDistance(enemies[i]) < getDistance(nearestEnemy)))
            {
                nearestEnemy = enemies[i];
            }
        }
        return nearestEnemy;
    }
    

    
    //gets absolute distance from this player to target player
    
    public int getDistance(Player p)
    {
        return Math.abs(p.getDistanceFromWall() - distanceFromWall);
    }
    
    //gets absolute distance from this player to target enemy
    
    public int getDistance(Enemy e)
    {
        return Math.abs(e.getDistanceFromWall() - distanceFromWall);
    }
    
    //used to print the victory message in the event that the players win the game
    
    public void printVictoryMessage()
    {
        System.out.println(victoryMessage);
    }
    
    public void defend()
    {
        isDefending = true;
    }
    
    public void noDefend()
    {
        isDefending = false;
    }
    
    public boolean isDefending()
    {
        return isDefending;
    }
    
    public Enemy target(Player player, Enemy[] enemies) //No specified weapon accuracy multiplier, so we go with default- 1.
    {
        int[][] targets = scanForEnemies(enemies, 1);
        Enemy target = targetSelection(enemies, targets, player);
        return target;
    }
    
    public Enemy target(Player player, Enemy[] enemies, int weapon_modifier) //The modifier or weapon modifier are equivalent to accuracy here- all other math is handled internally.
    {
        int[][] targets = scanForEnemies(enemies, weapon_modifier);
        Enemy target = targetSelection(enemies, targets, player);
        return target;
    }
    
    protected int[][] scanForEnemies(Enemy[] enemies, int modifier) //return an double-int array- first value is whether or not enemy is in range (1 = yes, 2 = no), second value is multiplier.  There are likely more efficient ways to perform this, I'm still doing it like this.
    {
        int[][] targets = new int[enemies.length][2];
        int range;
        for (int i=0; i<enemies.length; i++) {
            range = (Math.abs(getDistance(this)-getDistance(enemies[i])));
            if ( enemies[i].isAlive() && range <= gun.getRange()) //Checks to see if target is in range.
            {
                targets[i][0] = 1;
                targets[i][1] = modifier*(25-range);
            }
        }
        return targets;
    }
    
    private Enemy targetSelection(Enemy[] enemies, int[][] scanData, Player player) //Asks for a target and chooses it.
    {
        Scanner scan = new Scanner(System.in);
        ArrayList<Enemy> targets = new ArrayList<Enemy>();
        ArrayList<Integer> num_targets = new ArrayList<Integer>();
        
        System.out.println("Which target should "+player.getName()+" attack?  Input the number corresponding to the target to fire at it.");
        int index = 0;
        for (int i=0; i<enemies.length; i++)
        {
            if (scanData[i][0] == 1) //Target is in range.
            {
                targets.add(enemies[i]);
                System.out.print(index+": ");
                enemies[i].printStatus();
                
                int hitChance = (gun.getBaseToHit()+scanData[i][1]);
                if (hitChance > 100)
                    hitChance = 100;
                if (hitChance < 0)
                    hitChance = 0;
                System.out.println(" Range:  "+(Math.abs(getDistance(this)-getDistance(enemies[i])))+" ;Hit Chance: "+hitChance+"%");
                num_targets.add(index);
                index++;
            }
        }
        
         while (true)
                {
                    try
                    {
                        int response = scan.nextInt();
                        if (num_targets.contains(response))
                        {
                            return targets.get(response);
                        }
                        else
                        {
                            System.out.println("Invalid input.");
                        }
                    }
                    catch (Exception e)
                    {
                        System.out.println("Invalid input.");
                        scan.next();
                    }
                }
    }
    
    public boolean hasAbility()
    {
        return hasAbility;
    }
    
    public boolean hadAbility()
    {
        return hadAbility;
    }
    
    public void giveAbility(Ability a)//Gives ability if never had one. Only use in constructors.
    {
        hasAbility = true;
        hadAbility = true;
        ability = a;
    }
    
    public void giveAbility() //Gives ability if it has been taken
    {
        hasAbility = true;
    }
    
    public void takeAbility()
    {
        hasAbility = false;
    }
    
    public int getWeaponRange()
    {
        if (gun != null)
            return gun.getRange();
        else
            return spray.getRange();
    }
    
    public Ability getAbility()
    {
        return ability;
    }

    public void changeGun(Gun gun) //Changes the weapon.
    {
        this.gun = gun;
    }
    
    public void deploy()
    {
        deployed = true;
    }
    
    public void undeploy()
    {
        deployed = false;
    }
    
    public boolean isDeployed()
    {
        return deployed;
    }
    
    public String getDescription()
    {
        return description;
    }
}