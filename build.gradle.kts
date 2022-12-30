plugins {
  id("java")
  id("org.jetbrains.intellij") version "1.10.1"
  id("io.freefair.lombok") version "6.6.1"
}

group = "com.github.lppedd"
version = "0.0.1"

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

// See https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
  version.set("2022.1.4")
  type.set("IC")
  plugins.set(listOf("java" /* Only for code documentation */))
}

tasks {
  withType<JavaCompile> {
    sourceCompatibility = "11"
    targetCompatibility = "11"
  }

  patchPluginXml {
    sinceBuild.set("221")
    untilBuild.set("231.*")
  }
}
