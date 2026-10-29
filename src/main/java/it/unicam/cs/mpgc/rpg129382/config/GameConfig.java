package it.unicam.cs.mpgc.rpg129382.config;

public final class GameConfig {
    // Window
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;

    // Lake
    public static final double WATER_TOP = WINDOW_HEIGHT * 0.37;
    public static final double CATCH_ZONE_LEFT = WINDOW_WIDTH / 3.0;
    public static final double CATCH_ZONE_RIGHT = WINDOW_WIDTH / 2.0;
    // The zone straddles the surface: the hook never leaves the water, so it must overlap it.
    public static final double CATCH_ZONE_TOP = WATER_TOP - 30;
    public static final double CATCH_ZONE_BOTTOM = WATER_TOP + 40;

    // Hook
    public static final double HOOK_START_X = WINDOW_WIDTH / 2.0;
    public static final double HOOK_START_Y = WINDOW_HEIGHT / 2.0;
    public static final double HOOK_SPEED = 180;
    public static final double HOOKED_SPEED = 60;
    public static final double CATCH_RADIUS = 40;

    // Fish
    public static final int MAX_FISH = 6;
    public static final double FISH_SPAWN_MARGIN = 100;
    // Fish spawn out of reach of a hook resting in the catch zone, so they can't be caught instantly.
    public static final double FISH_SPAWN_TOP = CATCH_ZONE_BOTTOM + CATCH_RADIUS;
    public static final double SPAWN_MIN_SECONDS = 1;
    public static final double SPAWN_MAX_SECONDS = 4;

    // Fight
    public static final double PULL_MIN_SECONDS = 1;
    public static final double PULL_MAX_SECONDS = 2.5;
    public static final double CONTROL_MIN_SECONDS = 1.5;
    public static final double CONTROL_MAX_SECONDS = 3;
    public static final int LINE_BROKEN_PENALTY = 10;

    // Story bottles
    public static final double BOTTLE_SURFACE_Y = WATER_TOP + 15;
    public static final double BOTTLE_SPEED = 40;
    public static final double BOTTLE_MIN_SECONDS = 3;
    public static final double BOTTLE_MAX_SECONDS = 8;
    
    private GameConfig() {
    }
}
