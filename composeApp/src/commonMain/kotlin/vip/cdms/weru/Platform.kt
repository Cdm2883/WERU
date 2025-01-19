package vip.cdms.weru

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform