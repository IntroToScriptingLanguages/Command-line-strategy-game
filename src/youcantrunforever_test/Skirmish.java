package youcantrunforever_test;


public class Skirmish extends Mode{
    private String[] difficulties = {"Easy", "Medium", "Hard"}; //Difficulty affects enemy start positions and troop compositions.
     private int difficulty; //0 = easy, 1 = med, 2 = hard
     private String name;
     final private Player[] PLAYER_LIST = {new Player_Marine("Marine", 0,""),
                                           new Player_Medic("Medic", 0,""),
                                           new Player_Flamethrower("Flamethrower", 0,""),
                                           new Player_Sniper("Sniper", 0,""),
                                           new Player_Trooper("Trooper", 0,""),
                                           new Player_Engineer("Engineer", 0,"")
                                           };
     
     final private int techLevel = 6;
     final private int[] ARMYSIZES = {6, 5, 4};//0 = easy, 1 = mid, 2 = hard
     final private int[] SWARM_OPTIONS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
     
     final private String[] SWARMS = {"", "The Vanguard",     //Swarm 1- The Vanguard.  Drones
                                               "Warriors of Doom", //Swarm 2- Warriors of Doom.  Drones, Warriors, Titans
                                               "Demolition Team", //Swarm 3- Demolition Team. Drones, Slimesacks
                                               "Shock and Awe", //Swarm 4- Shock and Awe. Drones, Hunters, Death Knights
                                               "Reaper Batallion", //Swarm 5- Reaper Batallion. Warriors, Titans
                                               "Artillery Batallion", //Swarm 6- Artillery Batallion. Spikers, Carnifaxes
                                               "Dragon's Hand", //Swarm 7- Dragon's Hand. Slimesacks, Spikers
                                               "Royal Escort",//Swarm 8- Royal Escort. Queens, Carnifaxes, Warriors, Titans
                                               "Dark Crusaders",//Swarm 9- Dark Crusaders. Death Knights, Queens
                                               "Stormtroopers", //Swarm 0- Stormtroopers. Warriors, Death Knights, Carnifaxes, Queens
                                          };
     //Provides number of enemies spawned every level.
     final private int[] NUM_ENEMIES = {0, 9 /*1*/, 7/*2*/, 9/*3*/, 7/*4*/, 7/*5*/, 7/*6*/, 8/*7*/, 7/*8*/, 9/*9*/, 10/*10*/};
     //Provides chances of each enemy spawning: //0 = SlowAlien, 1 = MedAlien, 2 = BigAlien, 3 = FastAlien, 4 = RangedAlien, 5 = AlienBomb, 6 = Queen, 7 = Carnifax, 8 = DeathKnight
     final private int[][] ENEMY_CHANCE = {new int[9], {1, 0, 0, 0, 0, 0, 0, 0, 0}, //1
                                                       {3, 2, 1, 0, 0, 0, 0, 0, 0}, //2
                                                       {1, 0, 1, 0, 0, 0, 0, 0, 0}, //3
                                                       {3, 0, 0, 3, 0, 0, 0, 0, 1}, //4
                                                       {0, 1, 1, 0, 0, 0, 0, 0, 0}, //5
                                                       {0, 0, 0, 0, 3, 0, 0, 1, 0}, //6
                                                       {0, 0, 0, 0, 1, 1, 0, 0, 0}, //7
                                                       {0, 1, 1, 0, 0, 0, 1, 1, 0}, //8
                                                       {0, 0, 0, 0, 0, 0, 1, 0, 1}, //9
                                                       {0, 2, 0, 0, 0, 0, 1, 1, 1}, //10
                                          };
     
     public Skirmish(String name)
     {
        this.name = name; 
     }
     
     public void playSkirmish()
     {
         

         Player[] players;
         Enemy[] enemies;
         int swarmID;
         boolean keepplaying = true, win;
         
         while (keepplaying)
         {
             this.difficulty = chooseDifficulty();
             swarmID = chooseSwarm();
             enemies = generateEnemies(ENEMY_CHANCE[swarmID], NUM_ENEMIES[swarmID], difficulty);
             players = selectPlayers(ARMYSIZES[difficulty], PLAYER_LIST, techLevel);
             Game game = new Game(players, enemies, name);
             startGame(players, enemies, name, SWARMS[swarmID]);
             win = game.playGame();
             System.out.println("Do you want to play another game? y/n");
             String response = scan.nextLine();
             if (!("y".equals(response)))
             {
                 keepplaying = false;
             }
         }
     }
     
     
     
     public int getDifficulty()
     {
         return difficulty;
     }
     
     public int chooseSwarm() //Choose the swarm and creates the enemy list.
     {
         System.out.println("Choose the swarm that you wish to face.  Type the number that corresponds to that swarm.");
         int option;
         for (int i=1; i<11; i++)
         {
             option = SWARM_OPTIONS[i];
             System.out.println(option+": "+SWARMS[i]);
         }
         
         while (true)
         {
                    try
                    {
                        int response = scan.nextInt();
                        int swarmID = 1;
                        boolean found = false;
                        
                        for (; swarmID < 11; swarmID++)
                        {
                            if (SWARM_OPTIONS[swarmID] == response)
                            {
                                found = true;
                                break;
                            }
                        }
                        
                        if (found) //We have a valid option.
                        {
                            return swarmID;
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
     
}
