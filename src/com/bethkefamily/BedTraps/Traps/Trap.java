package com.bethkefamily.BedTraps.Traps;

import java.util.HashMap;

import org.bukkit.Location;

public interface Trap {
	
	
	/**
	 * Create the trap in the world. Set up the initial items needed, add to the trap HashMap.
	 * @param location - The location in which to create the trap.
	 * @return True if the spawn was successful, false if otherwise.
	 */
	public boolean spawn(Location location);
	
	/**
	 * Remove the trap when from the HashMap, nullifying it as a trap. Called on detonation.
	 * @param location - The location in which the trap is located.
	 * @return True if the spawn was successful, false if otherwise.
	 */
	public boolean remove(Location location);
	
	/**
	 * 
	 * @return the HashMap of traps. It consists of The location in which the trap is as well as the type of trap.
	 */
	public HashMap<Location, TrapType> getTraps();
	
	/**
	 * Trigger the trap at the specified location.
	 * @param location The location of the trap to trigger.
	 */
	public void trigger(Location location);
	
	/**
	 * The TrapType is the form of trap that is created.
	 * @author Paul
	 *
	 */
	public static enum TrapType{
		BEDTRAP,
		;
	}
}
