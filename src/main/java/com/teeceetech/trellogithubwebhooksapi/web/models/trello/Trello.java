package com.teeceetech.trellogithubwebhooksapi.web.models.trello;

public class Trello {
    public int board;
    public int card;

    public Trello() {
    }

    public int getBoard() {
        return board;
    }

    public void setBoard(int board) {
        this.board = board;
    }

    public int getCard() {
        return card;
    }

    public void setCard(int card) {
        this.card = card;
    }
}
