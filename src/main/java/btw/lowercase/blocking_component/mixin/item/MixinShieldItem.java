package btw.lowercase.blocking_component.mixin.item;

import btw.lowercase.blocking_component.component.BlockingComponent;
import btw.lowercase.blocking_component.component.Components;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShieldItem.class)
public abstract class MixinShieldItem {
    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true, index = 1)
    private static Item.Settings component$default(Item.Settings settings) {
        return settings.component(Components.BLOCKING_COMPONENT_TYPE, new BlockingComponent());
    }

    @Inject(method = "getUseAction", at = @At("RETURN"), cancellable = true)
    public void component$action(ItemStack stack, CallbackInfoReturnable<UseAction> cir) {
        if (!stack.contains(Components.BLOCKING_COMPONENT_TYPE)) {
            cir.setReturnValue(UseAction.NONE);
        }
    }

    @Inject(method = "getMaxUseTime", at = @At("RETURN"), cancellable = true)
    public void component$useTime(ItemStack stack, LivingEntity user, CallbackInfoReturnable<Integer> cir) {
        if (!stack.contains(Components.BLOCKING_COMPONENT_TYPE)) {
            cir.setReturnValue(0);
        }
    }

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void component$useTime(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        ItemStack stack = user.getStackInHand(hand);
        if (!stack.contains(Components.BLOCKING_COMPONENT_TYPE)) {
            cir.setReturnValue(TypedActionResult.pass(stack));
        }
    }
}
