package example.micronaut.crypto

import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client

@Client("https://api.kucoin.com/api/v1")
interface PriceClient {

    @Get("/market/orderbook/level1?symbol=BTC-USDT")
    fun latest(): BitcoinPrice
}
