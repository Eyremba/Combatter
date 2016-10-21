package me.mas.combatter.analyser;

import me.mas.combatter.util.UtilMaths;

import java.util.ArrayList;
import java.util.List;

class Analysis
{
    /* Reach */
    private List<Double> reaches = new ArrayList<>();
    private double highest = 0.0;

    void addReach(double reach)
    {
        reaches.add(reach);

        if (reach > highest)
            highest = reach;
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
        return highest;
    }

    /* CPS */
    private int clicks = 0;

    void addClick()
    {
        clicks += 1;
    }

    double getClicks()
    {
        return clicks;
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

    void addPing(int ping)
    {
        pings.add(ping);
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
}
