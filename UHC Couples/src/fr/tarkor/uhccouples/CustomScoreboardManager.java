package fr.tarkor.uhccouples;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class CustomScoreboardManager implements ScoreboardManager{
	
	
	public Player player;
	public Scoreboard scoreboard;
	public Objective objective;
	public String name = "test.scoreboard";
	
	
	public CustomScoreboardManager(Player player) {
		this.player = player;
		this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		
		if(Main.getInstance().sb.containsKey(player)) return;
		this.name = "sb." + new Random().nextInt(99999);
		this.objective = scoreboard.registerNewObjective(name, "dummy");
		objective.setDisplayName("§1-----§n§9UHC Couples§r§1-----");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		//le joueur a pas de scoreboard "sad"
		Main.getInstance().sb.put(player, this);
		
		
	}


	@Override
	public Scoreboard getMainScoreboard() {
		return scoreboard;
	}


	@Override
	public Scoreboard getNewScoreboard() {
		return null;
	}


	public void refresh() {
		for(String ligne : scoreboard.getEntries()){
			if(ligne.contains("§3Border")){
				scoreboard.resetScores(ligne);
				
				String lastligne = ligne.split(":")[0];
				String newligne = lastligne + ": ";
				
				objective.getScore(newligne).setScore(5);
			}
			
			if(ligne.contains("§6")){
				scoreboard.resetScores(ligne);
				
				double newligne = Bukkit.getWorld("world").getWorldBorder().getSize() / 2;
				BigDecimal number = new BigDecimal(newligne);
				number = number.setScale(1, RoundingMode.FLOOR);
				
				objective.getScore("§6" + number + " > 300").setScore(4);
			}
			if(ligne.contains("§3Timer")){
				scoreboard.resetScores(ligne);
				
				String lastligne = ligne.split(":")[0];
				String newligne = lastligne + ": " + GGameCycle.hours + "h " + GGameCycle.minutes + "m "+ GGameCycle.SCtimer + "s" ;
				
				objective.getScore(newligne).setScore(3);
			}
			if(ligne.contains("§3Online")){
				scoreboard.resetScores(ligne);
				
				String lastligne = ligne.split(":")[0];
				String newligne = lastligne + ": " + Bukkit.getOnlinePlayers().size();
				
				objective.getScore(newligne).setScore(2);
				}
			}
		}


	public void sendLine() {
		objective.getScore("§3Border : ").setScore(5);
		objective.getScore("§6").setScore(5);
		objective.getScore("§3Timer : 0").setScore(3);
		objective.getScore("§3Online : 0").setScore(2);
		objective.getScore("§3Kills : ").setScore(1);
		objective.getScore("Made By Tarkor#5087").setScore(0);
	}
	
	public void setScoreboard() {
		player.setScoreboard(scoreboard);
	}
	
}
