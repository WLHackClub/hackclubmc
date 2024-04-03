package io.github.wlhackclub.hackclubmc;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class HyperionCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            ItemStack hyperion = new ItemStack(Material.IRON_SWORD, 1);
            ItemMeta meta = hyperion.getItemMeta();
            meta.setDisplayName("§bHyperion");
            meta.getPersistentDataContainer().set(new NamespacedKey("hackclubmc", "hyperion"), PersistentDataType.INTEGER, 1);
            hyperion.setItemMeta(meta);
            player.getInventory().addItem(hyperion);
        }
        return true;
    }

}
