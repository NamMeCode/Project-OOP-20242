package control;

import card.ListOfCards;
import player.AbstractPlayer;
import player.Player;
import player.Bot;
import rule.ThirteenSRule;
import java.util.ArrayList;
import java.util.Scanner;
public class ThirteenSControl {
    final ArrayList<AbstractPlayer> playersInGame = new ArrayList<AbstractPlayer>();
    ArrayList<AbstractPlayer> playersWinGame = new ArrayList<AbstractPlayer>();
    private String gameType = "ThirteenS";
    ListOfCards Deck = new ListOfCards();
    ThirteenSRule rule = new ThirteenSRule();

    public ThirteenSControl(int numberOfPlayers, int numberOfBots) {
        Deck.initializeDeck();

        for (int i = 0; i < numberOfPlayers; i++) {
            playersInGame.add(new Player(gameType));
        }
        for (int i = 0; i < numberOfBots; i++) {
            playersInGame.add(new Bot(gameType));
        }
        for (AbstractPlayer player : playersInGame) {
            player.setCardsOnHand(Deck.drawCard(13));
        }
        for(AbstractPlayer player: playersInGame) {
            if(player.isWin()) {
                playersWinGame.add(player);
                playersInGame.remove(player);
            }
        }
        mainGame();
    }
    public void GuideLines()
    {
        System.out.println("To Select A Card On Your Hand, Use Instruction: Select [order of card] ");
        System.out.println("To Stop Selecting A Card On Your Hand, Use Instruction: Unselect [order of card] ");
        System.out.println("To Play Your Selected Cards, Use Instruction: Play");
        System.out.println("To pass the round, use Instruction: Pass");
    }
    public void mainGame() {
        AbstractPlayer playerWinLastRound = playersInGame.getFirst();
        while (playersInGame.size() > 1) {
            ThirteenSRound currentRound = new ThirteenSRound(playersInGame, playerWinLastRound);
            playerWinLastRound = currentRound.getPlayerLastInRound();
        }
        playersWinGame.add(playersInGame.getFirst());
        System.out.println();


    }

    private class ThirteenSRound {
        ArrayList<AbstractPlayer> playersInRound =new ArrayList<AbstractPlayer>();
        AbstractPlayer playerLastInRound;
        ListOfCards cardsOnTable = new ListOfCards();
        Scanner scanner= new Scanner(System.in);
        public ThirteenSRound(ArrayList<AbstractPlayer> playersInGame, AbstractPlayer playerStartRound) {
            initializePlayersInRound(playersInGame);
            int playerIndex = playersInRound.indexOf(playerStartRound);
            AbstractPlayer currentPlayer = playerStartRound;
            while(playersInRound.size() > 1) {
                turnOfAPlayer: while(true) {
                    MenuOfPlayer(currentPlayer);
                    if (currentPlayer instanceof Bot) {
                        if (!((Bot) currentPlayer).autoPlayCards(cardsOnTable))
                        {
                            playersInRound.remove(currentPlayer);
                            playerIndex--;
                        }
                        break turnOfAPlayer;
                    }

                    String decision= scanner.next();
                    switch(decision) {
                        case "Play":{
                            if (currentPlayer.playCards(cardsOnTable))
                            {
                                if (currentPlayer.isWin()) {
                                    playersWinGame.add(currentPlayer);
                                    playersInRound.remove(currentPlayer);
                                    playersInGame.remove(currentPlayer);
                                    playerIndex --;
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
                            playerIndex--;
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
                playerIndex=(playerIndex+1)%playersInRound.size();

                currentPlayer = playersInRound.get(playerIndex);
            }
            playerLastInRound = playersInRound.getFirst();
        }

        public void initializePlayersInRound(ArrayList<AbstractPlayer> playersInGame) {
            for (AbstractPlayer player : playersInGame) {
                playersInRound.add(player);
            }
        }

        public AbstractPlayer getPlayerLastInRound() {
            return playerLastInRound;
        }

        public void MenuOfPlayer(AbstractPlayer player) {
            System.out.println("The cards on table are: "+cardsOnTable.toString());

            if (player instanceof Player) {
                System.out.println("Player "+player.getId()+" Is Now Playing");
                System.out.println("The cards on hand are: " + player.toStringCardsOnHand());
                System.out.println("Your selected cards are: " + player.toStringCardsSelected());


            }
            if (player instanceof Bot)
            {
                System.out.println("Bot "+player.getId()+" Is Playing..");
                //remaining code
            }
        }


    }


}