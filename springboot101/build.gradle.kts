plugins {
    java
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
}
val springCloudVersion by extra("2024.0.1")

group = "org.hieunh1801"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    val camelVersion = "4.8.5"
    val javaFakerVersion = "1.0.2"

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework.retry:spring-retry")
    implementation("com.google.guava:guava:33.4.8-jre")


//    implementation("io.github.resilience4j:resilience4j-spring-boot3")
//    implementation("io.github.resilience4j:resilience4j-feign")
//    implementation("io.github.resilience4j:resilience4j-ratelimiter")

    // camel
    implementation("org.apache.camel.springboot:camel-spring-boot-starter:${camelVersion}")
    implementation("org.apache.camel.springboot:camel-http-starter:${camelVersion}")
    implementation("org.apache.camel:camel-jackson:${camelVersion}")
    implementation("org.apache.camel:camel-csv:${camelVersion}")
    implementation("org.apache.camel:camel-ftp:${camelVersion}")
    implementation("org.apache.camel:camel-jsonpath:${camelVersion}")
    implementation("org.apache.camel:camel-disruptor:${camelVersion}")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // other
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")




}
dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
