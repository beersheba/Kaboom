package me.rankov.kaboom.map

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.LocationComponentOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import kotlinx.android.synthetic.main.activity_map.*
import org.jetbrains.anko.longToast


class MapActivity : AppCompatActivity(), OnMapReadyCallback, PermissionsListener {
    override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {
        longToast("You need to grant location permissions")
    }

    override fun onPermissionResult(granted: Boolean) {
        if (granted) {
            mapboxMap?.getStyle { style -> enableLocationComponent(style) }
        } else {
            longToast("Location permission not granted")
            finish()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        permissionsManager?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private var permissionsManager: PermissionsManager? = null
    private var mapView: MapView? = null
    private var mapboxMap: MapboxMap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(this, getString(me.rankov.kaboom.R.string.mapbox_access_token))
        setContentView(me.rankov.kaboom.R.layout.activity_map)

        mapView = findViewById(me.rankov.kaboom.R.id.mapView)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(this)
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        this.mapboxMap = mapboxMap
        mapboxMap.setStyle("mapbox://styles/rankov/cjyot2xdp0knn1dp9fkp125aa") {
            //disable compass
            mapboxMap.uiSettings.isCompassEnabled = false

            //setZoomLevels
            mapboxMap.setMaxZoomPreference(8.0)
            mapboxMap.setMinZoomPreference(1.0)

            enableLocationComponent(it)
        }

    }

    @SuppressLint("MissingPermission")
    private fun enableLocationComponent(style: Style) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            // Get an instance of the component
            val locationComponent = mapboxMap?.locationComponent

            val locationComponentOptions = LocationComponentOptions.builder(this)
                    .foregroundTintColor(Color.RED)
                    .build()

            // Activate with a built LocationComponentActivationOptions object
            locationComponent?.activateLocationComponent(LocationComponentActivationOptions
                    .builder(this, style)
                    .locationComponentOptions(locationComponentOptions)
                    .build())

            // Enable to make component visible
            locationComponent?.isLocationComponentEnabled = true

            // Set the component's camera mode
            locationComponent?.cameraMode = CameraMode.TRACKING

            location.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> location.setColorFilter(Color.RED)
                    MotionEvent.ACTION_UP -> location.clearColorFilter()
                }
                mapboxMap?.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                                LatLng(locationComponent?.lastKnownLocation), 12.0))

                true
            }


        } else {
            permissionsManager = PermissionsManager(this)
            permissionsManager?.requestLocationPermissions(this)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    public override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }


    public override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    public override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    public override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }
}
