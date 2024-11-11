package btw.lowercase.blocking_component.component;

import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class Components {
    public static ComponentType<BlockingComponent> BLOCKING = ComponentType.<BlockingComponent>builder().codec(BlockingComponent.CODEC).build();

    public static void initalize() {
        Registry.register(Registries.DATA_COMPONENT_TYPE, "blocking", Components.BLOCKING);
    }
}
