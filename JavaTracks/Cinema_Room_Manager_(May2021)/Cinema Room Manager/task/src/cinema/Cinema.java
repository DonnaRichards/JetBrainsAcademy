package cinema;

import java.util.*;
//import java.lang.Number;

public class Cinema {

    final static Scanner scanner = new Scanner(System.in); // Do not change this line

    static int getNumber(String prompt) {
        int num = 0;
        // code from course using regex to check for number
        //        String input = scanner.nextLine();
        //        if (input.matches("\\d+"))

        do {
            System.out.println(prompt);
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a number!");
                scanner.next();
            }
            num = scanner.nextInt();
        } while (num < 0);
        return num;
    }

    static int getRows() {
        int rows = 0;
        do {
            rows = getNumber("Enter the number of rows: ");
        } while (rows <= 0);
        return rows;
    }

    static int getSeatsInRow() {
        int seatsPerRow = 0;
        do {
            seatsPerRow = getNumber("Enter the number of seats in each row: ");
        } while (seatsPerRow <= 0);
        return seatsPerRow;
    }

    static void printMenu() {
        System.out.println();
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    static int getRow(int rows) {
        int selectedRow = 0;
        do {
            selectedRow = getNumber("Enter a row number: ");
            if (selectedRow <= 0 || selectedRow > rows) {
                System.out.println("Wrong input!");
            }
        } while (selectedRow <= 0 || selectedRow > rows);
        return selectedRow;
    }

    static int getSeat(int seatsPerRow) {
        int selectedSeat = 0;
        do {
            selectedSeat = getNumber("Enter a seat number in that row: ");
            if (selectedSeat <= 0 || selectedSeat > seatsPerRow) {
                System.out.println("Wrong input!");
            }
        } while (selectedSeat <= 0 || selectedSeat > seatsPerRow);
        return selectedSeat;
    }

    static void buyTicket(char[][] cinemaLayout) {
        boolean validSeat = false;
        while (!validSeat) {
            int row = getRow(cinemaLayout.length);
            int seat = getSeat(cinemaLayout[row - 1].length);
            if (cinemaLayout[row - 1][seat - 1] == 'S') {
                System.out.println("Ticket price: $" + calcTicketPrice(row, cinemaLayout.length, cinemaLayout[row - 1].length));
                cinemaLayout[row - 1][seat - 1] = 'B';
                validSeat = true;
            } else {
                System.out.println("That ticket has already been purchased!");
            }
        }
    }

    static void printCinemaLayout(char[][] cinemaLayout) {
        System.out.println("Cinema:");
        System.out.print("  ");
        int rows = cinemaLayout.length;
        int seats_per_row = cinemaLayout[0].length;
        for (int seat = 0; seat < seats_per_row; seat++) {
            System.out.printf("%d ", seat + 1);
        }
        System.out.print("\n");
        for (int row = 0; row < rows; row++ ) {
            System.out.printf("%d ", row + 1);
            for (int seat = 0; seat < seats_per_row; seat++) {
                System.out.print(cinemaLayout[row][seat] + " ");
            }
            System.out.print("\n");
        }

    }

    static int calcTicketPrice(int row, int rows, int seats_per_row) {
        final int SEAT_BREAKPOINT = 60;
        final int FRONT_PRICE = 10;
        final int BACK_PRICE = 8;
        int total_seats = rows * seats_per_row;
        int price;
        if (total_seats <= SEAT_BREAKPOINT) {
            price = FRONT_PRICE;
        } else {
            int front_rows = rows / 2;
            if (row <= front_rows) {
                price = FRONT_PRICE;
            } else {
                price = BACK_PRICE;
            }
        }
        return price;
    }

    static int calcRevenue(int rows, int seats_per_row) {
        final int SEAT_BREAKPOINT = 60;
        final int FRONT_PRICE = 10;
        final int BACK_PRICE = 8;
        int total_seats = rows * seats_per_row;
        int revenue;
        if (total_seats <= SEAT_BREAKPOINT) {
            revenue = total_seats * FRONT_PRICE;
        } else {
            int front_rows = rows / 2;
            int back_rows = rows - front_rows;
            revenue = seats_per_row * ((front_rows * FRONT_PRICE) + (back_rows * BACK_PRICE));
        }
        return revenue;
    }

    static int purchasedTicketsCount(char[][] cinemaLayout) {
        int count = 0;
        double income = 0;
        for (int i = 0; i < cinemaLayout.length; i++) {
            for (int j = 0; j < cinemaLayout[i].length; j++ ) {
                if (cinemaLayout[i][j] == 'B') {
                    count += 1;
                    income += calcTicketPrice(i, cinemaLayout.length, cinemaLayout[i].length);
                }
            }
        }
        return count;
    }

    static int purchasedTicketsIncome(char[][] cinemaLayout) {
        int income = 0;
        for (int i = 0; i < cinemaLayout.length; i++) {
            for (int j = 0; j < cinemaLayout[i].length; j++ ) {
                if (cinemaLayout[i][j] == 'B') {
                    income += calcTicketPrice(i+1, cinemaLayout.length, cinemaLayout[i].length);
                }
            }
        }
        return income;
    }

    static void statistics(char[][] cinemaLayout) {
        int purchasedTicketCount = purchasedTicketsCount(cinemaLayout);
        System.out.printf("Number of purchased tickets: %d\n", purchasedTicketCount);
        double purchasedTicketPercent = (double)(purchasedTicketCount * 100) / (double)(cinemaLayout.length * cinemaLayout[0].length);
        System.out.printf("Percentage: %.2f%%\n", purchasedTicketPercent);
        int purchasedTicketIncome  = purchasedTicketsIncome(cinemaLayout);
        System.out.printf("Current income: $%d\n", purchasedTicketIncome);
        System.out.printf("Total income: $%d\n", calcRevenue(cinemaLayout.length, cinemaLayout[0].length));
    }

    public static void main(String[] args) {
        // Write your code here

        int rows = getRows();
        int seatsPerRow = getSeatsInRow();
        char[][] cinemaLayout = new char[rows][seatsPerRow];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seatsPerRow; j++) {
                cinemaLayout[i][j] = 'S';
            }
        }

        int response = -1;
        while (response != 0) {
            printMenu();
            response = getNumber("");
            if (response == 1) {
                printCinemaLayout(cinemaLayout);
            }
            else if (response == 2) {
                buyTicket(cinemaLayout);
            }
            else if (response == 3) {
                statistics(cinemaLayout);
            }
        }

        //        System.out.println("Total Income: ");
        //        System.out.printf("$%d\n", calcProfit(rows, seats_per_row));

    }
}