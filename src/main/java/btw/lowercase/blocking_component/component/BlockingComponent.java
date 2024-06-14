package btw.lowercase.blocking_component.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.dynamic.Codecs;

public class BlockingComponent {
    public static final Codec<BlockingComponent> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codecs.POSITIVE_FLOAT.optionalFieldOf("damage_reduce_multiplier", 1.0F).forGetter(BlockingComponent::getDamageReduceMultiplier),
            Codec.BOOL.optionalFieldOf("can_axe_disable", true).forGetter(BlockingComponent::canAxeDisable),
            Codecs.POSITIVE_INT.optionalFieldOf("total_disabled_ticks", 100).forGetter(BlockingComponent::getTotalDisabledTicks)
    ).apply(instance, BlockingComponent::new));

    private final float damageReduceMultiplier;
    private final boolean canAxeDisable;
    private final int totalDisabledTicks;

    public BlockingComponent(float damageReduceMultiplier, boolean canAxeDisable, int totalDisabledTicks) {
        this.damageReduceMultiplier = Math.min(Math.max(damageReduceMultiplier, 0.0F), 1.0F);
        this.canAxeDisable = canAxeDisable;
        this.totalDisabledTicks = totalDisabledTicks;
    }

    public BlockingComponent() {
        // Default values for vanilla shield
        this(1.0F, true, 100);
    }

    public float getDamageReduceMultiplier() {
        return this.damageReduceMultiplier;
    }

    public boolean canAxeDisable() {
        return this.canAxeDisable;
    }

    public int getTotalDisabledTicks() {
        return this.totalDisabledTicks;
    }
}
