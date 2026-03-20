package com.radutopor.songreco.features.trending.impl

import com.radutopor.songreco.core.webapi.webApiModule
import com.radutopor.songreco.features.trending.impl.repository.TrendingRepository
import com.radutopor.songreco.features.trending.impl.repository.TrendingRepositoryImpl
import com.radutopor.songreco.features.trending.impl.repository.TrendingWebApiMapper
import com.radutopor.songreco.features.trending.impl.repository.TrendingWebApiMapperImpl
import org.koin.dsl.module

val trendingModule = module {

    includes(webApiModule)

    single { TrendingReducerFactory() }
    single<TrendingWebApiMapper> { TrendingWebApiMapperImpl() }
    single<TrendingRepository> {
        TrendingRepositoryImpl(
            webApi = get(),
            mapper = get(),
        )
    }
    single {
        TrendingLauncherFactory(
            repository = get(),
        )
    }
    factory { params ->
        TrendingStore(
            reducerFactory = get(),
            launcherFactory = get(),
            featureScope = params.get(),
        )
    }
}
