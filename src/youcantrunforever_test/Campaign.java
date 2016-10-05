/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package youcantrunforever_test;
import java.util.ArrayList;
/**
 *
 * @author Me
 */
public class Campaign extends Mode{
     private int level;
     private String[] difficulties = {"Easy", "Medium", "Hard"}; //Difficulty affects enemy start positions and troop compositions.
     private int difficulty; //0 = easy, 1 = med, 2 = hard
     private ArrayList<Player> PLAYER_LIST = new ArrayList<>();
     Player[] player_choices;
     private int techlevel = 0;
     
     final private int[] ARMYSIZES = {6, 5, 4};//0 = easy, 1 = mid, 2 = hard
     
     final private String[] COMPOSITION = {"", "Drones",     //Prints army composition by level.  This is level 1.
                                               "Drones, Warriors", //2
                                               "Drones, Titans", //3
                                               "Drones, Hunters", //4
                                               "Drones, Warriors, Hunters", //5
                                               "Drones, Warriors, Hunters, Titans", //6
                                               "Spikers", //7
                                               "Hunters, Warriors, Spikers",//8
                                               "Drones, Spikers, Titans",//9
                                               "Drones, Slimesacks", //10
                                               "Hunters, Spikers, Slimesacks", //11
                                               "Titans, Queens",//12
                                               "Slimesacks, Warriors, Titans, Spikers",//13
                                               "Carnifaxes",//14
                                               "Hunters",//15
                                               "Warriors, Titans, Death Knights",//16
                                               "Death Knights, Slimesacks, Spikers",//17
                                               "Warriors, Carnifaxes, Queens",//18
                                               "Death Knights, Carnifaxes, Queens",//19
                                               "Unknown"
                                          };
     //Provides number of enemies spawned every level.
     final private int[] NUM_ENEMIES = {0, 3 /*1*/, 5/*2*/, 5/*3*/, 6/*4*/, 6/*5*/, 6/*6*/, 5/*7*/, 6/*8*/, 7/*9*/, 10/*10*/, 7/*11*/, 6/*12*/, 7/*13*/, 5/*14*/, 10/*15*/, 7/*16*/, 9/*17*/, 7/*18*/, 7/*19*/, 10/*20*/};
     //Provides chances of each enemy spawning: //0 = SlowAlien, 1 = MedAlien, 2 = BigAlien, 3 = FastAlien, 4 = RangedAlien, 5 = AlienBomb, 6 = Queen, 7 = Carnifax, 8 = DeathKnight
     final private int[][] ENEMY_CHANCE = {new int[9], {1, 0, 0, 0, 0, 0, 0, 0, 0}, //1
                                                       {3, 1, 0, 0, 0, 0, 0, 0, 0}, //2
                                                       {4, 0, 1, 0, 0, 0, 0, 0, 0}, //3
                                                       {3, 0, 0, 2, 0, 0, 0, 0, 0}, //4
                                                       {2, 1, 0, 2, 0, 0, 0, 0, 0}, //5
                                                       {4, 2, 2, 3, 0, 0, 0, 0, 0}, //6
                                                       {0, 0, 0, 0, 1, 0, 0, 0, 0}, //7
                                                       {0, 1, 0, 2, 1, 0, 0, 0, 0}, //8
                                                       {3, 0, 1, 0, 1, 0, 0, 0, 0}, //9
                                                       {1, 0, 0, 0, 0, 1, 0, 0, 0}, //10
                                                       {0, 0, 0, 2, 1, 3, 0, 0, 0}, //11
                                                       {0, 0, 3, 0, 0, 0, 2, 0, 0}, //12
                                                       {0, 2, 1, 0, 2, 2, 0, 0, 0}, //13
                                                       {0, 0, 0, 0, 0, 0, 0, 1, 0}, //14
                                                       {0, 0, 0, 1, 0, 0, 0, 0, 0}, //15
                                                       {0, 3, 3, 0, 0, 0, 0, 0, 2}, //16
                                                       {0, 0, 0, 0, 2, 3, 0, 0, 2}, //17
                                                       {0, 2, 0, 0, 0, 0, 1, 2, 0}, //18
                                                       {0, 0, 0, 0, 0, 0, 1, 2, 2}, //19
                                                       {1, 0, 0, 0, 0, 0, 1, 0, 0}, //20
                                          };
     
     public Campaign(int level_num, String name)
     {
         level = level_num;
         super.name = name;
         
         //If you started via level select.
         if (level_num > 1)
         {
             Player_Marine unit = new Player_Marine("Marine", 0,"");
             PLAYER_LIST.add(unit);
             techlevel++;
         }
         
         if (level_num > 2)
         {
             Player_Medic unit2 = new Player_Medic("Medic", 0,"");
             PLAYER_LIST.add(unit2);
             techlevel++;
         }
         
         if (level_num > 4)
         {
             Player_Flamethrower unit3 = new Player_Flamethrower("Flamethrower", 0,"");
                    PLAYER_LIST.add(unit3);
             techlevel++;
         }
         
         if (level_num > 6)
         {
             Player_Sniper unit4 = new Player_Sniper("Sniper", 0,"");
                    PLAYER_LIST.add(unit4);
             techlevel++;
         }
         
         if (level_num > 9)
         {
             Player_Trooper unit5 = new Player_Trooper("Trooper", 0,"");
                    PLAYER_LIST.add(unit5);
             techlevel++;
         }
         
         if (level_num > 13)
         {
             Player_Engineer unit6 = new Player_Engineer("Engineer", 0,"");
                    PLAYER_LIST.add(unit6);
             techlevel++;
         }
     }
     
     public void playCampaign() //Executes the WHOLE campaign here.  Levels are from 1 to 20.
     {
         this.difficulty = chooseDifficulty();

         Player[] players;
         Enemy[] enemies;
         boolean win;
         
         for (int i=level; i<=20; i++) //Let's get through each level and what we need for it.
         {
             printLevel(i);//First, we need to tell the player what stage he's on, any new unlocks, and what enemies will he be facing:
             System.out.println();
             
             if (i == 1) //Switch doesn't work for i>9...
             {
                    System.out.println("=============NEW UNIT UNLOCKED:============");
                    Player_Marine unit = new Player_Marine("Marine", 0,"");
                    PLAYER_LIST.add(unit);
                    System.out.println(unit.getName()+": "+unit.getDescription());
                    techlevel++;
             }
             if (i == 2)
             {
                    System.out.println("=============NEW UNIT UNLOCKED:============");
                    Player_Medic unit2 = new Player_Medic("Medic", 0,"");
                    PLAYER_LIST.add(unit2);
                    System.out.println(unit2.getName()+": "+unit2.getDescription());
                    techlevel++;
             }
             if (i == 4)
             {
                    System.out.println("=============NEW UNIT UNLOCKED:============");
                    Player_Flamethrower unit3 = new Player_Flamethrower("Flamethrower", 0,"");
                    PLAYER_LIST.add(unit3);
                    System.out.println(unit3.getName()+": "+unit3.getDescription());
                    techlevel++;
              }
             if (i == 6)
             {
                    System.out.println("=============NEW UNIT UNLOCKED:============");
                    Player_Sniper unit4 = new Player_Sniper("Sniper", 0,"");
                    PLAYER_LIST.add(unit4);
                    System.out.println(unit4.getName()+": "+unit4.getDescription());
                    techlevel++;
              }
             if (i == 9)
             {
                    System.out.println("=============NEW UNIT UNLOCKED:============");
                    Player_Trooper unit5 = new Player_Trooper("Trooper", 0,"");
                    PLAYER_LIST.add(unit5);
                    System.out.println(unit5.getName()+": "+unit5.getDescription());
                    techlevel++;
              }
             if (i == 13)
             {
                    System.out.println("=============NEW UNIT UNLOCKED:============");
                    Player_Engineer unit6 = new Player_Engineer("Engineer", 0,"");
                    PLAYER_LIST.add(unit6);
                    System.out.println(unit6.getName()+": "+unit6.getDescription());
                    techlevel++;
             }
             if (i == 20)
             {
                 System.out.println("***Commander "+name+", your recent string of victories has created a rare opportunity for us to end this war once and for all.\n\nOur scanners have successfully tracked down the alien mothership, which has landed somewhere near ***REDACTED***.\n\nYou will lead a strike team to infiltrate, board, and plant a bomb on this vessel, whose detonation should permanently halt the invasion.\n\nYour troops are to defend the bomb against what is likely the most powerful force on this planet.  It will be difficult, but the entire planet depends on your success!\n\nOne last thing, if the mission is a success, not one soldier will return alive.  This is a suicide mission.  Your soldiers' sacrifice will ensure the future of mankind.\n\n-***REDACTED***");
             }
             System.out.println();
             System.out.println("The following species of aliens have been reported roaming this territory: "+COMPOSITION[i]);
             System.out.println("** Press Enter to Continue. **");
             scan.nextLine();
             
            //Next, we have the players and enemies created.
             player_choices = new Player[PLAYER_LIST.size()];
             PLAYER_LIST.toArray(player_choices);
             players = selectPlayers(ARMYSIZES[difficulty], player_choices, techlevel);
             
             
             //Generate enemies, start the game!
             enemies = generateEnemies(ENEMY_CHANCE[i], NUM_ENEMIES[i], difficulty);
             Game game = new Game(players, enemies, name);
             
             startGame(players, enemies, name, "Aliens");

             if (i == 20)
             {
                 win = game.playBombGame(difficulty);
             }
             else
             {
             win = game.playGame();
             }
             
             if (i == 20 && win == false) //OOOH...
             {
                 System.out.println("Despite the effort of the bomb squad, they were ultimately overcome by the alien denizens of the mothership before the bomb could be detonated.\n\nThe bomb was destroyed, and the alien invasion continued unimpeded.  All your soldiers have died in vain.");
                 System.out.println();
                 System.out.println("\n** Press Enter to Continue. **");
                 scan.nextLine();
             }
             
             //Campaign endings
             if (i == 20 && win)
             {
                 System.out.println("The blast from the bomb's detonation could be seen and heard for miles. It was as if time had stopped within those terrible moments...");
                 System.out.println("\n** Press Enter to Continue. **");
                 scan.nextLine();
                 System.out.println("Every alien invader on Earth simultaneously looked to the sky... took its last breath, and fell to the ground.  The war was over, and the humans have finally been saved from the alien threat. ");
                 System.out.println("\n** Press Enter to Continue. **");
                 scan.nextLine();
                 System.out.println("It was then that every man, woman, and child on the planet realized they owed their lives to the sacrifice of the brave men who courageously fought on against impossible odds...");
                 System.out.println("\n** Press Enter to Continue. **");
                 scan.nextLine();
                 System.out.println("...fighting for their lives.");
                 System.out.println("** Press Enter to Continue. **");
                 scan.nextLine();
                 System.out.println("CONGRATULATIONS, Commander "+name+"!  Your actions have saved Earth from the aliens!");
                 System.out.println();
                 System.out.println("Last stage reached: 20\nCampaign complete!\n\nThanks for playing!");
                 System.out.println();
                 System.out.println("** Press Enter to End Campaign. **");
                 scan.nextLine();
                 return;
             }
             else if (win == false) //Lose...
             {
                 System.out.println("The aliens have overrun Earth.  Humanity never stood a chance.");
                 System.out.println("\n** Press Enter to Continue. **");
                 scan.nextLine();
                 System.out.println("Last stage reached: "+i+"\n\nThanks for playing!");
                 System.out.println();
                 System.out.println("** Press Enter to End Campaign. **");
                 scan.nextLine();
                 return;
             }
         }
     }
     
     public int getLevel()
     {
         return level;
     }
     
     public int getDifficulty()
     {
         return difficulty;
     }
     
     
     
     public int chooseDifficulty()//The player chooses the difficulty.
     {
         System.out.println("Choose your difficulty, Commander. 0=easy, 1=medium, 2=hard");
         int dif = 1; //Normal by default.
         while (true)
                {
                    try
                    {
                        int number = scan.nextInt();
                        if (number >= 0 && number <= 2) //We have a valid unit.
                        {
                            System.out.println(difficulties[number]+" chosen.");
                            dif = number;
                            System.out.println("** Press Enter to Continue. **");
                            scan.nextLine();
                            scan.nextLine();
                            break;
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
         return dif;
     }
     
     public void setDifficulty(int number)
     {
         if (number >= 0 && number <= 2)
         {
             difficulty = number;
         }
     }
     
    public void printLevel(int level) //Prints first upto players in array.
     {
         System.out.println("=============MISSION "+level+":============");
         System.out.println();
         System.out.println("Level key: "+LEVEL_CODES[level]);
         System.out.println();
         System.out.println("** Press Enter to Continue. **");
         scan.nextLine();
     }
     
     public void setLevel(int number)
     {
         level = number;
     }
}
