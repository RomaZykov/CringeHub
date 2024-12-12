package com.example.konsist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import com.lemonappdev.konsist.api.ext.list.withName
import com.lemonappdev.konsist.api.verify.assertFalse
import com.lemonappdev.konsist.api.verify.assertTrue
import kotlinx.coroutines.flow.Flow
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
            val featureLayers = featureNames.map { feature ->
                Layer("Feature $feature", "com.example.feature.${feature}..")
            }

            data.dependsOn(domain)
            domain.dependsOnNothing()

            featureLayers.forEach { feature ->
                feature.doesNotDependOn(data)
                featureLayers.filter { it != feature }.forEach {
                    feature.dependsOn(domain, it)
                }
            }
        }
    }

    @Test
    fun `Clean architecture features have correct dependencies`() {
//        Konsist.scopeFromProduction().packages.withName {
//            it.contains("com.example.feature")
//        }
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
    fun `Classes extending 'ViewModel' should have 'ViewModel' suffix`() {
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
                it.hasNameEndingWith("Preview")
            }
    }

    @Test
    fun `All JetPack Compose functions do not contain MutableState and Flows properties directly`() {
        Konsist
            .scopeFromProject()
            .functions()
            .withAnnotationOf(Composable::class)
            .assertFalse {
                it.hasParameter { parameter ->
                    parameter.hasTypeOf(Flow::class)
                    parameter.hasTypeOf(MutableState::class)
                }
            }
    }
}