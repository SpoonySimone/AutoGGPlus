# AutoGG
AutoGG+ is a Minecraft Forge mod developed by [SpoonySimone](https://github.com/SpoonySimone) that allows you to automatically say a selected phrase after a game has ended on supported servers.

This is a fork of [Sk1er's](https://github.com/Sk1erLLC) mod with new features and support.

## Support
I'll make a Discord server if this fork becomes famous (which I doubt)

## Developing
**Requirements:**
- JDK (Java Development Kit) 8
    * [AdoptOpenJDK](https://adoptopenjdk.net/)
    * [Other OpenJDK distributions](https://en.wikipedia.org/wiki/OpenJDK#OpenJDK_builds)
    * [Oracle JDK (proprietary)](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
    
- A Java IDE, I recommend using [IntelliJ IDEA](https://jetbrains.com/idea/)
 
## Building
The mod is automatically compiled on every push to GitHub Actions, but if you want to compile it yourself:
**Unix-based systems (GNU/Linux, OSX, etc):**
```bash
$ ./gradlew build
```

**Microsoft Windows:**
```batch
> gradlew.bat build
```

Artifacts will be placed in `versions/1.8.9/build/libs/`. 

## License
AutoGG+ is licensed under **GNU GPLv3**, see: [LICENSE](LICENSE).
