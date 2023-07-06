A client-server application that allows clients to store data in JSON format.

----------------------------------------------------------------------------------------------------------------------------------------
Example Output
----------------------------------------------------------------------------------------------------------------------------------------
````
> java Main -in setFile.json 
Client started!
Sent:
{
   "type":"set",
   "key":"person",
   "value":{
      "name":"Elon Musk",
      "car":{
         "model":"Tesla Roadster",
         "year":"2018"
      },
      "rocket":{
         "name":"Falcon 9",
         "launches":"87"
      }
   }
}

Received: {"response":"OK"}
> java Main -in getFile.json 
Client started!
Sent: {"type":"get","key":["person","name"]}
Received: {"response":"OK","value":"Elon Musk"}
> java Main -in updateFile.json 
Client started!
Sent: {"type":"set","key":["person","rocket","launches"],"value":"88"}
Received: {"response":"OK"}
> java Main -in secondGetFile.json 
Client started!
Sent: {"type":"get","key":["person"]}
Received:
{
   "response":"OK",
   "value":{
      "name":"Elon Musk",
      "car":{
         "model":"Tesla Roadster",
         "year":"2018"
      },
      "rocket":{
         "name":"Falcon 9",
         "launches":"88"
      }
   }
}

> java Main -in deleteFile.json 
Client started!
Sent: {"type":"delete","key":["person","car","year"]}
Received: {"response":"OK"}
> java Main -in secondGetFile.json 
Client started!
Sent: {"type":"get","key":["person"]}
Received:
{
   "response":"OK",
   "value":{
      "name":"Elon Musk",
      "car":{
         "model":"Tesla Roadster"
      },
      "rocket":{
         "name":"Falcon 9",
         "launches":"88"
      }
   }
}

> java Main -t exit 
Client started!
Sent: {"type":"exit"}
Received: {"response":"OK"}
````
