package com.github.app.domain.repo.mapper

import com.github.app.domain.repo.model.Language
import com.github.app.domain.repo.model.LanguageColor.CustomColor
import com.github.app.domain.repo.model.LanguageColor.DefaultColor
import io.kotest.matchers.equals.shouldBeEqual
import org.junit.jupiter.api.Test
import com.github.app.domain.contract.Language as ContractLanguage

class LanguageMapperImplTest {

    private val mapper = LanguageMapperImpl()

    @Test
    fun `verify a contract language is mapped to a domain language`() {
        val givenLanguage = "Kotlin"
        val givenColor = "#A97BFF"
        val givenContractLanguage = ContractLanguage(name = givenLanguage, color = givenColor, size = 100)

        val language = mapper.map(200, givenContractLanguage)

        language shouldBeEqual Language(
            name = givenLanguage,
            color = CustomColor(givenColor),
            weight = 0.5f,
            size = 100,
        )
    }

    @Test
    fun `verify a contract language with an invalid color is mapped to a domain language with a default color`() {
        val givenLanguage = "Kotlin"
        val givenContractLanguage = ContractLanguage(name = givenLanguage, color = "invalid-color", size = 100)

        val language = mapper.map(400, givenContractLanguage)

        language shouldBeEqual Language(
            name = givenLanguage,
            color = DefaultColor,
            weight = 0.25f,
            size = 100,
        )
    }
}
