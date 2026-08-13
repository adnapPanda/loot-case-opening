package com.lootcaseopening;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.function.Consumer;
import javax.inject.Inject;

import net.runelite.api.Client;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.input.KeyListener;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

public class LootCaseOpeningOverlay extends Overlay implements KeyListener {
    // Spin panel dimensions
    private static final int VIEWPORT_WIDTH = 620;
    private static final int VIEWPORT_HEIGHT = 130;
    private static final int ITEM_HEIGHT = 100;

    // Reveal panel dimensions
    private static final int REVEAL_WIDTH = 320;
    private static final int REVEAL_HEIGHT = 360;
    private static final long REVEAL_ANIM_MS = 450;
    private static final int REVEAL_IMAGE_SIZE = 100;

    //The icon displayed is slightly off-center, hence add a offset to correct
    private static final int IMAGE_X_OFFSET = 7;

    private final Client client;
    private final ClientThread clientThread;

    private LootCaseAnimator animator;
    private Consumer<LootItem> onComplete;
    private Runnable onClose;

    private boolean revealed = false;
    private LootItem revealedItem;
    private long revealStartTimeNanos;

    @Inject
    public LootCaseOpeningOverlay(Client client, ClientThread clientThread) {
        this.client = client;
        this.clientThread = clientThread;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ALWAYS_ON_TOP);
    }

    public void open(List<LootItem> pool, LootItem winningItem, Consumer<LootItem> onComplete, Runnable onClose) {
        this.animator = new LootCaseAnimator(pool, winningItem);
        this.animator.start();
        this.onComplete = onComplete;
        this.onClose = onClose;
        this.revealed = false;
        this.revealedItem = null;
        centerPanel(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
    }

    public void close() {
        animator = null;
        revealed = false;
        revealedItem = null;

        if (onClose != null) {
            Runnable callback = onClose;
            onClose = null; // avoid double firing if close() is called again
            callback.run();
        }

    }

    @Override
    public Dimension render(Graphics2D graphics) {
        if (animator == null && !revealed) {
            return null;
        }

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int panelWidth = revealed ? REVEAL_WIDTH : VIEWPORT_WIDTH;
        int panelHeight = revealed ? REVEAL_HEIGHT : VIEWPORT_HEIGHT;

        // Center on the game canvas every frame (panel size differs between phases).
        centerPanel(panelWidth, panelHeight);

        if (!revealed) {
            animator.update();

            drawPanelBackground(graphics, panelWidth, panelHeight);
            drawReel(graphics);
            drawPointer(graphics);
            drawFadeEdges(graphics);

            if (animator.consumeCompletionEvent()) {
                LootItem winner = getWinningItem();
                animator = null;
                revealed = true;
                revealedItem = winner;
                revealStartTimeNanos = System.nanoTime();

                if (onComplete != null) {
                    onComplete.accept(winner);
                }
            }
        } else {
            drawRevealPanel(graphics, panelWidth, panelHeight);
        }

        return new Dimension(panelWidth, panelHeight);
    }

    private void centerPanel(int panelWidth, int panelHeight) {
        int x = (client.getCanvasWidth() - panelWidth) / 2;
        int y = (client.getCanvasHeight() - panelHeight) / 2;
        setPreferredLocation(new Point(x, y));
    }


    private LootItem getWinningItem() {
        List<LootItem> reel = animator.getReel();
        int stride = animator.getSlotStride();
        int index = (int) Math.round(animator.getOffsetPx() / stride);
        index = Math.max(0, Math.min(reel.size() - 1, index));
        return reel.get(index);
    }

    private void drawPanelBackground(Graphics2D g, int w, int h) {
        g.setColor(new Color(20, 20, 20, 230));
        g.fillRoundRect(0, 0, w, h, 10, 10);
    }

    private void drawReel(Graphics2D g) {
        double offset = animator.getOffsetPx();
        int stride = animator.getSlotStride();
        int itemWidth = animator.getItemWidth();
        List<LootItem> reel = animator.getReel();

        int centerY = VIEWPORT_HEIGHT / 2;

        Shape oldClip = g.getClip();
        g.setClip(new Area(new Rectangle(4, 4, VIEWPORT_WIDTH - 8, VIEWPORT_HEIGHT - 8)));

        for (int i = 0; i < reel.size(); i++) {
            double x = i * stride - offset + VIEWPORT_WIDTH / 2.0 - itemWidth / 2.0;

            if (x + itemWidth < 0 || x > VIEWPORT_WIDTH) {
                continue;
            }

            LootItem item = reel.get(i);
            int slotX = (int) Math.round(x);
            int slotY = centerY - ITEM_HEIGHT / 2;

            g.setColor(item.getRarityColor());
            g.fillRoundRect(slotX, slotY, itemWidth, ITEM_HEIGHT, 6, 6);

            if (item.getImage() != null) {
                int imgX = slotX + (itemWidth - item.getImage().getWidth()) / 2;
                int imgY = slotY + (ITEM_HEIGHT - item.getImage().getHeight()) / 2;
                g.drawImage(item.getImage(), imgX, imgY, null);
            }
        }

        g.setClip(oldClip);
    }

    private void drawPointer(Graphics2D g) {
        int centerX = VIEWPORT_WIDTH / 2;
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(2f));
        g.drawLine(centerX, 0, centerX, VIEWPORT_HEIGHT);

        int[] xs = {centerX - 7, centerX + 7, centerX};
        int[] ys = {0, 0, 10};
        g.fillPolygon(xs, ys, 3);
    }

    private void drawFadeEdges(Graphics2D g) {
        int fadeWidth = 40;
        Color panel = new Color(20, 20, 20);

        GradientPaint left = new GradientPaint(0, 0, panel, fadeWidth, 0, new Color(20, 20, 20, 0));
        g.setPaint(left);
        g.fillRect(0, 0, fadeWidth, VIEWPORT_HEIGHT);

        GradientPaint right = new GradientPaint(
                VIEWPORT_WIDTH - fadeWidth, 0, new Color(20, 20, 20, 0), VIEWPORT_WIDTH, 0, panel);
        g.setPaint(right);
        g.fillRect(VIEWPORT_WIDTH - fadeWidth, 0, fadeWidth, VIEWPORT_HEIGHT);
    }

    private static double easeOutBack(double t) {
        double c1 = 1.70158;
        double c3 = c1 + 1;
        double tm1 = t - 1;
        return 1 + c3 * tm1 * tm1 * tm1 + c1 * tm1 * tm1;
    }

    private void drawRevealPanel(Graphics2D g, int w, int h) {
        long elapsedMs = (System.nanoTime() - revealStartTimeNanos) / 1_000_000L;
        double t = Math.min(1.0, elapsedMs / (double) REVEAL_ANIM_MS);
        double scale = easeOutBack(t);

        Color rarity = revealedItem.getRarityColor();

        // Panel background + rarity-colored border
        g.setColor(new Color(20, 20, 20, 235));
        g.fillRoundRect(0, 0, w, h, 14, 14);
        g.setStroke(new BasicStroke(3f));
        g.setColor(rarity);
        g.drawRoundRect(1, 1, w - 2, h - 2, 14, 14);

        int centerX = w / 2;
        int centerY = h / 2 - 20;

        // Radial glow behind the item, tinted by rarity
        float glowRadius = 110f;
        RadialGradientPaint glow = new RadialGradientPaint(
                new Point(centerX, centerY), glowRadius,
                new float[]{0f, 1f},
                new Color[]{
                        new Color(rarity.getRed(), rarity.getGreen(), rarity.getBlue(), 120),
                        new Color(rarity.getRed(), rarity.getGreen(), rarity.getBlue(), 0)
                });
        g.setPaint(glow);
        g.fillOval((int) (centerX - glowRadius), (int) (centerY - glowRadius),
                (int) (glowRadius * 2), (int) (glowRadius * 2));

        // The item itself, scaled up with the pop-in animation
        BufferedImage image = revealedItem.getImage();
        if (image != null) {
            int size = (int) Math.round(REVEAL_IMAGE_SIZE * scale);
            if (size > 0) {
                g.drawImage(image, centerX - size / 2 + IMAGE_X_OFFSET, centerY - size / 2, size, size, null);
            }
        }

        // Item name
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(Font.BOLD, 16f));
        FontMetrics fm = g.getFontMetrics();
        String name = revealedItem.getName();
        g.drawString(name, centerX - fm.stringWidth(name) / 2, h - 50);

        // Subtitle
        g.setFont(g.getFont().deriveFont(Font.PLAIN, 12f));
        fm = g.getFontMetrics();
        String subtitle = "You got an item!";
        g.setColor(new Color(200, 200, 200));
        g.drawString(subtitle, centerX - fm.stringWidth(subtitle) / 2, 28);

        // Close hint
        g.setFont(g.getFont().deriveFont(Font.PLAIN, 11f));
        fm = g.getFontMetrics();
        String hint = "Press ESC to close";
        g.setColor(new Color(150, 150, 150));
        g.drawString(hint, centerX - fm.stringWidth(hint) / 2, h - 20);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() != KeyEvent.VK_ESCAPE) {
            return;
        }

        if (!revealed && animator == null) {
            return;
        }

        e.consume();
        clientThread.invoke(() ->
        {
            if (revealed) {
                close();
            } else if (animator != null) {
                animator.skipToEnd();
            }
        });

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}