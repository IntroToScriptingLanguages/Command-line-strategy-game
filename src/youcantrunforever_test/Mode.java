
package youcantrunforever_test;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public abstract class Mode {
     protected Player[] players;
     protected Enemy[] enemies;
     Scanner scan = new Scanner(System.in);
     Random rand = new Random();
     protected String name;
     
     final protected int HUMAN_START_DISTANCE = 20;
     final protected int HUMAN_RAND_DISTANCE = 50;
     
     final protected int EASY_START_DISTANCE = 50;
     final protected int MED_START_DISTANCE = 40;
     final protected int HARD_START_DISTANCE = 30;
     final protected int EASY_RAND_DISTANCE = 25;
     final protected int MED_RAND_DISTANCE = 35;
     final protected int HARD_RAND_DISTANCE = 45;
     
     final protected int[][] DIFFICULTY_DISTANCES = { {EASY_START_DISTANCE, EASY_RAND_DISTANCE} , {MED_START_DISTANCE, MED_RAND_DISTANCE}, {HARD_START_DISTANCE, HARD_RAND_DISTANCE}}; //0 = easy, 1 = med, 2 = hard
     
     //Victory messages
     final protected String[] MARINE_MESSAGES = {"Never surrender!", "Remember: short, controlled bursts.", "Area clear!", "Mission accomplished!", "Operation complete!  Requesting immediate exfil at Alpha LZ!", "We've done it!"};
     final protected String[] MEDIC_MESSAGES = {"No one gets left behind!", "The best offense is a good defense.", "Good work, we saved many lives today.", "No soldier dies on my watch.", "HQ, we're coming home.", "We've won the battle.  Let's hope we'll win the war..."};
     final protected String[] FLAME_MESSAGES = {"Burn!  BURN!!!", "THEY MUST ALL BURN! BWAHAHA!!!", "I'm ON FIRE!  Seriously, got an extinguisher?", "The flames, the screams, the green sanguine pools soaking on the ground, it's like poetry...", "We came, we saw, we BURNED them to dust!", "They're like Pokemon!  Crispy, mutilated Pokemon!"};
     final protected String[] SNIPER_MESSAGES = {"Eyes like a hawk.", "They never saw it coming.", "Slow and steady wins the race.", "Stay calm and kill aliens.", "Blindly charging is a good way to get hit in the head.", "Don't interfere with the enemy when they're about to make mistakes."};
     final protected String[] TROOPER_MESSAGES = {"DIS IZ MY BOOMSTIK!", "ROOOOOOOAAAAAAAARRRRRR!!!", "KWABOOM!!!", "R dey dead yit?", "WE 'ILL BURRY DEM!  WAAAAAAAGGGHHH!!!", "Dem aleenz liek 'XPLOJUNZ too?  Cuz I LIEK 'XPLOJUNZ!"};
     final protected String[] ENGINEER_MESSAGES = {"Ugh, should have stuck to my day job...", "Never send a soldier to do an engineer's job.", "\"Join the army\", they said.  \"It'll be fun\", they said.", "The right tool can fix any problem.", "Is that it?  We got 'em all?", "I don't believe it! We survived..."};
     
     final protected int[] LEVEL_CODES = {0,  //AAH, my eyes!
     2521, 1029, 6275, 1924, 5049, 9847, 8192, 4694, 8102, 7990,
     77139, 20206, 48393, 19308, 42225, 92352, 91097, 36654, 20141, 142857};
     
     
     
     public Player[] selectPlayers(int amount, Player[] playertypes) //Chooses a specific amount of players, array should have one of each player type, tech are types available going from left...
     {
         Player[] player_array = new Player[amount];
         ArrayList<Integer> num_targets = new ArrayList<Integer>();
         int remaining = amount;
         System.out.println("Commander, choose "+amount+" of the following units to take into battle.  Type the number to add that unit to your squad:");
         for (int i=0; i<playertypes.length; i++)//Prints out the choices.
         {
             System.out.println(i+"- "+playertypes[i].getName()+": "+playertypes[i].getDescription());
             num_targets.add(i);
         }
         System.out.println();
         while (remaining > 0)
         {
                    try
                    {
                        int response = scan.nextInt();
                        if (num_targets.contains(response)) //We have a valid unit.
                        {
                            //I just realized objects in array store object references, not copies of objects unlike primitive data values.  We can't just write player_array[i] = playertypes[response]; here.
                            if (response == 0) //Marine!
                            {        
                                player_array[amount-remaining] = new Player_Marine("Marine", HUMAN_START_DISTANCE + rand.nextInt(HUMAN_RAND_DISTANCE), MARINE_MESSAGES[rand.nextInt(6)]);
                            }
                            else if (response == 1) //Medic!
                                    player_array[amount-remaining] = new Player_Medic("Medic", HUMAN_START_DISTANCE + rand.nextInt(HUMAN_RAND_DISTANCE), MEDIC_MESSAGES[rand.nextInt(6)]);
                            else if (response == 2) //Flamer!
                                    player_array[amount-remaining] = new Player_Flamethrower("Flamethrower", HUMAN_START_DISTANCE + rand.nextInt(HUMAN_RAND_DISTANCE), FLAME_MESSAGES[rand.nextInt(6)]);
                            else if (response == 3)//Sniper!
                                    player_array[amount-remaining] = new Player_Sniper("Sniper", HUMAN_START_DISTANCE + rand.nextInt(HUMAN_RAND_DISTANCE), SNIPER_MESSAGES[rand.nextInt(6)]);
                            else if (response == 4) //Trooper!
                                    player_array[amount-remaining] = new Player_Trooper("Trooper", HUMAN_START_DISTANCE + rand.nextInt(HUMAN_RAND_DISTANCE), TROOPER_MESSAGES[rand.nextInt(6)]);
                            else if (response == 5)//Engineer!
                                    player_array[amount-remaining] = new Player_Engineer("Engineer", HUMAN_START_DISTANCE + rand.nextInt(HUMAN_RAND_DISTANCE), ENGINEER_MESSAGES[rand.nextInt(6)]);
                            remaining--;
                            if (remaining != 0)
                                System.out.println("A "+playertypes[response].getName()+" was added to your squad!\n"+remaining+" members remaining.");
                            else
                                {
                                    System.out.println("A "+playertypes[response].getName()+" was added to your squad!");
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
          System.out.println("** Press Enter to Continue. **");
          scan.nextLine();
          return player_array;
     }
     
     public Player[] selectPlayers(int amount, Player[] playertypes, int tech) //Chooses a specific amount of players, array should have one of each player type, tech are types available going from left...
     {
         Player[] player_array = new Player[amount];
         ArrayList<Integer> num_targets = new ArrayList<Integer>();
         int remaining = amount;
         System.out.println("Commander, choose "+amount+" of the following units to take into battle.  Type the number to add that unit to your squad:");
         for (int i=0; i<tech; i++)//Prints out the choices.  As you can see, it only counts up to the tech level (i.e. tech 2 will allow first 2 unit types, tech 5 allows first 5 unit types).  You should never make tech higher than length of playertypes array.
         {
             System.out.println(i+"- "+playertypes[i].getName()+": "+playertypes[i].getDescription());
             num_targets.add(i);
         }
         System.out.println();
         while (remaining > 0)
         {
                    try
                    {
                        int response = scan.nextInt();
                        if (num_targets.contains(response)) //We have a valid unit.
                        {
                            //I just realized objects in array store object references, not copies of objects unlike primitive data values.  We can't just write player_array[i] = playertypes[response]; here.
                            if (response == 0) //Marine!
                            {        
                                player_array[amount-remaining] = new Player_Marine("Marine", HUMAN_START_DISTANCE + rand.nextInt(HUMAN_RAND_DISTANCE), MARINE_MESSAGES[rand.nextInt(6)]);
                            }
                            else if (response == 1) //Medic!
                                    player_array[amount-remaining] = new Player_Medic("Medic", HUMAN_START_DISTANCE + rand.nextInt(HUMAN_RAND_DISTANCE), MEDIC_MESSAGES[rand.nextInt(6)]);
                            else if (response == 2) //Flamer!
                                    player_array[amount-remaining] = new Player_Flamethrower("Flamethrower", HUMAN_START_DISTANCE + rand.nextInt(HUMAN_RAND_DISTANCE), FLAME_MESSAGES[rand.nextInt(6)]);
                            else if (response == 3)//Sniper!
                                    player_array[amount-remaining] = new Player_Sniper("Sniper", HUMAN_START_DISTANCE + rand.nextInt(HUMAN_RAND_DISTANCE), SNIPER_MESSAGES[rand.nextInt(6)]);
                            else if (response == 4) //Trooper!
                                    player_array[amount-remaining] = new Player_Trooper("Trooper", HUMAN_START_DISTANCE + rand.nextInt(HUMAN_RAND_DISTANCE), TROOPER_MESSAGES[rand.nextInt(6)]);
                            else if (response == 5)//Engineer!
                                    player_array[amount-remaining] = new Player_Engineer("Engineer", HUMAN_START_DISTANCE + rand.nextInt(HUMAN_RAND_DISTANCE), ENGINEER_MESSAGES[rand.nextInt(6)]);
                            remaining--;
                            if (remaining != 0)
                                System.out.println("A "+playertypes[response].getName()+" was added to your squad!\n"+remaining+" members remaining.");
                            else
                            {
                                System.out.println("A "+playertypes[response].getName()+" was added to your squad!");
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
          System.out.println("** Press Enter to Continue. **");
          scan.nextLine();
          scan.nextLine();
          return player_array;
     }
     
     public Enemy[] generateEnemies(int[] odds, int num_enemies, int difficulty) //Receives a 9-index array of the odds of each enemy type spawning in each level, second argument is how many enemies there are.
     {                                                             //This uses the createEnemy function to turn an integer into an alien type.  The algorithm randomly generates enemies with a "threshold" array with nine "threshold" values between 0 and the "total" of all the percentages.  If the number generated is between two thresholds, the monster generated will have the same index as the second threshold.  If two thresholds are the same, then that particular monster will never be generated.
        int total = 0, randNum, startingdistance;
        Enemy[] enemies_list = new Enemy[num_enemies];
        int[] threshold = new int[9]; //If the randomly generated number is between the previous compartment's threshold and this threshold, the enemy generated will be equal to the compartment number.  If the previous compartment's threshold and this threshold is 0, then no enemy will be generated for that.
        
        for (int i=0; i<9; i++)//Fill total and threshold.
        {
            total += odds[i];
            threshold[i] = total;
        }
        
        for (int i=0; i<num_enemies; i++)
        {
            startingdistance = DIFFICULTY_DISTANCES[difficulty][0] + rand.nextInt(DIFFICULTY_DISTANCES[difficulty][1]);
            randNum = rand.nextInt(total);
            for (int j=0; j<9; j++)
            {
                
                if (j == 0)
                {
                    if (threshold[j] != 0 && randNum < threshold[j])
                        enemies_list[i] = createEnemy(j, startingdistance);
                }
                else
                {
                    if ((threshold[j-1] != threshold[j]) && //To check if there's supposed to be a 0% chance of a monster appearing.
                            (randNum >= threshold[j-1]) && //Check if it's above the previous threshold.
                            (randNum < threshold[j])) //Create an enemy with j index, add it to the enemy array at index i.
                        enemies_list[i] = createEnemy(j, startingdistance);
                }
            }
        }
        return enemies_list;
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
     
     public void printPlayers(Player[] parray)
     {
         System.out.println("=============PLAYERS:============");
         Player p;
         for (int i=0; i<parray.length; i++)
         {
             p = parray[i];
             p.printStatus();
         }
         System.out.println("=================================");
     }
     
     public void printPlayers(Player[] parray, int upto) //Prints first upto players in array.
     {
         System.out.println("=============PLAYERS:============");
         Player p;
         for (int i=0; i<upto; i++)
         {
             p = parray[i];
             p.printStatus();
         }
         System.out.println("=================================");
     }
     
     public void printEnemies(Enemy[] earray)
     {
         System.out.println("=============ENEMIES:============");
         Enemy e;
         for (int i=0; i<earray.length; i++)
         {
             e = earray[i];
             e.printStatus();
         }
         System.out.println("=================================");
     }
     
     public void startGame(Player[] players, Enemy[] enemies, String name, String broodname) //Provides initialization messages.
     {
         System.out.println("============="+name+" vs "+broodname+"============");
         System.out.println();
         printPlayers(players);
         System.out.println();
         printEnemies(enemies);
         System.out.println();
         System.out.println("** Press Enter to Continue. **");
          scan.nextLine();
         System.out.println("Ready...set...ATTACK!");
         System.out.println();
         System.out.println("** Press Enter to Start the Game! **");
          scan.nextLine();
     }
}
