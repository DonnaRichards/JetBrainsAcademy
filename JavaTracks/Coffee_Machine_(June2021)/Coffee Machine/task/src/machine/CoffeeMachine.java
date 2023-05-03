package machine;

import java.util.Scanner;

class Machine {

    public enum States {
        WAITING_FOR_ACTION,
        COFFEE_SELECTION,
        WAITING_FOR_WATER_QTY,
        WAITING_FOR_MILK_QTY,
        WAITING_FOR_COFFEE_QTY,
        WAITING_FOR_CUPS_QTY,
        EXIT
    }

    int cashBalance;
    int waterStock;
    int milkStock;
    int coffeeStock;
    int cupStock;
    States state;

    Machine() {
        this.cashBalance = 550;
        this.waterStock = 400;
        this.milkStock = 540;
        this.coffeeStock = 120;
        this.cupStock = 9;
        this.state = States.WAITING_FOR_ACTION;
    }

    Machine(int cashBalance,
                       int waterStock,
                       int milkStock,
                       int coffeeStock,
                       int cupStock) {
        this.cashBalance = cashBalance;
        this.waterStock = waterStock;
        this.milkStock = milkStock;
        this.coffeeStock = coffeeStock;
        this.cupStock = cupStock;
        this.state = States.WAITING_FOR_ACTION;
    }

    private void printState() {
        System.out.println("\nThe coffee machine has: ");
        System.out.printf("%d ml of water\n", waterStock);
        System.out.printf("%d ml of milk\n", milkStock);
        System.out.printf("%d g of coffee beans\n", coffeeStock);
        System.out.printf("%d disposable cups\n", cupStock);
        System.out.printf("$%d of money\n\n", cashBalance);
    }

    private boolean checkWater(int waterNeeded) {
        if (Math.abs(waterNeeded) <= waterStock) {
            return true;
        } else {
            System.out.println("Sorry, not enough water!");
            return false;
        }
    }

    private void updateWater(int waterUsed) {
        waterStock += waterUsed;
    }

    private boolean checkMilk(int milkNeeded) {
        if (Math.abs(milkNeeded) <= milkStock) {
            return true;
        } else {
            System.out.println("Sorry, not enough milk!");
            return false;
        }
    }

    private void updateMilk(int milkUsed) {
        milkStock += milkUsed;
    }

    private boolean checkCoffeeBeans(int coffeeNeeded) {
        if (Math.abs(coffeeNeeded) <= coffeeStock) {
            return true;
        } else {
            System.out.println("Sorry, not enough coffee beans!");
            return false;
        }
    }

    private void updateCoffeeBeans(int coffeeUsed) {
        coffeeStock += coffeeUsed;
    }

    private boolean checkCoffeeCups(int cupsNeeded) {
        if (Math.abs(cupsNeeded) <= cupStock) {
            return true;
        } else {
            System.out.println("Sorry, not enough cups!");
            return false;
        }
    }

    private void updateCoffeeCups(int cupsUsed) {
        cupStock += cupsUsed;
    }

    private void updateCash(int price) {
        cashBalance += price;
    }

    private void makeCoffee(int water, int milk, int coffeeBeans, int cups, int cost) {
        if (checkWater(water) &&
                checkMilk(milk) &&
                checkCoffeeBeans(coffeeBeans) &&
                checkCoffeeCups(cups)) {
            System.out.println("I have enough resources, making you a coffee!");
            updateWater(water);
            updateMilk(milk);
            updateCoffeeBeans(coffeeBeans);
            updateCoffeeCups(cups);
            updateCash(cost);
        }
    }

    private void makeExpresso() {
        final int water = -250;
        final int milk = 0;
        final int coffeeBeans = -16;
        final int cups = -1;
        final int price = 4;
        makeCoffee(water, milk, coffeeBeans, cups, price);
    }

    private void makeLatte() {
        final int water = -350;
        final int milk = -75;
        final int coffeeBeans = -20;
        final int cups = -1;
        final int price = 7;

        makeCoffee(water, milk, coffeeBeans, cups, price);
    }

    private void makeCapuccino() {
        final int water = -200;
        final int milk = -100;
        final int coffeeBeans = -12;
        final int cups = -1;
        final int price = 6;

        makeCoffee(water, milk, coffeeBeans, cups, price);
    }

    private void buyCoffee(String choice) {
        switch (choice) {
            case "1":
                makeExpresso();
                break;

            case "2":
                makeLatte();
                break;

            case "3":
                makeCapuccino();
                break;

            case "back":
                break;

            default:
                System.out.println("Invalid choice");
        }
    }

    private void takeCash() {
        System.out.printf("I gave you $%d\n", cashBalance);
        cashBalance = 0;
    }

    private void resetToMainMenu() {
        System.out.println();
        System.out.println("Write action (buy, fill, take, remaining, exit): ");
        this.state = States.WAITING_FOR_ACTION;
    }

    public void interact(String command) {
        switch (this.state) {
            case WAITING_FOR_ACTION:
                switch (command) {
                    case "buy":
                        System.out.println();
                        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                        this.state = States.COFFEE_SELECTION;
                        break;
                    case "fill":
                        System.out.println("Write how many ml of water you want to add: ");
                        this.state = States.WAITING_FOR_WATER_QTY;
                        break;
                    case "take":
                        takeCash();
                        resetToMainMenu();
                        break;
                    case "remaining":
                        printState();
                        resetToMainMenu();
                        break;
                    case "exit":
                        this.state = States.EXIT;
                }
                break;
            case COFFEE_SELECTION:
                buyCoffee(command);
                resetToMainMenu();
                break;
            case WAITING_FOR_WATER_QTY:
                try {
                    updateWater(Integer.parseInt(command));
                    System.out.println("Write how many ml of milk you want to add: ");
                    this.state = States.WAITING_FOR_MILK_QTY;
                } catch (NumberFormatException e) {
                    System.out.println("Format error in quantity of water");
                    System.out.println("Write how many ml of water you want to add: ");
                }
                break;
            case WAITING_FOR_MILK_QTY:
                try {
                    updateMilk(Integer.parseInt(command));
                    System.out.println("Write how many grams of coffee beans you want to add: ");
                    this.state = States.WAITING_FOR_COFFEE_QTY;
                } catch (NumberFormatException e) {
                    System.out.println("Format error in quantity of milk");
                    System.out.println("Write how many ml of milk you want to add: ");
                }
                break;
            case WAITING_FOR_COFFEE_QTY:
                try {
                    updateCoffeeBeans(Integer.parseInt(command));
                    System.out.println("Write how many disposable cups of coffee you want to add:");
                    this.state = States.WAITING_FOR_CUPS_QTY;
                } catch (NumberFormatException e) {
                    System.out.println("Format error in quantity of coffee beans");
                    System.out.println("Write how many grams of coffee beans you want to add: ");
                }
                break;
            case WAITING_FOR_CUPS_QTY:
                try {
                    updateCoffeeCups(Integer.parseInt(command));
                    resetToMainMenu();
                } catch (NumberFormatException e) {
                    System.out.println("Format error in quantity of cups");
                    System.out.println("Write how many disposable cups of coffee you want to add:");
                }
                break;
            default:
                System.out.println("Unknown command");
                resetToMainMenu();
        }
    }
}

class CoffeeMachine {

    public static void main(String[] args) {

        final Scanner in = new Scanner(System.in);

        Machine machine = new Machine();
        System.out.println("Write action (buy, fill, take, remaining, exit): ");
        while (!machine.state.equals(Machine.States.EXIT)) {
            machine.interact(in.nextLine());
        }
    }
}
