package com.ozpehlivantugrul.passmanapp.utils

object RouteNames {

    const val welcome = "welcome"
    const val entrance = "entrance"

    // MAİN
    const val mainNav = "mainNav"
    const val home = "home"
    const val detail = "detail/{credentialId}"

    fun detailWithArgs(credentialId: String): String {
        return "detail/$credentialId"
    }

    const val generatePassword = "generatePassword"
    const val settings = "settings"
}