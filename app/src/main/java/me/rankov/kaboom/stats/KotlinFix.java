package me.rankov.kaboom.stats;

import com.mousebird.maply.GlobeController;
import com.mousebird.maply.QuadImageTileLayer;

public class KotlinFix {
    public static void addLayer(GlobeController globeController, QuadImageTileLayer layer) {
        globeController.addLayer(layer);
    }
}
