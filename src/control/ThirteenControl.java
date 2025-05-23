package control;

import card.ListOfCards;
import player.Actor;
import player.Player;
import player.ThirteenSBot;
import rule.ThirteenSRule;
import java.util.ArrayList;
import java.util.Scanner;
public class ThirteenControl {
    final ArrayList<Actor> playersInGame = new ArrayList<Actor>();
    ArrayList<Actor> playersWinGame = new ArrayList<Actor>();
    private final String gameType;
    ListOfCards Deck = new ListOfCards();
    ThirteenSRule rule = new ThirteenSRule();

    public ThirteenControl(int numberOfPlayers, int numberOfBots, String gameType) {
        Deck.initializeDeck(gameType);
        this.gameType = gameType;
        for (int i = 0; i < numberOfPlayers; i++) {
            playersInGame.add(new Player(gameType));
        }
        for (int i = 0; i < numberOfBots; i++) {
            playersInGame.add(new ThirteenSBot(gameType));
        }
        for (Actor player : playersInGame) {
            player.setCardsOnHand(Deck.drawCard(13));
        }
        for(Actor player: playersInGame) {
            if(player.isWin()) {
                playersWinGame.add(player);
                playersInGame.remove(player);
            }
        }
        mainGame();
    }
    public void guideLines()
    {
        System.out.println("To Select A Card On Your Hand, Use Instruction: Select [order of card] ");
        System.out.println("To Stop Selecting A Card On Your Hand, Use Instruction: Unselect [order of card] ");
        System.out.println("To Play Your Selected Cards, Use Instruction: Play");
        System.out.println("To pass the round, use Instruction: Pass");
    }
    public void mainGame() {
        guideLines();
        Actor playerWinLastRound = playersInGame.getFirst();

        while (playersInGame.size() > 1) {
            ThirteenRound currentRound = new ThirteenRound(playersInGame, playerWinLastRound);
            playerWinLastRound = currentRound.getPlayerLastInRound();
        }
        playersWinGame.add(playersInGame.getFirst());
        System.out.println();


    }

    private class ThirteenRound {
        ArrayList<Actor> playersInRound =new ArrayList<Actor>();
        Actor playerLastInRound;
        ListOfCards cardsOnTable = new ListOfCards();
        Scanner scanner= new Scanner(System.in);

        public ThirteenRound(ArrayList<Actor> playersInGame, Actor playerStartRound) {
            initializePlayersInRound(playersInGame);
            int currentPlayerIndex = playersInRound.indexOf(playerStartRound);
            Actor currentPlayer = playerStartRound;
            while(playersInRound.size() > 1) {
                turnOfAPlayer: while(true) {

                    if (currentPlayer instanceof ThirteenSBot) {
                        if (!((ThirteenSBot) currentPlayer).autoPlayCards(cardsOnTable))
                        {
                            playersInRound.remove(currentPlayer);
                            currentPlayerIndex--;
                        }
                        MenuOfPlayer(currentPlayer);
                        break turnOfAPlayer;
                    }
                    MenuOfPlayer(currentPlayer);


                    String decision= scanner.next();
                    switch(decision) {
                        case "Play":{
                            if (currentPlayer.playCards(cardsOnTable))
                            {
                                if (currentPlayer.isWin()) {
                                    playersWinGame.add(currentPlayer);
                                    playersInRound.remove(currentPlayer);
                                    playersInGame.remove(currentPlayer);
                                    currentPlayerIndex --;
                                }
                                break turnOfAPlayer;
                            }
                            else
                            {
                                System.out.println("Invalid Play");
                            }
                        }
                        case "Sort":
                        {
                            currentPlayer.sortCardsOnHand();
                            break;

                        }
                        case "Pass":
                        {
                            playersInRound.remove(currentPlayer);
                            currentPlayerIndex--;
                            break turnOfAPlayer;
                        }
                        case "Select":
                        {
                            int indexOfCard=scanner.nextInt();
                            currentPlayer.selectCard(indexOfCard);
                            break ;
                        }
                        case "Unselect":
                        {
                            int indexOfCard=scanner.nextInt();
                            currentPlayer.unselectCard(indexOfCard);
                            break ;
                        }
                    }
                }
                currentPlayerIndex=(currentPlayerIndex+1)%playersInRound.size();

                currentPlayer = playersInRound.get(currentPlayerIndex);
            }
            playerLastInRound = playersInRound.getFirst();
        }

        public void initializePlayersInRound(ArrayList<Actor> playersInGame) {
            for (Actor player : playersInGame) {
                playersInRound.add(player);
            }
        }

        public Actor getPlayerLastInRound() {
            return playerLastInRound;
        }

        public void MenuOfPlayer(Actor player) {
            System.out.println("The cards on table are: "+cardsOnTable.toString());

            if (player instanceof Player) {
                System.out.println("Player "+player.getId()+" Is Now Playing");
                System.out.println("The cards on hand are: " + player.toStringCardsOnHand());
                System.out.println("Your selected cards are: " + player.toStringCardsSelected());


            }
            if (player instanceof ThirteenSBot)
            {
                System.out.println("Bot "+player.getId()+" Is Playing..");
                //remaining code
            }
        }


    }


}