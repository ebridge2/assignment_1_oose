//-------------------------------------------------------------------------------------------------------------//
// Code based on a tutorial by Shekhar Gulati of SparkJava at
// https://blog.openshift.com/developing-single-page-web-applications-using-java-8-spark-mongodb-and-angularjs/
//-------------------------------------------------------------------------------------------------------------//

package com.oose2015.ebridge2.hareandhounds;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Collections;
import java.util.HashMap;

import static spark.Spark.*;

public class HareandhoundController {

    private static final String API_CONTEXT = "/hareandhounds/api";
    /** the game repo that the controller acts on*/
    private GameRepo grepo;
    /** leftover*/
    private final Logger logger = LoggerFactory.getLogger(HareandhoundController.class);
    /**
     * initializes a controller for the game repos so that the games can do things
     * @param gamerepo
     */
    public HareandhoundController(GameRepo gamerepo) {
        this.grepo = gamerepo;
        this.setupEndpoints();
    }
    /**
     * retrieves the appropriate function calls given a Json request and returns
     * packet in a hashmap that the transformer can turn into the appropriate response
     */
    private void setupEndpoints() {
    	//start a new game
        post(API_CONTEXT + "/games", "application/json", (request, response) -> {
        	response.status(201);
        	return this.grepo.addGame(request.body());
        }, new JsonTransformer());
        //adds a player to an existing game
        put(API_CONTEXT + "/games/:gameId", "application/json", (request, response) -> {
        	response.status(200);
        	String gameId = request.params(":gameId"); //find the game to add a player to
        	return this.grepo.addPlayer(gameId);
        }, new JsonTransformer());
        //describe the game state
        get(API_CONTEXT + "/games/:gameId/state", "application/json", (request, response) -> {
        	response.status(200);
        	String gameId = request.params(":gameId");
        	return this.grepo.fetchState(gameId);
        }, new JsonTransformer());
        //retrieves the board states
        get(API_CONTEXT + "/games/:gameId/board", "application/json", (request, response) -> {
        		response.status(200);
        		String gameId=request.params(":gameId");
        		return this.grepo.fetchBoard(gameId);        	
        }, new JsonTransformer());
        // a move of a game post
        post(API_CONTEXT + "/games/:gameId/turns", "application/json", (request, response) -> {
        	try {
        		String gameId = request.params(":gameId");
        		HashMap<String, String> returnMap = this.grepo.move(request.body(), gameId);
        		response.status(200);
        		return returnMap;
        	} catch (InvalidGameException ex) {
        		logger.error("Invalid Game Id");
        		response.status(404);
        		return ex.getHash();
        	} catch (InvalidPlayerException ex) {
        		logger.error("Invalid player id");
        		response.status(404);
        		return ex.getHash();
        	} catch (IncorrectTurnException ex) {
        		logger.error("Incorrect turn taken");
        		response.status(422);
        		return ex.getHash();
        	} catch (IllegalMoveException ex) {
        		logger.error("Illegal move");
        		response.status(422);
        		return ex.getHash();
        	}
        }, new JsonTransformer());
    }
}
