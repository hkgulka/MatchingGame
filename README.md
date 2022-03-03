# My Personal Project: Matching Game

## Basic Premises

The application will be a classic-style card matching game, in which the player is presented with a fixed number of cards (face down) with matching identity pairs, and must attempt to find the pairs of matching cards. 
- To match one card with another, the player will select two cards they believe (or guess) to be a match. When both selected, the identities of the cards will be revealed to the player for a brief amount of time
- If the two cards are a match, they will be removed from the board. If the cards are not a match, they will return to anonymity (face down) and stay where they are on the board
- The game is won when **all cards have been paired** with their match (i.e. the board is empty)

This application is intended to be used by anyone feeling bored, or maybe just interested in short memory-related games. Its main purpose is simply to be *entertaining* for the player.

Personally, this project is of interest to me because I wanted to make a fun application, but I also wanted it to require some degree of thinking to use. I enjoy playing games that test your memory, so this seemed like a very logical and enjoyable option!

## User Stories

- As a user, I want to be able to add more pairs of cards to the game
- As a user, I want to be able to start a game
- As a user, I want to be able to select a pair of cards and view their identities/guess at a match
- As a user, I want to be able to view the remaining (face down) cards on the board
- As a user, I want to be able to view the number of pair guesses I have made so far
- As a user, I want to be able to view the number of matches I have made so far
- As a user, I want to be able to quit the game and save the game's current state to a file at any time during the game
- As a user, I want to be able to reload the state of the game from a previous save file and continue playing it from that point
 