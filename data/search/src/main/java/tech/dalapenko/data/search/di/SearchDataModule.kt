package tech.dalapenko.data.search.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import tech.dalapenko.data.core.mapper.DtoMapper
import tech.dalapenko.data.search.datasource.remote.RemoteDataSource
import tech.dalapenko.data.search.datasource.remote.RemoteDataSourceImpl
import tech.dalapenko.data.search.mapper.SearchResultDtoMapper
import tech.dalapenko.data.search.model.SearchResult
import tech.dalapenko.data.search.repository.SearchRepository
import tech.dalapenko.data.search.repository.SearchRepositoryImpl
import tech.dalapenko.network.dto.FilmSearchItemDto

@Module(includes = [InternalSearchDataModule::class])
@InstallIn(ViewModelComponent::class)
object SearchDataModule {

    @Provides
    fun provideSearchRepository(
        remoteDataSource: RemoteDataSource
    ): SearchRepository {
        return SearchRepositoryImpl(remoteDataSource)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
internal interface InternalSearchDataModule {

    @Binds
    fun bindRemoteDataSource(remoteDataSource: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    fun bindDtoMapper(dtoMapper: SearchResultDtoMapper): DtoMapper<FilmSearchItemDto, SearchResult>
}