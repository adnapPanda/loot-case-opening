package com.lootcaseopening;

import java.util.ArrayList;
import java.util.List;

public class LootCaseAnimator
{
    private static final int ITEM_WIDTH = 100;
    private static final int ITEM_SPACING = 10;
    private static final int SLOT_STRIDE = ITEM_WIDTH + ITEM_SPACING;

    // How many filler items scroll past before landing on the winner.
    private static final int REEL_LENGTH = 50;
    // Winner is placed near the end so there's room to overshoot slightly less at the finish.
    private static final int WINNER_INDEX = REEL_LENGTH - 6;

    private static final long DURATION_MS = 6000; // total spin time
    private static final double TRANSITION_PERCENTAGE = 0.87; // At what percentage of the spin to transition to the reveal panel

    private final List<LootItem> reel = new ArrayList<>();

    private long startTimeNanos = -1;
    private boolean finished = false;
    private boolean completeFired = false;

    public LootCaseAnimator(List<LootItem> pool, LootItem winningItem)
    {
        for (int i = 0; i < REEL_LENGTH; i++)
        {
            if (i == WINNER_INDEX)
            {
                reel.add(winningItem);
            }
            else
            {
                reel.add(WeightedRandom.pick(pool));
            }
        }
    }

    public void start()
    {
        startTimeNanos = System.nanoTime();
        finished = false;
        completeFired = false;
    }

    public void update()
    {
        if (startTimeNanos < 0)
        {
            start();
        }
        long elapsedMs = (System.nanoTime() - startTimeNanos) / 1_000_000L;
        if (elapsedMs >= DURATION_MS * TRANSITION_PERCENTAGE)
        {
            finished = true;
        }
    }

    private static double easeOutQuint(double t)
    {
        double inv = 1 - t;
        return 1 - inv * inv * inv * inv * inv;
    }

    public double getOffsetPx()
    {
        long elapsedMs = startTimeNanos < 0 ? 0 : (System.nanoTime() - startTimeNanos) / 1_000_000L;
        double t = Math.min(1.0, elapsedMs / (double) DURATION_MS);
        double eased = easeOutQuint(t);

        double targetOffset = WINNER_INDEX * SLOT_STRIDE;

        return eased * targetOffset;
    }

    public List<LootItem> getReel()
    {
        return reel;
    }

    public int getSlotStride()
    {
        return SLOT_STRIDE;
    }

    public int getItemWidth()
    {
        return ITEM_WIDTH;
    }

    public boolean isFinished()
    {
        return finished;
    }

    public boolean consumeCompletionEvent()
    {
        if (finished && !completeFired)
        {
            completeFired = true;
            return true;
        }
        return false;
    }
}