package tech.dalapenko.data.premieres.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import tech.dalapenko.data.premieres.datasource.local.LocalDataSource
import tech.dalapenko.data.premieres.datasource.local.LocalDataSourceImpl
import tech.dalapenko.data.premieres.datasource.remote.RemoteDataSource
import tech.dalapenko.data.premieres.datasource.remote.RemoteDataSourceImpl
import tech.dalapenko.data.premieres.mapper.PremieresDboMapper
import tech.dalapenko.data.premieres.mapper.PremieresDtoMapper
import tech.dalapenko.data.premieres.model.Premiere
import tech.dalapenko.data.premieres.repository.PremieresRepository
import tech.dalapenko.data.premieres.repository.PremieresRepositoryImpl
import tech.dalapenko.core.database.dbo.FullPremiereDataDbo
import tech.dalapenko.core.database.mapper.DboMapper
import tech.dalapenko.core.network.dto.PremiereItemDto
import tech.dalapenko.core.network.mapper.DtoMapper

@Module(includes = [InternalPremieresDataModule::class])
@InstallIn(ViewModelComponent::class)
object PremieresDataModule {

    @Provides
    fun providePremieresRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): PremieresRepository {
        return PremieresRepositoryImpl(
            remoteDataSource,
            localDataSource
        )
    }
}

@Module
@InstallIn(ViewModelComponent::class)
internal interface InternalPremieresDataModule {

    @Binds
    fun bindRemoteDataSource(remoteDataSource: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    fun bindLocalDataSource(remoteDataSource: LocalDataSourceImpl): LocalDataSource

    @Binds
    fun bindDboMapper(dboMapper: PremieresDboMapper): DboMapper<FullPremiereDataDbo, Premiere>

    @Binds
    fun bindDtoMapper(dtoMapper: PremieresDtoMapper): DtoMapper<PremiereItemDto, Premiere>
}