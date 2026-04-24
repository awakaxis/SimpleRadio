package net.awakaxis.simpleradio.mixin;

import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ElytraItem.class)
public abstract class ElytraItemMixin {
    // todo: totally out of scope but im too lazy to put this in its own mod right now
    @Inject(method = "isFlyEnabled", at = @At("HEAD"), cancellable = true)
    private static void disableElytra(ItemStack elytraStack, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
}
