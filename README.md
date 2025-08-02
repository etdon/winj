<p align="center">  
    <img src="https://i.imgur.com/8rpcn5R.png">    
</p>

## Introduction

<b>winj</b> is your go-to Java library for seamless Windows API access, leveraging the recently added FFM API. With zero third-party runtime dependencies, it offers an easy-to-use interface that simplifies native functionality integration, allowing developers to harness the full power of Windows effortlessly. Whether you’re managing system resources or enhancing your application’s features, winj makes Windows development in Java a breeze!  

## Getting Started

> [!NOTE]    
> 👷 winj is in early development and currently **not** feature complete.

> [!IMPORTANT]
> Requirements:
> - Java 22<p>
> ℹ️ *The reason why Java 22 (non-LTS) has been prioritized over Java 21 (LTS) is the finalization of the FFM API.*

🪶 Maven:
```xml
<repository>
    <id>etdon-repo</id>
    <url>https://repo.etdon.com/repository/maven-releases/</url>
</repository>
```

```xml

<dependencies>
    <dependency>
        <groupId>com.etdon.winj</groupId>
        <artifactId>core</artifactId>
        <version>1.0.0</version>
    </dependency>
    <dependency>
        <groupId>com.etdon.winj</groupId>
        <artifactId>facade</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

🐘 Gradle:
```groovy
maven {         
    url = uri("https://repo.etdon.com/repository/maven-releases/")
}
```

```groovy
dependencies {
    implementation 'com.etdon.winj:core:1.0.0'
    implementation 'com.etdon.winj:facade:1.0.0'
}
```

## Building
The build management tool used for this project is [Apache Maven](https://maven.apache.org/). Executing the following command will install the compiled artifact into your local repository if no critical issues occur during any of the lifecycle phases.
```
mvn clean install
```
