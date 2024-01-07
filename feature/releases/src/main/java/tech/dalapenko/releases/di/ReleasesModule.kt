package tech.dalapenko.releases.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import tech.dalapenko.releases.model.datasource.local.LocalDataSource
import tech.dalapenko.releases.model.datasource.local.LocalDataSourceImpl
import tech.dalapenko.releases.model.datasource.remote.RemoteDataSource
import tech.dalapenko.releases.model.datasource.remote.RemoteDataSourceImpl
import tech.dalapenko.releases.model.repository.ReleaseRepository
import tech.dalapenko.releases.model.repository.ReleaseRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
interface ReleasesModule {

    @Binds
    fun bindRemoteDataSource(remoteDataSource: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    fun bindLocalDataSource(remoteDataSource: LocalDataSourceImpl): LocalDataSource

    @Binds
    fun bindReleaseRepository(releaseRepository: ReleaseRepositoryImpl): ReleaseRepository
}