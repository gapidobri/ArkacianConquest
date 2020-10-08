package ga.arkacia.conquest;

import ga.arkacia.conquest.objects.chunk.ClaimedChunk;
import ga.arkacia.conquest.objects.citizen.Citizen;
import ga.arkacia.conquest.objects.nation.Nation;
import ga.arkacia.conquest.objects.town.Town;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

import static ga.arkacia.conquest.ArkacianConquest.plugin;

public class Database {

    public static void updateDB() {
        FileConfiguration config = plugin.getConfig();
        config.set("chunks", ClaimedChunk.getClaimedChunks());
        config.set("citizens", Citizen.getCitizens());
        config.set("towns", Town.getTowns());
        config.set("nations", Nation.getNations());
        plugin.saveConfig();
    }

    public static void loadFromDB() {
        FileConfiguration config = plugin.getConfig();
        if (config.contains("chunks")) ClaimedChunk.claimedChunks = (List<ClaimedChunk>) config.getList("chunks");
        if (config.contains("citizens")) Citizen.citizens = (List<Citizen>) config.getList("citizens");
        if (config.contains("towns")) Town.towns = (List<Town>) config.get("towns");
        if (config.contains("nations")) Nation.nations = (List<Nation>) config.get("nations");

        Citizen.linkData();
        Town.linkData();

    }
}
