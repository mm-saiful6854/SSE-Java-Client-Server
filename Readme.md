Step 1: use jdk built-in http server.

SSE Implementation:
1. client sends text/event-stream in accept attribute of http
header.
2. sends formatted data like event such as id, data, event-type etc.
3. 