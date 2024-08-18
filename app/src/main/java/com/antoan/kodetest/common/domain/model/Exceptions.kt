package com.antoan.kodetest.common.domain.model

import java.io.IOException

class NoMoreAnimalsException(message: String): Exception(message)

class NetworkUnavailableException(message: String = "No network available :(") : IOException(message)