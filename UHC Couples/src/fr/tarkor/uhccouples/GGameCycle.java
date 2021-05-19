package fr.tarkor.uhccouples;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class GGameCycle extends BukkitRunnable{
	
	private Main main;
	static int timer = 0;
	static int SCtimer = 0;
	static int minutes = 0;
	static int hours = 0;
	
	public GGameCycle(Main main) {
		this.main = main;
	}
	
	@Override
	public void run() {
		if(timer == 600 || timer == 1200 || timer == 1800 || timer == 2400 || timer == 3000 || timer == 3600 || timer == 4200 || timer == 1 || timer == 2|| timer == 3|| timer == 4|| timer == 5|| timer == 6|| timer == 7|| timer == 8|| timer == 9|| timer == 10){
				Random r = new Random();
				double x = 0.0 + r.nextInt(1000);
				int y = 0 + r.nextInt(120);
				double z = 0.0 + r.nextInt(1000);
				
				Location spawnChest = new Location(Bukkit.getWorld("world"), x, y, z);
				spawnChest.getBlock().setType(Material.CHEST);
				
				Chest chest = (Chest) spawnChest.getBlock().getState();
				Inventory chestMenu = chest.getInventory();
				
				int alea = r.nextInt(5);
				switch(alea){
				case 0:
					chestMenu.setItem(r.nextInt(chestMenu.getSize()), new ItemStack(Material.DIAMOND, r.nextInt(5)));
					break;
				case 1: case 5:
					ItemStack BonToutou = new ItemStack(Material.BONE, 1);
					ItemMeta BTM = BonToutou.getItemMeta();
					BTM.setDisplayName("Bon Toutou");
					BonToutou.setItemMeta(BTM);
					chestMenu.setItem(r.nextInt(chestMenu.getSize()), BonToutou);
					break;
				case 2:
					chestMenu.setItem(r.nextInt(chestMenu.getSize()), new ItemStack(Material.GOLDEN_APPLE, r.nextInt(4)));
					break;
				case 3:
					ItemStack Revive = new ItemStack(Material.BEACON, 1);
					ItemMeta RM = Revive.getItemMeta();
					RM.setDisplayName("§aRessuscite votre partenaire");
					RM.addEnchant(Enchantment.DURABILITY, 1, true);
					RM.setLore(Arrays.asList("§dCet artefact a été créé il y a", "§d12 ans, il ressemble de près et de loin", "§dà une balise mais ce n'en ai pas une."));
					RM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					Revive.setItemMeta(RM);
					chestMenu.setItem(r.nextInt(chestMenu.getSize()), Revive);
					break;
				case 4:
					chestMenu.setItem(r.nextInt(chestMenu.getSize()), new ItemStack(Material.BREWING_STAND_ITEM, 1));
					break;
				default:
					chest.getWorld().createExplosion(chest.getLocation(), 1);
					break;
					
				
				}
				Bukkit.broadcastMessage("un coffre vient d'apparaître en X: " + x + " Y: " + y + " Z: " + z );
		}
		
		
		if(timer == 0)
		{
			Bukkit.broadcastMessage("PvP dans 20 minutes");
		}
		if(timer == 600 )
		{
			Bukkit.broadcastMessage("PvP dans 10 minutes");
		}
		
		if(timer == 1195 || timer == 1196 || timer == 1197 || timer == 1198 || timer == 1199 || timer == 1200 )
		{
			Bukkit.broadcastMessage("PvP dans " + Math.subtractExact(1200, timer) + "s");
		}
		
		if(timer == 1200){
				main.setState(GState.PVP);
				Bukkit.broadcastMessage("GState PVP");
				Bukkit.broadcastMessage("pvp actif");
		}
		
		if(timer == 15){
			main.setState(GState.PLAYING);
			Bukkit.broadcastMessage("GState Playing");
		}
		if(SCtimer == 60){
			minutes++;
			SCtimer = 0;
		}
		if(minutes == 60){
			hours++;
			minutes = 0;
		}
		SCtimer++;
		timer++;
	}
}
