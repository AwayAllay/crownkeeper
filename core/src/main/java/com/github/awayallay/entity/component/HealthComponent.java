package com.github.awayallay.entity.component;

import com.badlogic.ashley.core.Component;

public class HealthComponent implements Component {

    private final int maxHealth;
    private int health;

    public HealthComponent(int maxHealth) {
        this.maxHealth = maxHealth;
        health = maxHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
