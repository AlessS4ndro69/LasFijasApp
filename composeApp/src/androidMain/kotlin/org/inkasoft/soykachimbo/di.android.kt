package org.inkasoft.soykachimbo

import org.inkasoft.soykachimbo.data.database.getDatabaseBuilder
import org.koin.core.module.Module
import org.koin.dsl.module

actual val nativeModule = module {
    single { getDatabaseBuilder(get()) }
}
