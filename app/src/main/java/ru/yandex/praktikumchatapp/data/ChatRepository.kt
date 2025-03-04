package ru.yandex.praktikumchatapp.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.retryWhen

class ChatRepository(
    private val api: ChatApi = ChatApi()
) {

    val DELAY_FACTOR = 2
    var currentDelay = 100L

    fun getReplyMessage(): Flow<String> {
        val flow = api.getReply()
        return flow.retryWhen { cause, _ ->
            if (cause is Exception) {
                delay(currentDelay)
                currentDelay *= DELAY_FACTOR
                true
            } else {
                false
            }
        }
    }
}
