package youcantrunforever_test;

public abstract class Ability {
    protected Player user;
    protected String name;
    protected boolean isTargeted = false;
    
    public abstract GameAgent[] scanForTargets(Player[] players, Enemy[] enemies); //Checks to see if ability has valid targets.  Returns GameAgent array
    
    public String getName()
    {
        return name;
    }
    
    public boolean isTargeted() //if this is a targeted ability, this is by default false
    {
        return isTargeted;
    }
}
