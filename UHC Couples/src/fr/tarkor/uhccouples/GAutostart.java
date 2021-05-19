package fr.tarkor.uhccouples;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GAutostart extends BukkitRunnable{
	
	private int timer = 10;
	private Main main;

	public GAutostart(Main main) {
		this.main = main;
	}

	@Override
	public void run() {
		
		for(Player pls : main.getPlayers()){
			pls.setLevel(timer);
			
		}
		if(timer == 10 || timer == 5 || timer == 4 || timer == 3 || timer == 2 || timer == 1)
		{
			Bukkit.broadcastMessage("Lancement du jeu dans : §e" + timer + "§6s");
		}
		
		if(timer == 0)
		{
			Bukkit.broadcastMessage("Lancement de la partie, PvP actif dans 30 secondes");
			main.setState(GState.TP);
			Bukkit.broadcastMessage("GState TP");
			
			for(int i = 0; i < main.getPlayers().size(); i++)
			{
				Player player = main.getPlayers().get(i);
				Location spawn = main.getSpawns().get(i);
				player.teleport(spawn);
				player.setFoodLevel(20);
				player.setHealth(20);
				player.setLevel(0);
				player.getInventory().clear();
				player.setGameMode(GameMode.SURVIVAL);
				player.updateInventory();

			}
			
			GGameCycle cycle = new GGameCycle(main);
			cycle.runTaskTimer(main, 0, 20);
			cancel();
		}
			timer--;
		
	}
	
}
