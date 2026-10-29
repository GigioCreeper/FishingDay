package it.unicam.cs.mpgc.rpg129382.logic.story;

import it.unicam.cs.mpgc.rpg129382.config.RodCatalog;
import it.unicam.cs.mpgc.rpg129382.model.Player;

import java.util.Objects;

/**
 * Keeps track of how far the player has read the story.
 * The first chapter is unlocked from the start, every rod upgrade unlocks one more.
 */
public final class StoryProgress {
    private final StoryBook book;
    private final Player player;
    private int chaptersRead;

    /**
     * @param book the chapters of the story.
     * @param player the player whose rod unlocks the chapters.
     */
    public StoryProgress(StoryBook book, Player player) {
        this.book = Objects.requireNonNull(book);
        this.player = Objects.requireNonNull(player);
    }

    /**
     * @return true if a chapter is unlocked but not read yet.
     */
    public boolean hasUnreadChapter() {
        return chaptersRead < unlockedChapters();
    }

    /**
     * Reads the first unread chapter.
     * @return the text of the chapter.
     * @throws IllegalStateException if there is no unread chapter.
     */
    public String readNextChapter() {
        if (!hasUnreadChapter()) {
            throw new IllegalStateException("no unread chapter");
        }
        String chapter = book.chapter(chaptersRead);
        chaptersRead++;
        return chapter;
    }

    private int unlockedChapters() {
        return Math.min(RodCatalog.levelOf(player.getRod()) + 1, book.chapterCount());
    }
}