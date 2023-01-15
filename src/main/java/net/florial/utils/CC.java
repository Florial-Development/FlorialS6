package net.florial.utils;


import net.md_5.bungee.api.ChatColor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CC {
    public CC() {
    }

    public static String[] translate(String[] text) {
        for(int i = 0; i < text.length; ++i) {
            text[i] = translate(text[i]);
        }

        return text;
    }

    public static List<String> translate(List<String> text) {
        for(int i = 0; i < text.size(); ++i) {
            text.set(i, translate((String)text.get(i)));
        }

        return text;
    }

    public static String translate(String message) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

        for(Matcher matcher = pattern.matcher(message); matcher.find(); matcher = pattern.matcher(message)) {
            String hexCode = message.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');
            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder("");
            char[] var7 = ch;
            int var8 = ch.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                char c = var7[var9];
                builder.append("&").append(c);
            }

            message = message.replace(hexCode, builder.toString());
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
