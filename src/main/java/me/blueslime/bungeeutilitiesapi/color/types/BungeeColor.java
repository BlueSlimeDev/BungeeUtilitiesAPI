package me.blueslime.bungeeutilitiesapi.color.types;

import me.blueslime.bungeeutilitiesapi.color.ColorHandler;
import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BungeeColor extends ColorHandler {

    public String execute(String message) {
        Pattern pattern = Pattern.compile("&#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(message);

        while (matcher.find()) {
            String codeR = message.substring(matcher.start(), matcher.end());
            String code = codeR.replace("&", "");
            ChatColor color = ChatColor.of(code);

            message = message.replace(
                "&" + code,
                color + ""
            );

            matcher = pattern.matcher(
                message
            );
        }

        pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        matcher = pattern.matcher(message);

        while (matcher.find()) {
            String code = message.substring(matcher.start(), matcher.end());
            ChatColor color = ChatColor.of(code);

            message = message.replace(
                code,
                color + ""
            );

            matcher = pattern.matcher(
                message
            );
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
