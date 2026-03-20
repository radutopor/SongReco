package com.radutopor.songreco.features.root.impl

import com.radutopor.songreco.features.details.impl.detailsModule
import com.radutopor.songreco.features.list.impl.listModule
import com.radutopor.songreco.features.search.impl.searchModule
import com.radutopor.songreco.features.trending.impl.trendingModule
import org.koin.dsl.module

val rootModule = module {

    includes(
        searchModule,
        trendingModule,
        listModule,
        detailsModule,
    )

    single<SubstateProvider> { SubstateProviderImpl() }
    single {
        RootReducerFactory(
            substateProvider = get()
        )
    }
    factory { params ->
        RootStore(
            reducerFactory = get(),
            featureScope = params.get(),
        )
    }
}
