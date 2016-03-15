package com.example.delr.exchangerates;

import java.util.Random;

/**
 * Created by delr on 07.03.16.
 */
class Item {
    private String code;
    private String bid;
    private String ast;

    public Item(String code, String bid, String ast) {
        this.code = code;
        this.ast = ast;
        this.bid = bid;
    }
    public String getCode() {
        return code;
    }
    public String getBid() {
        return bid;
    }
    public String getAst() {
        return ast;
    }
    public static Item next() {
        Random rand = new Random();
        char ch;
        int numb1 = rand.nextInt(90);
        int numb2 = rand.nextInt(90);
        ch = (char)(rand.nextInt(27) + 97);
        return new Item(Character.toString(ch), Integer.toString(numb1), Integer.toString(numb2));
    }
}
