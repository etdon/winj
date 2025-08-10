<p align="center">  
    <img src="https://i.imgur.com/8rpcn5R.png">    
</p>

## Introduction

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

## Warnings

- The bindings are far from complete, you can however define new bindings in your own project without modifying the library directly.
- No production promises: Names and locations might change, things might get refactored changing the fundamental structure. Published
full release versions will remain untouched (unless critical fixes are needed) and changes will only be shipped with the next version.
Snapshot versions might change at any time.
  
## Examples

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
```java
Shellcode.builder()
    .mov(Register.R12, shellcodeHelper.getFunctionAddress(Library.KERNEL_32, "WinExec"))
    .sub(Register.RSP, (byte) 0x20)
    .call(Register.R12)
    .xor(Register.RCX, Register.RCX)
    .mov(Register.R13, shellcodeHelper.getFunctionAddress(Library.KERNEL_32, "ExitProcess"))
    .call(Register.R13)
    .build();
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

Required VM Options:

```xml
<manifestEntries>
    <Enable-Native-Access>ALL-UNNAMED</Enable-Native-Access>
</manifestEntries>
```

```
--enable-native-access=ALL-UNNAMED
```

## 📦 Building

The build management tool used for this project is [Apache Maven](https://maven.apache.org/). Executing the following
command will install the compiled artifact into your local repository if no critical issues occur during any of the
lifecycle phases.

```
mvn clean install
```
