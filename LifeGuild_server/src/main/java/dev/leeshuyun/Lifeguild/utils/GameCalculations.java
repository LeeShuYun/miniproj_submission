package dev.leeshuyun.Lifeguild.utils;

import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameCalculations {

    private static final Logger logger = LoggerFactory.getLogger(GameCalculations.class);

    // TODO
    public static int calculateCharacterLevelFromExp(int exp) {
        return (int) Math.log(exp) + 300;
    }

    /*
     * this only affects novice and player job class since reader and writer are
     * special classes
     * TODO add task momentum. faster player complete task consecutively
     * the higher the damage. unsure how to implement, maybe future version
     */
    public static int calcAtkDamage(
            double baseWeaponAtk,
            double weaponFactor,
            String taskDifficulty,
            int critRate) {

        // critRate is 1-100
        int damage = 0;
        double weaponPower = baseWeaponAtk / weaponFactor;

        switch (taskDifficulty) {
            case "low":
                return (int) (weaponPower * 1.2);
            case "med":
                return (int) (weaponPower * 2.4);
            case "high":
                if (isCriticalHit(critRate)) {
                    damage = (int) ((baseWeaponAtk * (1 + (0.25 * (critRate / 100)))) / weaponFactor);
                    System.out.println("high task crit damage=" + damage);
                } else {
                    damage = (int) (baseWeaponAtk / weaponFactor);
                }
                return damage;
            default:
                logger.error("calcAtkDamage() error>> baseWeaponAtk=%d, taskDifficulty=%s, characterCritRate=%d"
                        .formatted(baseWeaponAtk,
                                taskDifficulty, critRate));
                return 0;
        }
    }

    // crit rate is probability based out of 100%
    public static boolean isCriticalHit(int critRate) {
        Random rand = new Random();
        if (rand.nextInt(101) < critRate) {
            return true;
        }
        return false;
    }

    public static int calculateEnemyExpDrop(int level) {
        return (level ^ 2 + level) / 2 * 100 - (level * 100);
    }
}
