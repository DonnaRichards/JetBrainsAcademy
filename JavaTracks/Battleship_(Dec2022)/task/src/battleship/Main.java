package battleship;

import java.util.Scanner;

public class Main {


    private static void passMove() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Press Enter and pass the move to another player");
        sc.nextLine();
    }

    public static void main(String[] args) {

        final int NUMBER_OF_PLAYERS = 2;

        Board[] playerBoards = new Board[NUMBER_OF_PLAYERS];
        for (int player = 1; player <= NUMBER_OF_PLAYERS; player++) {
            playerBoards[player - 1] = new Board();
            playerBoards[player - 1].placeShips(player);
            if (player < NUMBER_OF_PLAYERS) passMove();
        }

        passMove();
        boolean winner = false;
        int playerTurn = 1;
        int playerBoard = 0;
        int opponentBoard = 1;
        while (!winner) {
            playerBoards[opponentBoard].printBoard(true);
            System.out.println("-".repeat(21));
            playerBoards[playerBoard].printBoard(false);
            System.out.printf("Player %d, it's your turn: \n", playerTurn);
            boolean shot = playerBoards[opponentBoard].takeAShot();
            if (shot) {
                winner = playerBoards[opponentBoard].allSunk();
                if (!winner) {
                    boolean shipSunk = false;
                    for (Ship ship : playerBoards[opponentBoard].getShips()) {
                        if (playerBoards[opponentBoard].shipSunk(ship)) {
                            shipSunk = true;
                            break; // from for loop
                        }
                    }
                    if (shipSunk) {
                        System.out.println("You sank a ship!");
                    }
                    else {
                        System.out.println("You hit a ship!");
                    }
                }
            }
            else {
                System.out.println("You missed!");
            }
            if (!winner) {
                passMove();
                playerTurn = playerTurn == 1 ? 2 : 1;
                playerBoard = playerBoard == 0 ? 1 : 0;
                opponentBoard = opponentBoard == 0 ? 1 : 0;
            }
        }
        System.out.println("You sank the last ship. You won. Congratulations!");
    }
}
