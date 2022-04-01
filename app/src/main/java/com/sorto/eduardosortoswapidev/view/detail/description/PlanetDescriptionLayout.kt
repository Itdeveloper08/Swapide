package com.sorto.eduardosortoswapidev.view.detail.description


import android.content.Context
import android.view.LayoutInflater

import com.sorto.eduardosortoswapidev.R
import com.sorto.eduardosortoswapidev.models.Planet
import com.sorto.eduardosortoswapidev.models.SWModel
import com.sorto.eduardosortoswapidev.utilities.FormatUtils
import kotlinx.android.synthetic.main.view_detail_planet.view.*

/**
 * Layout for Planet details text
 */
class PlanetDescriptionLayout : DescriptionLayout {

    internal lateinit var model: Planet

    constructor(context: Context) : super(context)

    constructor(context: Context, model: SWModel) : super(context) {

        this.model = model as Planet

        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_detail_planet, this, true)

        populateContent()
    }

    /**
     * Add content to layout
     */
    private fun populateContent() {

        climate.text = model.climate
        planet_gravity.text = model.gravity
        terrain.text = model.terrain
        population.text = FormatUtils.getFormattedNumber(model.population)
        rotation_period.text = FormatUtils.getFormattedDays(context, model.rotationPeriod)
        orbital_period.text = FormatUtils.getFormattedDays(context, model.orbitalPeriod)
        diameter.text = FormatUtils.getFormattedDistance(context, model.diameter)
        surface_water.text = FormatUtils.getFormattedPercentage(context, model.surfaceWater)
    }
}
