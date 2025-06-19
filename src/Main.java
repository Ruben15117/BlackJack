import java.util.*;

class card{
    char face;
    char val;
    int int_f;
    int int_v;
    card(){
    }
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
        char[] arrFace={'H','C','D','S'};
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
        int i= rand.nextInt(0,13);
        int j= rand.nextInt(0,4);
        while(!cards.get(i).get(j)){
            i= rand.nextInt(0,13);
            j= rand.nextInt(0,4);
        }
        cards.get(i).set(j,false);
        return new card(j,i);
    }

}


class BlackJack{
    Deck deck;
    BlackJack(){
        deck=new Deck();
    }

    public void printHand(ArrayList<card> hand){
        for(card i:hand){
            i.printCard();
        }
        System.out.println();
    }
    public void printPcHand(ArrayList<card> hand){
        for (int i = 0; i < hand.size()-1 ; i++) {
            hand.get(i).printCard();
        }
        System.out.println("#");
    }
    public void hasAce(ArrayList<card> hand){
        for (int i = 0; i < hand.size(); i++) {
            if(hand.get(i).face=='A' && hand.get(i).int_v==11){
                hand.get(i).int_v=1;
            }
        }
    }
    public boolean isValid(ArrayList<card> hand){
        int sum=0;
        for (int i = 0; i < hand.size(); i++) {
            sum+=hand.get(i).int_v;
        }
        if(sum>21){
            if(1==1)
                return false;
        }
        return true;
    }
    public void play(){
        ArrayList<card> user=new ArrayList<>();
        ArrayList<card> pc=new ArrayList<>();
        user.add(deck.getCard());
        user.add(deck.getCard());
        pc.add(deck.getCard());
        pc.add(deck.getCard());
        Scanner scan=new Scanner(System.in);
        String ans="";
        do{
            printHand(user);
            printPcHand(pc);
            System.out.println("new card? y/n");
            ans=scan.next();
            if (ans.equals("y")){
                user.add(deck.getCard());
            }
        }while(ans.equals("y"));
        printHand(user);
    }
}


public class Main {
    public static void main(String[] args) {
        BlackJack game=new BlackJack();
        game.play();
    }
}