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

/**
 *
 * @author PSTogether
 */
public class Gun_Sentry_Gun implements Gun
{
    Random rand = new Random();
    final int BASE_TOHIT = 20;
    final int BASE_DAMAGE = 7; //7-20*5 damage
    final int RAND_DAMAGE = 14;
    final int MAX_SHOTS = 99;
    final int RANGE = 15;
    final int ACCURACY = 2;
    int remainingShots = MAX_SHOTS;
    String gunName = "Sentry Gun";
    
    
    
    public @Override void shoot(Enemy e, int modifier)
    {
        if(remainingShots >0)
        {
                //gun fires 5 round bursts - check to see if each hits
                 System.out.println(gunName + " fires a barrage of bullets:");
                 int damage;
                 for(int i = 0; i < 5; i++)
                {
                //don't need to do overkill
                    if(e.isAlive()  && remainingShots > 0)
                    {
                        if(rand.nextInt(100) < BASE_TOHIT + modifier)
                        {
                             damage = BASE_DAMAGE + rand.nextInt(RAND_DAMAGE);
                             System.out.println("Inflicted "+damage+" damage to "+e.getName()); //Place this before addDamage for messages to work properly!
                             e.addDamage(damage);
                             
                             //System.out.println(BASE_TOHIT+modifier);//debug
                             remainingShots--;
                         }
                        else
                        {
                             System.out.println("Bullet missed!");
                             remainingShots--;
                        }
                    }
                    else if (remainingShots <= 0)
                    {
                        System.out.println(gunName + " is empty!  Need to reload!");
                        break;
                    }
                    else
                    {
                        break;
                    }
                }
            
            
        }
        else
        {
            System.out.println(gunName + " is empty!  Need to reload!");
        }
    }

    @Override
    public int getRemainingShots() 
    {
        return remainingShots;
    }
    
    @Override
    public int getMaxShots() 
    {
        return MAX_SHOTS;
    }
    
    @Override public int getRange()
    {
        return RANGE;
    }
    
    @Override public int getAccuracy()
    {
        return ACCURACY;
    }

    @Override
    public boolean isEmpty() 
    {
        if(remainingShots <= 0)
        {
            return true;
        }
        return false;
    }

    @Override
    public void reload() 
    {
        System.out.println("Reloading " + gunName);
        remainingShots = MAX_SHOTS;
    }
    
    @Override
    public int getBaseToHit()
    {
        return BASE_TOHIT;
    }
    
    public int getPlayerRange(Player p, Enemy e)
    {
        return Math.abs(p.getDistanceFromWall() - e.getDistanceFromWall());
    }
    
    @Override
    public void reduceAmmo()
    {
        remainingShots--;
    }
    
    @Override
   public String getName()
   {
       return gunName;
   }
}
