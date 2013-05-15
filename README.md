Monitor whether matches keep coming (heartbeats)

__State__: Working UDP server, still needs a configurable matcher because that is now hard-coded at single characters.

__TLDR__: If the match is missing for more than 1.5 times the last interval, it will be colored red.

Getting started
---------------
It's written in Java and some HTML, requires maven to build.

Run `mvn clean verify` next to the `pom.xml` file. Then `java -jar target/matchmonitor-0.0.1-SNAPSHOT.jar`. If there are no errors, you can visit `localhost:8080` for the homepage. It will poll every 5 seconds but not show much.

Now you need to inject data by posting UDP packets to port 8081. Start `nc -u localhost 8081` and type any character followed by return. After a few seconds one of the letters you entered will appear.

If you keep up a good interval of, for example, messages with an `a` in them, you should get `a` to turn up normal. Then if you stop, it should turn red indicating the packet _missed it's normal interval_.


