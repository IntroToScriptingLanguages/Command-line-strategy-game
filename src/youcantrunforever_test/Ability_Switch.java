package youcantrunforever_test;

import java.util.ArrayList;
import java.util.Scanner;

public class Ability_Switch extends Ability{
    
    final protected int HEAL = 10;
    final protected int RANGE = 15;
    
    public Ability_Switch(Player p){
        user = p;
        name = "Switch Weapon";
        isTargeted = false;
    };
    
    public void activate(Player player, Gun gun) //Switches your weapon.  Swapping gun automatically reloads it.
    {
         System.out.println(user.getName()+" switched to his "+gun.getName());
         player.changeGun(gun);
    }
    
    public String getAbilityName()
    {
        return name;
    }
    
    @Override
    public Player[] scanForTargets(Player[] players, Enemy[] enemies) //This isn't a targeted ability- you won't find any targets.
    {
        Player[] targets = new Player[0];
        return targets;
    }
}
