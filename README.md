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
