package me.mas.combatter.analyser;

import me.mas.combatter.util.UtilMaths;

import java.util.ArrayList;
import java.util.List;

class Analysis
{
    /* Reach */
    private List<Double> reaches = new ArrayList<>();
    private Double lowestReach;
    private Double highestReach;

    void addReach(double reach)
    {
        if (reaches.size() == 0)
        {
            highestReach = reach;
            lowestReach = reach;
        }

        reaches.add(reach);

        if (reach > highestReach)
            highestReach = reach;
        else if (reach < lowestReach)
            lowestReach = reach;
    }

    double getAvgReach()
    {
        if (reaches.size() == 0)
            return 0.0;

        double total = 0;

        for (double d : reaches)
        {
            total += d;
        }

        return UtilMaths.round(total / reaches.size(), 2);
    }

    double getHighestReach()
    {
        return highestReach == null ? 0.0 : UtilMaths.round(highestReach, 2);
    }

    double getLowestReach()
    {
        return lowestReach == null ? 0.0 : UtilMaths.round(lowestReach, 2);
    }

    /* CPS */
    private int clicks = 0;
    private Integer lowestCPS;
    private Integer highestCPS;

    void addClicks(int clicks)
    {
        if (this.clicks == 0)
        {
            lowestCPS = clicks;
            highestCPS = clicks;
        }

        this.clicks += clicks;

        if (clicks > highestCPS)
            highestCPS = clicks;
        else if (clicks < lowestCPS)
            lowestCPS = clicks;
    }

    double getClicks()
    {
        return clicks;
    }

    double getHighestCPS()
    {
        return highestCPS == null ? 0 : highestCPS;
    }

    double getLowestCPS()
    {
        return lowestCPS == null ? 0 : lowestCPS;
    }

    /* Hit % */
    private int interactions = 0;
    private int hits = 0;

    void addInteraction()
    {
        interactions += 1;
    }

    void addHit()
    {
        hits += 1;
    }

    String getAccuracy()
    {
        if (interactions == 0 || hits == 0)
            return "N/A";

        float percent = ((float) hits / interactions) * 100F;

        return String.valueOf(UtilMaths.round(percent, 2));
    }

    /* Ping */
    private List<Integer> pings = new ArrayList<>();
    private int lowestPing;
    private int highestPing;

    void addPing(int ping)
    {
        if (pings.size() == 0)
        {
            lowestPing = ping;
            highestPing = ping;
        }

        pings.add(ping);

        if (ping > highestPing)
            highestPing = ping;
        else if (ping < lowestPing)
            lowestPing = ping;
    }

    int getAvgPing()
    {
        int total = 0;

        for (int i : pings)
        {
            total += i;
        }

        if (total == 0)
            return 0;

        return total / pings.size();
    }

    int getHighestPing()
    {
        return highestPing;
    }

    int getLowestPing()
    {
        return lowestPing;
    }
}
