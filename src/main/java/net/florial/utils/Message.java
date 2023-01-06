package net.florial.utils;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Message {

    private Component component;

    public Message(String msg) {
        component = Component.text(tacc(msg)).asComponent();
    }

    public Message add(Message msg) {
        component = component.append(Component.text(tacc(msg.component.toString())));
        return this;
    }

    public Message showOnHover(String msg) {
        component = component.hoverEvent(HoverEvent.showText(Component.text(tacc(msg)).asComponent()));
        return this;
    }

    public void send(Player player) {
        player.sendMessage(component);
    }
    public static String tacc(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public void broadcast(String permission) {
        Bukkit.broadcast(component, permission);
    }
    public void broadcast() {
        Bukkit.broadcast(component);
    }
}
