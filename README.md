# Java-Web-Server-Threads
This is a basic implementation of a multithreaded web server in Java. The server is capable of serving static content, including HTML files and an image file (logo.png), and it responds to HTTP GET requests.

# Project Structure

- `WebServerThreads.java`: Main class responsible for creating a multithreaded web server.
- `HandleClients.java`: Runnable class handling individual client requests and serving static content.
- `www/`: Directory containing HTML files (`index.html` and `404.html`) and an image file (`logo.png`).
  - `index.html`: Sample HTML file for serving as the main page.
  - `404.html`: HTML file for a 404 error response.
  - `logo.png`: Image file used in the project.

