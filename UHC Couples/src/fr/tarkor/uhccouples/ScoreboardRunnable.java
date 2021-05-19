package fr.tarkor.uhccouples;

import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ScoreboardRunnable extends BukkitRunnable{
	

	@Override
	public void run() {
		for(Entry<Player, CustomScoreboardManager> scoreboard : Main.getInstance().sb.entrySet()){
			CustomScoreboardManager board = scoreboard.getValue();
			board.refresh();
		}
	}
	
}
