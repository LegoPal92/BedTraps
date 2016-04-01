package com.bethkefamily.BedTraps;

import org.bukkit.plugin.java.JavaPlugin;

import com.bethkefamily.BedTraps.listeners.BedClickListener;
import com.bethkefamily.BedTraps.listeners.BedPlaceListener;

public class BedTraps {
	
	private static JavaPlugin jp;
	/**
	 * The constructor will take the @link{org.bukkit.plugin.java.JavaPlugin} as an argument.
	 * Register the events in the two listener classes.
	 * @param jp The JavaPlugin in which this is to be running.
	 */
	public BedTraps(JavaPlugin javaplugin){
		jp = javaplugin;
		jp.getServer().getPluginManager().registerEvents(new BedPlaceListener(), jp);
		jp.getServer().getPluginManager().registerEvents(new BedClickListener(), jp);
		
	}
	
	/**
	 * @return The instance of the JavaPlugin.
	 */
	public static JavaPlugin getInstance(){
		return jp;
	}
}
