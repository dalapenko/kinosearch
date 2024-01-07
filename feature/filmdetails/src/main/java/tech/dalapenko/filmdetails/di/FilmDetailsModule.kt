package tech.dalapenko.filmdetails.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import tech.dalapenko.filmdetails.model.repository.FilmDetailsRepository
import tech.dalapenko.filmdetails.model.repository.FilmDetailsRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
interface FilmDetailsModule {

    @Binds
    fun bindFilmDetailsRepository(repository: FilmDetailsRepositoryImpl): FilmDetailsRepository
}