package ga.arkacia.conquest.gui;

import ga.arkacia.conquest.objects.citizen.Citizen;
import ga.arkacia.conquest.objects.nation.Nation;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class NationListGUI {
    public static void showGUI(Citizen citizen) {
        Inventory nationGUI = Bukkit.createInventory(null, 9, "List of Nations");
        for (Nation nation : Nation.nations) {
            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(nation.getDisplayName());
            item.setItemMeta(im);
            nationGUI.addItem(item);
        }
        //citizen.getPlayer().openInventory(nationGUI);
    }
}
