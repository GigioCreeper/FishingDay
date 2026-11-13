package it.unicam.cs.mpgc.rpg129382.config;

/**
 * The numbers that tune the game. Positions and distances are in pixels of the virtual window,
 * speeds in pixels per second and durations in seconds.
 */
public final class GameConfig {
    // Window

    /** The width of the virtual window: the game is scaled to fit the real one. */
    public static final int WINDOW_WIDTH = 1280;
    /** The height of the virtual window. */
    public static final int WINDOW_HEIGHT = 720;

    // Lake

    /** Where the water surface is: everything above it is sky and shore. */
    public static final double WATER_TOP = WINDOW_HEIGHT * 0.37;
    /** The left border of the zone where hooked fish are caught. */
    public static final double CATCH_ZONE_LEFT = WINDOW_WIDTH / 3.0;
    /** The right border of the catch zone. */
    public static final double CATCH_ZONE_RIGHT = WINDOW_WIDTH / 2.0;
    /** The top border of the catch zone. */
    public static final double CATCH_ZONE_TOP = WATER_TOP - 30;
    /** The bottom border of the catch zone. */
    public static final double CATCH_ZONE_BOTTOM = WATER_TOP + 40;
    /** True to draw the catch zone over the lake, so the player can see where to bring a hooked fish. */
    public static final boolean SHOW_CATCH_ZONE = true;

    // Hook

    /** Where the hook starts, horizontally. */
    public static final double HOOK_START_X = WINDOW_WIDTH / 2.0;
    /** Where the hook starts, vertically. */
    public static final double HOOK_START_Y = WINDOW_HEIGHT / 2.0;
    /** How fast the player moves the free hook. */
    public static final double HOOK_SPEED = 180;
    /** How fast the player moves the hook with a fish on it, while the fish is not pulling. */
    public static final double HOOKED_SPEED = 60;
    /** How close a fish or a bottle must be to the hook to get caught. */
    public static final double CATCH_RADIUS = 40;

    // Fish

    /** How many common fish can swim in the lake at the same time, at most. */
    public static final int MAX_FISH = 6;
    /** How far outside the screen fish and bottles appear and disappear. */
    public static final double FISH_SPAWN_MARGIN = 100;
    /**
     * The highest a fish can swim: out of reach of a hook resting in the catch zone, so it can't be caught instantly.
     */
    public static final double FISH_SPAWN_TOP = CATCH_ZONE_BOTTOM + CATCH_RADIUS;
    /** The shortest wait before a new fish appears. */
    public static final double SPAWN_MIN_SECONDS = 1;
    /** The longest wait before a new fish appears. */
    public static final double SPAWN_MAX_SECONDS = 4;

    // Fight

    /** The shortest time a hooked fish pulls. */
    public static final double PULL_MIN_SECONDS = 1;
    /** The longest time a hooked fish pulls. */
    public static final double PULL_MAX_SECONDS = 2.5;
    /** The shortest time the player controls a hooked fish between two pulls. */
    public static final double CONTROL_MIN_SECONDS = 1.5;
    /** The longest time the player controls a hooked fish between two pulls. */
    public static final double CONTROL_MAX_SECONDS = 3;
    /** The coins the player loses when the line breaks. */
    public static final int LINE_BROKEN_PENALTY = 10;

    // Story bottles

    /** The height at which bottles float, just below the surface. */
    public static final double BOTTLE_SURFACE_Y = WATER_TOP + 15;
    /** How fast bottles drift. */
    public static final double BOTTLE_SPEED = 40;
    /** The shortest wait before a new bottle appears. */
    public static final double BOTTLE_MIN_SECONDS = 3;
    /** The longest wait before a new bottle appears. */
    public static final double BOTTLE_MAX_SECONDS = 8;

    // Final fish

    /** The shortest wait before the final fish appears. */
    public static final double FINAL_FISH_MIN_SECONDS = 5;
    /** The longest wait before the final fish appears. */
    public static final double FINAL_FISH_MAX_SECONDS = 12;

    private GameConfig() {
    }
}
