Wolf2QuakeMap4j
===============

This is a _WORK_IN_PROGRESS_ Wolfenstein 3D map converter for Quake written in Java

+ What works:
  Map geometry conversion
  Basic tiled texture mappings via .properties files
  
- Todo:
  - A ceiling and floor layer needs to be generated (simple to do)
  - Entities.. Try to match tile numbers up with quake entities (think info_player_start)
  - A Quake brush is created for every tile (4096).  This number can decrease by looking at neighboring tiles and resizing the
    brush accordingly
  - More options for things like resizing the maps, etc
  - Testing on different versions of Wolfenstein 3D (currently tested on the CARMACIZED version (huffman compressed game data files)
  
- Todo perhaps:
  - A JavaFX UI to pick and choose quake textures to map to tiles
  - Extract Wolfenstein textures from the binaries

##To Compile / Run

This project is built with Gradle and JDK 1.7.

###To compile:

```
gradlew build
```

###To run:

```
gradlew installApp (to run it, run the batch file in build/install)
```

-OR-

```
gradlew run -Pargs="--directory=..."
```

### Texture mapping:

Basic texture mapping has been added.  With that said, you can map a Wolfenstein 3D tile number to a Quake texture name.
This is done via a .properties file.  The file name defaults to texturemapping.properties.

A simple way to get the texture numbers used (and a description of them) is to use Havoc's Wolf3D Editor (http://hwolf3d.dugtrio17.com/index.php?section=hwe)

The format of the .properties file is as follows:

```

# 1 = grey brick 1
1=city2_6
# 8 = blue brick 1
8=city2_5

# 9 = blue brick 2
9=city2_5

# 12 = wood
12=wizwood1_5

```

###Command line options:

```
usage: wmc4j
    --convert-all                       convert all maps to the location
                                        specified by output-directory
    --directory <DIRECTORY>             sets the directory to look for
                                        wolfenstein files in (defaults to ./)
    --display                           displays the map file information loaded
                                        from the gamemaps and maphead files
    --gamemaps <GAMEMAPS>               sets the ABSOLUTE location to the
                                        gamemaps file (overrides --directory)
                                        (defaults to ./gamemaps.wl6)
    --help                              print this message
    --maphead <MAPHEAD>                 sets the ABSOLUTE location of the
                                        maphead file (overrides --directory)
                                        (defaults to ./maphead.wl6
    --output-directory <directory>      the directory that maps will be output
                                        to (default to ./)
    --texture-properties <properties>   the location to a texture mapping
                                        properties file
    --wadname <wad>                     the file location of a wad file for
                                        .map's (defaults to base.wad)
```