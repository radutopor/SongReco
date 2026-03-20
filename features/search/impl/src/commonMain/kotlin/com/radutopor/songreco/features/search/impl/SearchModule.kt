package com.radutopor.songreco.features.search.impl

import org.koin.dsl.module

val searchModule = module {

    single { SearchReducerFactory() }
    factory {
        SearchStore(
            reducerFactory = get(),
        )
    }
}