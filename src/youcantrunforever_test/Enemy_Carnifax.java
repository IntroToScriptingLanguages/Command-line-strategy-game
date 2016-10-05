package youcantrunforever_test;

public class Enemy_Carnifax extends Enemy
{
    final int BASE_DAMAGE = 10; //10-25 damage, 25 range, 5 speed, 60 HP, Has 25% base accuracy (12% vs defending players), with a 3 range multiplier.  Attacks disable special abilities.
    final int RAND_DAMAGE = 16;
    final int ATTACK_DISTANCE = 25;
    final int BASE_TOHIT = 25;
    final int ACCURACY = 3;
    
    public Enemy_Carnifax(int startingDistanceFromWall)
    {
        super(60, 5, "Carnifax", startingDistanceFromWall);
    }
    
    public @Override void move(Player p)
    {
        //move the alien
        
        if(distanceFromWall > 0 && distanceFromWall > p.getDistanceFromWall())
        {
            //going toward the wall
            int move = rand.nextInt(runSpeed);
            distanceFromWall -= move;

            System.out.println("The "+name+" hovers forward "+move+" spaces... \nDistance is now "+distanceFromWall+".");
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
            System.out.println("The "+name+" hovers backwards "+move+" spaces... \nDistance is now "+distanceFromWall+".");
        }
    }
    
    public @Override void Attack(Player p)
    {
        if(getDistance(p) <= ATTACK_DISTANCE)
        {
            int damage;
            System.out.println(name + " fires a lightning bolt on "+p.getName()+"!");
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
            if (p.isAlive()) //Check to see if its alive!
            {
                  //System.out.println(accuracy); //Debug
                if (rand.nextInt(100) < accuracy)//Hit!
                {
                    System.out.println(p.getName()+" was hit!");
                    System.out.println(p.getName()+" took "+damage+" damage!");
                    p.addDamage(damage);
                    if (p.hadAbility() && p.hasAbility())
                    {
                        System.out.println("The electric bolt disabled "+p.getName()+"'s ability!");
                        p.takeAbility();
                    }
                }
                else //Miss!
                        {
                                System.out.println("The bolt missed!");
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
        System.out.println("The "+name+" crashes to the ground.");
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
            System.out.println("The "+name+" allows its shield to absorb the hit!");
        }
        else if(hitPoints <= 15 && hitPoints > 0)
        {
            System.out.println("Purple blood splatters from the "+name+"'s body...");
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
