package it.unicam.cs.mpgc.rpg129382.controller;

import it.unicam.cs.mpgc.rpg129382.logic.FishingGame;

import java.util.Objects;

public class FishingController {
    private final FishingGame game;

    public FishingController(FishingGame game) {
        this.game = Objects.requireNonNull(game);
    }

    public void setDirection(double directionX, double directionY) {
        game.setDirection(directionX, directionY);
    }

    public void onTick(double deltaSeconds) {
        game.update(deltaSeconds);
    }
}