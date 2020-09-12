package ga.arkacia.conquest.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

import static ga.arkacia.conquest.ArkacianConquest.plugin;

public class Commands implements CommandExecutor {

    public static ICommand[] commands = {
            new CommandNation(),
            new CommandTown()
    };

    public static void registerCommands() {
        Commands commandExecutor = new Commands();
        for (ICommand command : commands) {
            plugin.getCommand(command.getCommand()).setExecutor(commandExecutor);
            plugin.getLogger().info(ChatColor.GREEN + "Registered command " + command.getCommand().toUpperCase());
        }
    }

    public static void subCommand(ICommand[] subCommands, CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) return;
        boolean commandRun = false;
        for (ICommand cmdClass : subCommands) {
            if (cmdClass.getCommand().equals(args[0])) {
                cmdClass.run(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
                commandRun = true;
            }
        }
        if (!commandRun) {
            sender.sendMessage(ChatColor.RED + "This command doesn't exist");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        for (ICommand cmdClass : commands) {
            if (cmdClass.getCommand().equals(command.getName())) {
                cmdClass.run(sender, command, label, args);
            }
        }
        return true;
    }
}
