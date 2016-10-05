/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 *  Program written by Mark Sheehan
 *  02/24/14
 *  CS 101 Section 2
 */
package youcantrunforever_test;

public class Enemy_RangedAlien extends Enemy
{
    final int BASE_DAMAGE = 4; //4-10*3 damage, 20 range, 10 speed, 45 HP, Has 30% base accuracy (15% vs defending players), with a 2 range multiplier.  Fires 3 times.
    final int RAND_DAMAGE = 7;
    final int ATTACK_DISTANCE = 20;
    final int BASE_TOHIT = 30;
    final int ACCURACY = 2;
    
    public Enemy_RangedAlien(int startingDistanceFromWall)
    {
        super(45, 10, "Spiker", startingDistanceFromWall);
    }
    
    public @Override void move(Player p)
    {
        //move the alien
        
        if(distanceFromWall > 0 && distanceFromWall > p.getDistanceFromWall())
        {
            //going toward the wall
            int move = rand.nextInt(runSpeed);
            distanceFromWall -= move;

            System.out.println("The "+name+" skitters forward "+move+" spaces... \nDistance is now "+distanceFromWall+".");
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
            System.out.println("The "+name+" skitters backwards "+move+" spaces... \nDistance is now "+distanceFromWall+".");
        }
    }
    
    public @Override void Attack(Player p)
    {
        if(getDistance(p) <= ATTACK_DISTANCE)
        {
            int damage;
            System.out.println(name + " fires a volley of needles at "+p.getName()+"!");
            int range = Math.abs(p.getDistanceFromWall() - this.getDistanceFromWall());
            int baseaccuracy = ACCURACY*(25-range), accuracy;
            if (p.isDefending())
            {
                    accuracy = baseaccuracy+BASE_TOHIT/2;
                    damage = ( BASE_DAMAGE + rand.nextInt(RAND_DAMAGE) )/2; //rounds down
            }
            else
            {
                    accuracy = baseaccuracy+BASE_TOHIT;
                    damage = BASE_DAMAGE + rand.nextInt(RAND_DAMAGE);
            }
            for (int i=0; i<3; i++) //Fires 3 times
            {
                if (p.isAlive()) //Check to see if its alive!
                {
                    if (p.isDefending())
                    {
                            damage = ( BASE_DAMAGE + rand.nextInt(RAND_DAMAGE) )/2; //rounds down
                    }
                    else
                    {
                            damage = BASE_DAMAGE + rand.nextInt(RAND_DAMAGE);
                    }
                    //System.out.println(accuracy); //Debug
                    if (rand.nextInt(100) < accuracy)//Hit!
                    {
                        System.out.println(p.getName()+" was hit!");
                        System.out.println(p.getName()+" took "+damage+" damage!");
                        p.addDamage(damage);
                    }
                    else //Miss!
                    {
                        System.out.println("The missiles missed!");
                    }
                }
            }
            
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
            System.out.println("The "+name+" shrieks in pain!");
        }
        else if(hitPoints <= 15 && hitPoints > 0)
        {
            System.out.println("The "+name+" stumbles and groans...");
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
        
        //If range is under 10, always attack.  Otherwise, if the unit is in range there is a chance the unit will move equal to ((ATTACK_DISTANCE - range))/(ATTACK_DISTANCE), where ATTACK_DISTANCE - range will always be 1 - 15 inclusive, and will increase as the unit gets closer to its target.
        if (getDistance(target) < 10)
        {
            Attack(target);
        }
        else if (getDistance(target) >= 10 && getDistance(target) < ATTACK_DISTANCE)
        {
            int chanceToAttack = (int)((((double)(ATTACK_DISTANCE - getDistance(target)))/(ATTACK_DISTANCE))*100);
            //System.out.println(chanceToAttack); //Debug
            if (rand.nextInt(100) < chanceToAttack)
                Attack(target);
            else
                move(target);
        }
        else
        {
            move(target);
        }
    }
}
