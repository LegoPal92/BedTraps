package com.bethkefamily.BedTraps.Traps;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.material.Bed;
import org.bukkit.scheduler.BukkitTask;

import com.bethkefamily.BedTraps.BedTraps;

public class BedTrap implements Trap {

	private static HashMap<Location, BedTrap> traps;
	
	private Location one;
	private Location two;
	
	public BedTrap(){
		traps = new HashMap<>();
	}
	/**
	 * Inserts the bed into the HashMap. After checking to be sure that it gets both halves of the bed.
	 * @param location - the location in which the bed is placed.
	 * @return true if successful, false if failed.
	 */
	@Override
	public boolean spawn(Location location) {
		if (traps.containsKey(location)){
			return false;
		}
		if (location.getBlock().getType() == Material.BED
				|| location.getBlock().getType() == Material.BED_BLOCK){//I check for both bed and bed_block because not always
			 															//do I get the same result in previous builds.
			Bed bed = (Bed) location.getBlock().getState().getData();
			
			if (bed.isHeadOfBed()){//Get the location of the other half of the bed.
				two = location.getBlock().getRelative(bed.getFacing().getOppositeFace()).getLocation();
			} else {
				two = location.getBlock().getRelative(bed.getFacing()).getLocation();
			}
			traps.put(location, this);
			traps.put(two, this);
		}
		return true;
	}
	/**
	 * Removes the bed from the HashMap. Gets both blocks corresponding to the bed and removes them both.
	 * @param location - the location in which the bed is located.
	 * @return true if the removal is successful, false if there was no trap there.
	 */
	@Override
	public boolean remove(Location location) {
		if (traps.containsKey(location)){
			return false;
		}
		if (location.getBlock().getType() == Material.BED
				|| location.getBlock().getType() == Material.BED_BLOCK){//I check for both bed and bed_block because not always
																		//do I get the same result in previous builds.
			Bed bed = (Bed) location.getBlock().getState().getData();
			
			traps.remove(location);
			traps.remove(two);
		}
		return true;
	}
	/**
	 * 
	 * @param location - the location of the bedtrap.
	 * @return a Bedtrap at the specified location.
	 */
	public static BedTrap getTrap(Location location){
		return traps.get(location);
	}
	
	public HashMap<Location, Trap> getTraps(){
		return traps;
	}
	
	/**
	 * Trigger the explosion process at the location of the bedtrap. 
	 * @param location - The location of the BedTrap.
	 */
	@Override
	public void trigger(Location location){
		BedTrapRunnable btr = new BedTrapRunnable(location);
		BukkitTask bt = BedTraps.getInstance().getServer().getScheduler().runTaskTimer(BedTraps.getInstance(), btr, 0L, 20L);
		btr.setId(bt.getTaskId());
	}
	/**
	 * 
	 * BedTrapRunnable is the runnable in which the trap executes. 
	 * @author Paul
	 *
	 */
	private static class BedTrapRunnable implements Runnable{

		private BedTrap trap;
		private int seconds = 3;
		private int id;
		public BedTrapRunnable(BedTrap trap){
			this.trap = trap;
		}
		
		/**
		 * Run the code creating the smoke and click for three seconds. After the third second, it creates the explosion.
		 * 
		 */
		@Override
		public void run() {
			if (seconds == 0){
				location.getWorld().createExplosion(location, 4);
				seconds--;
				return;
			}
			if (seconds <0){
				Bukkit.getScheduler().cancelTask(id);
				return;
			}
				trap.one.getWorld().playEffect(location, Effect.SMOKE, 0);
				trap.one.getWorld().playEffect(second_half, Effect.SMOKE, 0);
				trap.one.getWorld().playSound(location, Sound.BLOCK_STONE_BUTTON_CLICK_ON, 10, 0);
			
			
			seconds--;
			
		}
		
		/**
		 * set the id of the runnable
		 * @param id The integer id of the runnable.
		 */
		public void setId(int id){
			this.id = id;
		}
		
	}
}
