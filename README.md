<p align="center">  
    <img src="https://i.imgur.com/8rpcn5R.png">    
</p>

<div align="center">

![Windows*](https://img.shields.io/badge/Windows*-white?style=for-the-badge&logo=github&label=Platform&color=%230173b3)
![x86-64*](https://img.shields.io/badge/x86–64*-white?style=for-the-badge&logo=mingww64&label=Architecture&color=%233fb911)
![Java](https://img.shields.io/badge/Java-white?style=for-the-badge&logo=github&label=Language&color=%23b07219)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-white?style=for-the-badge&logo=apache-maven&label=Building&color=%23C71A36)
![Apache 2.0](https://img.shields.io/badge/Apache%202.0-white?style=for-the-badge&logo=apache&label=License&color=%23D22128)

Badges marked with a `*` indicate limited compatability with alternatives, please check out the [documentation] for details.

</div>

## 🔰 Introduction

<b>winj</b> is your go-to Java library for seamless Windows API access, leveraging the FFM API. With zero third-party
runtime dependencies, it offers an easy-to-use interface that simplifies native functionality integration, allowing
developers to harness the full power of Windows effortlessly.

## Details

- Gives the user more control over the memory (de)allocation through extensive use of arenas.
- Bindings are provided for both higher level API functions and types as well as lower level ones which are often
  completely missing in similar libraries.
- Function calls and types are constructed cleanly with fluent builders.
- Facades (`facade` module) are built around more complex and constant flows like hook management as well
  as logically tied components.
- Lightweight with zero third-party runtime dependencies (less than 1 MB in compiled size).

## Warning

No production promises: Names and locations might change, things might get refactored changing the fundamental structure. Published
full release versions will remain untouched (unless critical fixes are needed) and changes will only be shipped with the next version.
Snapshot versions might change at any time.
  
## Examples

Check out the [documentation] for various examples paired with explanations.
```java
// Re-usable
final WindowsAPI windowsAPI = WindowsAPI.of(arena);
final NativeContext nativeContext = NativeContext.of(arena, this.nativeCaller);

final Window window = windowsAPI.getForegroundWindow();
final String windowText = window.getText(nativeContext);
```
```java
final MemorySegment windowHandle = (MemorySegment) this.nativeCaller.call(
    CreateWindowExW.builder()
        .className(className)
        .windowName(windowName)
        .moduleHandle(moduleHandle)
        .style(
            Flag.combine(
                WindowStyle.WS_SYSMENU,
                WindowStyle.WS_CAPTION,
                WindowStyle.WS_BORDER
            )
        )
        .build()
);

this.nativeCaller.call(
    ShowWindow.builder()
        .handle(windowHandle)
        .presentation(WindowPresentation.SW_SHOW)
        .build()
);
```

## Getting Started

> [!NOTE]    
> 👷 winj is in early development and currently **not** feature complete.

> [!IMPORTANT]
> Requirements:
> - Java 22<p>
> ℹ️ *Java 22 (non-LTS) has been prioritized over Java 21 (LTS) because of the finalization of the FFM API.*

🪶 Maven:

```xml
<repository>
    <id>etdon-repo</id>
    <url>https://repo.etdon.com/repository/maven-snapshots/</url>
</repository>
```

```xml
<dependencies>
    <dependency>
        <groupId>com.etdon.winj</groupId>
        <artifactId>core</artifactId>
        <version>1.0.1-SNAPSHOT</version>
    </dependency>
    <dependency>
        <groupId>com.etdon.winj</groupId>
        <artifactId>facade</artifactId>
        <version>1.0.1-SNAPSHOT</version>
    </dependency>
</dependencies>
```

🐘 Gradle:

```groovy
maven {
    url = uri("https://repo.etdon.com/repository/maven-snapshots/")
}
```

```groovy
dependencies {
    implementation 'com.etdon.winj:core:1.0.1-SNAPSHOT'
    implementation 'com.etdon.winj:facade:1.0.1-SNAPSHOT'
}
```

<details>
  <summary>Latest stable</summary>

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

</details>

### Required options:

🪶 Maven:

```xml
<manifestEntries>
    <Enable-Native-Access>ALL-UNNAMED</Enable-Native-Access>
</manifestEntries>
```

🐘 Gradle:

```groovy
jar {
    manifest {
        attributes(
            'Enable-Native-Access': 'ALL-UNNAMED'
        )
    }
}
```

🖥️ Command line:
```
--enable-native-access=ALL-UNNAMED
```

## 📦 Building

The build management tool used for this project is [Apache Maven][building]. Executing the following
command will install the compiled artifact into your local repository if no critical issues occur during any of the
lifecycle phases.

```
mvn clean install
```

## 🫴 Contributing
The contribution guidelines are a part of the `shared-guidelines` repository and can be found here: [Contributing][contributing]

## 📄 License
The project is licensed under the [Apache 2.0 License][license].
```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

[documentation]: https://docs.etdon.com/winj
[building]: https://maven.apache.org/
[contributing]: https://github.com/etdon/shared-guidelines/blob/main/CONTRIBUTING.md
[license]: https://github.com/etdon/winj/blob/master/LICENSE
