package tech.dalapenko.data.search.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import tech.dalapenko.data.search.mapper.SearchResultDtoMapper
import tech.dalapenko.data.search.model.SearchResult
import tech.dalapenko.core.network.dto.FilmSearchItemDto
import tech.dalapenko.core.network.mapper.DtoMapper
import tech.dalapenko.data.search.datasource.remote.PagingDataSource
import tech.dalapenko.data.search.repository.SearchRepository
import tech.dalapenko.data.search.repository.SearchRepositoryImpl

@Module(includes = [InternalSearchDataModule::class])
@InstallIn(ViewModelComponent::class)
object SearchDataModule {

    @Provides
    fun provideSearchRepository(
        pagingDataSourceFactory: PagingDataSource.Factory
    ): SearchRepository {
        return SearchRepositoryImpl(pagingDataSourceFactory)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
internal interface InternalSearchDataModule {

    @Binds
    fun bindDtoMapper(dtoMapper: SearchResultDtoMapper): DtoMapper<FilmSearchItemDto, SearchResult>
}