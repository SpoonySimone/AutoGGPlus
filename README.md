# AutoGG+

AutoGG+ is a mod designed for Hypixel to automatically say a message when a game finishes.

# Why AutoGG+?

Compared to other exisiting AutoGG mods, AutoGG+ has some exclusive features, such as:
- **Message randomization**: Tired of Hypixel constantly blocking your message for spam? AutoGG+ automatically rotates the capitalization of your message, so it never gets blocked.
- **Customizable messages**: You can set your own custom messages, so you can say whatever you want.
- **Support for the latest Hypixel games**: AutoGG+ supports most of Hypixel games (with more to come!).
- **Nice and easy to use configuration**: AutoGG+ uses [OneConfig](https://polyfrost.org/projects/oneconfig/) as its configuration manager, allowing you to easily change the mod's options in a beautiful interface with just a click of a button (R-SHIFT by default).

# Mod showcase
**Configuration menu**

<img src=https://raw.githubusercontent.com/SpoonySimone/AutoGGPlus/refs/heads/main/images/files/config_screen.png width="75%" height="75%"/>

**Mod in action**

<img src=https://raw.githubusercontent.com/SpoonySimone/AutoGGPlus/refs/heads/main/images/files/chat_screen.png width="75%" height="75%"/>

# Commands
- `/autoggplus` - Opens the configuration menu.
- `/gg` - Send a GG message in the chat with your configured settings.

# Credits
Credits to [Sk1er](https://www.youtube.com/@Sk1erDev) for a portion of the triggers list

Thanks to [Lucide](https://lucide.dev/) for providing the message icon

[![OneConfig Badge](https://polyfrost.org/media/branding/badges/badge_3.svg)](https://polyfrost.org/projects/oneconfig/)

# Download
You can download the mod [here](https://modrinth.com/project/autoggplus).

# Development
> [!WARNING]
> This section is meant for developers who want to contribute to the mod or build it locally.
> 
> If you just want to use the mod, you can ignore this and [download the mod](https://github.com/SpoonySimone/AutoGGPlus?tab=readme-ov-file#download) instead.

To build the mod locally, you have to:
1. Clone the repository
   ```
   git clone https://github.com/SpoonySimone/AutoGGPlus
   ``` 
2. Navigate to the cloned repository
   ```
   cd AutoGGPlus
   ```
3. Build the mod using Gradle

    **Linux**
    ```
    ./gradlew build
    ```
    **Windows**
    ```
    gradlew.bat build
    ```
4. The built mod will be located in `/versions/1.8.9-forge/build/libs/`
