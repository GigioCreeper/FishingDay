package it.unicam.cs.mpgc.rpg129382.logic.story;

import java.util.Objects;
import java.util.function.IntSupplier;

/**
 * Keeps track of how far the player has read the story.
 * How many chapters can be read is decided from outside; the story never unlocks more than it has.
 * Once every chapter is read the final fish awakes: the story is concluded when the player
 * catches it or when it breaks the line.
 */
public final class StoryProgress {
    private final StoryBook book;
    private final IntSupplier unlockedChapters;
    private int chaptersRead;
    private boolean concluded;

    /**
     * Creates the progress of a story that has not been read yet.
     *
     * @param book             the texts of the story.
     * @param unlockedChapters tells how many chapters the player can read now; it is asked again
     *                         every time, so the count can grow while the game goes on.
     */
    public StoryProgress(StoryBook book, IntSupplier unlockedChapters) {
        this.book = Objects.requireNonNull(book);
        this.unlockedChapters = Objects.requireNonNull(unlockedChapters);
    }

    /**
     * {@return true if a chapter is unlocked but not read yet}
     */
    public boolean hasUnreadChapter() {
        return chaptersRead < unlockedChapters();
    }

    /**
     * Reads the first unread chapter.
     *
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
     * {@return true if every chapter is read and the story is not concluded yet, so the final fish can appear}
     */
    public boolean awaitsFinal() {
        return chaptersRead == book.chapterCount() && !concluded;
    }

    /**
     * Concludes the story, because the player caught the final fish.
     *
     * @return the message of the happy ending.
     * @throws IllegalStateException if the story is not waiting for the final fish.
     */
    public StoryMessage winFinal() {
        requireFinal();
        concluded = true;
        return new StoryMessage(StoryMessage.Kind.VICTORY, book.victory());
    }

    /**
     * Concludes the story, because the final fish broke the line and dragged the player into the lake.
     *
     * @return the message of the bad ending.
     * @throws IllegalStateException if the story is not waiting for the final fish.
     */
    public StoryMessage loseFinal() {
        requireFinal();
        concluded = true;
        return new StoryMessage(StoryMessage.Kind.DEFEAT, book.defeat());
    }

    /**
     * {@return how many chapters the player has read}
     */
    public int getChaptersRead() {
        return chaptersRead;
    }

    /**
     * {@return true if the story has reached one of its endings}
     */
    public boolean isConcluded() {
        return concluded;
    }

    /**
     * {@return how many chapters the story has}
     */
    public int chapterCount() {
        return book.chapterCount();
    }

    /**
     * Puts the story back in a saved state.
     *
     * @param chaptersRead how many chapters the player has read.
     * @param concluded    true if the story has reached an ending.
     * @throws IllegalArgumentException if chaptersRead is negative or greater than the number of chapters.
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
        return Math.min(unlockedChapters.getAsInt(), book.chapterCount());
    }
}