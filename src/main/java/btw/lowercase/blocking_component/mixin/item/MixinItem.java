package btw.lowercase.blocking_component.mixin.item;

import btw.lowercase.blocking_component.component.Components;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class MixinItem {
    @Inject(method = "getUseAction", at = @At("RETURN"), cancellable = true)
    public void component$action(ItemStack stack, CallbackInfoReturnable<UseAction> cir) {
        if (!stack.contains(Components.BLOCKING_COMPONENT_TYPE) || stack.contains(DataComponentTypes.FOOD))
            return;
        cir.setReturnValue(UseAction.BLOCK);
    }

    @Inject(method = "getMaxUseTime", at = @At("RETURN"), cancellable = true)
    public void component$useTime(ItemStack stack, LivingEntity user, CallbackInfoReturnable<Integer> cir) {
        if (!stack.contains(Components.BLOCKING_COMPONENT_TYPE) || stack.contains(DataComponentTypes.FOOD))
            return;
        cir.setReturnValue(72000);
    }

    @Inject(method = "use", at = @At("RETURN"), cancellable = true)
    public void component$use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        ItemStack stack = user.getStackInHand(hand);
        if (stack.contains(Components.BLOCKING_COMPONENT_TYPE) && !stack.contains(DataComponentTypes.FOOD)) {
            user.setCurrentHand(hand);
            cir.setReturnValue(TypedActionResult.consume(stack));
        }
    }
}
