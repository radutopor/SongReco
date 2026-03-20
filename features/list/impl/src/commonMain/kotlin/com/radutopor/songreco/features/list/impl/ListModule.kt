package com.radutopor.songreco.features.list.impl

import com.radutopor.songreco.core.webapi.webApiModule
import com.radutopor.songreco.features.list.impl.repository.ListRepository
import com.radutopor.songreco.features.list.impl.repository.ListRepositoryImpl
import com.radutopor.songreco.features.list.impl.repository.ListWebApiMapper
import com.radutopor.songreco.features.list.impl.repository.ListWebApiMapperImpl
import org.koin.dsl.module

val listModule = module {

    includes(webApiModule)

    single { ListReducerFactory() }
    single<ListWebApiMapper> { ListWebApiMapperImpl() }
    single<ListRepository> {
        ListRepositoryImpl(
            webApi = get(),
            mapper = get(),
        )
    }
    single {
        ListLauncherFactory(
            repository = get(),
        )
    }
    factory { params ->
        ListStore(
            reducerFactory = get(),
            launcherFactory = get(),
            featureScope = params.get(),
            args = params.get(),
        )
    }
}
