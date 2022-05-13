import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktor_version = "2.0.1"



plugins {
	id("org.springframework.boot") version "2.6.7"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
//	kotlin("plugin.serialization") version "1.6.10"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
}

group = "com.tepe"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-web-services")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

//	implementation("io.ktor:ktor-client-core-jvm:$ktor_version")
//	implementation("io.ktor:ktor-client-cio:$ktor_version")
//	implementation("io.ktor:ktor-client-serialization:$ktor_version")
//	implementation("io.ktor:ktor-client-logging:$ktor_version")
	implementation("com.google.code.gson:gson:2.9.0")

//	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
