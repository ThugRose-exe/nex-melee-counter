package nexmeleecount;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("nexmeleecount")
public interface NexMeleeCounterConfig extends Config
{
    @ConfigItem(
        keyName = "showWhenNotFighting",
        name = "Show when not fighting Nex",
        description = "Show the overlay even when Nex is not present",
        position = 1
    )
    default boolean showWhenNotFighting()
    {
        return false;
    }

    @ConfigItem(
        keyName = "warnOnThird",
        name = "Warn on 3rd attack",
        description = "Highlights the counter red when the 3rd melee attack is imminent",
        position = 2
    )
    default boolean warnOnThird()
    {
        return true;
    }
}
