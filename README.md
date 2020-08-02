###### Code Lines

This application is CLI app for user to count java code lines in a particular file or files.

###### Project Build

1. One should clone the project from the repository
2. Call maven package command to compile project, run test and package jar file
`mvn package`
3. In `target/dist` folder created by Maven one would find jar code-lines.jar. 

###### Project Launch

To launch the program open terminal (cmd) and run command:
`java -jar relative/path/to/file/code-lines.jar`
 
###### Enable Logging

To enable logging launch app with VM option:
`-Dlogback.configurationFile=src\main\resources\logback-enabled.xml`
