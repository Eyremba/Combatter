package me.mas.combatter.analyser;

import me.mas.combatter.util.UtilMaths;

import java.util.ArrayList;
import java.util.List;

public class Analysis
{
    /* Reach */
    private List<Double> reaches = new ArrayList<>();

    void addReach(double reach)
    {
        reaches.add(reach);
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

    /* Accuracy */
    public int interactions = 0;
    public int hits = 0;

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

        return String.valueOf((hits / interactions) * 100);
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
