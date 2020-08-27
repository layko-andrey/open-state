package io.openfuture.state.controller

import io.openfuture.state.domain.Wallet
import io.openfuture.state.service.WalletService
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@RestController
@RequestMapping("/api/wallets")
class WalletController(private val walletService: WalletService) {

    @PostMapping
    suspend fun save(@RequestBody @Valid request: SaveWalletRequest): WalletDto {
        val wallet = walletService.save(request.address, request.webhook)
        return WalletDto(wallet)
    }

    @GetMapping("/address/{address}")
    suspend fun findByAddress(@PathVariable address: String): WalletDto {
        val wallet = walletService.findByAddress(address)
        return WalletDto(wallet)
    }

    data class WalletDto(
            val id: String,
            val address: String,
            val webhook: String,
            val lastUpdateDate: LocalDateTime
    ) {
        constructor(wallet: Wallet) : this(
                wallet.id.toHexString(),
                wallet.address,
                wallet.webhook,
                wallet.lastUpdateDate
        )
    }

    data class SaveWalletRequest(
            @field:NotNull @field:NotEmpty @field:NotBlank
            val address: String,
            @field:NotNull @field:NotEmpty @field:NotBlank
            val webhook: String,
    )
}
