package it.unicam.cs.mpgc.rpg129382.logic.story;

import java.util.Objects;

/**
 * A piece of the story shown to the player.
 *
 * @param kind what the message comes from, so the view can present it accordingly.
 * @param text the text of the message.
 */
public record StoryMessage(Kind kind, String text) {

    /**
     * Where a message comes from.
     */
    public enum Kind {
        /**
         * A chapter found in a bottle.
         */
        BOTTLE,
        /**
         * The final fish was caught: the story ends.
         */
        VICTORY,
        /**
         * The final fish broke the line and dragged the fisherman into the lake.
         */
        DEFEAT
    }

    /**
     * Creates a message, checking that nothing is missing.
     *
     * @throws NullPointerException if the kind or the text is null.
     */
    public StoryMessage {
        Objects.requireNonNull(kind);
        Objects.requireNonNull(text);
    }

    /**
     * {@return true if the message is one of the two endings, after which the story is over}
     */
    public boolean isEnding() {
        return kind != Kind.BOTTLE;
    }
}
