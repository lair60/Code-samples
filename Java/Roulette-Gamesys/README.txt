Roulette Game:

1.In order to play this game, first you need to launch the Server.java and then launch the Client.java per each user.Both files are in the directory src/main/java/Gamesys
2.The Server.java will search a file called "Input.txt" inside the classpath directory(If you are using Maven, /target/classes). By default, there is a file with some data.
3.The file has the following format:
Tiki_Monkey,1.0,2.0
Barbara,2.0,1.0

4.Any changes will save in the same file.
5.If you want to make a bet you need to enter [name(MaxLength 12), 1-36 or odd or even, Amount] 
for example: Barbara odd 1.5 or Barbara 25 4
6.If you want to exit, just enter 'end'.
7.Good luck!!


Testing with JUnit:

1.In order to test the play, first you need to launch the Server.java before launch the tests classes.
2.When the server is running, execute ClientTest1.java and ClientTest2.java in order to simulate the interaction between server and two clients(they send 10 messages to the server and they'll wait 10 responses).Both files are in the directory src/test/java/Gamesys
3.On the other hand, to test the Roulette's functions, it's necessary to execute RouletteTest.java(It's not necessary to execute the server.java in order to test the Roulette's functions)

If you have any doubt, please do not hesitate to contact me through my mail lair60@yahoo.es

Thank you!!!