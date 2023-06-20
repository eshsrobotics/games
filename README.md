# games
A repository for storing games that the El Segundo High School Robotics Club
programs together.

- [How to set up your computer](#how-to-set-up-your-computer)
  - [Installation](#installation)
    - [Windows installation](#windows-installation)
    - [Windows installation (via Chocolatey)](#windows-installation-via-chocolatey)
    - [MacOS installation](#macos-installation)
    - [Installing the Visual Studio Code plugins](#installing-the-Visual-Studio-Code-plugins)
  - [Cloning the repository](#cloning-the-repository)
- [libGDX Cheatsheet](#libgdx-cheatsheet)

## How to set up your computer

### Installation
#### What you need to install
1. ![icon](docs/vscode.png) **Visual Studio Code** — Integrated Development Environment (IDE)
    - It's not the best text editor in the world (_not even close_), but it
      does have a useful realtime collaboration plugin that allows all of us
      to work on the same code at the same time and see each other's edits.
2. **OpenJDK 17** — Java Development Kit (compiler and runtime)
    - We decided on 2023-06-01 that the first game would be written in Java,
      since that's  what the students are learning in AP Computer Science.
3. **Git** — Version control tool
    - Git allows us to easily document, share, search through, and merge
      changes even when our edits diverge.  Our club only uses the terminal
      version of Git.

#### Windows installation
1. If you have installed the Chocolatey package manager, you can skip to the
   next section.  Otherwise:
2. [Download VSCode](https://code.visualstudio.com) and install it.  Please
   pin it to your taskbar so it is easy to find.
    - FRC students who set up their computers as driver stations will already
      have a copy of VSCode with a name like *FRC VS Code 2023* or the like.
      If you have that, it will work just fine -- you don't need to install a
      separate copy of VSCode.
3. Install OpenJDK 17.

    Our preferred JDK is Adoptium (formerly AdoptOpenJDK)'s _Temurin_ JDK.
    You can install it [by hand](https://adoptium.net/download/) or you can
    use VSCode to do it:

    - Open VSCode.
    - Type `Ctrl + Shift + P` to activate the Command Palette.
    - Within the Palette, type "`install jdk`" and you should see a *Java:
       Install New JDK* entry or the like; activate it.
    - Click the giant Download button.

    Either way, make sure the `java` command is in your `PATH`, and that the
    JDK version is 17 or greater.  Both can be accomplished with a single
    shell command:
        ```shell
        java --version
        ```
4. Install Git.

   The most common Windows build of Git is [Git for
   Windows](https://gitforwindows.org/).  In addition to the `git.exe`
   terminal application itself, it includes:

   - A Git-specific shell called "Git Bash" (which we don't use),
   - A graphical interface called "Git GUI" (which we don't use), and
   - A credential manager that allows you to log into GitHub using your web
     browser whenever you execute `git push` (we do use this.)

   You can install this program separately, but it's easier to install it by
   installing ![icon](docs/cmder.png) **[Cmder](https://cmder.app/)**, a multi-tabbed terminal for
   Windows that has Git for Windows built into it.  You'll want the **full**
   version; once it is installed, pin it to your taskbar and use it as your
   preferred terminal.

#### Windows installation (via Chocolatey)
*Chocolatey is not required for this project.  But for those students who
don't the Chocolatey package manager and have the permission to
[install](https://chocolatey.org/install) it, we highly recommend that they
do.  Chocolatey makes installing and upgrading software much, much easier.*

If you have Chocolatey, open a terminal as an administrator and run the
following commands:

1. To install Visual Studio Code: `choco install vscode`
    - Please pin it to your taskbar to make it easy to open in the future.
2. To install OpenJDK 17: `choco install temurin17`
3. To install Git for Windows: `choco install git`
#### MacOS installation
These instructions rely on the [Homebrew](https://brew.sh) package manager.

1. Install Visual Studio Code: `brew install visual-studio-code`
    - Please pin it to your Dock to make it easy to open in the future.
2. Install OpenJDK 17: `brew install temurin17`
3. Install Git: `brew install git`

### Installing the Visual Studio Code plugins
1. Open VSCode.
2. Click on the ![icon](docs/extensions.png) *Extensions* button on the left sidebar.
3. Search for and install the following extensions:
    1. ![icon](docs/extension-pack-for-java.png) _Extension Pack for Java_ -
       allows you to build and debug Java code
    2. ![icon](docs/gitlens.png) _GitLens_ - shows commit history per-line
    3. ![icon](docs/live-share.png) _Live Share_ - allows collaborators to
       simultaneously edit the same buffers
    4. ![icon](docs/rewrap.png) _Rewrap_ - Allows you to word-wrap long lines
       using `Alt + Q` (`⌥Option + Q` on MacOS)
    5. _Base16 Rebecca_ - Uche likes purple color themes

### Cloning the repository
1. Create an account on GitHub if you haven't yet.

    We need the GitHub account for two things: allowing you to commit into
    _this_ repository and for getting that collaboration plugin working.

    - Just to emphasize: *Git* and *GitHub* are **not** the same thing.  Git
      is the version control software you will use with this project; GitHub
      is just a website where we happen to store our source code (and which
      integrates well with Visual Studio Code.)
    - Once you have the account, let Uche know about it so he can add it to
      the `eshsrobotics` programming group.

2. Choose a folder to store your source code (if you haven't yet)

   Most students store their code in a folder which is easy to find, like
   Desktop, Documents, or Downloads.  Create a subfolder underneath it to
   store your source code projects.  Make a note of where it is, since you
   will need to be able to navigate to it from a terminal.

   There's plenty of help online for learning [basic terminal
   commands](https://developer.mozilla.org/en-US/docs/Learn/Tools_and_testing/Understanding_client-side_tools/Command_line#basic_built-in_terminal_commands).
   The most important ones that we use are:

   - **`cd path/to/your/folder`** - Changes to a different folder
       - **`cd ..`** - Goes up to the parent folder
   - **`pushd path/to/your/folder`** - Changes to a different folder *while
     remembering where you were before*
       - **`popd`** - Goes back to where you were before you executed `pushd`
   - **`dir`** - Lists the contents of a folder
       - If you are using the Bash shell, try **`ls -Flarth`**

3. `cd` into that folder from the terminal.
4. Execute the clone command:
    ```sh
    git clone https://github.com/eshsrobotics/games
    ```
5. Finally, open the `games` folder from within Visual Studio Code.  You're done!

## libGDX Cheatsheet

The java games in this repository use [libGDX](https://libgdx.com), a Java game
library that combines several popular game-related frameworks.  It uses Gradle
for building and running, so there are a number of important Gradle targets:

1. Compiling and testing:
    - `./gradlew build`: Builds the application for all output environments.
        - If you see a project build fail with the following error:
                <pre>
                  Error: Could not find or load main class org.gradle.wrapper.GradleWrapperMain
                  Caused by: java.lang.ClassNotFoundException: org.gradle.wrapper.GradleWrapperMain
                </pre>
          Then you are the victim of **bad defaults** and a **bad error
          message**.  A more sensible error message would have said:
                <pre>
                  Error: could not find gradle-wrapper.jar
                  Did you accidentally add it to your .gitignore?
                </pre>

            - As it turns out, the `gdx-setup.jar` [project generation
              wizard](https://libgdx.com/wiki/start/project-generation) creates
              [broken
              projects](https://support.snyk.io/hc/en-us/articles/360007745957-Snyk-test-Could-not-find-or-load-main-class-org-gradle-wrapper-GradleWrapperMain)
              out of the box, since its `.gitignore` file excludes the directory
              that it puts `gradle-wrapper.jar` in.  Without this JAR file,
              `./gradlew` cannot operate.

              The person who _runs_ the wizard will have a local copy of
              `gradle-wrapper.jar`, but since it is automatically gitignored,
              it will not be pushed upstream when they push the rest of their
              code.  Everyone who pulls it will see the error message shown
              above!

            - To fix the problem, do the following:

                1. Change `.gitignore` to add an exception for
                   `gradle-wrapper.jar`.

                   ``` diff

                   @@ -93,6 +93,7 @@ nb-configuration.xml

                    /local.properties
                    .gradle/
                   +!gradle/wrapper/gradle-wrapper.jar
                    gradle-app.setting
                    /build/
                    /android/build/

                   ```
                1. `git add ./gradle/wrapper/gradle-wrapper.jar` so that other
                   people can see it.

1. Running:
    - `./gradlew desktop:run`: Runs the **desktop** version of the application
    - `./gradlew html:superDev`: Runs the web application in _Super Dev mode_,
      which allows browser debugging and on-the-fly recompilation.

      Visit **http://localhost:8080/index.html** to play your game.
    - `./gradlew ios:launchIPhoneSimulator`: Runs the **iPhone** version of the
      app.  This requires XCode, and so it will only succeed on Macs.
    - `./gradlew android:installDebug android:run`: Runs the **Android** version
      of the app.  This requires an installed Android SDK on your system; once
      you install it, you will need to set the `ANDROID_HOME` environment
      variable to point to that folder.

You can add `--debug` at the end of any of these Gradle targets to get more
information if something does wrong.