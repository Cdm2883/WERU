package vip.cdms.weru

private object CurrentPlatform : Platform.Desktop
actual fun getPlatform(): Platform = CurrentPlatform
