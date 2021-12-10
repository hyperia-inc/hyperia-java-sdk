package net.hyperia

class HyperiaClientException : Exception {
    constructor(message: String, cause: Exception): super(message, cause)
    constructor(message: String) : super(message)
}

const val APIKEY_REQUIRED: String = "apikey.required"
const val AT_LEAST_ONE_PROPERTY_REQUIRED = "at.least.one.property.required"