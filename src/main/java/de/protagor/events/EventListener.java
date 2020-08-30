package de.protagor.events;


import de.protagor.scoreboard.NetheriteScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles all events that have to be handled by this plugin.
 *
 * @author Protagor
 * @see org.bukkit.event.Listener
 */
public class EventListener implements Listener {

    /**
     * Stores all Ancient Debris Blocks that were placed by players.
     */
    private List<Block> ancientDebrisBlocksPlacedByPlayers = new ArrayList<>();

    /**
     * Adds a player to the scoreboard when he joins the server.
     * @param joinEvent Is called, when a player joins the server.
     */
    @EventHandler
    public void addPlayerToAncientDebrisScoreboard(PlayerJoinEvent joinEvent) {
        //stores player that joined the server
        Player player = joinEvent.getPlayer();
        //gets a reference to the NetheriteScoreboard singleton object
        NetheriteScoreboard scoreboard = NetheriteScoreboard.getInstance();

        //if necessary the player is added to the scoreboard
        if (!scoreboard.scoreboardContainsPlayer(player)) {
            scoreboard.addPlayerToScoreboard(player);
        }
    }

    /**
     * Increases the score of player in the Ancient Debris scoreboard when he breaks an Ancient Debris block
     * that has not been placed by a player.
     * @param breakEvent Is called when a block is broken by a player.
     */
    @EventHandler
    public void increaseAncientDebrisScoreOfPlayer(BlockBreakEvent breakEvent) {
        //stores player that broke a block
        Player player = breakEvent.getPlayer();
        //checks if broken block is Ancient Debris
        if (breakEvent.getBlock().getType() == Material.ANCIENT_DEBRIS) {
            //checks if block has been placed by a player
            if (!ancientDebrisBlocksPlacedByPlayers.contains(breakEvent.getBlock())) {
                //increases score of the player on the Netherite scoreboard
                NetheriteScoreboard scoreboard = NetheriteScoreboard.getInstance();
                scoreboard.increasePlayersScore(player);
                //sends a message to all player that an Ancient Debris block has been found
                Bukkit.broadcastMessage(
                        ChatColor.GOLD + player.getDisplayName() +
                                ChatColor.GREEN + " hat " +
                                ChatColor.DARK_PURPLE + ChatColor.MAGIC + "12" +
                                ChatColor.DARK_PURPLE + "Ancient Debris" +
                                ChatColor.DARK_PURPLE + ChatColor.MAGIC + "34" +
                                ChatColor.GREEN + " gefunden!"
                );
            } else {
                //if Ancient Debris block has been placed by a player score will not increase
                ancientDebrisBlocksPlacedByPlayers.remove(breakEvent.getBlock());
                int ancientDebrisStatisticCount = player.getStatistic(Statistic.MINE_BLOCK, Material.ANCIENT_DEBRIS);
                player.setStatistic(Statistic.MINE_BLOCK, Material.ANCIENT_DEBRIS, --ancientDebrisStatisticCount);
            }
        }
    }

    /**
     * Stores all Ancient Debris blocks that are placed by a players in the 'ancientDebrisBlocksPlacedByPlayers' hashmap.
     * @param placeEvent Is called when a player places a block.
     */
    @EventHandler
    public void addBlockToHashMap(BlockPlaceEvent placeEvent) {
        if (placeEvent.getBlockPlaced().getType() == Material.ANCIENT_DEBRIS) {
            ancientDebrisBlocksPlacedByPlayers.add(placeEvent.getBlockPlaced());
        }
    }

}
