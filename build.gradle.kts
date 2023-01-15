plugins {
  id("java")
  id("org.jetbrains.intellij") version "1.10.1"
}

group = "com.github.lppedd"
version = "0.0.2"

repositories {
  mavenCentral()
}

sourceSets {
  main {
    java {
      srcDir("src/main/gen")
    }
  }
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

// See https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
  version.set("2022.3")
  type.set("IC")
  plugins.set(listOf("java" /* Only for code documentation */))
}

dependencies {
  implementation("one.util:streamex:0.8.1")
}

tasks {
  patchPluginXml {
    sinceBuild.set("223")
    untilBuild.set("231.*")
  }
}
