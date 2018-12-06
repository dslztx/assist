package me.dslztx.assist.util;

public class EmailAssist {

    public static String obtainDomain(String emailAccount) {
        if (StringAssist.isBlank(emailAccount)) {
            return null;
        }

        int index = emailAccount.lastIndexOf('@');
        if (index == -1 || index == emailAccount.length() - 1) {
            return null;
        }
        return emailAccount.substring(index + 1);
    }
}
