package me.petterim1.versionextension;

import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.plugin.PluginBase;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.HashSet;

public class Main extends PluginBase {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        List<Integer> versions = getConfig().getIntegerList("extraVersions");
        Class<?> c = ProtocolInfo.class;
        try {
            Field f1 = c.getDeclaredField("ENABLED_PROTOCOLS");
            f1.setAccessible(true);
            getLogger().debug("Versions: " + versions.toString());
            Field f2 = c.getDeclaredField("ENABLED_PROTOCOLS");
            f2.setAccessible(true);
            Field m = Field.class.getDeclaredField("modifiers");
            m.setAccessible(true);
            m.setInt(f2, f2.getModifiers() &  ~Modifier.FINAL);
            f2.set(f2, new HashSet<>(versions));
            getLogger().debug("Set: " + f2.get(null).toString());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return;
        }
        getLogger().info("§aPlugin enabled successfully");
    }
}
