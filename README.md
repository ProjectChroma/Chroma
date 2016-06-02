# Chroma
A 2D puzzle platformer built on colors and shapes

## Installation for Development

1. Clone this repository (`git clone https://github.com/ProjectChroma/Chroma.git`)
1. Install [git-lfs](https://git-lfs.github.com/) using the directions at the bottom-left
1. Download [Slick2D](http://slick.ninjacave.com/) and extract its contents
1. Open Eclipse
1. Set your workspace to the folder you cloned into (i.e. `C:\Users\You\workspace`)
1. Create a new project called "Chroma" (same name as the folder); at the bottom, there should be a notification that the project will be configured automatically
1. Create a folder in the project called `lib`
1. Download [`gson-2.6.2.jar`](http://repo1.maven.org/maven2/com/google/code/gson/gson/2.6.2/) into `lib`
1. Copy `slick.jar`, `lwjgl.jar`, `ibxm.jar`, and `natives-windows.jar` from where you extracted Slick2D into `lib`
1. Create a folder called `natives-windows` in `lib`
1. Move `natives-windows.jar` into `natives-windows`
1. Open a command line and `cd` into the `natives-windows` folder
1. Run `jar -xf natives-windows.jar` to extract the jar's contents' into the folder
1. Go back to Eclipse, and refresh the project (right-click -> refresh or click -> F5)
1. Right-click the project and open "Build Path" > "Configure Build Path..."
1. Click "Add JARs..." and select `gson-2.6.2.jar`, `slick.jar`, `lwjgl.jar`, and `ibxm.jar`
1. Expand `lwjgl.jar` in the center area and double-click "Native library location: (None)"
1. Locate and select the `natives-windows` folder
1. Run `io.github.projectchroma.chroma.Chroma` to run the game

## Exporting Game
The launcher does all of the hard work for running the game, so the export process is very simple.

1. Right-click the project in Eclipse
1. Click Export...
1. Expand "Java" and select "Runnable JAR File", then click "Next"
1. Select the run configuration for the project (should point to `io.github.projectchroma.chroma.Chroma` as the main class)
1. Type `Chroma\Chroma.jar` for the jar-file path
1. Select "Package required libraries into generated JAR"
1. Click "Finish"

Your changes should now be made to `Chroma.jar` in the project root, which will be detected by the launcher.
