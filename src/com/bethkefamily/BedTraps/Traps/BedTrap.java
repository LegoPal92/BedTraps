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
			Bed bed = (Bed) location.getBlock();
			Location second_half;
			if (bed.isHeadOfBed()){//Get the location of the other half of the bed.
				second_half = location.getBlock().getRelative(bed.getFacing().getOppositeFace()).getLocation();
			} else {
				second_half = location.getBlock().getRelative(bed.getFacing()).getLocation();
			}
			traps.put(location, this);
			traps.put(second_half, this);
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
			Bed bed = (Bed) location.getBlock();
			Location second_half;
			if (bed.isHeadOfBed()){//Get the location of the other half of the bed.
				second_half = location.getBlock().getRelative(bed.getFacing().getOppositeFace()).getLocation();
			} else {
				second_half = location.getBlock().getRelative(bed.getFacing()).getLocation();
			}
			traps.remove(location);
			traps.remove(second_half);
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

		private Location location;
		private int seconds = 3;
		private int id;
		public BedTrapRunnable(Location trap){
			location = trap;
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
			
				Bed bed = (Bed) location.getBlock();
				Location second_half;
				if (bed.isHeadOfBed()){//Get the location of the other half of the bed.
					second_half = location.getBlock().getRelative(bed.getFacing().getOppositeFace()).getLocation();
				} else {
					second_half = location.getBlock().getRelative(bed.getFacing()).getLocation();
				}
				location.getWorld().playEffect(location, Effect.SMOKE, 0);
				location.getWorld().playEffect(second_half, Effect.SMOKE, 0);
				location.getWorld().playSound(location, Sound.BLOCK_STONE_BUTTON_CLICK_ON, 10, 0);
			
			
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
