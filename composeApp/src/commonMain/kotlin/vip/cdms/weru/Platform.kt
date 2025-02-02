package vip.cdms.weru

sealed interface Platform {
    interface Desktop : Platform
    interface Android : Platform
}

internal expect fun getPlatform(): Platform
val RuntimePlatform by lazy { getPlatform() }
