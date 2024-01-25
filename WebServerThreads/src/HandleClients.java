import java.io.*;
import java.net.Socket;

class HandleClients implements Runnable {

    private Socket clientSocket;

    public HandleClients(Socket socket) throws IOException {
        this.clientSocket = socket;
    }


    @Override
    public void run() {

        System.out.println(Thread.currentThread().getName());

        BufferedReader in = null;

        try {

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String input = null;
        try {

            input = in.readLine();
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        File index = new File("www/index.html");
        File error = new File("www/404.html");
        File logo = new File("www/logo.png");


        //INDEX
        BufferedReader readIndexFile = null;
        try {
            readIndexFile = new BufferedReader(new FileReader(index));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String fileIndexLine = null;
        try {
            fileIndexLine = readIndexFile.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String fileIndexResult = "";

        while (fileIndexLine != null) {
            fileIndexResult += fileIndexLine;
            try {
                fileIndexLine = readIndexFile.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        //404
        BufferedReader readErrorFile = null;
        try {
            readErrorFile = new BufferedReader(new FileReader(error));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String fileErrorLine = null;
        try {
            fileErrorLine = readErrorFile.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String fileErrorResult = "";

        while (fileErrorLine != null) {
            fileErrorResult += fileErrorLine;
            try {
                fileErrorLine = readErrorFile.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        //LOGO
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(logo);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //System.out.println(input);


        if (index.exists()) {

            if (input.equals("GET /index.html HTTP/1.1")) {

                String headerOK = "HTTP/1.0 200 Document Follows\r\n" +
                        "Content-Type: text/html; charset=UTF-8\r\n" +
                        "Content-Length: " + fileIndexResult.length() + " \r\n" +
                        "\r\n";

                try {
                    clientSocket.getOutputStream().write(headerOK.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                try {
                    clientSocket.getOutputStream().write(fileIndexResult.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                try {
                    clientSocket.getOutputStream().flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } else if (input.equals("GET /logo.png HTTP/1.1")) {

                String headerImgOK = "HTTP/1.0 200 Document Follows\r\n" +
                        "Content-Type: image/png \r\n" +
                        "Content-Length: " + logo.length() + " \r\n" +
                        "\r\n";

                try {
                    clientSocket.getOutputStream().write(headerImgOK.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                try {
                    sendImg(inputStream, clientSocket.getOutputStream());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                try {
                    clientSocket.getOutputStream().flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } else {

                String headerError = "HTTP/1.0 404 Not Found\r\n" +
                        "Content-Type: text/html; charset=UTF-8\r\n" +
                        "Content-Length: 200 \r\n" +
                        "\r\n";

                try {
                    clientSocket.getOutputStream().write(headerError.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                try {
                    clientSocket.getOutputStream().write(fileErrorResult.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                try {
                    clientSocket.getOutputStream().flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
        try {
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void sendImg(FileInputStream inputStream, OutputStream outputStream) {
        byte[] buffer = new byte[1024];
        try {
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
                //bytesRead = inputStream.read(buffer);
                //System.out.println(bytesRead);
            }
            inputStream.close();
            //outputStream.close();
        } catch (IOException e) {
            System.out.println("Error reading/writing file.");
        }

    }

}