/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 *  Program written by Mark Sheehan
 *  02/24/14
 *  CS 101 Section 2
 */
package youcantrunforever_test;

public class Enemy_DeathKnight extends Enemy
{
    final int BASE_DAMAGE = 1; //1-15 damage, 5 range, 20 speed, 160 HP, attacks penetrate through defend and undefend the target, attacks heal it.
    final int RAND_DAMAGE = 15;
    final int ATTACK_DISTANCE = 5;
    
    public Enemy_DeathKnight(int startingDistanceFromWall)
    {
        super(160, 20, "Death Knight", startingDistanceFromWall);
        canStun = false;
    }
    
    public @Override void move(Player p)
    {
        //move the alien
        
        if(distanceFromWall > 0 && distanceFromWall > p.getDistanceFromWall())
        {
            //going toward the wall
            int move = rand.nextInt(runSpeed);
            distanceFromWall -= move;

            System.out.println("The "+name+" crawls up "+move+" spaces... \nDistance is now "+distanceFromWall+".");
            if(distanceFromWall < p.getDistanceFromWall())
            {
                //went beyond the player - really just want to end up at same location
                distanceFromWall = p.getDistanceFromWall();
            }
            
            if(distanceFromWall < 0)
            {
                //went beyond the wall - can't allow that...
                distanceFromWall = 0;
            }
        }
        else if(p.getDistanceFromWall() > distanceFromWall)
        {
            //going backwards... those crafty players!
            int move = rand.nextInt(runSpeed);
            distanceFromWall += move;
            System.out.println("The "+name+" crawls back "+move+" spaces... \nDistance is now "+distanceFromWall+".");
        }
    }
    
    public @Override void Attack(Player p)
    {
        if(getDistance(p) <= ATTACK_DISTANCE)
        {
            int damage;
            System.out.println(name + " attacks "+p.getName()+" with its pincers!");
            if (p.isDefending())
            { 
                p.noDefend();
                System.out.println("The strike broke through "+p.getName()+"'s guard!");
                damage = BASE_DAMAGE + rand.nextInt(RAND_DAMAGE); //rounds down
            }
            else
                damage = BASE_DAMAGE + rand.nextInt(RAND_DAMAGE);
            System.out.println(p.getName()+" took "+damage+" damage!");
            p.addDamage(damage);
            System.out.println("The attack regenerated the "+name+"'s HP by "+damage+"!");
            heal(damage); //Restores HP equal to damage to this unit.
        }
        else
        {
            System.out.println(name + " missed!");
        }
    }
    
    public @Override void Die()
    {
        System.out.println("The "+name+" slumps to the ground dead.");
    }
    
    public @Override void setDistanceFromWall(int newDistance)
    {
        distanceFromWall = newDistance;
    }
    
    public @Override int getDistanceFromWall()
    {
        return distanceFromWall;
    }
    
    public @Override void addDamage(int damage)
    {
        hitPoints -= damage;
        if (hitPoints > MAX_HITPOINTS)
        {
            hitPoints = MAX_HITPOINTS;
        }
        else if(hitPoints > 100)
        {
            System.out.println("The "+name+"'s carapace absorbs the hit!");
        }
        else if (hitPoints <= 100 && hitPoints > 25)
        {
            System.out.println("The "+name+" endures the hit!");
        }
        else if(hitPoints <= 25 && hitPoints > 0)
        {
            System.out.println("The "+name+" appears to be wavering...");
        }
        if(!isAlive())
        {
            Die();
        }
    }
    
    public @Override String getName()
    {
        return name;
    }

    public void takeAction(Player[] players, Enemy[] enemies) 
    {
        //this is the logic that drives the enemy's behaviour
        
        //while it is not next to a player, go find one and give it a hug
        
        Player target = this.targetNearestPlayer(players);
        
        //attack if possible, otherwise move.
        
        if(getDistance(target) < ATTACK_DISTANCE)
        {
            Attack(target);
        }
        else
        {
            move(target);
        }
    }
}
