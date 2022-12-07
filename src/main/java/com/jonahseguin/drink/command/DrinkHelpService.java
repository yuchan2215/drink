package com.jonahseguin.drink.command;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DrinkHelpService {

    private final DrinkCommandService commandService;
    private HelpFormatter helpFormatter;

    public DrinkHelpService(DrinkCommandService commandService) {
        this.commandService = commandService;
        this.helpFormatter = (sender, container) -> {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&m--------------------------------"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bHelp &7- &6/" + container.getName()));
            for (DrinkCommand c : container.getCommands().values()) {
                TextComponent[] msgArray = {
                        new TextComponent(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&',
                                "&7" + (c.getName().length() > 0 ? " &e" + c.getName() : "/" + container.getName()) + " &7" + c.getMostApplicableUsage() + "&7")),
                        new TextComponent(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&',
                                "&7 ┗━ &f" + c.getShortDescription()))
                };
                Arrays.stream(msgArray).forEach(msg -> {
                    msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(ChatColor.GRAY + "/" + container.getName() + " " + c.getName() + " - " + ChatColor.WHITE + c.getDescription())));
                    msg.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + container.getName() + " " + c.getName()));
                    sender.spigot().sendMessage(msg);
                });

            }
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&m--------------------------------"));
        };
    }

    public void sendHelpFor(CommandSender sender, DrinkCommandContainer container) {
        this.helpFormatter.sendHelpFor(sender, container);
    }

    public void sendUsageMessage(CommandSender sender, DrinkCommandContainer container, DrinkCommand command) {
        sender.sendMessage(getUsageMessage(container, command));
    }

    public String getUsageMessage(DrinkCommandContainer container, DrinkCommand command) {
        String usage = ChatColor.RED + "使用方法: /" + container.getName() + " ";
        if (command.getName().length() > 0) {
            usage += command.getName() + " ";
        }
        if (command.getUsage() != null && command.getUsage().length() > 0) {
            usage += command.getUsage();
        } else {
            usage += command.getGeneratedUsage();
        }
        return usage;
    }

}
