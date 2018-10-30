public class Coin {
    private int coin;
    private String currencyName;
    private int index;

    public Coin(int index, String currencyName, int coin){
        this.index = index;
        this.currencyName = currencyName;
        this.coin = coin;
    }

    public int getCoin(){
        return coin;
    }


    public String getCurrencyName(){
        return currencyName;
    }

    public int getIndex(){
        return index;
    }

    public void setIndex(int index){
        this.index=index;
    }
}
