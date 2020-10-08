package ga.arkacia.conquest;

import org.bukkit.ChatColor;

public class Functions {
    public static String idGen(String displayName) {
        return ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', displayName.replace(" ", "_").toLowerCase()));
    }

    public static String stringFromArray(String[] arr) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String word : arr) {
            stringBuilder.append(word).append(" ");
        }
        String string = stringBuilder.toString();
        string = string.substring(0, string.length() - 1);
        return string;
    }
}
