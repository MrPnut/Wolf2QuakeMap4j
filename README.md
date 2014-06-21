Wolf2QuakeMap4j
===============

This is a _WORK_IN_PROGRESS_ Wolfenstein 3D map converter for Quake written in Java

+ What works:
  Map geometry conversion
  
- Todo:
  - Either extract textures from the Wolfenstein binaries, or provide a way to map tile numbers to textures
  - A ceiling and floor layer needs to be generated (simple to do)
  - Entities.. Try to match tile numbers up with quake entities (think info_player_start)
  - A Quake brush is created for every tile (4096).  This number can decrease by looking at neighboring tiles and resizing the
    brush accordingly
  - More options for things like resizing the maps, etc
  - Testing on different versions of Wolfenstein 3D (currently tested on the CARMACIZED version (huffman compressed game data files)
  
- Todo perhaps:
  - A JavaFX UI to pick and choose quake textures to map to tiles

To Compile / Run

This project is built with Gradle and JDK 1.7.  It should be compatible with JDK 1.5.

To compile:
Simply gradlew build

To run:
You can either assembly into an application with gradlew installApp -OR- you can run it with gradlew run -Pargs="--directory=..."

Command line options:

usage: wmc4j
    --convert-all             convert all maps to the location specified
                              by output-directory
    --directory <DIRECTORY>   sets the directory to look for wolfenstein
                              files in (defaults to ./)
    --display                 displays the map file information loaded
                              from the gamemaps and maphead files
    --gamemaps <GAMEMAPS>     sets the ABSOLUTE location to the gamemaps
                              file (overrides --directory) (defaults to
                              ./gamemaps.wl6)
    --help                    print this message
    --maphead <MAPHEAD>       sets the ABSOLUTE location of the maphead
                              file (overrides --directory) (defaults to
                              ./maphead.wl6
    --output-directory        the directory that maps will be output to
                              (default to ./)
    --wadname                 the file location of a wad file for .map's
                              (defaults to base.wad
