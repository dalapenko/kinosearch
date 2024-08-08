package tech.dalapenko.data.releases.di

import androidx.paging.ExperimentalPagingApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import tech.dalapenko.core.database.KinoDatabase
import tech.dalapenko.core.database.dao.KinoDao
import tech.dalapenko.core.database.dbo.FullReleaseDataDbo
import tech.dalapenko.core.database.mapper.DboMapper
import tech.dalapenko.core.network.dto.DigitalReleaseItemDto
import tech.dalapenko.core.network.mapper.DtoMapper
import tech.dalapenko.data.releases.datasource.ReleasesRemoteMediator
import tech.dalapenko.data.releases.datasource.local.PagingDataSource
import tech.dalapenko.data.releases.mapper.ReleaseDboMapper
import tech.dalapenko.data.releases.mapper.ReleaseDtoMapper
import tech.dalapenko.data.releases.model.Release
import tech.dalapenko.data.releases.repository.ReleaseRepository
import tech.dalapenko.data.releases.repository.ReleaseRepositoryImpl

@Module(includes = [InternalReleasesDataModule::class])
@InstallIn(ViewModelComponent::class)
object ReleasesDataModule {

    @ExperimentalPagingApi
    @Provides
    fun provideReleasesRepository(
        pagingDataSourceFactory: PagingDataSource.Factory,
        releasesRemoteMediatorFactory: ReleasesRemoteMediator.Factory
    ): ReleaseRepository {
        return ReleaseRepositoryImpl(
            pagingDataSourceFactory,
            releasesRemoteMediatorFactory
        )
    }

    @Provides
    fun providePagingDataSourceFactory(
        database: KinoDatabase,
        kinoDao: KinoDao,
        mapper: DboMapper<FullReleaseDataDbo, Release>
    ): PagingDataSource.Factory {
        return object : PagingDataSource.Factory {
            override fun create(): PagingDataSource {
                return PagingDataSource(database, kinoDao, mapper)
            }
        }
    }
}

@Module
@InstallIn(ViewModelComponent::class)
internal interface InternalReleasesDataModule {

    @Binds
    fun bindDboMapper(dboMapper: ReleaseDboMapper): DboMapper<FullReleaseDataDbo, Release>

    @Binds
    fun bindDtoMapper(dtoMapper: ReleaseDtoMapper): DtoMapper<DigitalReleaseItemDto, Release>
}