package fr.tarkor.uhccouples;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

public class GPlayerListeners implements Listener {
	
	private Main main;
	
	public GPlayerListeners(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		ItemStack None = new ItemStack(Material.AIR);
		Player player = event.getPlayer();
		Location spawn = new Location(Bukkit.getWorld("world"), 0.000, 14.000, 0.000, 0.0f, 0.0f);
		CustomScoreboardManager board = new CustomScoreboardManager(player);
		board.sendLine();
		board.setScoreboard();
		player.removePotionEffect(PotionEffectType.ABSORPTION);
		player.giveExp(-10000);
		player.getInventory().setHelmet(None);
		player.getInventory().setChestplate(None);
		player.getInventory().setLeggings(None);
		player.getInventory().setBoots(None);
		player.teleport(spawn);
		player.getInventory().clear();
		player.setFoodLevel(20);
		player.setHealth(20);
		player.setLevel(0);
		for(Team team : main.getTeams()){
			player.getInventory().addItem(team.getIcon());
		}
		if(!main.isState(GState.WAITING)){
			player.setGameMode(GameMode.SPECTATOR);
			player.sendMessage("le jeu à déjà démarré !");
			event.setJoinMessage(null);
			return;
		}
		
		if(!main.getPlayers().contains(player)) main.getPlayers().add(player);
		player.setGameMode(GameMode.SURVIVAL);
		event.setJoinMessage("§7[§5UHC COUPLES§7] §2" + player.getName() + "§3 joined the game ! <" + main.getPlayers().size() + "/" + Bukkit.getMaxPlayers() + ">");
		//    ICI
		//  || || ||
		//  \/ \/ \/
		if(main.isState(GState.WAITING) && main.getPlayers().size() == 2)
		{
			GAutostart start = new GAutostart(main);
			start.runTaskTimer(main, 0, 20);
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event){
		Player player = event.getPlayer();
		if(main.getPlayers().contains(player))
			{
				main.getPlayers().remove(player);
			}
		event.setQuitMessage("§7[§5UHC COUPLES§7] §2" + player.getName() + "§3 left <" + main.getPlayers().size() + "/" + Bukkit.getMaxPlayers() + ">");
		main.removePlayer(player);
		main.checkWin();
		
	}
	@EventHandler
	public void onPlace(BlockPlaceEvent event){
	if(main.isState(GState.WAITING)){
		event.setCancelled(true);
		return;
		}
	}
	@EventHandler
	public void onBreak(BlockBreakEvent event){
	if(main.isState(GState.WAITING)){
		event.setCancelled(true);
		return;
		}
	}
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event){
	if(main.isState(GState.WAITING)){
		event.setCancelled(true);
		return;
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent event){
		
		Player player = event.getPlayer();
		ItemStack it = event.getItem();
		if(main.isState(GState.WAITING)){
		if(it != null && it.getType() == Material.WOOL){
			
			for(Team team : main.getTeams()){
				
				if(team.getWoolData() == it.getData().getData()){
					main.addPlayer(player, team);
					continue;
				}
				if(team.getPlayers().contains(player)){
					team.getPlayers().remove(player);
				}
			}
		}
	}
		ItemStack None = new ItemStack(Material.AIR);
		ItemStack Revive = new ItemStack(Material.BEACON, 1);
		ItemMeta RM = Revive.getItemMeta();
		RM.setDisplayName("§aRessuscite votre partenaire");
		RM.addEnchant(Enchantment.DURABILITY, 1, true);
		RM.setLore(Arrays.asList("§dCet artefact a été créé il y a", "§d12 ans, il ressemble de près et de loin", "§dà une balise mais ce n'en ai pas une."));
		RM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		Revive.setItemMeta(RM);
		
		if(it != null && it.getType() == Material.BEACON){
			if(it.isSimilar(Revive)){
			event.setCancelled(true);
			player.sendMessage("un jour peut être");
			player.getInventory().setItemInHand(None);
			if(player instanceof Player){
				if(player == main.getPlayers().get(0)){
				player.sendMessage("§a"+ main.getPlayers().get(1));
					} 
				if(player == main.getPlayers().get(1)) {
				player.sendMessage("§a"+ main.getPlayers().get(0));
				if(main.getPlayers().get(0).getGameMode() == GameMode.SPECTATOR){
					Player lerea0 = main.getPlayers().get(0);
					player.sendMessage("Votre partenaire viens d'être ressuscité en ");
					lerea0.teleport(new Location(Bukkit.getWorld("world"), 0, 6, 0));
					lerea0.setHealth(5);
					lerea0.setGameMode(GameMode.SURVIVAL);
					}
				}
			}
			return;
				}
			}
		}
	@EventHandler
	public void onRegeneration(EntityRegainHealthEvent event){
		if(event.getRegainReason() == RegainReason.SATIATED){
			event.setCancelled(true);
			return;
		}
		event.setCancelled(false);
	}
}

