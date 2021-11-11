import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static Socket clientSocket;
    private static ServerSocket serverSocket;
    private static BufferedReader in;
    private static BufferedWriter out;
    private static StudentDAO dao = new StudentDAO();

    public static void main(String[] args) {
        try {
            try {
                serverSocket = new ServerSocket(4004);
                System.out.println("Server is running");
                clientSocket = serverSocket.accept();
                try {
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    String word = "";
                    while (!word.equals("stop")) {
                        word = in.readLine();
                        System.out.println(word);
                        switch (word) {
                            case "read": {
                                String students = dao.getStudents().toString();
                                out.write(students + "\n");
                                out.flush();
                                break;
                            }
                            case "write": {
                                Student student = new Student(in.readLine(), in.readLine(), Integer.parseInt(in.readLine()), in.readLine());
                                dao.addNewStudent(student);
                                System.out.println(student);
                                break;
                            }
                            default:
                                continue;
                        }
                    }
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (TransformerException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } finally {
                    clientSocket.close();
                    in.close();
                    out.close();
                }
            } finally {
                System.out.println("Server is closed");
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
