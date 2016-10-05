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

public class YouCantRunForever_Test {

    public static void main(String[] args) 
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please input your name here:");
        String name = "";
        while(true)
        {
            try
            {
                name = scan.next();
                break;
            }
            catch (Exception e)
            {
                System.out.println("Invalid input.");
                scan.next();
            }
        }
        Random rand = new Random();
        
        boolean on = true;
        //Create game
        while (on)
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
            System.out.println("|         ALIEN ESCAPE          |");
            System.out.println("=================================");
            System.out.println();
            System.out.println("Welcome, Commander "+name+"!  Please select a mode below.");
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("c-Campaign\nl-Level Select\ns-Skirmish\nq-Quit");
            String response;

            while(true)
            {
                try
                {
                    response = scan.next();
                    if("c".equals(response)) //Campaign
                    {
                        Campaign campaign = new Campaign(1, name);
                        campaign.playCampaign();
                        break;
                    }
                    else if("l".equals(response))//Level select
                    {
                        LevelSelect select = new LevelSelect(name);
                        select.selectLevel();
                        break;
                    }
                    else if("s".equals(response))//Skirmish
                    {
                        Skirmish skirmish = new Skirmish(name);
                        skirmish.playSkirmish();
                        break;
                    }
                    else if("q".equals(response)) //Quit
                    {
                        on = false;
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
        }
        System.out.println("Thanks for playing!");
        System.out.println("** Press Enter to Quit. **");
        scan.nextLine();
        scan.nextLine();
    }
        
}
