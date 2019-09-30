package me.rankov.kaboom.stats

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mousebird.maply.*
import me.rankov.kaboom.EXTRA_COUNTRY
import me.rankov.kaboom.country.Country
import java.io.File
import java.util.*

class StatsGlobeFragment : GlobeMapFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, inState: Bundle?): View? {
        super.onCreateView(inflater, container, inState)
        return baseControl.contentView
    }

    override fun chooseDisplayType(): MapDisplayType {
        return MapDisplayType.Globe
    }

    override fun controlHasStarted() {
        // setup base layer tiles
        val cacheDirName = "stamen_watercolor"
        val cacheDir = File(activity!!.cacheDir, cacheDirName)
        cacheDir.mkdir()
        val remoteTileSource = RemoteTileSource(RemoteTileInfo("http://tile.stamen.com/watercolor/", "png", 0, 18))
        remoteTileSource.setCacheDir(cacheDir)
        val coordSystem = SphericalMercatorCoordSystem()

        // globeControl is the controller when using MapDisplayType.Globe
        // mapControl is the controller when using MapDisplayType.Map
        val baseLayer = QuadImageTileLayer(globeControl, coordSystem, remoteTileSource)
        baseLayer.imageDepth = 1
        baseLayer.setSingleLevelLoading(false)
        baseLayer.setUseTargetZoomLevel(false)
        baseLayer.setCoverPoles(true)
        baseLayer.setHandleEdges(true)

        val country: Country? = arguments?.getParcelable(EXTRA_COUNTRY)
        val latlng = country?.latlng
        val lat = latlng?.get(0) ?: 31.5
        val lng = latlng?.get(1) ?: 34.75
        val name = country?.name ?: "Israel"

        // add layer and position
//        globeControl.addLayer(baseLayer)
        KotlinFix.addLayer(globeControl, baseLayer)
        globeControl.animatePositionGeo(Math.toRadians(lng), Math.toRadians(lat), 0.5, 1.0)

        // add place label
        insertLabels(lat, lng, name)
    }

    private fun insertLabels(lat: Double, lng: Double, name: String) {
        val labels = ArrayList<ScreenLabel>()

        val labelInfo = LabelInfo()
        labelInfo.setFontSize(70f)
        labelInfo.textColor = Color.BLACK
        labelInfo.typeface = Typeface.DEFAULT_BOLD
        labelInfo.setLayoutPlacement(LabelInfo.LayoutCenter)
        labelInfo.outlineColor = Color.WHITE
        labelInfo.outlineSize = 5f

        var layoutImportance = 1f

        val label = ScreenLabel()
        label.loc = Point2d.FromDegrees(lng, lat) // Longitude, Latitude
        label.text = name
        label.layoutImportance = layoutImportance++
        labels.add(label)

        // Add your markers to the map controller.
        val labelsComponentObject = globeControl.addScreenLabels(labels, labelInfo, MaplyBaseController.ThreadMode.ThreadAny)
    }
}