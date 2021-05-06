package com.geektech;

import java.util.Random;

public class Main {

    public static String[] avengersNames = {"Captain America", "Black Panther", "Iron Man"};
    public static int[] avengersHealth = {220, 310, 420};
    public static int[] avengersPower = {30, 40, 50};
    public static String villainName = "Thanos";
    public static int villainHealth = 2000;
    public static int villainPower = 100;
    public static int roundNumber = 0;
    public static String superAvenger = "";

    public static String medicName = "Shuri";
    public static int medicHealth = 510;
    public static int medicHealingPower = 100;
    public static String healedAvenger = "";

    public static String golemName = "Golem";
    public static int golemHealth = 500;
    public static int golemPower = 10;

    public static String luckyName = "Lucky";
    public static int luckyHealth = 230;

    public static String berserkName = "Berserk";
    public static int berserkHealth = 270;
    public static int berserkPower = 35;

    public static String thorName = "Thor";
    public static int thorHealth = 500;

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
        System.out.println("-----------ROUND NUMBER " + roundNumber + "-----------");
        thorChance();
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
            if (avengerHealth > 0 || medicHealth > 0 || golemHealth > 0 || luckyHealth > 0 || berserkHealth > 0 || thorHealth > 0) {
                allAvengersDead = false;
                break;
            }
        }
        if (allAvengersDead || medicHealth < 0 || golemHealth < 0 || luckyHealth < 0 || berserkHealth < 0 || thorHealth < 0) {
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
                avengersHealth[i] = avengersHealth[i] - (villainPower * 4/5);
            }
        }
        if (villainHealth > 0) {
            medicHealth = medicHealth - (villainPower * 4/5);
        }
        if (golemHealth > 0 && villainHealth > 0) {                                                 // 1/5 part of damage to Shuri and Thor
            golemHealth = golemHealth - villainPower - (villainPower * avengersNames.length * 1/5) - (villainPower * 2/5);
        }
        if (luckyHealth > 0 && villainHealth > 0) {
            luckyHealth = luckyHealth - villainPower * luckyChance();
        }
        if (berserkHealth > 0 && villainHealth > 0) {
            berserkHealth = berserkHealth - (villainPower * 8/10);
        }
        if (thorHealth > 0 && villainHealth > 0) {
            thorHealth = thorHealth - (villainPower * 4/5);
        }
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
            if (golemHealth < 0) {
                golemHealth = 0;
            }
            if (luckyHealth < 0) {
                luckyHealth = 0;
            }
            if (berserkHealth < 0) {
                berserkHealth = 0;
            }
            if (thorHealth < 0) {
                thorHealth = 0;
            }
        }
        if (golemHealth > 0 && villainHealth > 0) {
            villainHealth = villainHealth - golemPower;
        }
        if (berserkHealth > 0 && villainHealth > 0) {
            villainHealth = villainHealth - berserkPower - villainPower * 2/10;
        }
        if (villainHealth < 0) {
            villainHealth = 0;
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
        if (medicHealth < 0) {
            medicHealth = 0;
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

    // Lucky's Random chance
    public static int luckyChance() {
        Random random = new Random();
        int randomChance = random.nextInt(2);
        return randomChance;
    }

    // Thor's Random chance
    public static void thorChance(){
        Random random = new Random();
        int randomChance = random.nextInt(3);
        if (randomChance == 0) {
            villainPower = 0;
            System.out.println("Thor stunned Thanos for one round!");
        } else {
            villainPower = 100;
            System.out.println("Thor missed!");
        }
    }

    // Statistics
    public static void printStatistics() {
        System.out.println("-----------------------------------------------");
        System.out.println(villainName + "' health = " + villainHealth + ", Damage Power = [" + villainPower + "]");
        for (int i = 0; i < avengersNames.length; i++) {
            System.out.println(avengersNames[i] + "'s health = " + avengersHealth[i] +
                    ", Damage Power = [" + avengersPower[i] + "]");
        }
        System.out.println(medicName + "'s health = " + medicHealth + ", Healing Power = [" + medicHealingPower + "]");
        System.out.println(golemName + "'s health = " + golemHealth + ", Damage Power = [" + golemPower + "]");
        System.out.println(luckyName + "'s health = " + luckyHealth);
        System.out.println(berserkName + "'s health = " + berserkHealth + ", Damage Power = [" + berserkPower +
                "], Returned Power = [" + villainPower * 2/10 + "]");
        System.out.println(thorName + "'s health = " + thorHealth);
    }

}
