package banking;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class IntroMenu {
    Scanner scanner = new Scanner(System.in);
    private final Map<Long, Integer> cardsBalance = new HashMap<>();
    private final Map<Long, Integer> cardNumPass = new HashMap<>();

    void askCreateOrLog() {
        System.out.println("1. Create an account\n" +
                "2. Log into account\n" +
                "0. Exit");
        int choice = scanner.nextInt();
        if(choice == 1) {
            System.out.println(createCard());
            askCreateOrLog();
        }
        else if(choice == 2){
            logInAcc();
        }
        else if(choice == 0){
            System.out.println("\nBye!");
            System.exit(0);
        }
        else System.out.println("\nWrong choice\n");
             askCreateOrLog();
    }

    String createCard() {
        cardNumPass.put(1L,1);
        long numberCard = 4000000000000000L + ((long) (Math.random() * 1000000000L) * 10);   //create random cardnumber
        for (Map.Entry<Long, Integer> entry : cardNumPass.entrySet()) {                                //check for matches
            if(numberCard == entry.getKey()){
                numberCard = 4000000000000000L + ((long)(Math.random()*1000000000L) * 10);
            }
        }
        int checksum = luhnCheckNum(numberCard);
        numberCard = numberCard + checksum;
        int passCard = ThreadLocalRandom.current().nextInt(9999);
        int balanceCard = 0;
        cardsBalance.put(numberCard, balanceCard);
        cardNumPass.put(numberCard, passCard);
        return "\nYour card has been created\n" +
                "Your card number:\n" +
                numberCard + "\n" +
                "Your card PIN:\n" +
                passCard + "\n";
    }

    int luhnCheckNum(long numberCard) {
        String temp = Long.toString(numberCard);
        int[] numbersCard = new int[temp.length()];
        for (int i = 0; i < temp.length(); i++) {
            numbersCard[i] = temp.charAt(i) - '0';
        }
        int len = numbersCard.length;     // узнаем длину номера карты
        int number = 0;                    // текущая цифра в цикле (см. ниже)
        int check_digit = 0;               // переменная которая будет хранить проверочную цифру
        int i;
        for(i = 0; i < len; i++) {      // главный цикл, в процессе него вычисляется проверочная цифра
            number = numbersCard[i];
            if(i % 2 != 0) {                // если позиция цифры нечётное, то:
                number *= 2;               // умножаем цифру на 2
                if(number > 9) {            // согласно алгоритму, ни одно число не должно быть больше 9
                    number -= 9;           // второй вариант сведения к единичному разряду
                }
            }
            check_digit += number;         // прибавляем к check_digit номера согласно алгоритму
        }
        return (check_digit * 9) % 10;     // возвращаем проверочное число вычисленное согласно алгоритму
    }

    void logInAcc() {
        System.out.println("\nEnter your card number:");
        Long cardN = scanner.nextLong();
        System.out.println("Enter your PIN:");
        Integer cardP = scanner.nextInt();
        System.out.println();
        int i = 1;
        for (Map.Entry<Long, Integer> entry : cardNumPass.entrySet()) {
            if(cardN.equals(entry.getKey()) && cardP.equals(entry.getValue())) {
                System.out.println("You have successfully logged in!");
                InAcc.inAcc(entry.getKey());
                break;
            }
            else if(i == cardNumPass.size()) {
                System.out.println("Wrong card number or PIN!\n");
                askCreateOrLog();
                break;
            }
            i++;
        }
    }


     void inAcc(long numberCard) {
        System.out.println("\n1. Balance\n" +
                "2. Log out\n" +
                "0. Exit");
        int check = scanner.nextInt();
        if(1 == check) {
            System.out.println("\n" + cardsBalance.get(numberCard));
            inAcc(numberCard);
        }
        else if(2 == check) {
            System.out.println("\nYou have successfully logged out!\n");
            askCreateOrLog();
        }
        else if(0 == check) {
            System.out.println("\nBye!");
            System.exit(0);;
        }
        else System.out.println("\nWrong choice\n");
             inAcc(numberCard);
    }

}
