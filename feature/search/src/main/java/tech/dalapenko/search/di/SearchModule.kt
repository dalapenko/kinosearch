package tech.dalapenko.search.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import tech.dalapenko.search.model.repository.SearchRepository
import tech.dalapenko.search.model.repository.SearchRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
interface SearchModule {

    @Binds
    fun bindSearchRepository(repository: SearchRepositoryImpl): SearchRepository
}