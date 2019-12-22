package rip.skyland.pearls.listener;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import rip.skyland.pearls.entity.CustomEnderpearl;

public class EnderpearlListener implements Listener {

    /**
     * method to set the cancelled boolean to false
     * if you click the air, spigot calls the event as cancelled.
     */

    @EventHandler(priority = EventPriority.LOWEST)
    public void interactEventCancellation(PlayerInteractEvent event) {
        event.setCancelled(false);
    }


    /**
     *
     * replace the default enderpearl with our custom enderpearl
     * could possibly do this easier and better, but cba.
     *
     * @param event the fired event
     */

    @EventHandler(priority= EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR) {
            return;
        }

        if (!event.isCancelled() && event.getItem() != null && event.getItem().getType().equals(Material.ENDER_PEARL)) {
            Player player = event.getPlayer();

            int amount = player.getInventory().getItemInHand().getAmount();
            if (amount < 2) {
                player.getInventory().remove(player.getInventory().getHeldItemSlot());
            } else {
                player.getInventory().getItemInHand().setAmount(amount - 1);
            }

            CustomEnderpearl enderpearl = new CustomEnderpearl(player);
            ((CraftWorld) player.getLocation().getWorld()).getHandle().addEntity(enderpearl);

            event.setCancelled(true);
        }
    }
}