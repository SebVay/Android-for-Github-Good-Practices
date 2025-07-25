plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.convention.config)
    alias(libs.plugins.convention.dependencies)
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

private fun Project.githubToken(): String {
    return (findProperty("github.token") as? String)
        .orEmpty()
        .also {
            if (it.isEmpty()) {
                error("GITHUB_TOKEN NOT FOUND. The `github.token` gradle key is mandatory to build the project.")
            }
        }
}
