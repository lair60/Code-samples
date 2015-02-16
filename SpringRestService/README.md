This project consists of a backend REST service and a frontend Angular JS client.

INSTRUCTIONS
============
* Verify that the existing backend REST endpoints work.
* Create a new REST endpoint at '/convert' that takes an Arabic numeral request
  parameter and converts the number to a Roman numeral.

* Verify that the frontend application runs and can communicate with both of
  the REST endpoints.
* Add a feature to the frontend that utilizes the '/convert' REST endpoint.

Stack
=====

Backend App
-----------

The backend service is a standalone REST service with (2) endpoints
- http://localhost:8080/greeting
- http://localhost:8080/greeting?name=John_Doe

### Building
1. From the command line, run ./SpringRestService/service/gradlew.bat build.
2. This creates the jar file ./SpringRestService/service/build/libs/gs-rest-service-0.1.0.jar.

### Running
1. From the command line, run java -jar ./SpringRestService/service/build/libs/gs-rest-service-0.1.0.jar.
2. Open a web browser and navigate to the URL's above.


Client App
----------

### Platform & tools

You need to install Node.js and then the development tools. Node.js comes with a package manager called [npm](http://npmjs.org) for installing NodeJS applications and libraries.
* [Install node.js](http://nodejs.org/download/) (requires node.js version >= 0.8.4)
* Install Grunt-CLI as a global npm module:

    ```
    npm install -g grunt-cli
    ```

The client application is a straight HTML/Javascript application that uses a
Node.js build tool [Grunt.js](gruntjs.com). Grunt relies upon some 3rd party
libraries that we need to install as local dependencies using npm.

* Install local dependencies (from the project root folder):

    ```
    cd client
    npm install
    cd ..
    ```

  (This will install the dependencies declared in the client/package.json file)

### Building

    ```
    cd client
    grunt build
    cd ..
    ```

### Running

    ```
    cd client
    grunt serve
    cd ..
    ```

