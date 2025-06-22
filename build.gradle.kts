plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.plugin.yml)
    alias(libs.plugins.run.paper)
    alias(libs.plugins.shadowJar)
}

group = "net.nitkanikita"
version = "1.0"

repositories {
    mavenCentral()
    google()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.codemc.org/repository/maven-public/")
    maven("https://maven.enginehub.org/repo/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")

    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://repo.mineinabyss.com/releases")
}

dependencies {
    compileOnly(libs.paper.api)

    implementation(libs.configurate.core)
    implementation(libs.configurate.extra.kotlin)
    implementation(libs.configurate.yaml)

    implementation(libs.cloud.paper)
    implementation(libs.cloud.minecraft.extras)
    implementation(libs.cloud.kotlin.extensions)
    implementation(libs.cloud.kotlin.coroutines)
    implementation(libs.cloud.paper.signed.arguments)

}

tasks.runServer {
    minecraftVersion("1.20.4")
}

paper {
    main = "me.nitkanikita.runeenchantments.RuneEnchantmentsPlugin"
    apiVersion = "1.20"
    authors = listOf("nitkanikita21")
    name = "RuneEnchantments"
    version = project.version.toString()
}

kotlin {
    jvmToolchain(21)
}