package com.oose2015.ebridge2.hareandhounds;
/**
 * a gamestate enum, so that hte gamestate is always one
 * of the possible options and always appears correct
 * @author eric
 *
 */
public enum Gamestate {
	/** the possible states in which the game can be*/
	WAITING_FOR_SECOND_PLAYER, TURN_HARE, TURN_HOUND, 
	WIN_HARE_BY_ESCAPE, WIN_HARE_BY_STALLING, WIN_HOUND;

}
