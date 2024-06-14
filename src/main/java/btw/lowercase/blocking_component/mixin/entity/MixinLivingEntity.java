package btw.lowercase.blocking_component.mixin.entity;

import btw.lowercase.blocking_component.component.BlockingComponent;
import btw.lowercase.blocking_component.component.Components;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {
    @Shadow public abstract ItemStack getActiveItem();

    @Shadow public abstract boolean isBlocking();

    @Inject(method = "blockedByShield", at = @At("RETURN"), cancellable = true)
    public void blockedByShield$denyDamageIfValueGreaterThanOrEqualToOne(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        ItemStack item = this.getActiveItem();
        if (!item.getComponents().contains(Components.BLOCKING_COMPONENT_TYPE)) {
            cir.setReturnValue(false);
            return;
        }
        BlockingComponent component = Objects.requireNonNull(item.getComponents().get(Components.BLOCKING_COMPONENT_TYPE));
        cir.setReturnValue(isBlocking() && component.getDamageReduceMultiplier() == 1.0F);
    }

    @ModifyVariable(method = "damage", at = @At("HEAD"), argsOnly = true, index = 2)
    public float damage$modifyAmount(float value) {
        ItemStack stack = this.getActiveItem();
        if (!stack.getComponents().contains(Components.BLOCKING_COMPONENT_TYPE) || !isBlocking())
            return value;
        BlockingComponent component = Objects.requireNonNull(stack.getComponents().get(Components.BLOCKING_COMPONENT_TYPE));
        return value * component.getDamageReduceMultiplier();
    }
}
