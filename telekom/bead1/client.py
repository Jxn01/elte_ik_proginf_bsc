# Script paraméterezése: python3 client.py cs.json

# A program kimenete:
# esemény sorszám. < esemény név >: < node1 > <-> < node2 > st:< szimuálciós idő > [- < sikeres/sikertelen >] 

# Pl.:
# 1. igény foglalás: A<->C st:1 – sikeres
# 2. igény foglalás: B<->C st:2 – sikeres
# 3. igény felszabadítás: A<->C st:5
# 4. igény foglalás: D<->C st:6 – sikeres
# 5. igény foglalás: A<->C st:7 – sikertelen

"""
cs1.json:
{
	"end-points": [ "A", "B", "C", "D" ],
	"switches": [ "S1", "S2", "S3", "S4" ],
	"links" : [
		{
			"points" : [ "A", "S1" ],
			"capacity" : 10.0
		},
    {
      "points" : [ "B", "S2" ],
      "capacity" : 10.0
    },
    {
      "points" : [ "D", "S4" ],
      "capacity" : 10.0
    },
    {
      "points" : [ "S1", "S4" ],
      "capacity" : 10.0
    },
    {
      "points" : [ "S1", "S3" ],
      "capacity" : 10.0
    },
    {
      "points" : [ "S2", "S3" ],
      "capacity" : 10.0
    },
    {
      "points" : [ "S4", "C" ],
      "capacity" : 10.0
    },
    {
      "points" : [ "S3", "C" ],
      "capacity" : 10.0
    }
	],
	"possible-circuits" : [
		["D", "S4", "C"],
		["C", "S4", "D"],
		["A", "S1", "S4", "C"],
		["A", "S1", "S3", "C"],
		["C", "S4", "S1", "A"],
		["C", "S3", "S1", "A"],
		["B", "S2", "S3", "C"],
		["C", "S3", "S2", "B"],
		["B", "S2", "S3", "S1", "A"],
		["A", "S1", "S3", "S2", "B"],
		["D", "S4", "S1", "S3", "S2", "B"],
		["B", "S2", "S3", "S1", "S4", "D"],
		["A", "S1", "S4", "D"],
		["D", "S4", "S1", "A"]
	],
	"simulation" : {
		"duration" : 11,
		"demands" : [
			{
				"start-time" : 1,
				"end-time" : 5,
				"end-points" : ["A", "C"],
				"demand" : 10.0
			},
			{
				"start-time" : 2,
				"end-time" : 10,
				"end-points" : ["B", "C"],
				"demand" : 10.0
			},
      {
        "start-time" : 6,
        "end-time" : 10,
        "end-points" : ["D", "C"],
        "demand" : 10.0
      },
      {
        "start-time" : 7,
        "end-time" : 10,
        "end-points" : ["A", "C"],
        "demand" : 10.0
      }
		]
	}
}"""

import json
import sys

if __name__ == '__main__':
  if len(sys.argv) < 2:
    print("Usage: python3 client.py cs1.json")
    sys.exit(1)
  
  data = json.load(open(sys.argv[1]))
  links = data['links']
  possible_circuits = data['possible-circuits']
  paths = []
  for i in range(len(possible_circuits)):
    path = {"startpoint": None, "endpoint": None, "connections": []}
    conn_count = 0
    for j in range(len(possible_circuits[i])):
      if j == 0:
        path["startpoint"] = possible_circuits[i][j]
      else:
        if j == len(possible_circuits[i]) - 1:
          path["endpoint"] = possible_circuits[i][j]
        conn_start = possible_circuits[i][j-1] 
        conn_end = possible_circuits[i][j]
        path["connections"].append({"startpoint": None, "endpoint":None, "capacity": None})
        path["connections"][conn_count]["startpoint"] = conn_start
        path["connections"][conn_count]["endpoint"] = conn_end
        connection = list(filter(lambda link: (link["points"][0] == conn_start and link["points"][1] == conn_end) or (link["points"][1] == conn_start and link["points"][0] == conn_end), links))
        if connection[0]["points"][0] != conn_start:
          connection[0]["points"][0], connection[0]["points"][1] = connection[0]["points"][1], connection[0]["points"][0]
        capacity = list(filter(lambda link: link["points"][0] == conn_start and link["points"][1] == conn_end, links))[0]["capacity"]
        path["connections"][conn_count]["capacity"] = capacity
        conn_count += 1
    paths.append(path)
  
  duration = data['simulation']['duration']
  demands = data['simulation']['demands']
  
  i = 0
  demand_counter = 0
  event_counter = 1
  event_endings = []
  
  while i < duration:
    if demand_counter < len(demands) and demands[demand_counter]['start-time'] == i:
      demand = demands[demand_counter]
      if len(list(filter(lambda path: path["startpoint"] == demand["end-points"][0] and path["endpoint"] == demand["end-points"][1], paths))) > 0:
        success = True
        for path in list(filter(lambda path: path["startpoint"] == demand["end-points"][0] and path["endpoint"] == demand["end-points"][1], paths)):
          if success == False: break
          for connection in path["connections"]:
            if connection["capacity"] < demand["demand"]:
              print(str(event_counter)+". igény foglalás: "+demand["end-points"][0]+"<->"+demand["end-points"][1]+" st:"+str(i)+" - sikertelen")
              success = False
              break
          if success:
            allocate_connections = path["connections"]
            for alloc_conn in allocate_connections:
              for path_temp in paths:
                for connection_temp in path_temp["connections"]:
                  if connection_temp["startpoint"] == alloc_conn["startpoint"] and connection_temp["endpoint"] == alloc_conn["endpoint"]:
                    connection_temp["capacity"] -= demand["demand"]
            event_endings.append({"index": demand_counter, "end-time": demand['end-time'], "path": allocate_connections})
            print(str(event_counter)+". igény foglalás: "+demand["end-points"][0]+"<->"+demand["end-points"][1]+" st:"+str(i)+" - sikeres")
            break
      else:
        print(str(event_counter)+". igény foglalás: "+demand["end-points"][0]+"<->"+demand["end-points"][1]+" st:"+str(i)+" - sikertelen")
      
      demand_counter += 1
      event_counter += 1
    

    for event in list(filter(lambda event: event["end-time"] == i, event_endings)):
      print(str(event_counter)+". igény felszabadítás: "+demands[event['index']]["end-points"][0]+"<->"+demands[event['index']]["end-points"][1]+" st:"+str(i))
      event_counter += 1
      deallocate_connections = event["path"]
      for dealloc_conn in deallocate_connections:
        for path_temp in paths:
          for connection_temp in path_temp["connections"]:
            if connection_temp["startpoint"] == dealloc_conn["startpoint"] and connection_temp["endpoint"] == dealloc_conn["endpoint"]:
              connection_temp["capacity"] += demand["demand"]
      event_endings.remove(event)
    i+=1
  