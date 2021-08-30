package top.theillusivec4.somnus;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import top.theillusivec4.somnus.api.PlayerSleepEvents;

public class Somnus implements ModInitializer {
    @SuppressWarnings("deprecation")
    @Override
    public void onInitialize() {
        EntitySleepEvents.ALLOW_SLEEP_TIME.register((player, sleepingPos, vanillaResult) ->
                PlayerSleepEvents.CAN_SLEEP_NOW.invoker().canSleepNow(player, sleepingPos)
                        .map(res -> res ? ActionResult.SUCCESS : ActionResult.FAIL)
                        .orElse(ActionResult.PASS)
        );

        EntitySleepEvents.ALLOW_SLEEPING.register((player, sleepingPos) -> {
            if (!(player instanceof ServerPlayerEntity serverPlayer)) {
                return null;
            }
            return PlayerSleepEvents.TRY_SLEEP.invoker().trySleep(serverPlayer, sleepingPos);
        });
    }
}
