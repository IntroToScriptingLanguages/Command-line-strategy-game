/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 *  Program written by Mark Sheehan
 *  02/24/14
 *  CS 101 Section 2
 */
package youcantrunforever_test;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author PSTogether
 */
public class Game 
{
    //useful varaibles
        
    Random rand = new Random();
    Scanner scan = new Scanner(System.in);
    Player[] players;
    Enemy[] enemies;
    GameAgent[] gameagents;
    int roundNumber = 1;
    boolean win;
    String name;
    final int BOMB_DET = 20;
    
    final protected int EASY_START_DISTANCE = 50;
     final protected int MED_START_DISTANCE = 40;
     final protected int HARD_START_DISTANCE = 30;
     final protected int EASY_RAND_DISTANCE = 25;
     final protected int MED_RAND_DISTANCE = 35;
     final protected int HARD_RAND_DISTANCE = 45;
     
     final protected int[][] DIFFICULTY_DISTANCES = { {EASY_START_DISTANCE, EASY_RAND_DISTANCE} , {MED_START_DISTANCE, MED_RAND_DISTANCE}, {HARD_START_DISTANCE, HARD_RAND_DISTANCE}}; //0 = easy, 1 = med, 2 = hard

    public Game(Player[] players, Enemy[] enemies, String nombre)
    {
        this.players = players;
        this.enemies = enemies;
        name = nombre;
        
        gameagents = new GameAgent[players.length + enemies.length];
        
        for (int i=0; i<players.length; i++)
        {
            gameagents[i] = players[i];
        }
        
        for (int i=0; i<enemies.length; i++)
        {
            gameagents[i+players.length] = enemies[i];
        }
    }
    
    public boolean playGame() //Plays through the game and returns a boolean value on whether the player won or lost
    {
        while(this.hasSomebodyWon(players, enemies) == false)
        {
            System.out.println();
            this.playRound();
        }
        
        if(this.checkAtLeastOnePlayerAlive(players)) //Players won!
        {
            this.printPlayersWinMessages(players);
            System.out.println();
            System.out.println();
            System.out.println("** Press Enter to Continue. **");
            scan.nextLine();
        }
        else //Players lost!
        {
            this.printPlayersLoseMessage();
            System.out.println();
            System.out.println();
            System.out.println("** Press Enter to Continue. **");
            scan.nextLine();
        }
        return win;
    }
    
    public boolean playBombGame(int difficulty) //Final level, player must defend the bomb.
    {
        while(roundNumber <= BOMB_DET)
        {
            System.out.println();
            System.out.println((BOMB_DET - roundNumber + 1)+" turns until the bomb is detonated!");
            System.out.println();
            this.playRound();
            Enemy enemy, newEnemy;
            for (int i=0; i<enemies.length; i++)
            {
                enemy = enemies[i];
                if (enemy.isAlive() == false) //Enemies may respawn after death.
                {
                    if (rand.nextInt(100) < 100)
                    {
                        newEnemy = createEnemy((rand.nextInt(8)+1), DIFFICULTY_DISTANCES[difficulty][0] + rand.nextInt(DIFFICULTY_DISTANCES[difficulty][1]));
                        enemies[i] = newEnemy;
                        System.out.println("A "+newEnemy.getName()+" appeared "+newEnemy.getDistanceFromWall()+" tiles away from the wall!");
                    }
                }
            }
            System.out.println("** Press Enter to Continue. **");
            scan.nextLine();
            
        }
        
        if(this.checkAtLeastOnePlayerAlive(players)) //Players won!
        {
            win = true;
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("=================================");
        
            System.out.println("=================================");
            System.out.println("|            VICTORY            |");
            System.out.println("=================================");
            System.out.println();
            System.out.println("All communications with the bomb squad ends in static, as the bomb detonates in the heart of the alien craft...");
            System.out.println();
            System.out.println();
            System.out.println("** Press Enter to Continue. **");
            scan.nextLine();
        }
        else //Players lost!
        {
            this.printPlayersLoseMessage();
            System.out.println();
            System.out.println();
            System.out.println("** Press Enter to Continue. **");
            scan.nextLine();
        }
        return win;
    }

    //now let's start the main loop

    //set up initiative order

    public void playRound()
    {
        System.out.println(name+" and Enemy's statuses:");
        
        for(Player p : players)
        {
            p.printStatus();
        }
        
        System.out.println();
                
        for(Enemy e : enemies)
        {
            e.printStatus();
        }
        
        System.out.println("=================================");
        System.out.println("** Press Enter to Continue. \n Round: " + roundNumber++ + " **");
        scan.nextLine();
        
        
        
        System.out.println("=================================");
        System.out.println("|          Human' Turn          |");
        System.out.println("=================================");
        //play through one iteration
        for(int i = 0; i < players.length; i++)//Players round
        {
            if(players[i].isAlive() && (hasSomebodyWon(players, enemies) == false))
            {
                System.out.println();
                String name = players[i].getName();
                System.out.println(name + "'s Turn:");
                players[i].printStatus();
                boolean inrange = false;
                int range = players[i].getWeaponRange(), enemies_in_range = 0, enemies_wounded = 0;
                for (Enemy enemy : enemies)
                            {
                                if (enemy.isAlive() && getRange(players[i], enemy) <= range) //Enemy in range!
                                {
                                    inrange = true;
                                    enemies_in_range++;
                                    if (enemy.getHP() < enemy.getMaxHP()) //Enemy has been wounded!
                                        enemies_wounded++;
                                }
                            }
                System.out.println("Enemies in range: "+enemies_in_range+"; Enemies wounded: "+enemies_wounded+" \n");
                
                System.out.println("What will "+name+" do?  Type a to attack, d to defend, r to reload, m to run towards the wall, f to use special ability, s for status.");
                char action;
                while (true) //Until the player gives a valid input.
                {
                    try
                    {
                        String response = scan.next();
                        if ("a".equals(response))//If unit is out of ammo, it will automatically reload.  Check to see if there are targets.
                        {
                            if (inrange)
                            {
                            action = 'a';
                            break; //It's alot better to use break here, though it is possible to make a boolean that is by default false, set it true here, and check the while statement if the statement is false.
                            }
                            else
                            {
                                System.out.println("No targets in range!");
                            }
                        }
                        else if ("d".equals(response))
                        {
                            action = 'd';
                            break;
                        }
                        else if ("r".equals(response))//Checks to see if weapon is empty.
                        {
                            if (players[i].getGun() != null)
                            {
                                if (players[i].getGun().getRemainingShots() < players[i].getGun().getMaxShots())
                                {   
                                    action = 'r';
                                    break;
                                }
                                else
                                {
                                    System.out.println("The gun is full!");
                                }
                            }
                            else
                            {
                                if (players[i].getSpray().getRemainingShots() < players[i].getSpray().getMaxShots())
                                {   
                                    action = 'r';
                                    break;
                                }
                                else
                                {
                                    System.out.println("The gun is full!");
                                }
                            }
                        }
                        else if ("m".equals(response))//Checks to see if the player's back is against the wall or if player is immobilized.
                        {
                            if (players[i].getDistanceFromWall() > 0 && players[i].isDeployed() == false)
                            {
                                 action = 'm';
                                 break;
                            }
                            else if (players[i].isDeployed())
                            {
                                System.out.println(name + " is deployed!");
                            }
                            else
                            {
                                System.out.println(name + " is up against the wall.  Can't run anymore!");
                            }
                        }
                        else if ("f".equals(response)) //Check to see if unit actually has ability.
                        {
                            if (players[i].hadAbility() && players[i].hasAbility()) //Can use ability!
                            {
                                Ability ability = players[i].getAbility();
                                if (ability.isTargeted()) //Checks to see if ability is targeted, and if so if it has valid targets.
                                {
                                    if (ability.scanForTargets(players, enemies).length > 0)//There are targets!
                                    {
                                        action = 'f';
                                        break;
                                    }
                                    else //No targets...
                                    {
                                        System.out.println("No targets in range!");
                                    }
                                }
                                else
                                {
                                action = 'f';
                                break;
                                }
                            }
                            else if (players[i].hadAbility() && players[i].hasAbility() == false)//Ability is locked!
                            {
                                System.out.println(name+" can't use this ability at this time!");
                            }
                            else
                            {
                                System.out.println(name+" doesn't have a special ability!");
                            }
                        }
                        else if ("s".equals(response))
                        {
                            System.out.println("PLAYER AND ENEMY STATUSES:");
        
                            for(Player p : players)
                            {
                                 p.printStatus();
                            }
        
                            System.out.println();
                
                            for(Enemy e : enemies)
                            {
                                e.printStatus();
                            }
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
                players[i].takeAction(players, enemies, action);
                System.out.println();
                System.out.println("** Press Enter to Continue. **");
                scan.nextLine();
                scan.nextLine(); //I need to put this here twice- first input token is consumed by scan.next() somewhere in takeAction...
                
            }
            
        }

        //Resets after players' turn.
        
        for (Player player : players)
        {
            if (player.hadAbility()) //If the player had a special ability but it was taken away because of an enemy, it gets it back after the players' turn.
                player.giveAbility();
        }
        

        
        System.out.println("=================================");
        
        System.out.println("=================================");
        System.out.println("|        Aliens' Turn          |");
        System.out.println("=================================");
        
        for (int i=0; i < enemies.length; i++)//Enemies' turn
        {
            if (enemies[i].isAlive() && (hasSomebodyWon(players, enemies) == false) && enemies[i].isStunned() == false)
            {
                String name = enemies[i].getName();
                System.out.println(name + "'s Turn:");
                enemies[i].takeAction(players, enemies);
                System.out.println();
                System.out.println("** Press Enter to Continue. **");
                        scan.nextLine();
                System.out.println();
                
            }
            else if (enemies[i].isStunned())
            {    
                System.out.println(enemies[i].getName()+" was stunned!  It is unable to move!");
                System.out.println();
                System.out.println("** Press Enter to Continue. **");
                        scan.nextLine();
            }
            
        }
        
        //Resets after enemy turn
        for (Player player : players)
        {
            player.noDefend();
        }
        
        for (Enemy enemy : enemies)
        {
            enemy.unstun();
        }
        System.out.println();
        System.out.println();
    }

    public boolean checkAtLeastOnePlayerAlive(Player[] players)
    {
        for(Player p : players)
        {
            if(p.isAlive() == true)
            {
                //at least one is alive!  Yay!
                return true;
            }
        }
        //nobody's left alive...
        return false;
    }

    public boolean checkAtLeastOneEnemyAlive(Enemy[] enemies)
    {
        for(Enemy e : enemies)
        {
            if(e.isAlive() == true)
            {
                return true;
            }
        }

        return false;
    }

    public boolean hasSomebodyWon(Player[] players, Enemy[] enemies)
    {
        if(checkAtLeastOneEnemyAlive(enemies) && checkAtLeastOnePlayerAlive(players))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public static void shuffleGameAgents(GameAgent[] agents)
    {
        Random rand = new Random();

        GameAgent tempAgent;
        int swapIndex_1;
        int swapIndex_2;

        for(int i = 0; i < agents.length * 10; i++)
        {
            //swap
            swapIndex_1 = rand.nextInt(agents.length);
            swapIndex_2 = rand.nextInt(agents.length);

            tempAgent = agents[swapIndex_1];
            agents[swapIndex_1] = agents[swapIndex_2];
            agents[swapIndex_2] = tempAgent;
        }
    }
    
    public void printPlayersWinMessages(Player[] players)
    {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("=================================");
        
        System.out.println("=================================");
        System.out.println("|            VICTORY            |");
        System.out.println("=================================");
        for(Player p : players)
        {
            if(p.isAlive())
            {
                System.out.print(p.getName()+": ");
                p.printVictoryMessage();
                System.out.println();
            }
        }
        win = true;
    }
    
    public void printPlayersLoseMessage()
    {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("=================================");
        
        System.out.println("=================================");
        System.out.println("|            DEFEAT             |");
        System.out.println("=================================");
        win = false;
    }
    
    public int getRange(Player p, Enemy e)
    {
        return (Math.abs(p.getDistanceFromWall() - e.getDistanceFromWall()));
    }
    
    public Enemy createEnemy(int index, int startingdistance)//0 = SlowAlien, 1 = MedAlien, 2 = BigAlien, 3 = FastAlien, 4 = RangedAlien, 5 = AlienBomb, 6 = Queen, 7 = Carnifax, 8 = DeathKnight
     {
         if (index == 0)
                 return new Enemy_SlowAlien(startingdistance);
         else if (index == 1)
                 return new Enemy_MedAlien(startingdistance);
         else if (index == 2)
                 return new Enemy_BigAlien(startingdistance);
         else if (index == 3)
                 return new Enemy_FastAlien(startingdistance);
         else if (index == 4)
                 return new Enemy_RangedAlien(startingdistance);
        else if (index == 5)
                 return new Enemy_AlienBomb(startingdistance);
         else if (index == 6)
                 return new Enemy_Queen(startingdistance);
         else if (index == 7)
                 return new Enemy_Carnifax(startingdistance);
         else if (index == 8)
                 return new Enemy_DeathKnight(startingdistance);
          else
                 return null;
     }
    
    public boolean getResults()
    {
        return win;
    }
}
