package it.unicam.cs.mpgc.rpg129382.logic.shop;

/**
 * How an attempt to buy the next rod ended.
 */
public enum PurchaseResult {
    /** The rod was bought and equipped. */
    PURCHASED,
    /** The player does not have enough coins: nothing changed. */
    NOT_ENOUGH_MONEY,
    /** The player already has the best rod: there is nothing left to buy. */
    MAX_LEVEL_REACHED
}
