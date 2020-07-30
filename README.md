NOTE this project has been discontinued, this is due to poor planning on my part as I rushed into the project without properly researching before development started. About 90% of the code would have to be deleted and completely remade using the improved systems. So instead of continuing this project, I am going to restart with a better plan for development under the repository, BetterEngine. This will be linked here once it is created. 
Main improvements to make for betterEngine:
* The general structure of the project will be improved upon, as a lot of code is squashed together instead of making a more robust system
* Currently the program makes too great use of Singletons, whereas a project of this size would have much better expandibility if an event based structure was employed
* Within the editor, a lot of UI code is reused and one of the bugs of the program cannot be fixed without a complete rebuild of the way the mouse listeners system works within the program. 
* An objects location is currently based on its bottom-left corner x and y position. This means that the entire system breaks as soon as rotation is involved. As when an object is rotated it is rotated about the base x and y coords instead of the centre of the object making it more complicated. Moving the position of the object to the centre of the object will also improve the collision system
* The x and y coord of an object is based on how many pixels of my specific monitor that object takes up. This means that the pixel density of the screen would effect the size of objects. This can be improved upon by converting to a metric system. This conversion would also allow for the use of real world constants for physics calculations. 

# Engine2D

A project to create a 2D Game Engine. This project includes a game creation screen along with a level editor, to allow you to create 2D games without much of a need for code.
While making use of this project, please keep to the [code of conduct](https://github.com/booksaw/Engine2D/blob/master/CODE_OF_CONDUCT.md)

## Installation

Currently, to install the program, download the git repo and add it to an empty [eclipse](https://eclipse.org/downloads) project.
NOTE: There will be an improved installation method in the future. 

## Usage

For details of usage of this project, please view the [WIKI](https://github.com/booksaw/Engine2D/wiki) 

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.
For more details about contribution, view [Contribution.md](https://github.com/booksaw/Engine2D/blob/master/CONTRIBUTING.md)
Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
