plugins {
    java
    id("org.springframework.boot") version "3.3.1"
    id("io.spring.dependency-management") version "1.1.5"
    id("io.qameta.allure") version "2.11.2"
}

group = "org.alfabank"
version = "0.0.1-SNAPSHOT"
val aspectJVersion = "1.9.22.1"
val allureVersion = "2.25.0"

val agent: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = true
}

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
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("io.rest-assured:rest-assured:5.4.0")
    implementation("org.junit.jupiter:junit-jupiter-api:5.11.0-M2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.1")
    implementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    implementation("io.qameta.allure:allure-junit5")
    agent("org.aspectj:aspectjweaver:${aspectJVersion}")
}

tasks.withType<Test> {
    jvmArgs = listOf(
        "-javaagent:${agent.singleFile}"
    )
    useJUnitPlatform()
    ignoreFailures = true
    finalizedBy("allureServe")
}

