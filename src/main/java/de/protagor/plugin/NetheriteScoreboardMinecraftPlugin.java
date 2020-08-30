package de.protagor.plugin;

import de.protagor.events.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Objects;
import java.util.Set;

/**
 * Main Pluign Class.
 *
 * @author Protagor
 * @see org.bukkit.plugin.java.JavaPlugin
 */
public class NetheriteScoreboardMinecraftPlugin extends JavaPlugin {

    /**
     * Reference to the pluginmanager.
     */
    SimplePluginManager pluginManager = (SimplePluginManager) Bukkit.getPluginManager();

    @Override
    public void onEnable() {

        pluginManager.registerEvents(new EventListener(), this);

        removeAllObjectivesFromScoreboard(
                Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard()
        );
    }

    /**
     * Removes all objectiges of a given scoreboard.
     * @param scoreboard Scoreboard which objectives should be removed.
     */
    private void removeAllObjectivesFromScoreboard(Scoreboard scoreboard) {
        Set<Objective> objectives = scoreboard.getObjectives();

        objectives.forEach(Objective::unregister);

    }

}
