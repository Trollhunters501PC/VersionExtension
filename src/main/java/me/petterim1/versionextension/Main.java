package me.petterim1.versionextension;

import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.plugin.PluginBase;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

public class Main extends PluginBase {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        List<Byte> versions = getConfig().getByteList("extraVersions");
        Class<?> c = ProtocolInfo.class;
        try {
            Field f1 = c.getDeclaredField("ENABLED_PROTOCOLS");
            f1.setAccessible(true);
            byte currentProtocol = f1.getByte(null);
            getLogger().debug("Current protocol: " + currentProtocol);
            versions.add(currentProtocol);
            getLogger().debug("Versions: " + versions.toString());
            Field f2 = c.getDeclaredField("ENABLED_PROTOCOLS");
            f2.setAccessible(true);
            Field m = Field.class.getDeclaredField("modifiers");
            m.setAccessible(true);
            m.setByte(f2, f2.getModifiers() & ((byte) ~Modifier.FINAL));
            f2.set(f2, versions);
            getLogger().debug("Set: " + f2.get(null).toString());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return;
        }
        getLogger().info("§aPlugin enabled successfully");
    }
}
