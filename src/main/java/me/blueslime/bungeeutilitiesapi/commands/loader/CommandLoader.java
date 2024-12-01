package me.blueslime.bungeeutilitiesapi.commands.loader;

import me.blueslime.bungeeutilitiesapi.commands.AdvancedCommand;
import net.md_5.bungee.api.plugin.Plugin;

public class CommandLoader {

    private static CommandLoader LOADER_INSTANCE = null;

    public static CommandLoader build(Plugin plugin) {
        if (LOADER_INSTANCE == null) {
            LOADER_INSTANCE = new CommandLoader(plugin);
        }
        return LOADER_INSTANCE;
    }

    private final Plugin plugin;

    private CommandLoader(Plugin plugin) {
        this.plugin = plugin;
    }

    private void registerCommand(AdvancedCommand<?> executable) {
        plugin.getProxy().getPluginManager().registerCommand(
            plugin,
            executable
        );
    }

    public CommandLoader register(AdvancedCommand<?> command) {
        registerCommand(command);
        return this;
    }

    public CommandLoader unregister(AdvancedCommand<?> command) {
        plugin.getProxy().getPluginManager().unregisterCommand(command);
        return this;
    }

    public void finish() {

    }

}