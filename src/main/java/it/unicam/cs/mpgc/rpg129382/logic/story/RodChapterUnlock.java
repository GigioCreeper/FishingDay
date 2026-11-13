package it.unicam.cs.mpgc.rpg129382.logic.story;

import it.unicam.cs.mpgc.rpg129382.config.RodCatalog;
import it.unicam.cs.mpgc.rpg129382.model.Player;

import java.util.Objects;
import java.util.function.IntSupplier;

/**
 * The story rule that unlocks one chapter for every rod:
 * the starting rod unlocks the first chapter, and each upgrade unlocks the next one.
 */
public final class RodChapterUnlock implements IntSupplier {
    private final Player player;

    /**
     * Creates the rule for the given player.
     *
     * @param player the player whose rod unlocks the chapters.
     */
    public RodChapterUnlock(Player player) {
        this.player = Objects.requireNonNull(player);
    }

    /**
     * {@return the number of chapters unlocked by the player's rod}
     */
    @Override
    public int getAsInt() {
        return RodCatalog.levelOf(player.getRod()) + 1;
    }
}
