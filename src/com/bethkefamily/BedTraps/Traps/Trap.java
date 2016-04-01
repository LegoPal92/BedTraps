package com.bethkefamily.BedTraps.Traps;

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
	 * Trigger the trap at the specified location.
	 * @param location The location of the trap to trigger.
	 */
	public void trigger(Location location);
	

}
