package com.geektech;

import java.util.Random;

public class Main {

    public static String[] avengersNames = {"Captain America", "Black Panther", "Thor"};
    public static int[] avengersHealth = {220, 310, 420};
    public static int[] avengersPower = {30, 40, 50};
    public static String villainName = "Thanos";
    public static int villainHealth = 1000;
    public static int villainPower = 100;
    public static int roundNumber = 0;
    public static String superAvenger = "";

    public static String medicName = "Shuri";
    public static int medicHealth = 510;
    public static int medicHealingPower = 80;
    public static String healedAvenger = "";

    public static void main(String[] args) {
        printStatistics();
        System.out.println("-----------THANOS INVADES THE EARTH!!!-----------");
        while (!gameOver()) {
            round();
        }
    }

    // Round
    public static void round() {
        roundNumber++;
        System.out.println("-----------Round Number " + roundNumber + "-----------");
        villainHits();
        superAvenger = getAvengerForSuperHit();
        healedAvenger = getAvengerForHealing();
        medicHeals();
        avengersHit();
        printStatistics();
    }

    // Game Over
    public static boolean gameOver() {
        if (villainHealth <= 0) {
            System.out.println("-----------------------------------");
            System.out.println("AVENGERS WON!!! THE EARTH IS SAFE!!!");
            System.out.println("-----------------------------------");
            return true;
        }
        boolean allAvengersDead = true;
        for (int avengerHealth : avengersHealth) {
            if (avengerHealth > 0) {
                allAvengersDead = false;
                break;
            }
        }
        if (allAvengersDead) {
            System.out.println("-----------------------------------");
            System.out.println("OH NO!!! " + villainName + " WON!!! THE EARTH IS DESTROYED!!!");
            System.out.println("-----------------------------------");
        }
        return allAvengersDead;
    }

    // Villain Hits
    public static void villainHits() {
        for (int i = 0; i < avengersHealth.length; i++) {
            if (avengersHealth[i] > 0 && villainHealth > 0) {
                avengersHealth[i] = avengersHealth[i] - villainPower;
            }
        }
        medicHealth = medicHealth - villainPower;
    }

    // Avengers Hit
    public static void avengersHit() {
        Random random = new Random();
        int coef = random.nextInt(6) + 2;
        for (int i = 0; i < avengersPower.length; i++) {
            if (avengersHealth[i] > 0 && villainHealth > 0) {
                if (superAvenger == avengersNames[i]) {
                    villainHealth = villainHealth - avengersPower[i] * coef;
                    System.out.println("Super Hit Avenger is " + superAvenger + ", Super Hit Power = ["
                            + (avengersPower[i] * coef) + "]");
                }
                villainHealth = villainHealth - avengersPower[i];
            }
            if (avengersHealth[i] < 0) {
                avengersHealth[i] = 0;
            }
            if (villainHealth < 0) {
                villainHealth = 0;
            }
            if (medicHealth < 0) {
                medicHealth = 0;
            }
        }
    }

    // Shuri Heals
    public static void medicHeals() {
        for (int i = 0; i < avengersHealth.length; i++) {
            if (avengersHealth[i] > 0 && avengersHealth[i] < 100 && medicHealth > 0) {
                if (healedAvenger == avengersNames[i]) {
                    avengersHealth[i] = avengersHealth[i] + medicHealingPower;
                    System.out.println("Healed Avenger is " + healedAvenger + ", Restored Health = {"
                            + medicHealingPower + "}");
                }
            }
        }
    }

    // Healed Random Avenger
    public static String getAvengerForHealing() {
        Random random = new Random();
        int randomIndex = random.nextInt(avengersNames.length);
        return avengersNames[randomIndex];
    }

    // Super Power Random Avenger
    public static String getAvengerForSuperHit() {
        Random random = new Random();
        int randomIndex = random.nextInt(avengersNames.length);
        return avengersNames[randomIndex];
    }

    // Statistics
    public static void printStatistics() {
        System.out.println(villainName + "' health = " + villainHealth + ", Damage Power = [" + villainPower + "]");
        for (int i = 0; i < avengersNames.length; i++) {
            System.out.println(avengersNames[i] + "'s health = " + avengersHealth[i] +
                    ", Damage Power = [" + avengersPower[i] + "]");
        }
        System.out.println(medicName + "' health = " + medicHealth + ", Healing Power = [" + medicHealingPower + "]");
    }
}
