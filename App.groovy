def webServerConf = [
  port: 8080,
  host: 'localhost'
]

// Start the web server, with the config we defined above

container.deployModule('vertx.web-server-v1.0', webServerConf);