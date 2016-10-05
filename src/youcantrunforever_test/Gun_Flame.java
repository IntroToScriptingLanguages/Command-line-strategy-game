package youcantrunforever_test;

import java.util.Random;

public class Gun_Flame implements SprayGun //Burns all enemies within range.
{
    Random rand = new Random();
    final int BASE_DAMAGE = 25; //25-35 damage
    final int RAND_DAMAGE = 35;
    final int MAX_SHOTS = 4;
    final int RANGE = 5;
    int remainingShots = MAX_SHOTS;
    String gunName = "Flamethrower";
    
     @Override
   public String getName()
   {
       return gunName;
   }
    
    @Override
    public void burn(Player player, Enemy[] enemies){
        if(remainingShots >0)
        {
            System.out.println(gunName+" ejected a wave of flame!");
            int range, damage;
            for (Enemy enemy : enemies)
            {
                if (enemy.isAlive())
                {
                    range = getPlayerRange(player, enemy);
                    if (range <= RANGE) //Target is in range!
                    {
                        damage = BASE_DAMAGE + rand.nextInt(RAND_DAMAGE);
                        System.out.println(enemy.getName()+" was burned!\n"+enemy.getName()+" took "+damage+" damage!");
                        enemy.addDamage(damage);
                     }
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
    
        //gets absolute distance from this player to target player
    
    public int getPlayerRange(Player p, Enemy e)
    {
        return Math.abs(p.getDistanceFromWall() - e.getDistanceFromWall());
    }
}
