<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>${groupId}</groupId>
    <artifactId>${artifactId}</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>${packagingType}</packaging>

    <properties>
        <!-- 编译时的编码 -->
        <maven.compiler.encoding>UTF-8</maven.compiler.encoding>
        <maven.test.skip>true</maven.test.skip>
    </properties>

    <dependencies>
        <!-- https://mvnrepository.com/artifact/junit/junit -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.8</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.4</version>
        </dependency>
        <dependency>
            <groupId>commons-collections</groupId>
            <artifactId>commons-collections</artifactId>
            <version>3.2.2</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.google.guava/guava -->
        <dependency>
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
            <version>19.0</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-api -->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.21</version>
        </dependency>
    </dependencies>


    <build>
        <sourceDirectory>${r"${basedir}"}/src/main/java</sourceDirectory>
        <outputDirectory>${r"${basedir}"}/target/${r"${project.artifactId}"}/classes</outputDirectory>
        <resources>
            <resource>
                <directory>${r"${basedir}"}/src/main/resources</directory>
                <excludes>
                    <exclude>**/*.java</exclude>
                </excludes>
            </resource>
        </resources>
        <testSourceDirectory>${r"${basedir}"}/src/test/java</testSourceDirectory>
        <testOutputDirectory>${r"${basedir}"}/target/${r"${project.artifactId}"}/test-classes</testOutputDirectory>
        <testResources>
            <testResource>
                <directory>${r"${basedir}"}/src/main/resources</directory>
            </testResource>
        </testResources>
    </build>

</project>