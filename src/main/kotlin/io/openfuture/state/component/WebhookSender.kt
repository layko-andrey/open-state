package io.openfuture.state.component

import io.openfuture.state.domain.dto.TrackingTransactionDto
import io.openfuture.state.entity.Transaction
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class WebhookSender {

    fun sendWebHook(urlList: List<String>, transaction: Transaction) {
        urlList.forEach {
            RestTemplate().postForLocation(it, TrackingTransactionDto(transaction))
        }

    }

}
