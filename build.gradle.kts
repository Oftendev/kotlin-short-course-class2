import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
}
// 1.5.10 - версия Kotlin компилятора
group = "guru.bravit"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()  // большой репозиторий
    maven("https://dl.bintray.com/kotlin/kotlin-eap") // подключаем Kotlin early access
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev") // будет приходить всё, связанное с Compose
}

val osName = System.getProperty("os.name")             //  Графика зависит от системы и мы определяем OS с которой
val targetOs = when {                                  //  мы работаем
    osName == "Mac OS X" -> "macos"                    //
    osName.startsWith("Win") -> "windows"        //
    osName.startsWith("Linux") -> "linux"        //
    else -> error("Unsupported OS: $osName")          //
}                                                     //

val osArch = System.getProperty("os.arch")         // определяем архитектуру (x64 или arm64)
var targetArch = when (osArch) {
    "x86_64", "amd64" -> "x64"
    "aarch64" -> "arm64"
    else -> error("Unsupported arch: $osArch")

}
val target = "${targetOs}-${targetArch}"

var version = "0.0.0-SNAPSHOT"
if (project.hasProperty("skiko.version")) {
    version = project.properties["skiko.version"] as String
}

dependencies {           //зависимости
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.4.1") // для многопоточного программирования
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.1")
    implementation("org.jetbrains.skiko:skiko-jvm-runtime-$target:$version")  // Skiko подключаем
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.2.0")            // работа  датой и временем
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}



tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"   // настройка Java
}