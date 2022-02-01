import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("io.github.lognet.grpc-spring-boot") version "4.5.7"
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
}

group = "com.creditas.performance"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.webjars:stomp-websocket:2.3.4")
    implementation("org.webjars:webjars-locator-core")
    implementation("org.webjars:sockjs-client:1.5.1")
    implementation("org.webjars:jquery:3.6.0")
    implementation("org.webjars:bootstrap:5.1.1")
    implementation("org.webjars.npm:grpc-web:0.4.0")

    // rest
    implementation("org.apache.httpcomponents:httpclient:4.5.13")

    // web- socket
    implementation("org.springframework:spring-websocket")
    implementation("org.springframework:spring-messaging")

    // grpc
    implementation("io.github.lognet:grpc-spring-boot-starter:4.5.9")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "16"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

grpcSpringBoot {
    grpcSpringBootStarterVersion.set("")
}
