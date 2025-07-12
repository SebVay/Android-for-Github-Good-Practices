plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.common.config.convention)
    alias(libs.plugins.common.dependencies.convention)
    alias(libs.plugins.apollo)
}

android {
    namespace = "com.github.app.data.apollo"

    defaultConfig {
        buildConfigField("String", "GITHUB_TOKEN", "\"" + project.githubToken() + "\"")
    }
}

apollo {
    service("service") {
        packageName.set("com.github.app.data.apollo")

        introspection {
            endpointUrl.set("https://api.github.com/graphql")
            headers.put("Authorization", "Bearer ${project.githubToken()}")
            schemaFile.set(file("src/main/graphql/schema.graphqls"))
        }
    }
}

dependencies {
    implementation(libs.apollo.runtime)
}

fun Project.githubToken(): String = findProperty("github.token") as String
