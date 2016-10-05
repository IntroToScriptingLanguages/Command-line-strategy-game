package youcantrunforever_test;

public class Player_Flamethrower extends Player
{
    public Player_Flamethrower(String name, int distanceFromWall, String victoryMessage)
    {
        super(75, 10, new Gun_Flame(), name, distanceFromWall, victoryMessage);
        description = "Close-combat units equipped with a short-range flamethrower capable of burning all nearby aliens in a sea of flames.  It inflicts heavy damage but is an easy target for enemies.";
    }
    
    @Override
    public void Attack(Enemy e) { //This is never used.
    }
    
    public void sprayAttack(Enemy[] enemies)
    {
        spray.burn(this, enemies);
    }

    @Override public void takeAction(Player[] players, Enemy[] enemies, char action)
    {
        //put all logic here
        
        if (action == 'a') //ATTACK!!!!
        {
            if(spray.isEmpty())
            {   
                System.out.println(name+"'s weapon is empty!");
                spray.reload();
            }
            else
            {
                sprayAttack(enemies);
            }
        }   
        else if (action == 'd')//Hold this position!
        {
            this.defend();
            System.out.println(name+" hunkers down!");
        }
        else if (action == 'r')//Reloading!  Cover me!
        {
            spray.reload();
        }
        else if (action == 'm')//Fall back!  There's too many of them!
        {
            run();
        }
        else
        {
            System.out.println("Invalid command.");
        }
    }
    
}
