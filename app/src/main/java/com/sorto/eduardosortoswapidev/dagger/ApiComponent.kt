package com.sorto.eduardosortoswapidev.dagger


import dagger.Component
import com.sorto.eduardosortoswapidev.network.StarWarsService
import com.sorto.eduardosortoswapidev.viewmodels.CategoryViewModel
import com.sorto.eduardosortoswapidev.viewmodels.DetailViewModel
import com.sorto.eduardosortoswapidev.viewmodels.SearchViewModel
import javax.inject.Singleton


@Component(modules = [ApiModule::class])
interface ApiComponent {

    @Singleton
    fun inject(service: StarWarsService)

    fun inject(viewModel: CategoryViewModel)

    fun inject(viewModel: DetailViewModel)

    fun inject(viewModel: SearchViewModel)
}
