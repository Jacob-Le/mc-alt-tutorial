# Minecraft Alternative Tutorial

## Minecraft Setup
### Requirements
1. A copy of Minecraft version 1.19 (needs to be purchased if not already owned!): https://www.minecraft.net/en-us/download
2. Minecraft Forge Mod Loader 1.19: https://files.minecraftforge.net/net/minecraftforge/forge/index_1.19.html

### Steps
1. Download the "installer" for Forge (link above)
2. Locate the mods folder and put `altttutorial-0.0.1-1.19.jar` inside:
    * Windows
        1. Open file explorer
        2. Navigate to `<DRIVE>\Users\<USER>\AppData\Roaming\.minecraft`
        3. Create a folder called `mods` if it doesn't exist
        4. Copy + paste `altttutorial-0.0.1-1.19.jar` into the `mods` folder
    * Mac
        1. Open Finder, go to Library
        2. Go to Application Support > Minecraft
        3. Create a folder called `mods` if it doesn't exist
        4. Copy + paste `altttutorial-0.0.1-1.19.jar` into the `mods` folder
2. Start the Minecraft Launcher
3. Change the selection of "Latest Release" to "forge"

## IDE Setup
### Requirements
1. An IDE - Intellij IDEA, Eclipse, or Visual Studio Code should work fine
2. Java JDK 17
    * If you don't have it, you can find the distribution that matches your OS here:
        * Windows: https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.4+8/OpenJDK17U-jdk_x64_windows_hotspot_17.0.4_8.msi
            * Run the .msi file after the download is finished and follow the installation instructions.
        * Mac: https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.3+7/OpenJDK17U-jdk_x64_mac_hotspot_17.0.3_7.pkg 
            * Open the .pkg file after the download is finished and follow the installation instructions.
        * Linux: https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.4%2B8/OpenJDK17U-jdk_x64_linux_hotspot_17.0.4_8.tar.gz
        * Otherwise, you may be able to find your distribution somewhere here: https://github.com/adoptium/temurin17-binaries/releases
        * NOTE: Visual Studio Code has a handy Java extension pack by Microsoft that makes the setup for JDK much easier (https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)

### Intellij IDEA Setup
1. Extract the contents of the zipped file to a location of your choice
2. Open IDEA, select Import > select the extracted folder
3. Select the build.gradle file, IDEA should begin the process of importing the gradle project
4. Run the following command: `gradlew genIntellijRuns` (`./gradlew genIntellijRuns` if you are on Mac/Linux)
5. Refresh the Gradle Project in IDEA if required

### Eclipse Setup
1. Extract the contents of the zipped file to a location of your choice
2. Navigate through a command line interface (terminal, CMD, etc) into the extracted folder
3. Run the following command: `gradlew genEclipseRuns` (`./gradlew genEclipseRuns` if you are on Mac/Linux)
4. Open Eclipse, select Import > Existing Gradle Project > Select Folder 
    * Alternatively, you can run `gradlew eclipse` to generate the project

### Visual Studio Code Setup
1. Extract the contents of the zipped file to a location of your choice
2. Open VSCode, select File > Open Folder > select the extracted folder
3. Recommended: Install the "Gradle for Java" extension by Microsoft (https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-gradle)
4. Run the `init` gradle task
 
## Evaluation Instructions
1. Read all instructions carefully
2. Run the game
    * Minecraft distribution: Press play in the launcher
    * Eclipse / IDEA: run button
    * Visual Studio Code: execute the forgegradle runs/runClient task
3. Wait for the main menu to load, select Singleplayer
    * Minecraft distribution - world setup:
        1. Select peaceful difficulty
        2. Set game mode to "Survival"
        3. Select "More World Options"
        4. Copy + Paste in the following seed: `7221083892190558231`
        5. Select Create New World
4. Your task within the game is to obtain a Copper Ingot.  Additional instructions will come from within the game and from exploration.
5. Once the copper ingot has been collected, exit out of the game.
6. Find the `results.zip` file
    * If using an IDE, it is located here: `mc-alt-tutorial/run/results.zip`
    * If using Minecraft launcher on Windows, it is located here: `C:\Users\<USER>\AppData\Roaming\.minecraft\results.zip` 
7. Answer the questions in the linked survey and attach the `results.zip` file.
8. Direct all questions towards the Piazza post at: https://piazza.com/class/l2zfw553n634xg?cid=246

## Minecraft Controls
Information about the controls will be provided in-game, but this will be here for reference in case you forget.

* __Attack__: Left Mouse Button
* __Break Block__: Hold Left Mouse Button
* __Move Forward__: W
* __Move Backward__: S
* __Move Left__: A 
* __Move Right__: D
* __Jump__: Spacebar 
* __Use / Place Block__: Right Mouse Button 
* __Open Inventory__: E
* __Open Menu__: ESC 
* __Look__: Move the mouse