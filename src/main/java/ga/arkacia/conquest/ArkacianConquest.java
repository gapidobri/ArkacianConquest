package ga.arkacia.conquest;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import static ga.arkacia.conquest.commands.Commands.registerCommands;

public final class ArkacianConquest extends JavaPlugin implements Listener {

    public static ArkacianConquest plugin;

    // Temporary variables

    public static int nationPrice = 20;
    public static int townPrice = 10;

    // END - Temporary variables

    @Override
    public void onEnable() {
        plugin = this;
        registerCommands();

        /*
        Database db = new Database(
                "localhost",
                "mc_arkacian",
                "mc_arkacian",
                "arkacian",
                3306);

        try {
            db.openConnection();
            db.setSomething();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

         */
    }

    @Override
    public void onDisable() {
        plugin = null;
    }
}
