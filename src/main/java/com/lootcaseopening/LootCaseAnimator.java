package com.lootcaseopening;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LootCaseAnimator
{
    private static final int ITEM_WIDTH = 100;
    private static final int ITEM_SPACING = 10;
    private static final int SLOT_STRIDE = ITEM_WIDTH + ITEM_SPACING;

    // How many filler items scroll past before landing on the winner.
    private static final int REEL_LENGTH = 50;
    private static final int WINNER_INDEX = REEL_LENGTH - 6;

    private static final long DURATION_MS = 6000; // total spin time
    private static final double TRANSITION_PERCENTAGE = 0.87; // At what percentage of the spin to transition to the reveal panel

    private static final Random RANDOM = new Random();
    private static final double WINNING_ITEM_DEVIATION = ITEM_WIDTH * 0.3;
    //Make sure the winning spin doesn't always land in the exact middle
    private final double landingDeviation = (RANDOM.nextDouble() * 2 - 1) * WINNING_ITEM_DEVIATION;

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

        double targetOffset = WINNER_INDEX * SLOT_STRIDE + landingDeviation;

        return eased * targetOffset;
    }

    public void skipToEnd()
    {
        startTimeNanos = System.nanoTime() - (DURATION_MS * 1_000_000L);
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