/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 *  Program written by Mark Sheehan
 *  02/24/14
 *  CS 101 Section 2
 */
package youcantrunforever_test;

public class Enemy_BigAlien extends Enemy
{
    final int BASE_DAMAGE = 20; //20-59 damage, 5 range, 5 speed, 100 HP, attacks penetrate through defend and undefend the target.
    final int RAND_DAMAGE = 40;
    final int ATTACK_DISTANCE = 5;
    
    public Enemy_BigAlien(int startingDistanceFromWall)
    {
        super(100, 5, "Titan", startingDistanceFromWall);
    }
    
    public @Override void move(Player p)
    {
        //move the alien
        
        if(distanceFromWall > 0 && distanceFromWall > p.getDistanceFromWall())
        {
            //going toward the wall
            int move = rand.nextInt(runSpeed);
            distanceFromWall -= move;

            System.out.println("The "+name+" advances "+move+" spaces... \nDistance is now "+distanceFromWall+".");
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
            System.out.println("The "+name+" retreats "+move+" spaces... \nDistance is now "+distanceFromWall+".");
        }
    }
    
    public @Override void Attack(Player p)
    {
        if(getDistance(p) <= ATTACK_DISTANCE)
        {
            int damage;
            System.out.println(name + " crushes "+p.getName()+" with a massive hammer-arm!");
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
        else if(hitPoints > 15)
        {
            System.out.println("The "+name+" endures the hit!");
        }
        else if(hitPoints <= 15 && hitPoints > 0)
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
