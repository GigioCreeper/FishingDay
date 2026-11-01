package it.unicam.cs.mpgc.rpg129382.logic.story;

import it.unicam.cs.mpgc.rpg129382.config.RodCatalog;
import it.unicam.cs.mpgc.rpg129382.model.Player;

import java.util.Objects;

/**
 * Keeps track of how far the player has read the story.
 * The first chapter is unlocked from the start, every rod upgrade unlocks one more.
 * Once every chapter is read the final fish awakes, and catching it concludes the story.
 */
public final class StoryProgress {
    private final StoryBook book;
    private final Player player;
    private int chaptersRead;
    private boolean concluded;

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

    /**
     * @return true if every chapter is read and the final fish has not been caught yet.
     */
    public boolean awaitsFinal() {
        return chaptersRead == book.chapterCount() && !concluded;
    }

    /**
     * Concludes the story, because the player caught the final fish.
     * @return the message of the happy ending.
     * @throws IllegalStateException if the final fish is not awake.
     */
    public StoryMessage winFinal() {
        requireFinal();
        concluded = true;
        return new StoryMessage(StoryMessage.Kind.VICTORY, book.victory());
    }

    /**
     * The final fish broke the line: the story is concluded and the player died.
     * @return the message of the bad ending.
     * @throws IllegalStateException if the final fish is not awake.
     */
    public StoryMessage loseFinal() {
        requireFinal();
        concluded = true;
        return new StoryMessage(StoryMessage.Kind.DEFEAT, book.defeat());
    }
    public int getChaptersRead() {
        return chaptersRead;
    }

    public boolean isConcluded() {
        return concluded;
    }

    /**
     * Puts the story back in a saved state.
     * @param chaptersRead how many chapters the player has read.
     * @param concluded true if the story has reached an ending.
     * @throws IllegalArgumentException if the book has fewer chapters.
     */
    public void restore(int chaptersRead, boolean concluded) {
        if (chaptersRead < 0 || chaptersRead > book.chapterCount()) {
            throw new IllegalArgumentException("Invalid chapters read: " + chaptersRead);
        }
        this.chaptersRead = chaptersRead;
        this.concluded = concluded;
    }

    private void requireFinal() {
        if (!awaitsFinal()) {
            throw new IllegalStateException("the finale is not available");
        }
    }

    private int unlockedChapters() {
        return Math.min(RodCatalog.levelOf(player.getRod()) + 1, book.chapterCount());
    }
}