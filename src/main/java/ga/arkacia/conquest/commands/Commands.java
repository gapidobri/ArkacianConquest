package ga.arkacia.conquest.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ga.arkacia.conquest.ArkacianConquest.plugin;

public class Commands implements CommandExecutor, TabCompleter {

    public static ICommand[] commands = {
            new CommandNation(),
            new CommandTown()
    };

    public static void registerCommands() {
        Commands commandExecutor = new Commands();
        for (ICommand command : commands) {
            plugin.getCommand(command.getCommand()).setExecutor(commandExecutor);
            plugin.getLogger().info(ChatColor.GREEN + "Registered command " + command.getCommand().toUpperCase());
            plugin.getCommand(command.getCommand()).setTabCompleter(commandExecutor);
        }
    }

    public static void subCommand(ISubCommand[] subCommands, CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) return;
        boolean commandRun = false;
        for (ISubCommand cmdClass : subCommands) {
            if (cmdClass.getSubCommand().equals(args[0])) {
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

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        switch (args.length) {
            case 1:
                List<String> completes = new ArrayList<>();
                for (ICommand cmd : commands) {
                    if (cmd.getCommand().equals(command.getName())) {
                        for (ISubCommand subCmd : cmd.getSubCommands()) {
                            completes.add(subCmd.getSubCommand());
                        }
                    }
                }
                return completes;

            case 2:
                List<String> subCompletes = new ArrayList<>();
                for (ICommand cmd : commands) {
                    
                }
                return subCompletes;
        }
        return null;
    }
}
