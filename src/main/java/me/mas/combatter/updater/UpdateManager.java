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
    private final Combatter combatter;

    public UpdateManager(Combatter combatter)
    {
        this.combatter = combatter;

        VERSION_URL = "https://api.spiget.org/v2/resources/30707/versions?size=" + Integer.MAX_VALUE + "&spiget__ua=" + combatter.getDescription().getName();
        DESC_URL = "https://api.spiget.org/v2/resources/30707/updates?size=" + Integer.MAX_VALUE + "&spiget__ua=" + combatter.getDescription().getName();
    }

    private final String VERSION_URL;
    private final String DESC_URL;

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

        String lastVersion = ((JSONObject) versionsArray.get(versionsArray.size() - 1)).get("name").toString();

        if (Integer.parseInt(lastVersion.replaceAll("\\.", "")) > Integer.parseInt(combatter.getDescription().getVersion().replaceAll("\\.", "")))
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
