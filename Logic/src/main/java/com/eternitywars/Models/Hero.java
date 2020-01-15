package com.eternitywars.Models;

public class Hero {
    private int hp;
    private int deathessence;
    private int mana;

    public Hero() {
        this.hp = 30;
        this.deathessence = 0;
        this.mana = 1;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDeathessence() {
        return deathessence;
    }

    public void setDeathessence(int deathessence) {
        this.deathessence = deathessence;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }
}
