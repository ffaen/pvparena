package net.slipcor.pvparena.commands;

import java.util.Arrays;
import java.util.HashSet;

import net.slipcor.pvparena.arena.Arena;
import net.slipcor.pvparena.core.Language;
import net.slipcor.pvparena.core.Language.MSG;

import org.bukkit.command.CommandSender;

/**
 * <pre>PVP Arena SETOWNER Command class</pre>
 * 
 * A command to set an arena owner
 * 
 * @author slipcor
 * 
 * @version v0.9.0
 */

public class PAA_SetOwner extends PAA__Command {

	public PAA_SetOwner() {
		super(new String[] {});
	}

	@Override
	public void commit(Arena arena, CommandSender sender, String[] args) {
		if (!this.hasPerms(sender, arena)) {
			return;
		}
		
		if (!this.argCountValid(sender, arena, args, new HashSet<Integer>(Arrays.asList(1)))) {
			return;
		}
		
		//                                   args[0]
		// usage: /pa {arenaname} setowner [playername]
		
		arena.setOwner(args[0]);
		arena.msg(sender, Language.parse(MSG.SETOWNER_DONE, args[0], arena.getName()));
	}

	@Override
	public String getName() {
		return this.getClass().getName();
	}
}
