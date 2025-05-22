package control;

import card.ListOfCards;
import player.Player;
import player.*;
import java.util.Scanner;

public class ReddogControl {
    private final String gameType="Reddog";
    Scanner sc= new Scanner(System.in);
    public ReddogControl()
    {
        startGame();
    }
    public void startGame(){
        ReddogPlayer player= new ReddogPlayer(1000);

        while(true){
            System.out.println("Welcome to Red Dog");
            System.out.println("Your total amount of chips are: "+player.getChipStack());
            System.out.println("Type the number of chips that you want to bet");
            int bet=sc.nextInt();

            startRound(player,bet);
            player.setCardsOnHand(new ListOfCards());
        }




    }
    public void MenuOfPlayer(ReddogPlayer player) {
        System.out.println("The cards on hand are: " + player.toStringCardsOnHand());

    }
    public void startRound(ReddogPlayer player,int bet)
    {

        if (bet>player.getChipStack())
        {
            System.out.println("You don't have enough chips");
            return;
        }

        else
        {

            int payRatio=1;
            player.decreaseChipStack(bet);
            ListOfCards deck= new ListOfCards();
            deck.initializeDeck(gameType);
            player.addCard(deck.drawCard());
            player.addCard(deck.drawCard());
            MenuOfPlayer(player);
            if (player.getHandType()=="Pair")
            {
                player.addCard(deck.drawCard());
                MenuOfPlayer(player);
                if (player.getHandType()=="ThreeOfAKind")
                {

                    System.out.println("Three of a kind, you win!");
                    payRatio=11;
                    player.increaseChipStack(bet*(payRatio+1));
                }
                else
                {

                    System.out.println("Push");
                    player.increaseChipStack(bet);
                }
                return;
            }
            else if (player.getSpread()==0)
            {
                System.out.println("Push");
                player.increaseChipStack(bet);
                return;
            }
            else
            {
                if (player.getChipStack()>=bet)
                {
                    System.out.println("Do you want to double your bet?");
                    System.out.println("1. Yes");
                    System.out.println("2. No");

                    if (sc.nextInt()==1)
                    {
                        player.decreaseChipStack(bet);
                        bet=bet*2;
                    }
                }

                player.addCard(deck.drawCard());

                switch (player.getSpread())
                {
                    case 1:
                    {
                        payRatio=5;
                        break;
                    }

                    case 2:
                    {
                        payRatio=4;
                        break;
                    }
                    case 3:
                    {
                        payRatio=2;
                        break;
                    }
                    default:
                        payRatio=1;

                }
                MenuOfPlayer(player);
                if (player.isWin())
                {

                    System.out.println("You won with pay ratio " + payRatio);
                    player.increaseChipStack(bet*(payRatio+1));
                }
                else
                {

                    System.out.println("You lost ");
                }


            }

        }
    }

}
