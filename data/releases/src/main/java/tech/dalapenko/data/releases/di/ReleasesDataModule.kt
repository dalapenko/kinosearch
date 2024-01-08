package tech.dalapenko.data.releases.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import tech.dalapenko.data.releases.datasource.local.LocalDataSource
import tech.dalapenko.data.releases.datasource.local.LocalDataSourceImpl
import tech.dalapenko.data.releases.datasource.remote.RemoteDataSource
import tech.dalapenko.data.releases.datasource.remote.RemoteDataSourceImpl
import tech.dalapenko.data.releases.mapper.ReleaseDboMapper
import tech.dalapenko.data.releases.mapper.ReleaseDtoMapper
import tech.dalapenko.data.releases.model.Release
import tech.dalapenko.data.releases.repository.ReleaseRepository
import tech.dalapenko.data.releases.repository.ReleaseRepositoryImpl
import tech.dalapenko.core.database.dbo.FullReleaseDataDbo
import tech.dalapenko.core.database.mapper.DboMapper
import tech.dalapenko.core.network.dto.DigitalReleaseItemDto
import tech.dalapenko.core.network.mapper.DtoMapper

@Module(includes = [InternalReleasesDataModule::class])
@InstallIn(ViewModelComponent::class)
object ReleasesDataModule {

    @Provides
    fun provideReleasesRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): ReleaseRepository {
        return ReleaseRepositoryImpl(
            remoteDataSource,
            localDataSource
        )
    }
}

@Module
@InstallIn(ViewModelComponent::class)
internal interface InternalReleasesDataModule {

    @Binds
    fun bindRemoteDataSource(remoteDataSource: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    fun bindLocalDataSource(remoteDataSource: LocalDataSourceImpl): LocalDataSource

    @Binds
    fun bindDboMapper(dboMapper: ReleaseDboMapper): DboMapper<FullReleaseDataDbo, Release>

    @Binds
    fun bindDtoMapper(dtoMapper: ReleaseDtoMapper): DtoMapper<DigitalReleaseItemDto, Release>
}