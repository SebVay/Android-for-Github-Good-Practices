import org.gradle.api.artifacts.VersionCatalog

internal fun VersionCatalog.getVersionInt(name: String): Int {
    return findVersion(name).get().toString().toInt()
}

internal fun VersionCatalog.findPluginId(name: String): String {
    return findPlugin(name).get().get().pluginId
}