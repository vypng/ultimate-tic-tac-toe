/* NAME: 	Vy Nguyen 
 * ID:		VPN200000 
 * CLASS:	CS 2336.001
 * 
 * ANALYSIS:
 * 		An Ultimate Tic-Tac-Toe Board game consists of 9 standard Tic-Tac-Toe boards, where
 * 		the patterns/rules for winning a standard board are the same for the larger board. When
 * 		a player chooses a box, the other player is sent to the corresponding board for their
 * 		next turn (e.g. box 3 goes to board 3). That player must place a mark no matter if the
 * 		board already has a winner. If the board is full, the player gets to choose which board
 * 		they get to play on. The players can either be a human or an AI.
 * DESIGN:
 * 		Upon starting a new game, the player can choose different game-modes, such as Player vs.
 * 		Player, Player vs. AI, and AI vs. AI, from a menu. Only integers inputs will be accepted
 * 		here. Then, the first player will get to choose their mark, while the second player gets 
 * 		the remaining mark. Only strings X or O (lower or upper-case) will be accepted here as 
 * 		inputs. Then, start the game: switch players after every turn, print the current board,
 * 		output if there are any winners, notify the players who is playing, what board they're
 * 		playing on, and ask the player which box they'd like to place their mark on. Write
 * 		corresponding methods to do all of this. The player can only choose a board if they're
 * 		the first person playing or if the board they're sent to is already full. Continue to 
 * 		repeat all of this until the game finishes (if there's a winner or if there's a tie).
 * 		Finally, output the final Ultimate Tic-Tac-Toe Board along with a final message.
 * TEST:
 * 		This program was tested with Humans and AIs as players. In all three game-modes (mentioned
 * 		above in the Design), players only tied if all 9 standard boards were full and if there
 * 		was no winner for the UltTTT board. A player would only win if they won three standard
 * 		boards vertically, horizontally, or diagonally. The AI vs. AI game-mode was run multiple
 * 		times to instantly see these possibilities (tie/no winner, player1 wins, or player2 wins).  
 */

public abstract class Player {
	private String name;
	private String mark;

	// super class
	public Player(String name, String mark) {
		setName(name);
		setMark(mark);
	}
	
	// methods to choose board and box, later overridden
	// in AI and Human classes
	public abstract int chooseBoard();
	public abstract int chooseBox();
	
	// getters and setters for name and mark
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setMark(String mark) {
		this.mark = mark;
	}
	
	public String getMark() {
		return mark;
	}
}
