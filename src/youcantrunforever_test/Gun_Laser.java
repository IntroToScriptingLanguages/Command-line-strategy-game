/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package youcantrunforever_test;

import java.util.Random;

/**
 *
 * @author Me
 */
public class Gun_Laser implements Gun{
    Random rand = new Random();
    final int BASE_TOHIT = 100; //100% accuracy
    final int BASE_DAMAGE = 6; //6-15 damage
    final int RAND_DAMAGE = 10;
    final int MAX_SHOTS = 6;
    final int RANGE = 10;
    final int ACCURACY = 0;
    int remainingShots = MAX_SHOTS;
    String gunName = "Laser Pistol";
    
    public @Override void shoot(Enemy e, int modifier)
    {
        if(remainingShots >0)
        {
                 System.out.println(gunName + " fires a beam of light:");
                 int damage;
                    if(e.isAlive())
                    {
                        if(true)
                        {
                            damage = BASE_DAMAGE + rand.nextInt(RAND_DAMAGE);
                            System.out.println("Inflicted "+damage+" damage to "+e.getName());
                             e.addDamage(damage);
                             
                             //System.out.println(BASE_TOHIT+modifier);//debug
                         }
                        else
                        {
                             System.out.println("Bullet missed!");
                        }
                    }
            
            remainingShots--;
        }
        else
        {
            System.out.println(gunName + " is empty!  Need to reload!");
        }
    }

    @Override
    public int getMaxShots() 
    {
        return MAX_SHOTS;
    }
    
    @Override
    public int getRemainingShots() 
    {
        return remainingShots;
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
