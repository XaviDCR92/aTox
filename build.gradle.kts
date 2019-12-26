plugins {
    id("com.github.ben-manes.versions") version "0.27.0"
    id(BuildPlugin.download) version BuildPlugin.downloadVersion
}

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(BuildPlugin.gradle)
        classpath(BuildPlugin.kotlinGradle)

        classpath("com.github.ben-manes:gradle-versions-plugin:0.27.0")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean").configure {
    delete("build")
}
