package youcantrunforever_test;

import java.util.Random;

/**
 *
 * @author PSTogether
 */
public class Gun_Rocket implements Gun //fires 
{
    Random rand = new Random();
    final int BASE_TOHIT = 90;  //Accuracy solved in player class.
    final int BASE_DAMAGE = 30; //30-54 damage
    final int RAND_DAMAGE = 15;
    final int MAX_SHOTS = 1;
    final int RANGE = 35;
    final int ACCURACY = 0;
    int remainingShots = MAX_SHOTS;
    String gunName = "Rocket Launcher";
    
    public @Override void shoot(Enemy e, int modifier)
    {
        if(remainingShots > 0)
        {
                 System.out.println(e.getName()+" was hit by the blast!");
                 int damage;
                    if(e.isAlive())
                    {
                            damage = BASE_DAMAGE + rand.nextInt(RAND_DAMAGE);
                            System.out.println("Inflicted "+damage+" damage to "+e.getName());
                             e.addDamage(damage);
                    }
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
