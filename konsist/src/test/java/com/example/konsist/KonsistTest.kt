package com.example.konsist

import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.DependencyRules
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.architecture
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import com.lemonappdev.konsist.api.ext.list.functions
import com.lemonappdev.konsist.api.ext.list.withAllParentsOf
import com.lemonappdev.konsist.api.ext.list.withAnnotationOf
import com.lemonappdev.konsist.api.verify.assertFalse
import com.lemonappdev.konsist.api.verify.assertTrue
import kotlin.test.Test

class KonsistTest {

    // Clean architecture test
    @Test
    fun `Less than 7 number of functions in an interface`() {
        Konsist.scopeFromProject()
            .interfaces()
            .assertTrue {
                it.numFunctions(false, includeLocal = false) < 7
            }
    }

    // Architecture tests
    private fun arch(vararg featureNames: String): DependencyRules {
        return architecture {
            val data = Layer("Data", "com.example.data..")
            val domain = Layer("Domain", "com.example.domain..")

            data.dependsOn(domain)
            domain.dependsOnNothing()
            featureNames.forEach {
                Layer("Feature $it", "com.example.${it}..").dependsOn(domain)
            }
        }
    }

    @Test
    fun `clean architecture features have correct dependencies`() {
        Konsist
            .scopeFromProduction()
            .assertArchitecture(arch("auth", "onboarding"))
    }

    // ViewModel tests
    private val viewModelScope = Konsist
        .scopeFromProject()
        .classes()
        .withAllParentsOf(ViewModel::class)

    @Test
    fun `classes extending 'ViewModel' should have 'ViewModel' suffix`() {
        viewModelScope
            .assertTrue { it.name.endsWith("ViewModel") }
    }

    @Test
    fun `'ViewModel' classes should not have private methods for better testing`() {
        viewModelScope
            .functions()
            .assertFalse { it.hasPrivateModifier }
    }

    // JetpackCompose tests
    @Test
    fun `All JetPack Compose previews contain 'Preview' in method name`() {
        Konsist
            .scopeFromProject()
            .functions()
            .withAnnotationOf(Preview::class)
            .assertTrue {
                it.hasNameContaining("Preview")
            }
    }
}