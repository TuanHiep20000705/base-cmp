package org.example.project.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.project.data.repository.NetworkRepository
import org.example.project.ui.censor_screen.CensorViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(provideHttpClientModule, provideRepositoryModule, provideViewModelModule)
    }
}

val provideHttpClientModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(json = Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
            }
        }
    }
}
val provideRepositoryModule = module {
    single<NetworkRepository> { NetworkRepository(get()) }
}
val provideViewModelModule = module {
    single {
        CensorViewModel(get())
    }
}