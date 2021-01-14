package net.frankheijden.insights.managers;

import net.frankheijden.insights.Insights;
import net.frankheijden.insights.interfaces.Hook;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class HookManager {

    private static HookManager instance;
    private final List<Hook> hooks;

    public HookManager() {
        instance = this;
        this.hooks = new ArrayList<>();
    }

    public static HookManager getInstance() {
        return instance;
    }

    public void addHook(Hook hook) {
        hooks.add(hook);
    }

    public void removeHook(Hook hook) {
        hooks.remove(hook);
    }

    public List<Hook> getHooks() {
        return hooks;
    }

    public boolean tryHook(String plugin, Hook hook) {
        if (canHook(plugin)) {
            addHook(hook);
            Insights.logger.info(String.format("Successfully hooked into %s!", plugin));
            return true;
        }
        return false;
    }

    public boolean canHook(String plugin) {
        return Bukkit.getPluginManager().getPlugin(plugin) != null;
    }

    public boolean shouldCancel(Block block) {
        for (Hook hook : hooks) {
            if (hook.shouldCancel(block)) return true;
        }
        return false;
    }

    public boolean shouldCancel(Entity entity) {
        for (Hook hook : hooks) {
            if (hook.shouldCancel(entity)) return true;
        }
        return false;
    }
}
