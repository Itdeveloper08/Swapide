package com.sorto.eduardosortoswapidev.view.detail.hero


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.sorto.eduardosortoswapidev.R
import com.sorto.eduardosortoswapidev.models.Category
import com.sorto.eduardosortoswapidev.models.Film
import com.sorto.eduardosortoswapidev.models.SWModel
import com.sorto.eduardosortoswapidev.utilities.FormatUtils
import com.sorto.eduardosortoswapidev.viewmodels.SWImage
import kotlinx.android.synthetic.main.view_detail_hero_tall.view.*

/**
 * Layout for Film details text
 */
class TallHeroLayout : HeroLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, model: SWModel) : super(context) {

        this.model = model

        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_detail_hero_tall, this, true)

        populateContent()
        updateHeroImage(heroImage, model.imagePath, SWImage.getFallbackImage(model.categoryId))
    }

    /**
     * Add content to layout
     */
    private fun populateContent() {

        title = heroTitle
        subtitle = heroSubtitle

        if(model.categoryId == Category.FILMS ) {

            episode.text = context.getString(R.string.episode_number, FormatUtils.IntegerToRomanNumeral((model as Film).episodeId))
            episode.visibility = View.VISIBLE

            openingCrawl.text = (model as Film).openingCrawl
            openingCrawl.visibility = View.VISIBLE

        } else {
            heroSubtitle.text = model.subtitle
            heroSubtitle.visibility = View.VISIBLE
        }

        heroTitle.text = model.title
        heroTitle.visibility = View.VISIBLE

        updateHeroImage(heroImage, model.imagePath, SWImage.getFallbackImage(model.categoryId))
    }
}
