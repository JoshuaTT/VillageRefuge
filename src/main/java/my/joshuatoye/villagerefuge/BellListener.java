package my.joshuatoye.villagerefuge;

import jdk.jfr.internal.EventWriterMethod;
import org.bukkit.Material;
import org.bukkit.block.Beacon;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Random;

public class BellListener implements Listener {

    @EventHandler
    public void onBellRung(PlayerInteractEvent event)
    {
        if(event.useInteractedBlock() == Event.Result.ALLOW
                || event.useItemInHand() == Event.Result.ALLOW) {
            Block interactedBlock = event.getClickedBlock();

            if (interactedBlock.getType() == Material.BELL) {
                attemptAttractRefugee(interactedBlock);
            }
        }
    }

    private void attemptAttractRefugee(Block rungBell)
    {
        boolean beaconIsInRange = false;

        for(int i = -20; i < 21 && !beaconIsInRange; i++)
            for(int j = -20; j < 21 && !beaconIsInRange; j++)
                for(int k = -20; k < 21 && !beaconIsInRange; k++)
                {
                    Block checkBlock = rungBell.getRelative(i, j, k);
                    if(checkBlock.getType() == Material.BEACON)
                    {
                        beaconIsInRange = true;
                        Beacon beacon = (Beacon) checkBlock.getState();
                        rollForAttract(beacon);
                    }
                }
    }

    private void rollForAttract(Beacon foundBeacon)
    {
        int UPPER_BOUND = 10001;
        int THRESHOLD = 9900;
        int beaconTier = foundBeacon.getTier();
        double beaconModifier[] = {-10000, 0, .02, .03, .05};


        Random rand = new Random(System.currentTimeMillis());
        if(rand.nextInt(UPPER_BOUND) > THRESHOLD - (THRESHOLD * beaconModifier[beaconTier])) {
            int villagerBiome = rand.nextInt(8);
            if(villagerBiome != 7) {
                Villager v = (Villager) foundBeacon.getWorld().spawnEntity(foundBeacon.getLocation(), EntityType.VILLAGER);
                Villager.Type[] types = Villager.Type.values();
                v.setVillagerType(types[rand.nextInt(7)]);
            }
            else
            {
                ZombieVillager zv = (ZombieVillager) foundBeacon.getWorld().spawnEntity(foundBeacon.getLocation(), EntityType.ZOMBIE_VILLAGER);
            }
        }

    }
}
