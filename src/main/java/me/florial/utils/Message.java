package me.florial.utils;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Message {

    private final Component component;

    public Message(String msg) {
        component = Component.text(tacc(msg)).asComponent();
    }

    public Component add(String msg) {
        return component.append(Component.text(msg)).asComponent();
    }

    public Component showOnHover(String msg) {
        return component.hoverEvent(HoverEvent.showText(Component.text(tacc(msg)).asComponent()));
    }

    public void send(Player player) {
        player.sendMessage(component);
    }
    public static String tacc(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }


}
