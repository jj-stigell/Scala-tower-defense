# The game map is formed under the MAP.
# Each row represents row on a grid style map that has dimensions m x n.
# Minimum recommended size is 5x5
# Symbols are:
# 0 = Area where towers are placed
# 1 = Enemy path
# 2 = Enemy start point, must be on the side of the map
# 3 = End of the enemy path, player must protect this area

# Enemies are added one enemy type per row under the ENEMY.
# 0 = small enemy
# 1 = big enemy
# "enemy type" [0 or 1] / "amount of enemies" [0 - 100]

# Towers are added one enemy type per row under the TOWER.
# 0 = small tower
# 1 = big tower
# "tower type" [0 or 1] / "amount of towers" [0 - 100]

# Towers locations are added under TOWERLOCATION. Each tower has one
# location coordinate x and y which are separated from others with semicolon ";".
# Amount of locations must match with the amount of specific towers and
# location coordinates should not go outside the map perimeter.
# First row of locations is for small towers.
# Second row of locations is for big towers.

# Set the healthpoints under the HEALTH.
# Must be positive integer.
# "Current health" / "Max. health"

# Set the money in the bank under the MONEY.
# Must be positive integer.

# Set the number of waves under the WAVES.
# Must be positive integer.
# "Current wave" / "Max. waves"

### EXAMPLE SAV-FILE ###

#MAP
0,2,0,0,0,0,0,0,0,0,0,0
0,1,1,0,0,1,1,1,1,1,1,0
0,0,1,1,0,1,0,0,0,0,1,0
0,0,0,1,1,1,0,1,1,1,1,0
0,0,0,0,0,0,0,1,0,0,0,0
0,1,1,1,0,0,0,1,1,1,0,0
0,1,0,1,0,0,0,0,0,1,0,0
0,1,0,1,0,1,1,1,1,1,0,0
0,1,0,1,1,1,0,0,0,0,0,0
0,3,0,0,0,0,0,0,0,0,0,0

#ENEMY
0/5
1/7

#TOWER
0/5
1/7

#TOWERLOCATION
100,250;200,350;700,200
189,578;200,455;340,125

#HEALTH
10/20

#MONEY
300

#WAVES
20/25