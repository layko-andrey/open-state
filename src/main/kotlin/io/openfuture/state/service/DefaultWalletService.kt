package io.openfuture.state.service

import io.openfuture.state.domain.Wallet
import io.openfuture.state.exception.NotFoundException
import io.openfuture.state.repository.WalletRepository
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service

@Service
class DefaultWalletService(private val walletRepository: WalletRepository) : WalletService {

    override suspend fun save(address: String, webhook: String): Wallet {
        val wallet = Wallet(address, webhook)
        return walletRepository.save(wallet).awaitSingle()
    }

    override suspend fun findByAddress(address: String): Wallet {
        val wallet = walletRepository.findByAddress(address).awaitFirstOrNull()
        wallet ?: throw NotFoundException("Wallet with address $address not found")
        return wallet
    }

}
