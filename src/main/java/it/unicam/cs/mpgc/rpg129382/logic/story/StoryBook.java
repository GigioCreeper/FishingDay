package it.unicam.cs.mpgc.rpg129382.logic.story;

/**
 * The texts of the story: the chapters, in reading order, and the two endings.
 */
public interface StoryBook {

    /**
     * {@return how many chapters the story has}
     */
    int chapterCount();

    /**
     * {@return the text of the chapter}
     *
     * @param index the position of the chapter, starting from 0.
     * @throws IndexOutOfBoundsException if there is no chapter at that position.
     */
    String chapter(int index);

    /**
     * {@return the text shown when the player catches the final fish}
     */
    String victory();

    /**
     * {@return the text shown when the final fish breaks the line}
     */
    String defeat();
}
