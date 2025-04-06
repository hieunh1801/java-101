plugins {
    java
    id("org.springframework.boot") version "3.3.10"
    id("io.spring.dependency-management") version "1.1.7"

}

group = "org.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}


repositories {
    mavenCentral()
    flatDir {
        dirs("libs")
    }
}

dependencies {
    val camelVersion = "4.8.5"
    val javaFakerVersion = "1.0.2"
    implementation("org.apache.camel.springboot:camel-spring-boot-starter:${camelVersion}")
    implementation("org.apache.camel.springboot:camel-http-starter:${camelVersion}")
    implementation("org.apache.camel:camel-jackson:${camelVersion}")
    implementation("org.apache.camel:camel-csv:${camelVersion}")
    implementation("org.apache.camel:camel-ftp:${camelVersion}")
    implementation("org.apache.camel:camel-jsonpath:${camelVersion}")
//    implementation("com.github.javafaker:javafaker:1.0.2")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jeasy:easy-random-core:5.0.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

}

tasks.withType<Test> {
    useJUnitPlatform()
}
