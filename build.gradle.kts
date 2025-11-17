plugins {
    java
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
}

group = "ru.comfort.soft"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_23

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
    implementation("org.apache.poi:poi-ooxml:5.4.0")

//    implementation("javax.annotation:javax.annotation-api:1.3.2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.jar {
    enabled = false
}

tasks.bootJar {
    archiveFileName.set("n-min-finder.jar")
    manifest {
        attributes("Start-Class" to "ru.comfort.soft.NMinApplication")
    }
}

tasks.withType<JavaCompile> {
    options.compilerArgs.addAll(listOf(
        "--enable-preview",
        "-parameters",
        "-Xlint:preview"
    ))
}

tasks.withType<Test> {
    jvmArgs("--enable-preview")
}

tasks.bootRun {
    jvmArgs("--enable-preview")
}