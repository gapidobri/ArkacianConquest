package ga.arkacia.conquest;

import ga.arkacia.conquest.gui.RegionTitle;
import ga.arkacia.conquest.objects.bank.Bank;
import ga.arkacia.conquest.objects.chunk.ClaimedChunk;
import ga.arkacia.conquest.objects.citizen.Citizen;
import ga.arkacia.conquest.objects.nation.Nation;
import ga.arkacia.conquest.objects.town.Town;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import static ga.arkacia.conquest.Database.loadFromDB;
import static ga.arkacia.conquest.Database.updateDB;
import static ga.arkacia.conquest.commands.Commands.registerCommands;

public final class ArkacianConquest extends JavaPlugin implements Listener {

    public static ArkacianConquest plugin;
    public static int nationPrice = 20;

    // Temporary variables
    public static int townPrice = 10;
    public static int chunkPrice = 2;

    static {
        ConfigurationSerialization.registerClass(Town.class, "Town");
    }

    // END - Temporary variables

    @Override
    public void onEnable() {
        plugin = this;

        ConfigurationSerialization.registerClass(ClaimedChunk.class);
        ConfigurationSerialization.registerClass(Citizen.class);
        ConfigurationSerialization.registerClass(Town.class);
        ConfigurationSerialization.registerClass(Bank.class);
        ConfigurationSerialization.registerClass(Nation.class);

        // TODO: Move lang.yml to method
        File langFile = new File(getDataFolder(), "lang.yml");
        FileConfiguration langConf = YamlConfiguration.loadConfiguration(langFile);
        langConf.options().copyDefaults(true);
        saveResource("lang.yml", false);

        loadFromDB();
        registerCommands();

        getServer().getPluginManager().registerEvents(new RegionTitle(), this);

        //updateDB();

    }

    @Override
    public void onDisable() {
        plugin = null;
    }
}
