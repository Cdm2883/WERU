package vip.cdms.weru

private object CurrentPlatform : Platform.Android
actual fun getPlatform(): Platform = CurrentPlatform
