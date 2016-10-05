package youcantrunforever_test;

import java.util.Random;

/**
 *
 * @author PSTogether
 */
public class Gun_Medic_Rifle implements Gun //An alternative to the Marine Rifle, fires single-shots that deal 8-22 damage.
{
    Random rand = new Random();
    final int BASE_TOHIT = 30;
    final int BASE_DAMAGE = 8; //8-22 damage
    final int RAND_DAMAGE = 15;
    final int MAX_SHOTS = 8;
    final int RANGE = 20;
    final int ACCURACY = 3;
    int remainingShots = MAX_SHOTS;
    String gunName = "Battle Rifle";
    
    public @Override void shoot(Enemy e, int modifier)
    {
        if(remainingShots >0)
        {
                 System.out.println(gunName + " fires a single shot:");
                 int damage;
                    if(e.isAlive())
                    {
                        if(rand.nextInt(100) < BASE_TOHIT + modifier)
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
