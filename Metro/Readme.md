An Java application to help navigate metro systems and find the shortest path useing Dijkstra's and depth first search algorithms.

A Json file of the metro system is loaded through a command line argument and the user can the select the start and end stations and the program will calculate the shortest routes.

Example output
--------------------------------------------------------------------------------------------------------------------
````
> java metro.Main london_underground.json
> /route "Piccadilly line" Ickenham "Central line" "North Acton"

Ickenham
Ruislip
Ruislip Manor
Eastcote
Rayners Lane
South Harrow
Sudbury Hill
Sudbury Town
Alperton
Park Royal
North Ealing
Ealing Common
Transition to District line
Ealing Common
Ealing Broadway
Transition to Central line
Ealing Broadway
West Acton
North Acton
````
