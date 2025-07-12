package com.github.app.core.ui.component

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "PIXEL_4_XL", device = Devices.PIXEL_4_XL)
@Preview(name = "PIXEL_4_XL LANDSCAPE", widthDp = 869, heightDp = 412)
@Preview(name = "PIXEL", device = Devices.PIXEL)
@Preview(name = "SMALL DEVICE", widthDp = 320, heightDp = 540)
@Preview(name = "SMALL DEVICE LANDSCAPE", widthDp = 540, heightDp = 320)
annotation class MultiDevicePreviews
