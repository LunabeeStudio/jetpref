import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.net.URI

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.plugin.compose)
    alias(libs.plugins.agp.library)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.vanniktech.maven.publish)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
    }

    sourceSets {
        androidMain {
            dependencies {
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(libs.androidx.core.ktx)
                implementation(project(":datastore-model"))
                implementation(project(":material-ui"))
            }
        }
    }
}

android {
    val projectCompileSdk: String by project
    val projectMinSdk: String by project

    namespace = "dev.patrickgold.jetpref.datastore.ui"
    compileSdk = projectCompileSdk.toInt()

    defaultConfig {
        minSdk = projectMinSdk.toInt()
        consumerProguardFiles("proguard-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

publishing {
    repositories {
        maven {
            authentication {
                credentials.username = project.properties["artifactory_deployer_release_username"] as? String
                credentials.password = project.properties["artifactory_deployer_release_api_key"] as? String
            }
            url = URI.create("https://artifactory.lunabee.studio/artifactory/jetpref-local/")
        }
    }
}

mavenPublishing {
    val projectGroupId: String by project
    val artifactId = "jetpref-datastore-ui"
    val projectVersion: String by project
    coordinates(projectGroupId, artifactId, projectVersion)
}
