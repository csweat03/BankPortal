# BankPortal
A primarily server based application, with a client shell to send and receive information from the server.
The server sends messages with SHA256+Base64 full encryption, and the client must connect to the server over a modified 3-way-handshake.
The server also talks to a MySQL Database and uses Java Socket, rather than a library, for the most lightweight communications possible in Java.
