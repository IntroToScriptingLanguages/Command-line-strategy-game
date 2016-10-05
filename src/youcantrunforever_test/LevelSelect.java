package youcantrunforever_test;

public class LevelSelect extends Mode{
    
    
    
    public LevelSelect(String name)
    {
        super.name = name;
    }
    
    public void selectLevel()
    {
        
        Campaign campaign;
        while (true)
        {
            System.out.println("Input level code.  Press 0 to quit.");
           try
            {
                int code = scan.nextInt();
                boolean found = false;
                if (code == 0) //Quits the program here.
                    return;
                
                for (int i=1; i<=20; i++)
                {
                    if (LEVEL_CODES[i] == code) //Found level code!
                    {
                        campaign = new Campaign(i,name);
                        campaign.playCampaign();
                        return;
                    }
                }
                System.out.println("Invalid level code.");
           }
           catch (Exception e)
          {
               System.out.println("Invalid level code.");
              scan.next();
           }
        }
    }
}
