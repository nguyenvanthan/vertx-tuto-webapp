def webServerConf = [

  // Normal web server stuff
  port: 8080,
  host: 'localhost',
  
  bridge: true,  // also act like an event bus bridge
  
  inbound_permitted: [ // allow messages from the client --> server
    // Allow calls to get static album data from the persistor
    [
      address : 'vertx.mongopersistor',
      match : [
        action : 'find',
        collection : 'albums'
      ]
    ]
  ],
  outbound_permitted: [ [:] ] // allow messages from the server --> client
 
]


def mongodbConf = [
	address: 'vertx.mongopersistor',
	host: 'localhost',
	port: 27017,
	db_name: 'vertx_db'
]	

// Start the web server, with the config we defined above

container.with {
  // Deploy a MongoDB persistor module
  deployModule('vertx.mongo-persistor-v1.0', mongodbConf, 1) { // add my mongodb configuration
	deployVerticle('StaticData.groovy') // add static data
  }

  // Start the web server, with the config we defined above
  deployModule('vertx.web-server-v1.0', webServerConf)
}
