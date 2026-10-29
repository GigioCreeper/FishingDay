package it.unicam.cs.mpgc.rpg129382.logic.story;

/**
 * The texts of the story: the chapters, in reading order, and the two endings.
 */
public interface StoryBook {

    /**
     * @return how many chapters the story has.
     */
    int chapterCount();

    /**
     * @param index the position of the chapter, starting from 0.
     * @return the text of the chapter.
     * @throws IndexOutOfBoundsException if there is no chapter at that position.
     */
    String chapter(int index);

    /**
     * @return the text shown when the player catches the final fish.
     */
    String victory();

    /**
     * @return the text shown when the final fish breaks the line.
     */
    String defeat();
}
