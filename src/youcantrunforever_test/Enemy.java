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

public abstract class Enemy implements GameAgent
{
    //====
    //Data
    //====
    
    protected int distanceFromWall;
    protected int hitPoints;
    protected int MAX_HITPOINTS;
    protected int runSpeed;
    protected String name;
    protected Random rand = new Random();
    protected boolean stun = false;
    protected boolean canStun = true;
    
    //===========
    //Constructor
    //===========
    
    public Enemy(int MAX_HITPOINTS, int runSpeed, String name, int distanceFromWall)
    {
        this.MAX_HITPOINTS = MAX_HITPOINTS;
        hitPoints = MAX_HITPOINTS;
        this.runSpeed = runSpeed;
        this.name = name;
        this.distanceFromWall = distanceFromWall;
    }
    
    //================
    //Abstract Methods
    //================
    
    //use with each action
    public abstract void takeAction(Player[] player, Enemy[] enemies);
    
    //called whenever this Enemy dies (hp < 0)
    
    public abstract void Die();
    
    //used to move towards (and up to, if close enough) a player
    
    public abstract void move(Player p);
    
    //commences an attack against the selected player
    
    public abstract void Attack(Player p);
    
    //called whenever damage is dealt to this Enemy
    
    public abstract void addDamage(int damage);
    
    //========================
    //Overrides from GameAgent
    //========================
    
    //returns the name of the Enemy
    
    @Override
    public String getName()
    {
        return name;
    }
    
    //prints the status of the Enemy including name, remaining HP, and distance from the wall
    
    @Override
    public void printStatus()
    {
        if(isAlive())
        {
            System.out.print(name + ": \t\tHP - " + hitPoints + " / " + MAX_HITPOINTS + ";\t Distance: " + 
                    distanceFromWall+" ");
            if (stun)
                System.out.print("(Stunned) ");
            System.out.println();
        }
        else
        {
            System.out.println("** " + name + " is DEAD **");
        }
    }
    
    //indicates if the Enemy is alive - i.e., hp > 0
    
    @Override
    public boolean isAlive()
    {
        return hitPoints > 0;
    }
    
    //=====================
    //Implemented Functions
    //=====================
    
    //returns the distance from the wall
    
    public int getDistanceFromWall()
    {
        return distanceFromWall;
    }
    
    //sets the distance from the wall
    
    public void setDistanceFromWall(int newDistance)
    {
        distanceFromWall = newDistance;
    }
    
    //used to find the nearest alive player

    public Player targetNearestPlayer(Player[] players)
    {
        //Note: may target dead player if all other players died this round...
        
        Player nearestPlayer = null;    //it is assumed that this function will only be called if there is an alive player
        
        //find first alive player in array
        
        for(int i = 0; i < players.length; i++)
        {
            if(players[i].isAlive())
            {
                nearestPlayer = players[i];
                break;
            }
        }
        
        //now find nearest alive player
        
        for(int i = 0 ; i < players.length; i++)
        {
            if(players[i].isAlive() && (getDistance(players[i]) < getDistance(nearestPlayer)))
            {
                nearestPlayer = players[i];
            }
        }

        return nearestPlayer;
    }
    
    //gets absolute distance from this enemy to target player
    
    public int getDistance(Player p)
    {
        return Math.abs(p.getDistanceFromWall() - distanceFromWall);
    }
    
    //gets absolute distance from this enemy to target enemy
    
    public int getDistance(Enemy e)
    {
        return Math.abs(e.getDistanceFromWall() - distanceFromWall);
    }
    
    public boolean isStunned()
    {
        return stun;
    }
    
    public void stun()
    {
        stun = true;
    }
    
    public void unstun()
    {
        stun = false;
    }
    
    public int getHP()
    {
        return hitPoints;
    }
    
    public int getMaxHP()
    {
        return MAX_HITPOINTS;
    }
    
    public boolean canStun()
    {
        return canStun;
    }
    
    public void heal(int amount)
    {
        hitPoints += amount;
        if (hitPoints > MAX_HITPOINTS)
        {
            hitPoints = MAX_HITPOINTS;
        }
    }
}
