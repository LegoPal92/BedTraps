package me.legopal92.example.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.legopal92.example.Traps.BedTrap;

public class BedClickListener implements Listener {

	/**
	 * Trigger the trap on click. Provided it is a trapped bed.
	 * Cancels the event if the block is a trapped bed.
	 * @param event The PlayerInteractEvent which we are to check for traps.
	 */
	@EventHandler
	public void onBedClick(PlayerInteractEvent event){
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK){
			return;
		}
		
		if (event.getClickedBlock().getType() == Material.BED
				|| event.getClickedBlock().getType() == Material.BED_BLOCK){//I check for both bed and bed_block because not always
			 																//do I get the same result in previous builds.
			BedTrap bt = new BedTrap();
			if (!bt.getTraps().containsKey(event.getClickedBlock().getLocation())){//Checking if the block is a trap.
				return;
			}
			event.setCancelled(true);
			bt.trigger(event.getClickedBlock().getLocation());
		}
	}
}
