package com.data.observer

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.domain.observer.ConnectivityObserver
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlin.time.Duration.Companion.milliseconds

class NetworkConnectivityObserver(
    private val context: Context
) : ConnectivityObserver {
    override fun observe(): Flow<Boolean> = callbackFlow {
        val manager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(true)
            }

            override fun onLost(network: Network) {
                trySend(false)
            }

            override fun onUnavailable() {
                trySend(false)
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        manager.registerNetworkCallback(request, callback)

        val isConnected = manager
            .getNetworkCapabilities(manager.activeNetwork)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

        trySend(isConnected)

        awaitClose { manager.unregisterNetworkCallback(callback) }
    }.distinctUntilChanged()
        .onEach {
            delay(500.milliseconds)
        }
}