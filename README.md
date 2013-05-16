Matchmonitor
============

Monitor whether matches keep coming (heartbeats)

__TLDR__: If the matching IP is missing for more than 1.5 times the last interval it was seen, it will be colored red. Current version only matches on the first IP address in the UDP packet.

Getting started
---------------
It's written in Java and some HTML, requires maven to build.

Run one of the releases using `java -jar target/matchmonitor-0.0.2.jar`. If there are no errors, you can visit `http://localhost:8080` for the homepage. The page will update every 5 seconds.

Now you need to inject data by posting UDP packets to port 8081. Start `nc -u localhost 8081` and type any IP address followed by return. After a few seconds it should appear on the HTML page.

If you keep the same IP address coming in UDP packets at a regular interval, the IP address will turn blue. If `matchmonitor` does not receive the same IP address in 1.5 times the regular interval, it will turn the IP address red.

This means that it warns you if the IP mention _missed it's normal interval_.

Downloads
---------

You can download complete jar releases here: [https://mega.co.nz/#F!ll8hDJKT!QzHVQVs6tdw1P8a5GdZSWQ](https://mega.co.nz/#F!ll8hDJKT!QzHVQVs6tdw1P8a5GdZSWQ)

Example
-------
To send your `syslog` lines to `matchmonitor`, you could use:

    tail -f /var/log/syslog | while read x; do echo "$x" | nc -u localhost 8081; done


