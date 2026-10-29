package it.unicam.cs.mpgc.rpg129382.logic.story;

import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Adapts a {@link ResourceBundle} to a {@link StoryBook}.
 * The bundle holds one entry per chapter, with the keys chapter.0, chapter.1, ...
 */
public final class ResourceStoryBook implements StoryBook {
    private static final String KEY_PREFIX = "chapter.";

    private final ResourceBundle bundle;
    private final int chapterCount;

    /**
     * @param bundle the bundle that holds the chapters.
     * @throws IllegalArgumentException if the bundle has no chapter.
     */
    public ResourceStoryBook(ResourceBundle bundle) {
        this.bundle = Objects.requireNonNull(bundle);
        this.chapterCount = countChapters();
        if (chapterCount == 0) {
            throw new IllegalArgumentException("the story has no chapters");
        }
    }

    /**
     * Loads the story from a properties file on the classpath.
     * @param baseName the name of the file without ".properties", e.g. "story".
     * @return the loaded story.
     */
    public static ResourceStoryBook load(String baseName) {
        return new ResourceStoryBook(ResourceBundle.getBundle(Objects.requireNonNull(baseName)));
    }

    @Override
    public int chapterCount() {
        return chapterCount;
    }

    @Override
    public String chapter(int index) {
        Objects.checkIndex(index, chapterCount);
        return bundle.getString(KEY_PREFIX + index);
    }

    private int countChapters() {
        int count = 0;
        while (bundle.containsKey(KEY_PREFIX + count)) {
            count++;
        }
        return count;
    }
}