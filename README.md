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
- As a user, I want to be able to quit the game and have the option to save the game's current state to a file at any time during the game
- As a user, when I start the game I want to have the option to reload the state of the game from a previous save file and continue playing it from that point
 

## Phase 4: Task 2

Here is a sample of the events that may be logged when a typical Matching Game is played:

Wed Mar 30 23:22:28 PDT 2022

Added a pair of cards with identity 'C' to the game.

Wed Mar 30 23:22:33 PDT 2022

Added a pair of cards with identity 'D' to the game.

Wed Mar 30 23:22:40 PDT 2022

Guessed a match between card at location 2 and card at location 8.

Wed Mar 30 23:22:44 PDT 2022

Guessed a match between card at location 1 and card at location 7.

Wed Mar 30 23:22:48 PDT 2022

Guessed a match between card at location 3 and card at location 1.

Wed Mar 30 23:22:48 PDT 2022

Match found for cards with identity 'D'.

Wed Mar 30 23:22:53 PDT 2022

Guessed a match between card at location 4 and card at location 2.

Wed Mar 30 23:22:53 PDT 2022

Match found for cards with identity 'A'.

Wed Mar 30 23:22:57 PDT 2022

Guessed a match between card at location 7 and card at location 6.

Wed Mar 30 23:22:57 PDT 2022

Match found for cards with identity 'B'.

Wed Mar 30 23:23:00 PDT 2022

Guessed a match between card at location 5 and card at location 8.

Wed Mar 30 23:23:00 PDT 2022

Match found for cards with identity 'C'.

## Phase 4: Task 3

If I had more time to work on this project, some refactoring I would do is:

- Combine both the associations between MatchingGame and Card into 1 association with multiplicity 0...52 (a field that contains all cards in the game), and alter the methods that use this association to work with that
- Add a new abstract Panel class which encompasses the shared behaviour of each of the panel classes
- Refactor each of StartPanel, MenuPanel, GamePanel, ScorePanel, and GameOverPanel to extend the abstract Panel class and utilize any behaviour from Panel as applicable
- Remove any associations between MatchingGameApp and StartPanel, MenuPanel, GamePanel, ScorePanel, and GameOverPanel, and create a bidirectional association between MatchingGameApp and Panel, with MatchingGameApp having 5 of Panel and Panel having 1 of MatchingGameApp
- Make the MatchingGameApp constructor instantiate a list (field) that holds objects of apparent type Panel and add one StartPanel, one MenuPanel, one GamePanel, one ScorePanel, and one GameOverPanel, and add another method to MatchingGameApp that adds the next panel in the list to the JFrame and removes the previous one
- Add a unidirectional association from Panel to MatchingGame (multiplicity 1) to allow all subclasses to access the game, and remove any associations between the individual panel classes and MatchingGame