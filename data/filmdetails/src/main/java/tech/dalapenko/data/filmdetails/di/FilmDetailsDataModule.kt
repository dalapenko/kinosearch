package tech.dalapenko.data.filmdetails.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import tech.dalapenko.data.core.mapper.DtoMapper
import tech.dalapenko.data.filmdetails.datasource.remote.RemoteDataSource
import tech.dalapenko.data.filmdetails.datasource.remote.RemoteDataSourceImpl
import tech.dalapenko.data.filmdetails.mapper.FilmDetailsDtoMapper
import tech.dalapenko.data.filmdetails.model.Film
import tech.dalapenko.data.filmdetails.repository.FilmDetailsRepository
import tech.dalapenko.data.filmdetails.repository.FilmDetailsRepositoryImpl
import tech.dalapenko.core.network.dto.FilmDto

@Module(includes = [InternalFilmDetailsDataModule::class])
@InstallIn(ViewModelComponent::class)
object FilmDetailsDataModule {

    @Provides
    fun provideFilmDetailsRepository(
        remoteDataSource: RemoteDataSource
    ): FilmDetailsRepository {
        return FilmDetailsRepositoryImpl(remoteDataSource)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
internal interface InternalFilmDetailsDataModule {

    @Binds
    fun bindRemoteDataSource(remoteDataSource: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    fun bindDtoMapper(dtoMapper: FilmDetailsDtoMapper): DtoMapper<FilmDto, Film>
}