package it.unicam.cs.mpgc.rpg129382.logic.story;

/**
 * The chapters of the story, in reading order.
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
}