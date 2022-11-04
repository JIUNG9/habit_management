package com.module.habit;

public enum IncreaseOrDecrease {
    Increase("increase"),
    Decrease("decrease");

    String increaseOrDecrease;

    IncreaseOrDecrease(String input) {
    this.increaseOrDecrease=input;
    }
    public String getIncreaseOrDecrease(){
        return this.increaseOrDecrease;
    }


}
