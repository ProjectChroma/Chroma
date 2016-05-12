# Chroma
A 2D puzzle platform built on colors and shapes

## Installation for Development

1. Clone this repository (`git clone https://github.com/ProjectChroma/Chroma.git`)
1. Download [Slick2D](http://slick.ninjacave.com/) and extract its contents
1. Open Eclipse
1. Set your workspace to the folder you cloned into (i.e. `C:\Users\You\workspace`)
1. Create a new project called "Chroma" (same name as the folder); at the bottom, there should be a notification that the project will be configured automatically
1. Create a folder in the project called `lib`
1. Copy `slick.jar`, `lwjgl.jar`, `ibxm.jar`, and `natives-windows.jar` from where you extracted Slick2D into `lib`
1. Create a folder called `natives-windows` in `lib`
1. Move `natives-windows.jar` into `natives-windows`
1. Open a command line and `cd` into the `natives-windows` folder
1. Run `jar -xf natives-windows.jar` to extract the jar's contents' into the folder
1. Go back to Eclipse, and refresh the project (right-click -> refresh or click -> F5)
1. Right-click the project and open "Build Path" > "Configure Build Path..."
1. Click "Add JARs..." and select `slick.jar`, `lwjgl.jar`, and `ibxm.jar`
1. Expand `lwjgl.jar` in the center area and double-click "Native library location: (None)"
1. Locate the `natives-windows` folder
1. Run `io.github.projectchroma.chroma.Chroma` to run the game
