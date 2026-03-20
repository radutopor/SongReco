package com.radutopor.songreco.features.details.impl

import com.radutopor.songreco.core.webapi.webApiModule
import com.radutopor.songreco.features.details.impl.repository.DetailsRepository
import com.radutopor.songreco.features.details.impl.repository.DetailsRepositoryImpl
import com.radutopor.songreco.features.details.impl.repository.DetailsWebApiMapper
import com.radutopor.songreco.features.details.impl.repository.DetailsWebApiMapperImpl
import org.koin.dsl.module

val detailsModule = module {

    includes(webApiModule)

    single { DetailsReducerFactory() }
    single<DetailsWebApiMapper> { DetailsWebApiMapperImpl() }
    single<DetailsRepository> {
        DetailsRepositoryImpl(
            webApi = get(),
            mapper = get(),
        )
    }
    single {
        DetailsLauncherFactory(
            repository = get(),
        )
    }
    factory { params ->
        DetailsStore(
            reducerFactory = get(),
            launcherFactory = get(),
            featureScope = params.get(),
            args = params.get(),
        )
    }
}
