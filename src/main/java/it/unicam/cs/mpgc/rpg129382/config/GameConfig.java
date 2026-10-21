package it.unicam.cs.mpgc.rpg129382.config;

public final class GameConfig {
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;

    public static final double HOOK_SPEED = 300.0;

    public static final double WATER_TOP = WINDOW_HEIGHT * 0.4;
    public static final double FISH_SPAWN_MARGIN = 100;
    public static final int MAX_FISH = 6;
    public static final double SPAWN_MIN_SECONDS = 1;
    public static final double SPAWN_MAX_SECONDS = 4;
    public static final double CATCH_RADIUS = 40;

    public static final double CATCH_LINE = WATER_TOP + 20;
    private GameConfig() {}
}
