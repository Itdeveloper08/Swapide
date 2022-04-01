package com.sorto.eduardosortoswapidev.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sorto.eduardosortoswapidev.dagger.DaggerApiComponent
import com.sorto.eduardosortoswapidev.models.*
import com.sorto.eduardosortoswapidev.network.StarWarsService
import com.sorto.eduardosortoswapidev.utilities.SortUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import kotlin.collections.ArrayList

class CategoryViewModel : ViewModel() {

    @Inject
    lateinit var service: StarWarsService


    private val compositeDisposable = CompositeDisposable()

    private val defaultPage = 1
    private var page = defaultPage

    private var modelList = ArrayList<SWModel>()
    private var liveData = MutableLiveData<ArrayList<SWModel>>()
    private var hasLoadError = MutableLiveData<Boolean>()
    private var isLoading = MutableLiveData<Boolean>()


    private var nextUrl: String? = null
    private var categoryId: String = ""

    init {
        DaggerApiComponent.create().inject(this)
    }


    fun getList(category: String): LiveData<ArrayList<SWModel>> {

        this.categoryId = category
        loadData()
        return liveData
    }



    fun getLoadError(): LiveData<Boolean> {
        return hasLoadError
    }


    fun getLoading(): LiveData<Boolean> {
        return isLoading
    }


    fun loadNextPage() {

        if (hasNextPage()) {
            loadData()
        }
    }

    fun hasNextPage(): Boolean {
        return (nextUrl != null)
    }


    fun dispose() {
        compositeDisposable.dispose()
    }

    fun getId(): String {
        return categoryId
    }

    fun getItem(position: Int): SWModel {
        return modelList[position]
    }

    fun getCount(): Int = modelList.size


    private fun loadData() {

        isLoading.value = true

        when (categoryId) {
            Category.FILMS -> fetchFilms()
            Category.PEOPLE -> fetchPeople()
            Category.SPECIES -> fetchSpecies()
            Category.PLANETS -> fetchPlanets()
            Category.STARSHIPS -> fetchStarships()
            Category.VEHICLES -> fetchVehicles()
        }
    }


    private fun fetchFilms() {

        compositeDisposable.add(
                service.getFilmsByPage(page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<SWModelList<Film>>() {

                            override fun onSuccess(list: SWModelList<Film>) {

                                list.results?.let {
                                    val films = SortUtils.sortFilmsByEpisode(it)
                                    applyResults(films, list.next)
                                }
                            }

                            override fun onError(e: Throwable) {
                                Timber.e(e)
                                hasLoadError.value = true
                            }
                        }))
    }


    private fun fetchPeople() {

        compositeDisposable.add(service.getPeopleByPage(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<SWModelList<People>>() {

                    override fun onSuccess(list: SWModelList<People>) {

                        list.results?.let { applyResults(it, list.next) }
                    }

                    override fun onError(e: Throwable) {
                        Timber.e(e)
                        hasLoadError.value = true
                    }
                }))
    }


    private fun fetchPlanets() {

        compositeDisposable.add(service.getPlanetsByPage(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<SWModelList<Planet>>() {

                    override fun onSuccess(list: SWModelList<Planet>) {

                        list.results?.let { applyResults(it, list.next) }
                    }

                    override fun onError(e: Throwable) {
                        Timber.e(e)
                        hasLoadError.value = true
                    }
                }))
    }


    private fun fetchSpecies() {

        compositeDisposable.add(service.getSpeciesByPage(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<SWModelList<Species>>() {

                    override fun onSuccess(list: SWModelList<Species>) {

                        list.results?.let { applyResults(it, list.next) }
                    }

                    override fun onError(e: Throwable) {
                        Timber.e(e)
                        hasLoadError.value = true
                    }
                }))
    }


    private fun fetchStarships() {

        compositeDisposable.add(service.getStarshipsByPage(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<SWModelList<Starship>>() {

                    override fun onSuccess(list: SWModelList<Starship>) {

                        list.results?.let { applyResults(it, list.next) }
                    }

                    override fun onError(e: Throwable) {
                        Timber.e(e)
                        hasLoadError.value = true
                    }
                }))
    }


    private fun fetchVehicles() {

        compositeDisposable.add(service.getVehiclesByPage(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<SWModelList<Vehicle>>() {

                    override fun onSuccess(list: SWModelList<Vehicle>) {

                        list.results?.let { applyResults(it, list.next) }
                    }

                    override fun onError(e: Throwable) {
                        Timber.e(e)
                        hasLoadError.value = true
                    }
                }))
    }


    private fun applyResults(results: ArrayList<*>, next: String?) {

        page = getNextPageInt(next)

        for (i in results.indices) {
            val item = results[i]
            if (item is SWModel) {
                modelList.add(item)
            }
        }

        isLoading.value = false
        hasLoadError.value = false
        liveData.value = modelList
    }


    private fun getNextPageInt(url: String?): Int {

        nextUrl = url

        if (url == null)
            return defaultPage

        val uri = Uri.parse(url)
        uri.getQueryParameter("page")?.let { return Integer.parseInt(it) }

        return defaultPage
    }
}
