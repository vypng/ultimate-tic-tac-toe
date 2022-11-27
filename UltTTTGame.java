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

import java.util.*;

public class UltTTTGame {
	// data fields
	int size = 9;
	int choice;
	Board[] UltTTTBoard = new Board[size];
	Player[] players = new Player[2];
	int currPlayer, currBoard, currBox, prevBoard;
	boolean firstMove;

	// parameterized constructor
	public UltTTTGame(Player player1, Player player2) {
		// generate 9 standard TTT boards for the UltimateTTT board
		for(int i = 0; i < size; i++)
			UltTTTBoard[i] = new Board();
		// on start of a new game, assign players to array and indicate
		// that it's the first move
		this.firstMove = true;
		this.players[0] = player1;
		this.players[1] = player2;
	}
	
	// method for players to choose marks
	private String chooseMark() {
		// user inputs their desired mark
		Scanner markInput = new Scanner(System.in);
		String mark = markInput.nextLine();
		
		// input validation for marks
		while(!mark.equals("X") && !mark.equals("x") 
				&& !mark.equals("O") && !mark.equals("o") ) {
			System.out.print("Invalid input. Choose a mark! X or O?");
			mark = markInput.nextLine();
		}
		
		// converting lowercase input marks to uppercase
		if(mark.equals("x"))
			mark = "X";
		else if (mark.equals("o"))
			mark = "O";
			
		return mark;
	}
	
	// method user picks which gamemode they want to play
	void chooseGamemode() {
		System.out.println("➤ Choose your gamemode!");
		System.out.println("  1. Player vs. Player");
		System.out.println("  2. Player vs. AI");
		System.out.println("  3. AI vs. AI");
		System.out.print("➤ Enter your choice: ");
		
		while(true) {
			try {
				// user inputs desired gamemode choice
				Scanner choiceInput = new Scanner(System.in);
				int playerChoice = choiceInput.nextInt();
				this.choice = playerChoice;
				// input validation for player's gamemode choice (1-3)
				while(playerChoice != 1 && playerChoice != 2 && playerChoice != 3) {
						System.out.print("Invalid input. Choose again: ");
						playerChoice = choiceInput.nextInt();
						this.choice = playerChoice;
				}
				break;
			}
			// if input is not integer
			catch (InputMismatchException e){
				System.out.print("Invalid input. Choose again: ");
			}
		}
	}
	
	void determineMarks() {
		System.out.println();
		// based on choice, let players choose their marks. first human player
		// will always be the priority choice, while AI will be last to choose
		switch (choice) {
		// Player vs. Player -> human player1 chooses mark, player2 gets remaining mark
		case 1:
			System.out.print("➤ PLAYER 1 | Choose a mark! X or O?: ");
			String firstMark = chooseMark();
			if(firstMark.equals("O")) {
				System.out.println("➤ PLAYER 2 | Player 2's mark is X!");
				UltTTTGame newGame = new UltTTTGame(new Human("Player1", firstMark), new Human("Player2", "X"));
				newGame.start();
			}
			else if(firstMark.equals("X")) {
				System.out.println("➤ PLAYER 2 | Player 2's mark is O!");
				UltTTTGame newGame = new UltTTTGame(new Human("Player1", firstMark), new Human("Player2", "O"));
				newGame.start();
			}
			break;
		// Player vs. AI -> human player1 chooses mark, AI gets remaining mark
		case 2:
			System.out.print("➤ PLAYER 1 | Choose a mark! X or O?: ");
			String humanMark = chooseMark();
			if(humanMark.equals("O")) {
				System.out.println("➤ PLAYER 2 | AI Player 2's mark is X!");
				UltTTTGame newGame = new UltTTTGame(new Human("Player1", humanMark), new AI("Player2", "X"));
				newGame.start();
			}
			else if(humanMark.equals("X")) {
				System.out.println("➤ PLAYER 2 | AI Player 2's mark is O!");
				UltTTTGame newGame = new UltTTTGame(new Human("Player1", humanMark), new AI("Player2", "O"));	
				newGame.start();
			}
			break;
		// AI vs. AI -> generate random mark for the AI player1, AI player2 gets remaining mark
		case 3:
			String aiMark = randomMark();
			System.out.println("➤ PLAYER 1 | AI player 1's mark is " + aiMark + "!");

			if(aiMark.equals("O")) {
				System.out.println("➤ PLAYER 2 | AI player 2's mark is X!");
				UltTTTGame newGame = new UltTTTGame(new AI("Player1", aiMark), new AI("Player2", "X"));	
				newGame.start();
			}
			else if(aiMark.equals("X")) {
				System.out.println("➤ PLAYER 2 | AI player 2's mark is O!");
				UltTTTGame newGame = new UltTTTGame(new AI("Player1", aiMark), new AI("Player2", "O"));	
				newGame.start();
			}
			break;
		}
		System.out.println();
	}
	
	// method to generate random marks for AI players
	public String randomMark() {
		int randMarker = (int)(Math.random()*2);
		if(randMarker == 1) {
			return "X";
		}
		return "O";
	}
	
	// method to switch players after every turn, using player indices
	private void switchPlayer() {
		if(currPlayer == 0)
			currPlayer = 1;
		else if(currPlayer == 1)
			currPlayer = 0;
	}
	
	// method to start game!
	void start() {
		// set current player index to 1 (which will switch to player index 0 upon start)
		this.currPlayer = 1;
		do {
			// at the beginning of each turn: 
			// 		1. switch the current player,
			// 		2. print the current board,
			// 		3. check if there's a winner for the individual boards
			switchPlayer();
			print();
			checkWinners();
			System.out.println("➤ Player " + players[currPlayer].getMark() + " is currently playing!");
			
			// the player gets to choose a board only when the board
			// they're sent to is full or if it's the first move
			if(UltTTTBoard[currBoard].isFull() || firstMove) {
				currBoard = players[currPlayer].chooseBoard();
				firstMove = false;
			}
			// any other time in the game, the box selected will send you to
			// the corresponding board
			else {
				currBox = currBoard;
			}
			System.out.println("You're on Board #" + (currBoard+1) + "!");
			
			// store the current board in case player chooses a full box
			prevBoard = currBoard;
			// set the current box to whatever box the current player chooses
			currBox = players[currPlayer].chooseBox();
			
			// every time a move is made on a box, check if individual board has a winner
			// if not, check if ultimate board has a winner
			if(UltTTTBoard[currBoard].makeMark(players[currPlayer].getMark(), currBox)) {
				if(!UltTTTBoard[currBoard].hasWon())
					UltTTTBoard[currBoard].hasWinner();
				// set current box to current board to continue playing
				currBox = currBoard;
			}
			// else if move can't be made/the box is full,  
			else {
				System.out.println("Box is full! Please choose another one.");
				switchPlayer();
				// go back to previous box 
				prevBoard = currBox;
			}
		}
		while (!gameOver());
	}
	
	
	// method to print the ultimate tic-tac-toe board
	private void print() {
		System.out.println("\n--~~=►► CURRENT BOARD ◄◄=~~--");
		System.out.println("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
		
		// print each row of the board with whichever placeholder is present
        for(int i = 0; i < 3; i++) {
            System.out.print(" ▌ ");
            for (int j = 0; j < 3; j++)
                System.out.print(UltTTTBoard[i].boxes[j].getPlaceHolder() + " "); 
        }
        System.out.print(" ▌ \n");

        for(int i = 0; i < 3; i++) {
            System.out.print(" ▌ ");
            for (int j = 3; j < 6; j++)
                System.out.print(UltTTTBoard[i].boxes[j].getPlaceHolder() + " "); 
        }
        System.out.print(" ▌ \n");
        
        for(int i = 0; i < 3; i++) {
            System.out.print(" ▌ ");
            for (int j = 6; j < 9; j++)
                System.out.print(UltTTTBoard[i].boxes[j].getPlaceHolder() + " "); 
        }
        System.out.print(" ▌ ");
		System.out.println("\n▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");

        for(int i = 3; i < 6; i++) {
            System.out.print(" ▌ ");
            for (int j = 0; j < 3; j++)
                System.out.print(UltTTTBoard[i].boxes[j].getPlaceHolder() + " "); 
        }
        System.out.print(" ▌ \n");

        for(int i = 3; i < 6; i++) {
            System.out.print(" ▌ ");
            for (int j = 3; j < 6; j++)
                 System.out.print(UltTTTBoard[i].boxes[j].getPlaceHolder() + " "); 
        }
        System.out.print(" ▌ \n");
        
        for(int i = 3; i < 6; i++) {
            System.out.print(" ▌ ");
            for (int j = 6; j < 9; j++)
                 System.out.print(UltTTTBoard[i].boxes[j].getPlaceHolder() + " "); 
        }
        System.out.print(" ▌ ");
		System.out.println("\n▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");

        for(int i = 6; i < 9; i++) {
            System.out.print(" ▌ ");
            for (int j = 0; j < 3; j++)
                System.out.print(UltTTTBoard[i].boxes[j].getPlaceHolder() + " "); 
        }
        System.out.print(" ▌ \n");

        for(int i = 6; i < 9; i++) {
            System.out.print(" ▌ ");
            for (int j = 3; j < 6; j++)
                 System.out.print(UltTTTBoard[i].boxes[j].getPlaceHolder() + " "); 
        }
        System.out.print(" ▌ \n");
        
        for(int i = 6; i < 9; i++) {
            System.out.print(" ▌ ");
            for (int j = 6; j < 9; j++)
                 System.out.print(UltTTTBoard[i].boxes[j].getPlaceHolder() + " "); 
        }
        System.out.print(" ▌ ");
		System.out.println("\n▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
    }

	
	// method to check the winners of any individual boards
	// after each turn
	public void checkWinners() {
		// go through 9 boards, check hasWon method in board, output winners
		for(int i=0; i<size; i++) {
			if(UltTTTBoard[i].hasWon()) {
				System.out.println("Player " + UltTTTBoard[i].getWinner()
									+ " has won Board #" + (i+1) + "!");
			}
		}
		System.out.println();
	}
	
	// game is over only if board is full or if there's a winner 
	public boolean gameOver() {
		// if game tied, print final board and output tied message
		if (isFull()) {
			print();
			checkWinners();
			System.out.println("➤ Game tied! Try again next time •︿•");
			return true;
		}
		else if (hasWinner()) {
			return true;
		}
		return false;
	}
	
	
	// method to check all possibilities for a player to win
	public boolean hasWinner() {
		// there's a winner for UltTTT game if a board has a winner and its winner is
		// equal to the corresponding boards needed to win (vertical, horizontal, diagonal).
		// then, print final board and a winning message
		if (UltTTTBoard[0].hasWon() 
				&& UltTTTBoard[0].getWinner().equals(UltTTTBoard[3].getWinner())
        		&& UltTTTBoard[0].getWinner().equals(UltTTTBoard[6].getWinner())) {
			print();
			checkWinners();
            System.out.println("➤ " + UltTTTBoard[0].getWinner() + " won three boards vertically! Congrats! •ᴗ•");
            return true;
        }
        else if (UltTTTBoard[1].hasWon() 
        		&& UltTTTBoard[1].getWinner().equals(UltTTTBoard[4].getWinner())
        		&& UltTTTBoard[1].getWinner().equals(UltTTTBoard[7].getWinner())) {
			print();
			checkWinners();
            System.out.println("➤ " + UltTTTBoard[1].getWinner() + " won three boards vertically! Congrats! •ᴗ•");
            return true;
        }
        else if (UltTTTBoard[2].hasWon() 
        		&& UltTTTBoard[2].getWinner().equals(UltTTTBoard[5].getWinner())
        		&& UltTTTBoard[2].getWinner().equals(UltTTTBoard[8].getWinner())) {
			print();
			checkWinners();
            System.out.println("➤ " + UltTTTBoard[2].getWinner() + " won three boards vertically! Congrats! •ᴗ•");
            return true;
        }
        else if (UltTTTBoard[0].hasWon()
        		&& UltTTTBoard[0].getWinner().equals(UltTTTBoard[1].getWinner())
				&& UltTTTBoard[0].getWinner().equals(UltTTTBoard[2].getWinner())) {
			print();
			checkWinners();
            System.out.println("➤ " + UltTTTBoard[0].getWinner() + " won three boards horizontally! Congrats! •ᴗ•");
            return true;
        }
        else if (UltTTTBoard[3].hasWon() 
        		&& UltTTTBoard[3].getWinner().equals(UltTTTBoard[4].getWinner())
        		&& UltTTTBoard[3].getWinner().equals(UltTTTBoard[5].getWinner())) {
			print();
			checkWinners();
            System.out.println("➤ " + UltTTTBoard[3].getWinner() + " won three boards horizontally! Congrats! •ᴗ•");
            return true;
        }
        else if (UltTTTBoard[6].hasWon() 
        		&& UltTTTBoard[6].getWinner().equals(UltTTTBoard[7].getWinner())
        		&& UltTTTBoard[6].getWinner().equals(UltTTTBoard[8].getWinner())) {
			print();
			checkWinners();
            System.out.println("➤ " + UltTTTBoard[6].getWinner() + " won three boards horizontally! Congrats! •ᴗ•");
            return true;
        }
        else if (UltTTTBoard[0].hasWon() 
        		&& UltTTTBoard[0].getWinner().equals(UltTTTBoard[4].getWinner())
        		&& UltTTTBoard[0].getWinner().equals(UltTTTBoard[8].getWinner())) {
			print();
			checkWinners();
            System.out.println("➤ " + UltTTTBoard[0].getWinner() + " won three boards diagonally! Congrats! •ᴗ•");
            return true;
        }
        else if (UltTTTBoard[2].hasWon() 
        		&& UltTTTBoard[2].getWinner().equals(UltTTTBoard[4].getWinner())
        		&& UltTTTBoard[2].getWinner().equals(UltTTTBoard[6].getWinner())) {
			print();
			checkWinners();
            System.out.println("➤ " + UltTTTBoard[2].getWinner() + " won three boards diagonally! Congrats! •ᴗ•");
            return true;
        }
        return false;
	}
	
	// method to check if each individual board is available
	public boolean isFull() {
		for(int i=0; i<size; i++) {
			if(!UltTTTBoard[i].isFull()) {
				return false;
			}
		}
		return true;
	}
}
