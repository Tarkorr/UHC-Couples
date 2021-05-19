package fr.tarkor.uhccouples;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;


public class GDamageListeners implements Listener {
	
	private Main main;
	
	public GDamageListeners(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event)
	{
		Entity victim = event.getEntity();
		DamageCause dc = event.getCause();
		if(main.isState(GState.TP)){
			if (dc == DamageCause.FALL){
			 event.setCancelled(true);
			 return;
				}
			}
        if(victim instanceof Player)
        {
            Player player = (Player)victim;
            if(player.getHealth() <= event.getDamage())
            {
                event.setDamage(0);
                main.eliminate(player);
            	}
        	}
        }
	//team.players.get(0)
	
	@EventHandler
	public void onPvp(EntityDamageByEntityEvent event){

		Entity victim = event.getEntity();
		Entity damager = event.getDamager();
		
		if(victim instanceof Player){
			if(damager instanceof Player){
		if(!main.isState(GState.PVP)){
			event.setCancelled(true);
			return;
			}
		}
	}
		if(damager instanceof Player){
		if(victim instanceof Player){
			if(main.getTeam((Player)event.getEntity()) == main.getTeam((Player)event.getDamager())){
				event.getDamager().sendMessage("Vous ne pouvez pas attaquer votre partenaire");
				event.setCancelled(true);
				return;
				}
				Player player = (Player)victim;
				Player killer = player;

			if(player.getHealth() <= event.getDamage())
			{
				if(damager instanceof Player) killer = (Player) damager;
				if(damager instanceof Arrow)
				{	
					Arrow arrow = (Arrow)damager;
					if(arrow.getShooter() instanceof Player) killer = (Player)arrow.getShooter();
				}
				killer.sendMessage("tu viens de tuer§r" + player.getName());
				event.setDamage(0);
				main.eliminate(player);
				main.checkWin();
				}
			}
		}
	}
}

