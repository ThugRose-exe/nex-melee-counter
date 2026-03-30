package nexmeleecount;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.Getter;
import net.runelite.api.Actor;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.NpcDespawned;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
    name = "Nex Melee Counter",
    description = "Counts Nex's melee attacks up to 3",
    tags = {"nex", "melee", "counter", "pvm", "boss"}
)
public class NexMeleeCounterPlugin extends Plugin
{
    private static final int NEX_ID = 11278;
    private static final int NEX_MELEE_ANIMATION = 9181;
    private static final int MAX_MELEE_COUNT = 3;

    @Inject
    private Client client;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private NexMeleeCounterOverlay overlay;

    @Inject
    private NexMeleeCounterConfig config;

    @Getter
    private int meleeCount = 0;

    @Getter
    private boolean nexPresent = false;

    @Override
    protected void startUp()
    {
        overlayManager.add(overlay);
        meleeCount = 0;
        nexPresent = false;
    }

    @Override
    protected void shutDown()
    {
        overlayManager.remove(overlay);
        meleeCount = 0;
        nexPresent = false;
    }

    @Subscribe
    public void onAnimationChanged(AnimationChanged event)
    {
        Actor actor = event.getActor();

        if (!(actor instanceof NPC))
        {
            return;
        }

        NPC npc = (NPC) actor;

        if (npc.getId() != NEX_ID)
        {
            return;
        }

        nexPresent = true;

        int animId = npc.getAnimation();

        if (animId == NEX_MELEE_ANIMATION)
        {
            meleeCount++;

            if (meleeCount > MAX_MELEE_COUNT)
            {
                meleeCount = 1;
            }
        }
        else if (animId != -1)
        {
            meleeCount = 0;
        }
    }

    @Subscribe
    public void onNpcDespawned(NpcDespawned event)
    {
        if (event.getNpc().getId() == NEX_ID)
        {
            meleeCount = 0;
            nexPresent = false;
        }
    }

    @Provides
    NexMeleeCounterConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(NexMeleeCounterConfig.class);
    }
}
