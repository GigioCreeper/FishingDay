package it.unicam.cs.mpgc.rpg129382.config;

public final class GameConfig {
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;

    public static final double HOOK_SPEED = 300.0;

    public static final double WATER_TOP = WINDOW_HEIGHT * 0.37;
    public static final double FISH_SPAWN_MARGIN = 100;
    public static final int MAX_FISH = 6;
    public static final double SPAWN_MIN_SECONDS = 1;
    public static final double SPAWN_MAX_SECONDS = 4;
    public static final double CATCH_RADIUS = 40;

    public static final double CATCH_ZONE_LEFT = WINDOW_WIDTH / 3.0;
    public static final double CATCH_ZONE_RIGHT = WINDOW_WIDTH * 2.0 / 4.0;
    public static final double CATCH_ZONE_BOTTOM = WATER_TOP + 80;
    private GameConfig() {}
}
