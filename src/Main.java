import java.util.*;

class card{ 
    char face;
    char val;
    int int_f;
    int int_v;
    card(int f, int v){
        int_f=f;
        if(v<9){
            int_v=v+2;
        } else if (v<12) {
            int_v=10;
        }
        else{
            int_v=11;
        }
        char[] arrFace={'♥','♦','♣','♠'};
        char[] arrVal={'2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'};
        face=arrFace[f];
        val=arrVal[v];
    }
    void printCard(){
        System.out.print(face);
        System.out.print(val + " ");
    }

}


class Deck{
    ArrayList<ArrayList<Boolean>> cards;
    Deck(){
        this.cards=new ArrayList<>();
        for(int i=0;i<13;i++){
            this.cards.add(new ArrayList<>());
            for (int j = 0; j < 4; j++) {
                this.cards.get(i).add(true);
            }
        }
    }
    public card getCard(){
        Random rand=new Random();
        int i=rand.nextInt(0,13);
        int j= rand.nextInt(0,4);
        while(!cards.get(i).get(j)){
            i= rand.nextInt(0,13);
            j= rand.nextInt(0,4);
        }
        cards.get(i).set(j,false);
        return new card(j,i);
    }
    public void clear(){
        for(int i=0;i<13;i++){
            for (int j = 0; j < 4; j++) {
                this.cards.get(i).set(j,true);
            }
        }
    }

}


class BlackJack{
    Deck deck;
    int userMoney;
    int betSize;
    BlackJack(){
        deck=new Deck();
        Scanner scan=new Scanner(System.in);
        System.out.println("how much money have you got?");
        userMoney=scan.nextInt();
        System.out.println("how much do you wanna bet?");
        betSize=scan.nextInt();
    }
    public void printBalance(){
        System.out.println("Your Money " + userMoney + "     BetSize " + betSize);
    }
    private void printHand(ArrayList<card> hand){
        for(card i:hand){
            i.printCard();
        }
        System.out.println(getHandVal(hand));
    }
    private void printDealerHand(ArrayList<card> hand){
        for (int i = 0; i < hand.size()-1 ; i++) {
            hand.get(i).printCard();
        }
        System.out.println("#");
    }
    private void printGameState(ArrayList<card> user, ArrayList<card> dealer){
        System.out.print("dealer ");
        printHand(dealer);
        System.out.print("you ");
        printHand(user);
    }
    private int getHandVal(ArrayList<card> hand){
        int sum=0;
        for (card i: hand) {
            sum+=i.int_v;
        }
        return sum;
    }
    private boolean isDeckPlayable(ArrayList<card> hand){
        int sum=getHandVal(hand);
        int i=0;
        while(i <hand.size() && sum>21){
            if(hand.get(i).int_v==11){
                sum-=10;
                hand.get(i).int_v=1;
            }
            i++;
        }
        return sum<=21;
    }
    private boolean dealerCardNeed(ArrayList<card> hand){
        int sum=getHandVal(hand);
        return sum<17;
    }
    public void play(){
        ArrayList<card> user=new ArrayList<>();
        ArrayList<card> dealer=new ArrayList<>();
        user.add(deck.getCard());
        user.add(deck.getCard());
        isDeckPlayable(user);
        dealer.add(deck.getCard());
        dealer.add(deck.getCard());
        isDeckPlayable(dealer);
        Scanner scan=new Scanner(System.in);
        String ans;
        do{
            System.out.print("dealer ");
            printDealerHand(dealer);
            System.out.print("you ");
            printHand(user);
            System.out.println("new card? y/n");
            ans=scan.next();
            if (ans.equals("y")){
                user.add(deck.getCard());
                if(!isDeckPlayable(user)){
                    System.out.println("you lost!!");
                    userMoney-=betSize;
                    printGameState(user, dealer);
                    deck.clear();
                    return;
                }
            }else {
                break;
            }
        }while(true);
        while (dealerCardNeed(dealer)){
                dealer.add(deck.getCard());
                if(!isDeckPlayable(dealer)){
                    System.out.println("you won!!");
                    userMoney+=betSize;
                    printGameState(user, dealer);
                    deck.clear();
                    return;
                }
        }
        if (getHandVal(user)>getHandVal(dealer)){
            System.out.println("you won!!");
            userMoney+=betSize;
        } else if (getHandVal(user)==getHandVal(dealer)) {
            System.out.println("Draw!!");
        } else {
            System.out.println("you lost!!");
            userMoney-=betSize;
        }
        printGameState(user, dealer);
        deck.clear();
    }
}


public class Main {
    public static void main(String[] args) {
        BlackJack game=new BlackJack();
        Scanner scan1=new Scanner(System.in);
        String ans="y";
        while (ans.equals("y")){
            game.play();
            if (game.userMoney<game.betSize){
                System.out.println("You got kicked out of Casino, because you are broke ");
                break;
            }
            game.printBalance();
            System.out.println("Do you wanna continue? y/n");
            ans=scan1.next();
        }
    }
}