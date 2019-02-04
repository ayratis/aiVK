package com.iskhakovayrat.aivk.model.longpoll_server;

public class Updates {
    private int first;
    private int second;
    private int third;
    private int fourth;
    private int fifth;
    private String text;

    public Updates(int first, int second, int third, int fourth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    public Updates(int first, int second, int third, int fourth, int fifth, String text) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
        this.fifth = fifth;
        this.text = text;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

    public int getThird() {
        return third;
    }

    public int getFourth() {
        return fourth;
    }

    public int getFifth() {
        return fifth;
    }

    public String getText() {
        return text;
    }
}
