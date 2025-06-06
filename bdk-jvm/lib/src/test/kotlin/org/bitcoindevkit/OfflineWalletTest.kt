package org.bitcoindevkit

import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse
import java.io.File

class OfflineWalletTest {
    private val descriptor: Descriptor = Descriptor(
        "wpkh(tprv8ZgxMBicQKsPf2qfrEygW6fdYseJDDrVnDv26PH5BHdvSuG6ecCbHqLVof9yZcMoM31z9ur3tTYbSnr1WBqbGX97CbXcmp5H6qeMpyvx35B/84h/1h/1h/0/*)",
        Network.TESTNET
    )
    private val changeDescriptor: Descriptor = Descriptor(
        "wpkh(tprv8ZgxMBicQKsPf2qfrEygW6fdYseJDDrVnDv26PH5BHdvSuG6ecCbHqLVof9yZcMoM31z9ur3tTYbSnr1WBqbGX97CbXcmp5H6qeMpyvx35B/84h/1h/1h/1/*)",
        Network.TESTNET
    )

    @Test
    fun testDescriptorBip86() {
        val mnemonic: Mnemonic = Mnemonic(WordCount.WORDS12)
        val descriptorSecretKey: DescriptorSecretKey = DescriptorSecretKey(Network.TESTNET, mnemonic, null)
        val descriptor: Descriptor = Descriptor.newBip86(descriptorSecretKey, KeychainKind.EXTERNAL, Network.TESTNET)

        assertTrue(descriptor.toString().startsWith("tr"), "Bip86 Descriptor does not start with 'tr'")
    }

   @Test
   fun testNewAddress() {
        var conn: Persister = Persister.newInMemory()
        val wallet: Wallet = Wallet(
            descriptor,
            changeDescriptor,
            Network.TESTNET,
            conn
        )
        val addressInfo: AddressInfo = wallet.revealNextAddress(KeychainKind.EXTERNAL)

        assertTrue(addressInfo.address.isValidForNetwork(Network.TESTNET), "Address is not valid for testnet network")
        assertTrue(addressInfo.address.isValidForNetwork(Network.SIGNET), "Address is not valid for signet network")
        assertFalse(addressInfo.address.isValidForNetwork(Network.REGTEST), "Address is valid for regtest network, but it shouldn't be")
        assertFalse(addressInfo.address.isValidForNetwork(Network.BITCOIN), "Address is valid for bitcoin network, but it shouldn't be")

        assertEquals(
            expected = "tb1qhjys9wxlfykmte7ftryptx975uqgd6kcm6a7z4",
            actual = addressInfo.address.toString()
        )
    }

    @Test
    fun testBalance() {
        var conn: Persister = Persister.newInMemory()
        val wallet: Wallet = Wallet(
            descriptor,
            changeDescriptor,
            Network.TESTNET,
            conn
        )

        assertEquals(
            expected = 0uL,
            actual = wallet.balance().total.toSat()
        )
    }
}
