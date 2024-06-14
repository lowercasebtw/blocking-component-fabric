package btw.lowercase.blocking_component.mixin.entity;

import btw.lowercase.blocking_component.component.BlockingComponent;
import btw.lowercase.blocking_component.component.Components;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Objects;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity {
    @Shadow @NotNull public abstract ItemStack getWeaponStack();

    @ModifyArg(method = "disableShield", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/ItemCooldownManager;set(Lnet/minecraft/item/Item;I)V"), index = 1)
    public int component$disabledTicks(int duration) {
        ItemStack stack = this.getWeaponStack();
        if (!stack.getComponents().contains(Components.BLOCKING_COMPONENT_TYPE))
            return duration;
        BlockingComponent component = Objects.requireNonNull(stack.getComponents().get(Components.BLOCKING_COMPONENT_TYPE));
        return component.getTotalDisabledTicks();
    }
}
