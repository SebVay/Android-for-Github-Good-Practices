package com.github.app.domain.repo.mapper

import com.github.app.domain.repo.model.Language
import com.github.app.domain.repo.model.LanguageColor
import com.github.app.domain.repo.model.LanguageColor.CustomColor
import com.github.app.domain.repo.model.LanguageColor.DefaultColor
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import com.github.app.domain.contract.Language as ContractLanguage

/**
 * Maps a [ContractLanguage] object to a [Language] object.
 *
 * The weight of a language is calculated based on its size relative to the total bytes in a repository.
 * The color of a language is determined by its hex color code, if valid, otherwise a default color is used.
 */
internal fun interface LanguageMapper {
    fun map(totalBytes: Int, contractLanguage: ContractLanguage): Language
}

internal class LanguageMapperImpl : LanguageMapper {

    private val colorRegex = Regex("^#[a-fA-F0-9]{6}$")

    override fun map(totalBytes: Int, contractLanguage: ContractLanguage): Language {

        return Language(
            name = contractLanguage.name,
            color = contractLanguage.asColor(),
            weight = contractLanguage.asWeight(totalBytes),
            size = contractLanguage.size,
        )
    }

    private fun ContractLanguage.asColor(): LanguageColor {
        val color = this.color

        return when (color.isValidColor()) {
            true -> CustomColor(color)
            false -> DefaultColor
        }
    }

    private fun ContractLanguage.asWeight(totalBytes: Int): Float {
        return size.toFloat() / totalBytes
    }


    @OptIn(ExperimentalContracts::class)
    private fun String?.isValidColor(): Boolean {
        contract {
            returns(true) implies (this@isValidColor != null)
        }

        return this != null && this.matches(colorRegex)
    }
}
