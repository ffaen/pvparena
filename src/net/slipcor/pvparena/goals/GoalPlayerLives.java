package net.slipcor.pvparena.goals;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import net.slipcor.pvparena.arena.Arena;
import net.slipcor.pvparena.core.Debug;
import net.slipcor.pvparena.listeners.PlayerListener;
import net.slipcor.pvparena.neworder.ArenaGoal;
import net.slipcor.pvparena.runnables.InventoryRestoreRunnable;

/**
 * <pre>Arena Goal class "PlayerLives"</pre>
 * 
 * The first Arena Goal. Players have lives. When every life is lost, the player
 * is teleported to the spectator spawn to watch the rest of the fight.
 * 
 * @author slipcor
 * 
 * @version v0.9.0
 */

public class GoalPlayerLives extends ArenaGoal {
	public GoalPlayerLives() {
		super("PlayerLives");
		db = new Debug(100);
	}

	HashMap<String, Integer> lives = new HashMap<String, Integer>();

	@Override
	public String version() {
		return "v0.9.0.0";
	}

	@Override
	public void commitPlayerDeath(Arena arena, Player player,
			boolean doesRespawn, String error, PlayerDeathEvent event) {
		if (!lives.containsKey(player.getName())) {
			return;
		}
		int i = lives.get(player.getName());
		db.i("lives before death: " + i);
		if (i < 1) {
			if (!arena.getArenaConfig().getBoolean("game.preventDeath")) {
				return; // stop
				// player died => commit death!
			}
			db.i("faking player death");

			PlayerListener.commitPlayerDeath(arena, player, event);
		} else {
			i--;
			lives.put(player.getName(), i);

			new InventoryRestoreRunnable(arena, player, event.getDrops(), 0);
			arena.respawnPlayer(player, i, event.getEntity()
					.getLastDamageCause().getCause(), player.getKiller());
		}
	}
}
