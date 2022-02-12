package banking;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class IntroMenu {
    Scanner scanner = new Scanner(System.in);
    Map<Long, Integer> cardsBalance = new HashMap<>();
    Map<Long, Integer> cardNumPass = new HashMap<>();
    long numberCard = 0;
    int passCard = 0;
    int balanceCard = 0;
    int checksum = 9;

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
            System.out.println();
            System.out.println("Bye!");
            System.exit(0);
        }
        else System.out.println();
             System.out.println("Wrong choice");
             System.out.println();
             askCreateOrLog();
    }

    String createCard() {
        cardNumPass.put(1L,1);
        numberCard = 4000000000000000L + ((long)(Math.random()*1000000000L) * 10);   //create random cardnumber
        for (Map.Entry<Long, Integer> entry : cardNumPass.entrySet()) {                                //check for matches
            if(numberCard == entry.getKey()){
                numberCard = 4000000000000000L + ((long)(Math.random()*1000000000L) * 10);
            }
        }
        checksum = luhnCheckNum(numberCard);
        numberCard = numberCard + checksum;
        passCard = (int)(Math.random()*9999);
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
        System.out.println();
        System.out.println("Enter your card number:");
        Long cardN = scanner.nextLong();
        System.out.println("Enter your PIN:");
        Integer cardP = scanner.nextInt();
        System.out.println();
        int i = 1;
        for (Map.Entry<Long, Integer> entry : cardNumPass.entrySet()) {
            if(cardN.equals(entry.getKey()) && cardP.equals(entry.getValue())) {
                System.out.println("You have successfully logged in!");
                inAcc(entry.getKey());
                break;
            }
            else if(i == cardNumPass.size()) {
                System.out.println("Wrong card number or PIN!");
                System.out.println();
                askCreateOrLog();
                break;
            }
            i++;
        }
    }

     void inAcc(long numberCard) {
        System.out.println();
        System.out.println("1. Balance\n" +
                "2. Log out\n" +
                "0. Exit");
        int check = scanner.nextInt();
        if(1 == check) {
            System.out.println();
            System.out.println(cardsBalance.get(numberCard));
            inAcc(numberCard);
        }
        else if(2 == check) {
            System.out.println();
            System.out.println("You have successfully logged out!");
            System.out.println();
            askCreateOrLog();
        }
        else if(0 == check) {
            System.out.println();
            System.out.println("Bye!");
            System.exit(0);;
        }
        else System.out.println();
             System.out.println("Wrong choice");
             System.out.println();
             inAcc(numberCard);
    }
}
