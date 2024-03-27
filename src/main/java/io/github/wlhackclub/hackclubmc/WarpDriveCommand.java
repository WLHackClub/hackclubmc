package io.github.wlhackclub.hackclubmc;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class WarpDriveCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            ItemStack warpDrive = new ItemStack(Material.END_CRYSTAL, 1);
            ItemMeta meta = warpDrive.getItemMeta();
            meta.setDisplayName("§bWarp Drive");
            meta.setLore(List.of("§9----------", "§eAbility: §aInstant Transmission", "§fLeft click while sneaking to teleport to the End.", "§9----------"));
            meta.getPersistentDataContainer().set(new NamespacedKey("hackclubmc", "warp"), PersistentDataType.INTEGER, 1);
            warpDrive.setItemMeta(meta);
            ((Player) sender).getInventory().addItem(warpDrive);
        }
        return true;
    }

}
