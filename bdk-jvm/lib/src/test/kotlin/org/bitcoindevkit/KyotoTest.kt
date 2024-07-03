package org.bitcoindevkit

import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class KyotoTest {
    private val descriptor: Descriptor = Descriptor("wpkh(tprv8ZgxMBicQKsPf2qfrEygW6fdYseJDDrVnDv26PH5BHdvSuG6ecCbHqLVof9yZcMoM31z9ur3tTYbSnr1WBqbGX97CbXcmp5H6qeMpyvx35B/84h/1h/0h/0/*)", Network.SIGNET)
    private val changeDescriptor: Descriptor = Descriptor("wpkh(tprv8ZgxMBicQKsPf2qfrEygW6fdYseJDDrVnDv26PH5BHdvSuG6ecCbHqLVof9yZcMoM31z9ur3tTYbSnr1WBqbGX97CbXcmp5H6qeMpyvx35B/84h/1h/0h/1/*)", Network.SIGNET)

    @Test
    fun testKyoto() {
        // val wallet: Wallet = Wallet(descriptor, changeDescriptor, Network.SIGNET)
        runBlocking {
            val (node, client) = buildKyotoClient()
            runNode(node)
            client.update()
        }
    }
}
