package com.catalpa.pocket.util;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Random;

/**
 * Created by bruce on 2018/6/4.
 */
public class RandomUtil {

    private static char[] CAPITAL_LETTER = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static char[] SMALL_LETTER = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static char[] NUMBER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /**
     * generate random string with capital, small letter and number
     * @param length
     * @return
     */
    public static String generateString(int length) {
        return generateString(true, true, true, null, length);
    }

    /**
     * generate random string with capital, small letter, special character and number
     * @param special
     * @param length
     * @return
     */
    public static String generateString(char[] special, int length) {
        return generateString(true, true, true, special, length);
    }

    /**
     * generate random string
     * @param hasCapital
     * @param hasSmall
     * @param hasNumber
     * @param special
     * @param length
     * @return
     */
    public static String generateString(boolean hasCapital, boolean hasSmall, boolean hasNumber, char[] special, int length) {
        StringBuilder secret = new StringBuilder();

        char[] chars = {};
        if (hasCapital) {
            chars = ArrayUtils.addAll(chars, CAPITAL_LETTER);
        }
        if (hasSmall) {
            chars = ArrayUtils.addAll(chars, SMALL_LETTER);
        }
        if (hasNumber) {
            chars = ArrayUtils.addAll(chars, NUMBER);
        }
        if (special != null) {
            chars = ArrayUtils.addAll(chars, special);
        }
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            secret.append(chars[random.nextInt(chars.length)]);
        }
        return secret.toString();
    }

    /**
     * generate random integer
     * @param max
     * @return
     */
    public static Integer randomInteger(Integer max) {
        return randomInteger(0, max);
    }

    /**
     * generate random integer
     * @param min
     * @param max
     * @return
     */
    public static Integer randomInteger(Integer min, Integer max) {
        Random random = new Random();
        Integer nextInteger;
        if (min < 0) {
            int flag = random.nextInt(2);
            nextInteger = null;
            // if flag != 0, retrun negative integer, if falg == 0, retrun positive integer
            if (flag != 0) {
                nextInteger = -(random.nextInt(-min) + 1);
            } else {
                nextInteger = random.nextInt(max) + 1;
            }
        } else {
            nextInteger = random.nextInt(max);
            nextInteger = (nextInteger + min) <= max ? nextInteger + min : nextInteger;
        }
        return nextInteger;
    }
}
