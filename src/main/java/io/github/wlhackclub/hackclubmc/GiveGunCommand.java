package io.github.wlhackclub.hackclubmc;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GiveGunCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            ItemStack gun = new ItemStack(Material.DIAMOND_HOE, 1);
            ItemMeta meta = gun.getItemMeta();
            meta.setDisplayName("§cAh Super Gun");
            meta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
            gun.setItemMeta(meta);
            player.getInventory().addItem(gun);
        }
        return true;
    }

}
