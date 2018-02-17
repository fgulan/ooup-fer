package hr.fer.zemris.ooup.lab3.zad_2.plugins;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * @author Filip Gulan
 */
public class PluginsFactory {

    public static List<IPlugin> getPlugins() {
        ServiceLoader<IPlugin> loader = ServiceLoader.load(IPlugin.class);
        List<IPlugin> plugins = new ArrayList<>();
        for (IPlugin plugin : loader) {
            plugins.add(plugin);
        }
        return plugins;
    }
}
