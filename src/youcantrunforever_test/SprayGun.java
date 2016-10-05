/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 *  Program written by Mark Sheehan
 *  02/24/14
 *  CS 101 Section 2
 */
package youcantrunforever_test;

/**
 *
 * @author PSTogether
 */
public interface SprayGun //No accuracy- weapon always hits.
{
    public void burn(Player player, Enemy[] enemies); 
    public int getRemainingShots();
    public boolean isEmpty();
    public int getRange();
    public void reload();
    public int getMaxShots();
    public String getName();
}
