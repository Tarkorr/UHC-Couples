package fr.tarkor.uhccouples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.tarkor.uhccouples.GGameCycle;;

public class Main extends JavaPlugin{
	
	public HashMap<Player, CustomScoreboardManager> sb = new HashMap<>();
	public static Main instance;
	
	public static Main getInstance(){
		return instance;
	}
	private List<Player> players = new ArrayList<>();
	private List<Team> teams = new ArrayList<>();
	private List<Location> spawns = new ArrayList<>();
	private GState state;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		getCommand("auto").setExecutor(this);
		getCommand("revive").setExecutor(this);
		ConfigurationSection section = getConfig().getConfigurationSection("teams");
		for(String team : section.getKeys(false))
		{
			String name = section.getString(team + ".name");
			String tag = section.getString(team + ".color").replace("&", "§");
			byte data = (byte) section.getInt(team + ".data");
			teams.add(new Team(name, tag, data));
		}
		System.out.println(teams.size() + " teams ont été chargés");
		
		setState(GState.WAITING);
		instance = this;
		new ScoreboardRunnable().runTaskTimer(this, 0L, 20L);
		World world = Bukkit.getWorld("world");
		Random rtp = new Random();
		//negative / negative
		/*
		spawns.add(new Location(world, Math.negateExact(rtp.nextInt(2500)), 250, Math.negateExact(rtp.nextInt(2500))));
		spawns.add(new Location(world, Math.negateExact(rtp.nextInt(2500)), 250, Math.negateExact(rtp.nextInt(5000))));
		*/
		spawns.add(new Location(world, 2500, 250, 2500));
		spawns.add(new Location(world, 2500, 250, 2500));
		spawns.add(new Location(world, Math.negateExact(rtp.nextInt(5000)), 250, Math.negateExact(rtp.nextInt(2500))));
		spawns.add(new Location(world, Math.negateExact(rtp.nextInt(5000)), 250, Math.negateExact(rtp.nextInt(5000))));
		//positive / negative
		spawns.add(new Location(world, rtp.nextInt(2500), 250, Math.negateExact(rtp.nextInt(2500))));
		spawns.add(new Location(world, rtp.nextInt(2500), 250, Math.negateExact(rtp.nextInt(5000))));
		spawns.add(new Location(world, rtp.nextInt(5000), 250, Math.negateExact(rtp.nextInt(2500))));
		spawns.add(new Location(world, rtp.nextInt(5000), 250, Math.negateExact(rtp.nextInt(5000))));
		//negative / positive
		spawns.add(new Location(world, Math.negateExact(rtp.nextInt(2500)), 250, rtp.nextInt(2500)));
		spawns.add(new Location(world, Math.negateExact(rtp.nextInt(2500)), 250, rtp.nextInt(5000)));
		spawns.add(new Location(world, Math.negateExact(rtp.nextInt(5000)), 250, rtp.nextInt(2500)));
		spawns.add(new Location(world, Math.negateExact(rtp.nextInt(5000)), 250, rtp.nextInt(5000)));
		//positive / positive
		spawns.add(new Location(world, rtp.nextInt(2500), 250, rtp.nextInt(2500)));
		spawns.add(new Location(world, rtp.nextInt(2500), 250, rtp.nextInt(5000)));
		spawns.add(new Location(world, rtp.nextInt(5000), 250, rtp.nextInt(2500)));
		spawns.add(new Location(world, rtp.nextInt(5000), 250, rtp.nextInt(5000)));
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new GPlayerListeners(this), this);
		pm.registerEvents(new GDamageListeners(this), this);
		
		
		WorldBorder wb = world.getWorldBorder();
		wb.setCenter(0.000, 0.000);
		wb.setSize(10000);
		wb.setDamageAmount(5);
		int time1 = 900;
		int time2 = 1200;
		int time3 = 1800;
		//int time4 = 2700;
		//int time5 = 3600;
		double wb1 = 10000;
		double wb2 = 8000;
		double wb3 = 5000;
		double wb4 = 2000;
		double wb5 = 600;
		double wb6 = wb1 - wb2;
		double wb7 = wb6 / time1;
		double wb8 = wb2 - wb3;
		double wb9 = wb8 / time2;
		double wb10 = wb3 - wb4;
		double wb11 = wb10 / time2;
		double wb12 = wb4 - wb5;
		double wb13 = wb12 / time3;
		int totaltime = time1 + time2 + time2 + time3;
		System.out.println(wb7);
		System.out.println(wb9);
		System.out.println(wb11);
		System.out.println(wb13);
		System.out.println("durée approximative de la partie " + totaltime);
		System.out.println("didier des champs ptdr ==========> " + Bukkit.getWorld("world").getWorldBorder().getSize());
		
		
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			@Override
			public void run() {
				if(GGameCycle.timer >= 1 && GGameCycle.timer <= 901){
				if(wb.getSize() >= 8000){
				wb.setSize(wb.getSize() - wb7);
					}
				}
				if(GGameCycle.timer >= 901 && GGameCycle.timer <= 2101){
				if(wb.getSize() >= 5000){
				wb.setSize(wb.getSize() - wb9);	
					}
				}
				if(GGameCycle.timer >= 2101 && GGameCycle.timer <= 3301){
				if(wb.getSize() >= 2000){
				wb.setSize(wb.getSize() - wb11);	
					}
				}
				if(GGameCycle.timer >= 3301){
				if(wb.getSize() >= 600){
				wb.setSize(wb.getSize() - wb13);	
					}
				}
				if(GGameCycle.timer >= 5200){
				wb.setSize(600);
				}
			}
		}, 0, 20);
	}
	
	public void setState(GState state){
		this.state = state;
	}
	public boolean isState(GState state){
		return this.state == state;
	}
	
	public List<Player> getPlayers(){
		return players;
	}
	
	public List<Location> getSpawns(){
		return spawns;
	}
	public void eliminate(Player player) {
		if(players.contains(player))players.remove(player);
		player.setGameMode(GameMode.SPECTATOR);
		player.sendMessage("vous avez perdu");
		checkWin();
	}
	public void checkWin() {
		if(players.size() >= 1){
			Player winner = players.get(0);
			Bukkit.broadcastMessage("Partie terminé");
			Bukkit.broadcastMessage(winner.getName() + " a gagné");
		}
		
	}
	public void addPlayer(Player player, Team team){
		String tag = team.getTag() + team.getName();
		if(team.getPlayers().contains(player)){
			player.sendMessage("vous êtes déjà dans l'équipe " + tag);
			return;
		}
		if(team.getSize() >= 2){
			player.sendMessage("L'équipe " + tag + " §fest pleine");
			return;
		}
		team.addPlayer(player);
		player.setPlayerListName(team.getTag() + "{" + team.getName() + "} " + "§r" + player.getName());
		player.sendMessage("Vous rejoignez l'équipe " + tag);
	}
	public void removePlayer(Player player){
		for(Team team : teams){
			if(team.getPlayers().contains(player));
			team.removePlayer(player);
		}
	}
	public void autoTeam(Player player){
		for(Team team : teams){
			if(team.getSize() < 2){
				addPlayer(player, team);
				break;
			}
		}
		
	}
	public List<Team> getTeams(){
		return teams;
	}
	public Team getTeam(Player player){
        for(Team team : teams){
            if(!team.getPlayers().contains(player))
            return team;
        }
        return null;
    }
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(label.equalsIgnoreCase("Auto") && sender instanceof Player)
		{
			
		}
		if(label.equalsIgnoreCase("revive") && sender instanceof Player)
		{
			Player player = (Player) sender;
			ItemStack Revive = new ItemStack(Material.BEACON, 1);
			ItemMeta RM = Revive.getItemMeta();
			RM.setDisplayName("§aRessuscite votre partenaire");
			RM.addEnchant(Enchantment.DURABILITY, 1, true);
			RM.setLore(Arrays.asList("§dCet artefact a été créé il y a", "§d12 ans, il ressemble de près et de loin", "§dà une balise mais ce n'en ai pas une."));
			RM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			Revive.setItemMeta(RM);
			player.getInventory().addItem(Revive);
		}
		return false;
	}
		
}
	
	

