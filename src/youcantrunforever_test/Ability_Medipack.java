package youcantrunforever_test;

import java.util.ArrayList;
import java.util.Scanner;

public class Ability_Medipack extends Ability{
    
    final protected int HEAL = 10;
    final protected int RANGE = 15;
    
    public Ability_Medipack(Player p){
        user = p;
        name = "Medpack";
        isTargeted = true;
    };
    
    public void activate(Player[] players) //Heals an ally.  Medipacks heal 10-25 damage.
    {
         System.out.println(user.getName()+" activated the Medpack!");
         int healAmount = (int)(Math.random()*16)+HEAL;
         int[][] targets = scanForAllies(players);
         Player target = targetSelection(players, targets, user);
         heal(target, healAmount);
         System.out.println(user.getName()+" healed "+target.getName()+" for "+healAmount+" HP!");
    }
    
    public String getAbilityName()
    {
        return name;
    }
    
    public void heal(Player p, int healamount)
    {
          p.addDamage(-healamount);
    }
    
    @Override
    public Player[] scanForTargets(Player[] players, Enemy[] enemies) //Returns list of targets.
    {
        ArrayList<Player> target_list = new ArrayList<Player>();
        int range;
        for (int i=0; i<players.length; i++) {
            range = (Math.abs(getDistance(user)-getDistance(players[i])));
            if ( players[i].isAlive() && range <= RANGE) //Checks to see if target is in range.
            {
                target_list.add(players[i]);
            }
        }
        Player[] targets = new Player[target_list.size()];
        target_list.toArray(targets);
        return targets;
    }
    
    
    private int[][] scanForAllies(Player[] players) //return an double-int array- first value is whether or not enemy is present (1 = yes, 2 = no), second value is multiplier.  There are likely more efficient ways to perform this, I'm still doing it like this.
    {
        int[][] targets = new int[players.length][2];
        int range;
        for (int i=0; i<players.length; i++) {
            range = (Math.abs(getDistance(user)-getDistance(players[i])));
            if ( players[i].isAlive() && range <= RANGE) //Checks to see if target is in range.
            {
                targets[i][0] = 1;
                targets[i][1] = 0;
            }
        }
        return targets;
    }
    
    private Player targetSelection(Player[] players, int[][] scanData, Player player) //Asks for a target and chooses it.
    {
        Scanner scan = new Scanner(System.in);
        ArrayList<Player> targets = new ArrayList<Player>();
        ArrayList<Integer> num_targets = new ArrayList<Integer>();
        
        System.out.println("Which target should "+player.getName()+" heal?  Input the number corresponding to the target to fire at it.");
        int index = 0;
        for (int i=0; i<players.length; i++)
        {
            if (scanData[i][0] == 1) //Target is in range.
            {
                targets.add(players[i]);
                System.out.print(index+": ");
                players[i].printStatus();
                System.out.println();
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
