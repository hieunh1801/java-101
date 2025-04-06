plugins {
    java
    id("io.quarkus")
}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:quarkus-camel-bom:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-rest")
    implementation("org.apache.camel.quarkus:camel-quarkus-core")
    implementation("io.quarkus:quarkus-rest-jsonb")
    implementation("io.quarkus:quarkus-rest-jackson")
    implementation("io.quarkus:quarkus-arc")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
}

group = "org.example"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}


tasks.register("buildUberJar") {
    group = "build"
    description = "Build Uber Jar"
    doFirst {
        project.extensions.extraProperties["quarkus.package.jar.enabled"] = true
        project.extensions.extraProperties["quarkus.package.jar.type"] = "uber-jar"
    }
    dependsOn("build") // quarkus101-1.0-SNAPSHORT-runner.jar
}

tasks.register("buildJar") {
    group = "build"
    description = "Build Jar"
    doFirst {
        project.extensions.extraProperties["quarkus.package.type"] = "jar"
        project.extensions.extraProperties["quarkus.package.jar.enabled"] = true
    }
    dependsOn("build")
}

tasks.register("buildNativeWithDocker") {
    group = "build"
    description = "Build Native"
    doFirst {
        project.extensions.extraProperties["quarkus.native.container-build"] = true
        project.extensions.extraProperties["quarkus.package.type"] = "native"
    }
    dependsOn("build")
}