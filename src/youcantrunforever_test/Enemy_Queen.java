/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 *  Program written by Mark Sheehan
 *  02/24/14
 *  CS 101 Section 2
 */
package youcantrunforever_test;

public class Enemy_Queen extends Enemy
{
    final int BASE_DAMAGE = 0; //Cannot attack, 10 speed, 75 HP, can heal allies by 20-40 HP within 15 range.
    final int RAND_DAMAGE = 0;
    final int ATTACK_DISTANCE = 15;//Distance the queen will repair an injured unit.
    final int BASE_HEAL = 20;
    final int RAND_HEAL = 21;
    final int FOLLOW_DISTANCE = 8; //Distance the queen will follow an uninjured unit.
    
    public Enemy_Queen(int startingDistanceFromWall)
    {
        super(75, 10, "Queen", startingDistanceFromWall);
    }
    
    public @Override void move(Player p)
    {
        //move the alien
        
        if(distanceFromWall > 0 && distanceFromWall > p.getDistanceFromWall())
        {
            //going toward the wall
            int move = rand.nextInt(runSpeed);
            distanceFromWall -= move;

            System.out.println("The "+name+" moves forward "+move+" spaces... \nDistance is now "+distanceFromWall+".");
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
            System.out.println("The "+name+" moves backwards "+move+" spaces... \nDistance is now "+distanceFromWall+".");
        }
    }
    
    public void move(Enemy e)
    {
        //move the alien
        
        if(distanceFromWall > 0 && distanceFromWall > e.getDistanceFromWall())
        {
            //going toward the wall
            int move = rand.nextInt(runSpeed);
            distanceFromWall -= move;

            System.out.println("The "+name+" moves forward "+move+" spaces... \nDistance is now "+distanceFromWall+".");
            if(distanceFromWall < e.getDistanceFromWall())
            {
                //went beyond the player - really just want to end up at same location
                distanceFromWall = e.getDistanceFromWall();
            }
            
            if(distanceFromWall < 0)
            {
                //went beyond the wall - can't allow that...
                distanceFromWall = 0;
            }
        }
        else if(e.getDistanceFromWall() > distanceFromWall)
        {
            //going backwards... those crafty players!
            int move = rand.nextInt(runSpeed);
            distanceFromWall += move;
            System.out.println("The "+name+" moves backwards "+move+" spaces... \nDistance is now "+distanceFromWall+".");
        }
    }
    
    public void repair(Enemy e)
    {
        int healAmount = rand.nextInt(RAND_HEAL)+BASE_HEAL;
        e.heal(healAmount);
        System.out.println(e.getName()+" was healed by "+healAmount+" HP!");
    }
    
    public @Override void Attack(Player p) //This does nothing!
    {
       
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
        else if(hitPoints > 10)
        {
            System.out.println("The "+name+" lets loose a horrific shriek!");
        }
        else if(hitPoints <= 10 && hitPoints > 0)
        {
            System.out.println("The "+name+" is losing its composure...");
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

    @Override
    public void takeAction(Player[] players, Enemy[] enemies) 
    {
        //AI will try to keep itself within healing range at all times.  If there is no enemy to heal and it’s near an enemy, then it’ll try to move up to within 8 tiles of that unit.  Otherwise, it’ll sit there.
        
        Enemy target = this.targetNearestEnemy(enemies);
        Enemy target_injured = this.targetNearestInjuredEnemy(enemies);
        Player targetplayer = this.targetNearestPlayer(players);
        
        if (target_injured != null)
        {
            if(getDistance(target_injured) <= ATTACK_DISTANCE) //Enemy within range is hurt, heal it!
            {
                repair(target_injured);
                //System.out.println(target.getName()+", "+target_injured.getName());//debug
            }
            else //Enemy out of range is hurt, get in closer!
            {
                move(target_injured);
            }
        }
        else if (target != null) //No enemies hurt, but there's still an enemy out there!
        {
            if (getDistance(target) > FOLLOW_DISTANCE)//Move up to it!  
            {
                move(target);
            }
            else//Enemy is already in follow distance, stop, drop, and roll!
            {
                System.out.println(name+"is waiting ominously...");
            }
        }
        else //No enemies, so move up!!
        {
             move(targetplayer);
        }
    }
    
    public Enemy targetNearestEnemy(Enemy[] enemies) //This can't be self.
    {
        
        Enemy nearestEnemy = null; 
        
        for(int i = 0; i < enemies.length; i++)
        {
            if(!(enemies[i].equals(this)) && enemies[i].isAlive())
            {
                nearestEnemy = enemies[i];
                break;
            }
        }
        
        //now find nearest enemy
        
        for(int i = 0 ; i < enemies.length; i++)
        {
            if(!(enemies[i].equals(this)) && enemies[i].isAlive() && (getDistance(enemies[i]) < getDistance(nearestEnemy)))
            {
                nearestEnemy = enemies[i];
            }
        }

        return nearestEnemy;
    }
    
    public Enemy targetNearestInjuredEnemy(Enemy[] enemies) //This can be self.
    {
        
        Enemy nearestEnemy = null; 
        
        //find nearest injured enemy
        
        for(int i = 0 ; i < enemies.length; i++)
        {
            if(enemies[i].isAlive() && ( nearestEnemy == null || (getDistance(enemies[i]) < getDistance(nearestEnemy)) ) && enemies[i].getHP() < enemies[i].getMaxHP())
            {
                nearestEnemy = enemies[i];
            }
        }

        return nearestEnemy;
    }
}
