package fr.tarkor.uhccouples;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Team {
	private String name;
	private String tag;
	private byte woolData;
	List<Player> players = new ArrayList<>();
	
	public Team(String name, String tag, byte wooldata) {
		this.name = name;
		this.tag = tag;
		this.woolData = wooldata;
	}
	
	public ItemStack getIcon(){
		ItemStack i = new ItemStack(Material.WOOL, 1, woolData);
		ItemMeta iM = i.getItemMeta();
		iM.setDisplayName("rejoindre la team " + tag + name);
		i.setItemMeta(iM);
		return i;
	}
	public void addPlayer(Player player){
		players.add(player);
	}
	public void removePlayer(Player player){
		players.remove(player);
	}
	
	public List<Player> getPlayers(){
		return players;
	}
	public int getSize(){
		return players.size();
	}

	public String getName() {
		return name;
	}

	public String getTag() {
		return tag;
	}

	public byte getWoolData() {
		return woolData;
	}
	
}
