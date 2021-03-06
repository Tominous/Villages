package com.stefthedev.villages.commands;

import com.stefthedev.villages.Villages;
import com.stefthedev.villages.utilities.Chat;
import com.stefthedev.villages.utilities.Command;
import com.stefthedev.villages.utilities.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;

public class VillageCommand extends Command implements TabCompleter {

    private final Villages villages;
    private Set<Command> commands;

    public VillageCommand(Villages villages) {
        super("village");
        this.villages = villages;
        this.commands = new HashSet<>();
    }

    public boolean run(Player player, String[] args) {
        if(args.length > 0) {
            commands.forEach(command -> {
                if(args[0].equalsIgnoreCase(command.toString())) {
                    if(player.hasPermission(toString() + "." + command.toString())) {
                        command.run(player, args);
                    }
                }
            });
        } else {
            player.sendMessage(Chat.color(Message.HELP.toString().replace("{0}", villages.getDescription().getVersion())));
            commands.forEach(command -> player.sendMessage(
                    Message.HELP_ITEM.toString().replace("{0}", command.toString())
            ));
        }
        return false;
    }

    public void initialise(Command... commands) {
        this.commands.addAll(Arrays.asList(commands));
    }

    public Set<Command> getCommands() {
        return Collections.unmodifiableSet(commands);
    }

    public Villages getVillages() {
        return villages;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        if(strings.length == 1) {
            commands.forEach(subCommand -> list.add(subCommand.toString()));
        }
        return list;
    }
}
