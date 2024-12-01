package me.blueslime.bungeeutilitiesapi.commands;

import me.blueslime.bungeeutilitiesapi.color.ColorHandler;
import me.blueslime.bungeeutilitiesapi.commands.loader.CommandLoader;
import me.blueslime.bungeeutilitiesapi.commands.sender.Sender;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings({"unused"})
public abstract class AdvancedCommand<T extends Plugin> extends Command {
    protected final T plugin;
    private String command;

    public AdvancedCommand(T plugin, String command) {
        this(plugin, command, "");
    }

    public AdvancedCommand(T plugin, String command, Collection<String> aliases) {
        this(plugin, command, "", aliases.toArray(new String[0]));
    }

    public AdvancedCommand(T plugin, Configuration configuration, String commandPath, String permissionPath, String aliasesPath) {
        this(
            plugin,
            configuration != null ? configuration.getString(commandPath, "") : "",
            (configuration != null ? configuration.getString(permissionPath, "") : ""),
            (configuration != null ? configuration.getStringList(aliasesPath) : new ArrayList<String>()).toArray(new String[0])
        );
    }

    public AdvancedCommand(T plugin, String command, String permission, String... aliases) {
        super(command, permission == null ? null : permission.isEmpty() ? null : permission, aliases);
        this.command = command;
        this.plugin = plugin;
    }

    public AdvancedCommand(T plugin) {
        this(plugin,null);
    }

    public boolean overwriteCommand() {
        return false;
    }

    public void register() {
        if (this.command != null) {
            CommandLoader.build(plugin)
                .register(this)
                .finish();
        }
    }
    
    public void unregister() {
        CommandLoader.build(plugin)
            .unregister(this)
            .finish();
    }

    public AdvancedCommand<T> setNoPermissionMessage(String command) {
        this.command = command;
        super.setPermissionMessage(command);
        return this;
    }

    /**
     * Command Execution
     * @param sender Source object which is executing this command
     * @param arguments All arguments passed to the command, split via ' '
     */
    public abstract void executeCommand(Sender sender, String[] arguments);

    @Override
    public void execute(CommandSender sender, String[] args) {
        executeCommand(Sender.build(sender), args);
    }

    public String getCommand() {
        return command;
    }

    public T getPlugin() {
        return plugin;
    }

    public static String colorize(String text) {
        return ColorHandler.convert(text);
    }
}


