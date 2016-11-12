package me.mas.combatter.updater;

import me.mas.combatter.Combatter;
import org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;

public class UpdateManager
{
    private Combatter combatter;

    public UpdateManager(Combatter combatter)
    {
        this.combatter = combatter;
    }

    private final String VERSION_URL = "https://api.spiget.org/v2/resources/30707/versions?size=" + Integer.MAX_VALUE + "&spiget__ua=" + combatter.getDescription().getName();
    private final String DESC_URL = "https://api.spiget.org/v2/resources/30707/updates?size=" + Integer.MAX_VALUE + "&spiget__ua=" + combatter.getDescription().getName();

    public Object[] getLatestUpdate()
    {
        JSONArray versionsArray;

        try
        {
            versionsArray = (JSONArray) JSONValue.parseWithException(IOUtils.toString(new URL(String.valueOf(VERSION_URL))));
        }
        catch (IOException | ParseException ex)
        {
            Bukkit.getLogger().info("Unable to check for updates. Please contact ItsMas_ on Spigot");
            return null;
        }

        Double lastVersion = Double.parseDouble(((JSONObject) versionsArray.get(versionsArray.size() - 1)).get("name").toString());

        if (lastVersion > Double.parseDouble(combatter.getDescription().getVersion()))
        {
            // Update available
            JSONArray updatesArray;

            try
            {
                updatesArray = (JSONArray) JSONValue.parseWithException(IOUtils.toString(new URL(String.valueOf(DESC_URL))));
            }
            catch (IOException | ParseException ex)
            {
                Bukkit.getLogger().info("Unable to check for updates. Please contact ItsMas_ on Spigot");
                return null;
            }

            String updateName = ((JSONObject) updatesArray.get(updatesArray.size() - 1)).get("title").toString();

            return new Object[] {lastVersion, updateName};
        }

        return null;
    }
}
