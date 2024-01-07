package tech.dalapenko.data.releases.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import tech.dalapenko.data.core.mapper.DboMapper
import tech.dalapenko.data.core.mapper.DtoMapper
import tech.dalapenko.data.releases.datasource.local.LocalDataSource
import tech.dalapenko.data.releases.datasource.local.LocalDataSourceImpl
import tech.dalapenko.data.releases.datasource.remote.RemoteDataSource
import tech.dalapenko.data.releases.datasource.remote.RemoteDataSourceImpl
import tech.dalapenko.data.releases.mapper.ReleaseDboMapper
import tech.dalapenko.data.releases.mapper.ReleaseDtoMapper
import tech.dalapenko.data.releases.model.Release
import tech.dalapenko.data.releases.repository.ReleaseRepository
import tech.dalapenko.data.releases.repository.ReleaseRepositoryImpl
import tech.dalapenko.database.dbo.FullReleaseDataDbo
import tech.dalapenko.network.dto.DigitalReleaseItemDto

@Module(includes = [InternalReleasesDataModule::class])
@InstallIn(ViewModelComponent::class)
interface ReleasesDataModule {
    @Binds
    fun bindReleaseRepository(releaseRepository: ReleaseRepositoryImpl): ReleaseRepository
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