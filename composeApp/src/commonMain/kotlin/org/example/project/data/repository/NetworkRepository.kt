package org.example.project.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import org.example.project.data.response.CensoredText
import org.example.project.network.NetWorkResult
import org.example.project.utils.toResultFlow

class NetworkRepository(private val httpClient: HttpClient) {
    suspend fun getCensor(uncensored: String): Flow<NetWorkResult<CensoredText>> {
        return toResultFlow {
            val response = httpClient.get(
                urlString = "https://www.purgomalum.com/service/json"
            ) {
                parameter("text", uncensored)
            }.body<CensoredText>()
            NetWorkResult.Success(response)
        }
    }
}