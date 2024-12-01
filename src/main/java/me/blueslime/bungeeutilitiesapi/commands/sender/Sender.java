package me.blueslime.bungeeutilitiesapi.commands.sender;

import me.blueslime.bungeeutilitiesapi.color.ColorHandler;
import me.blueslime.bungeeutilitiesapi.text.TextReplacer;
import me.blueslime.bungeeutilitiesapi.text.TextUtilities;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;

import java.util.Collection;
import java.util.List;

@SuppressWarnings("unused")
public class Sender {
    private final CommandSender sender;

    private Sender(CommandSender sender) {
        this.sender = sender;
    }

    /**
     * Create a Sender instance
     * @param sender to be converted
     * @return Sender instance
     */
    public static Sender build(CommandSender sender) {
        return new Sender(sender);
    }

    /**
     * Check if the sender is a player
     * @return result
     */
    public boolean isPlayer() {
        return sender instanceof ProxiedPlayer;
    }

    /**
     * Convert the Sender to a player
     * @return Player
     */
    public ProxiedPlayer toPlayer() {
        return (ProxiedPlayer)sender;
    }

    /**
     * Check if the sender is a ConsoleCommandSender
     * @return boolean result
     */
    public boolean isConsole() {
        return !isPlayer();
    }

    /**
     * Check if a CommandSender contains a specified permission
     * @param permission to check
     * @return boolean result
     */
    public boolean hasPermission(String permission) {
        return !isPlayer() || sender.hasPermission(permission);
    }

    /**
     * Convert the sender to a CommandSender
     * @return Entity
     */
    public CommandSender toCommandSender() {
        return sender;
    }

    /**
     * Send a message to the sender
     * @param replacer replacements for the messages
     * @param messages to be sent
     */
    public void send(TextReplacer replacer, String... messages) {
        if (messages == null || messages.length == 0) {
            sender.sendMessage(new TextComponent(" "));
            return;
        }
        for (String message : messages) {
            sender.sendMessage(
                new TextComponent(
                    colorize(
                        replacer == null ?
                            message :
                            replacer.apply(message)
                    )
                )
            );
        }
    }

    /**
     * Send messages to the sender
     * @param messages to be sent
     */
    public void send(String... messages) {
        send(null, messages);
    }

    /**
     * Send messages from a configuration path to the sender
     * @param configuration for search the current path
     * @param path of the message
     * @param def value if the path is not set
     * @param replacer replacements for messages
     */
    public void send(Configuration configuration, String path, Object def, TextReplacer replacer) {
        Object ob = configuration.get(path, def);

        if (ob == null) {
            return;
        }

        if (ob instanceof List) {
            List<?> list = (List<?>)ob;
            for (Object object : list) {
                send(
                    replacer,
                    object.toString()
                );
            }
        } else {
            send(
                colorize(
                    replacer == null ?
                        ob.toString() :
                        replacer.apply(ob.toString())
                )
            );
        }
    }

    /**
     * Send messages from a configuration path to the sender
     * @param configuration for search the current path
     * @param path of the message
     * @param def value if the path is not set
     */
    public void send(Configuration configuration, String path, Object def) {
        send(configuration, path, def, null);
    }

    /**
     * Send base components to the sender
     * @param components to be sent
     */
    public void send(BaseComponent... components) {
        sender.sendMessage(components);
    }

    /**
     * Send messages from a configuration path to the sender
     * @param configuration for search the current path
     * @param path of the message
     * @param replacer replacements for messages
     */
    public void send(Configuration configuration, String path, TextReplacer replacer) {
        Object ob = configuration.get(path);

        if (ob == null) {
            return;
        }

        if (ob instanceof List) {
            List<?> list = (List<?>)ob;
            for (Object object : list) {
                send(
                    colorize(
                        replacer == null ?
                            object.toString() :
                            replacer.apply(object.toString())
                    )
                );
            }
        } else {
            send(
                colorize(
                    replacer == null ?
                        ob.toString() :
                        replacer.apply(ob.toString())
                )
            );
        }
    }

    /**
     * Send messages from a configuration path to the sender
     * @param configuration for search the current path
     * @param path of the message
     */
    public void send(Configuration configuration, String path) {
        send(configuration, path, null);
    }

    /**
     * Send a list of messages to the sender
     * @param messages to be sent
     * @param replacer replacements for messages
     */
    public void send(Collection<String> messages, TextReplacer replacer) {
        if (messages == null || messages.isEmpty()) {
            return;
        }

        for (String message : messages) {
            sender.sendMessage(
                new TextComponent(
                    colorize(
                        replacer == null ?
                            message :
                            replacer.apply(message)
                    )
                )
            );
        }
    }

    /**
     * Send a message list to the sender
     * @param messages to be sent
     */
    public void send(List<String> messages) {
        send(messages, null);
    }

    /**
     * Colorize a text value
     * @param text to be colorized
     * @return colored text
     */
    public static String colorize(String text) {
        return ColorHandler.convert(text);
    }

    /**
     * Colorize a String List text value
     * @param list to be colorized
     * @return colored text
     */
    public static List<String> colorizeList(List<String> list) {
        return TextUtilities.colorizeList(list);
    }
}