package it.unicam.cs.mpgc.rpg129382.logic.shop;

/**
 * How a rod of the shop relates to the rod the player is using.
 */
public enum RodStatus {
    /** It is the rod the player is using. */
    EQUIPPED,
    /** It is a rod the player had before upgrading. */
    OWNED,
    /** It is the next rod: the player can buy it. */
    AVAILABLE,
    /** It is further ahead: the player must buy the rods before it first. */
    LOCKED
}
