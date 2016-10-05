package youcantrunforever_test;

import java.util.ArrayList;
import java.util.Scanner;

public class Ability_RocketNet extends Ability{
    
    final private int RANGE = 35; //Same range as rocket!
    final private int HITCHANCE = 50;
    
    public Ability_RocketNet(Player p){
        user = p;
        name = "Rocket Net";
        isTargeted = true;
    };
    
    public void activate(Enemy[] enemies) //Stuns an enemy.  Enemy stunned loses a turn.  Misses half the time.
    {
         int[][] targets = scanForEnemies(enemies);
         Enemy target = targetSelection(enemies, targets, user);
         System.out.println(user.getName()+" launched the Rocket Net!");
         if ((int)(Math.random()*100) < HITCHANCE) //Rocket hit!
        {
             System.out.println("Direct hit!");
             target.stun();
             System.out.println(target.getName()+" is stunned! It is unable to move!");
         }
         else
        {
            System.out.println("The rocket missed the target!"); //Rocket miss!
        }
    }
    
    public String getAbilityName()
    {
        return name;
    }
    
    @Override
    public Enemy[] scanForTargets(Player[] players, Enemy[] enemies) //Returns list of targets.
    {
        ArrayList<Enemy> target_list = new ArrayList<Enemy>();
        int range;
        for (int i=0; i<enemies.length; i++) {
            range = (Math.abs(getDistance(user)-getDistance(enemies[i])));
            if ( enemies[i].isAlive() && range <= RANGE) //Checks to see if target is in range.
            {
                target_list.add(enemies[i]);
            }
        }
        Enemy[] targets = new Enemy[target_list.size()];
        target_list.toArray(targets);
        return targets;
    }
    
    private int[][] scanForEnemies(Enemy[] enemies) //return an double-int array- first value is whether or not enemy is present (1 = yes, 2 = no), second value is multiplier.  There are likely more efficient ways to perform this, I'm still doing it like this.
    {
        int[][] targets = new int[enemies.length][2];
        int range;
        for (int i=0; i<enemies.length; i++) {
            range = (Math.abs(getDistance(user)-getDistance(enemies[i])));
            if ( enemies[i].isAlive() && range <= RANGE && enemies[i].canStun()) //Checks to see if target is in range.
            {
                targets[i][0] = 1;
                targets[i][1] = 0;
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
                
                int hitChance = (HITCHANCE);
                System.out.println(" Range:  "+(Math.abs(getDistance(user)-getDistance(enemies[i])))+" ;Hit Chance: "+hitChance+"%");
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
    
    //gets absolute distance from this player to target player
    
    public int getDistance(Player p)
    {
        return Math.abs(p.getDistanceFromWall() - user.getDistanceFromWall());
    }
    
    //gets absolute distance from this player to target enemy
    
    public int getDistance(Enemy e)
    {
        return Math.abs(e.getDistanceFromWall() - user.getDistanceFromWall());
    }
}
