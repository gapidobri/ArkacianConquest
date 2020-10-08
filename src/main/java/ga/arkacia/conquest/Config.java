package ga.arkacia.conquest;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Config {
    public static String lang(String id, String... args) {
        File langFile = new File(ArkacianConquest.plugin.getDataFolder(), "lang.yml");
        FileConfiguration langConf = YamlConfiguration.loadConfiguration(langFile);
        String string = langConf.getString(id);
        int i = 0;
        for (String arg : args) {
            string = string.replaceAll("%" + (i + 1) + "%", args[i]);
            i++;
        }
        string = ChatColor.translateAlternateColorCodes('&', string);
        return string;
    }
}
