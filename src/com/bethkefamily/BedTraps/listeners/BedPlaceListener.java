package com.bethkefamily.BedTraps.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.bethkefamily.BedTraps.Traps.BedTrap;

public class BedPlaceListener implements Listener {
	
	/**
	 * The purpose of this method is to setup a BedTrap. It checks to see
	 * if the block placed is a Bed, and provided it is, the BedTrap is created.
	 * 
	 * @param event - The BlockPlaceEvent in which a bed is being placed. 
	 */
	@EventHandler
	public void onBedPlace(BlockPlaceEvent event){
		if (event.getBlock().getType() != Material.BED_BLOCK ||
				event.getBlock().getType() != Material.BED){ //I check for both bed and bed_block because not always
															 //do I get the same result in previous builds.
			return;
		}
		BedTrap bt = new BedTrap();
		bt.spawn(event.getBlock().getLocation());
		
	}
	
}
