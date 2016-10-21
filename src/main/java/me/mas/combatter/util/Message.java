package me.mas.combatter.util;

public enum Message
{
    NO_PERMS("&cYou don't have permission"),
    ANALYSE_ARGS("Usage: /analyse <player> [time]"),
    PLAYER_NULL("That player is not online"),
    NUMBER_INVALID("That is not a valid number"),
    PLAYER_OFFLINE("&cERROR - The target logged off whilst being analysed"),
    ANALYSIS_NULL("&cERROR - The analysis results could not be loaded"),
    ALREADY_ANALYSING("You may only analyse one player at a time"),
    ALREADY_BEING_ANALYSED("That player is already being analysed");

    private String msg;

    Message(String msg)
    {
        this(msg, true);
    }

    Message(String msg, boolean prefix)
    {
        this.msg = (prefix ? "&aCombatter > &d" : "&a") + msg;
    }

    public String getMsg()
    {
        return msg;
    }
}
