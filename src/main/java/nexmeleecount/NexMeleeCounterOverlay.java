package nexmeleecount;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

public class NexMeleeCounterOverlay extends OverlayPanel
{
    private final NexMeleeCounterPlugin plugin;
    private final NexMeleeCounterConfig config;

    @Inject
    public NexMeleeCounterOverlay(NexMeleeCounterPlugin plugin, NexMeleeCounterConfig config)
    {
        this.plugin = plugin;
        this.config = config;
        setPosition(OverlayPosition.BOTTOM_LEFT);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (!plugin.isNexPresent() && !config.showWhenNotFighting())
        {
            return null;
        }

        int count = plugin.getMeleeCount();

        Color countColor;
        if (config.warnOnThird() && count >= 3)
        {
            countColor = Color.RED;
        }
        else if (count == 2)
        {
            countColor = Color.ORANGE;
        }
        else
        {
            countColor = Color.WHITE;
        }

        panelComponent.getChildren().add(TitleComponent.builder()
            .text("Nex Melee")
            .color(Color.CYAN)
            .build());

        panelComponent.getChildren().add(LineComponent.builder()
            .left("Attacks:")
            .right(count + " / 3")
            .rightColor(countColor)
            .build());

        return super.render(graphics);
    }
}
