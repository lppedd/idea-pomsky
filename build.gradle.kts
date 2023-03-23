import org.jetbrains.grammarkit.tasks.GenerateLexerTask
import org.jetbrains.grammarkit.tasks.GenerateParserTask

plugins {
  id("java")
  id("org.jetbrains.intellij") version "1.13.2"
  id("org.jetbrains.grammarkit") version "2022.3.1"
}

group = "com.github.lppedd"
version = "0.0.3"

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

intellij {
  version.set("2022.3")
  type.set("IC")
  plugins.set(listOf("java" /* Only for code documentation */))
}

grammarKit {
  jflexRelease.set("1.7.0-1")
  grammarKitRelease.set("2022.3.1")
}

dependencies {
  implementation("one.util:streamex:0.8.1")
}

tasks {
  wrapper {
    distributionType = Wrapper.DistributionType.ALL
  }

  val generateLexer = task<GenerateLexerTask>("generatePomskyLexer") {
    sourceFile.set(file("src/main/java/com/github/lppedd/idea/pomsky/lang/lexer/pomsky.flex"))
    targetDir.set("src/main/gen/com/github/lppedd/idea/pomsky/lang/lexer")
    targetClass.set("PomskyFlexLexer")
    purgeOldFiles.set(true)
  }

  val generateParser = task<GenerateParserTask>("generatePomskyParser") {
    dependsOn(generateLexer)

    sourceFile.set(file("src/main/java/com/github/lppedd/idea/pomsky/lang/parser/pomsky.bnf"))
    targetRoot.set("src/main/gen")
    pathToParser.set("com/github/lppedd/idea/pomsky/lang/parser/PomskyGeneratedParser.java")
    pathToPsiRoot.set("psi")
    purgeOldFiles.set(true)
  }

  compileJava {
    dependsOn(generateParser)
  }

  patchPluginXml {
    sinceBuild.set("223")
    untilBuild.set("233.*")
  }

  runPluginVerifier {
    ideVersions.set(listOf(
        "IC-2022.3",
        "IC-2023.1",
    ))
  }
}
