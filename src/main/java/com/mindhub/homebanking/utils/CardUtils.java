package com.mindhub.homebanking.utils;

import java.text.DecimalFormat;

public final class CardUtils {
    public static int generateCvv() {
        return (int) (Math.random() * 999);
    }

    public static String generateNumber() {
        DecimalFormat format = new DecimalFormat("0000");
        String number = "";
        for (int i = 0; i < 4; i++) {
            number += format.format((int) (Math.random() * 9999));
            if (i != 3) {
                number += "-";
            }
        }
        return number;
    }
}
