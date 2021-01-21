package de.poxh.friends.listener;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class TextComponentBuilder {

    private final String text;
    private String hover;
    private String click;
    private ClickEvent.Action action;

    public TextComponentBuilder(String text) {
        this.text = text;
    }

    public TextComponentBuilder addHover(String hover) {
        this.hover = hover;
        return this;
    }

    public TextComponentBuilder addClickEvent(ClickEvent.Action clickEventAction, String value) {
        this.action = clickEventAction;
        this.click = value;
        return this;
    }

    public TextComponent build() {
        TextComponent textComponent = new TextComponent();
        textComponent.setText(this.text);
        if(this.hover != null) {
            textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(this.hover).create()));
        }
        if(this.click != null && (this.action != null)) {
            textComponent.setClickEvent(new ClickEvent(action, this.click));
        }
        return textComponent;
    }

    public enum ClickEventType {
        RUN_COMMAND, SUGGEST_COMMAND, OPEN_URL
    }
}