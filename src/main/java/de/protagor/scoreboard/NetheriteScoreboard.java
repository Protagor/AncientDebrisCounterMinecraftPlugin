package de.protagor.scoreboard;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.Objects;

/**
 * Singleton class to store data in the objective that tracks how many Ancient Debris blocks
 * were placed by each player.
 *
 * @author Protagor
 */
public class NetheriteScoreboard {

    /**
     * Reference to the main-scoreboard of the world.
     */
    private final Scoreboard mainScoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();

    /**
     * Singleton reference to the only instance of this class.
     */
    private static final NetheriteScoreboard instance = new NetheriteScoreboard();

    /**
     * Singleton constructor.
     */
    private NetheriteScoreboard() {
        //new objective is added to the main-scoreboard
        Objective ancientDebrisObjective = mainScoreboard.registerNewObjective(
                "Ancient Debris",
                "dummy",
                "Ancient Debris Counter"
        );

        ancientDebrisObjective.setDisplaySlot(DisplaySlot.PLAYER_LIST);

    }

    /**
     * Adds a player to the Ancient Debris objective.
     * @param player Player that should be added.
     */
    public void addPlayerToScoreboard(Player player) {
        Score score = Objects.requireNonNull(mainScoreboard.getObjective("Ancient Debris")).getScore(player.getDisplayName());

        score.setScore(player.getStatistic(Statistic.MINE_BLOCK, Material.ANCIENT_DEBRIS));
    }

    /**
     * Increases the score of a given player in the objective.
     * @param player Player whose score should be increased.
     */
    public void increasePlayersScore(Player player) {
        Score score = Objects.requireNonNull(mainScoreboard.getObjective("Ancient Debris")).getScore(player.getDisplayName());
        int scoreNumber = score.getScore();
        score.setScore(++scoreNumber);
    }

    /**
     * Return if the Ancient Debris objective contains a given player.
     * @param player Player that should be checked.
     * @return Returns true if player is tracked by the objective and false if not.
     */
    public boolean scoreboardContainsPlayer(Player player) {
        return Objects.requireNonNull(mainScoreboard.getObjective("Ancient Debris")).getScore(player.getDisplayName()).isScoreSet();
    }

    /**
     * Singleton getInstance() method to get a reference to the only object of this class.
     * @return Returns the only instance of this class.
     */
    public static NetheriteScoreboard getInstance() {
        return instance;
    }

}
