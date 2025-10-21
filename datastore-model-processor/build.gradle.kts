import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.net.URI

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.vanniktech.maven.publish)
}

kotlin {
    jvm {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
    }

    sourceSets {
        jvmMain {
            dependencies {
                implementation(libs.ksp.api)
            }
        }
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
    val artifactId = "jetpref-datastore-model-processor"
    val projectVersion: String by project
    coordinates(projectGroupId, artifactId, projectVersion)
}
