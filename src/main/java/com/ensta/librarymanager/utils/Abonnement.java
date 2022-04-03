package com.ensta.librarymanager.utils;

public enum Abonnement {
    BASIC, PREMIUM, VIP;

    public static Abonnement get_from_string(String abo) {
        if (abo == "PREMIUM")
            return Abonnement.PREMIUM;
        else if (abo == "VIP")
            return Abonnement.VIP;
        else
            return Abonnement.BASIC;
    }
}
