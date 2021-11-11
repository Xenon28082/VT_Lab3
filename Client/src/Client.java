import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static Socket clientSocket;
    private static BufferedReader reader;
    private static BufferedReader in;
    private static BufferedWriter out;
    private static UserDAO dao = new UserDAO();


    public static void main(String[] args) {
        User user = null;
        try {
            user = dao.login();
            while(user == null){
                System.out.println("Repeat login");
                user = dao.login();
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

        String permission = user.getPermission();
        System.out.println(permission);
        try {
            try {
                clientSocket = new Socket("localhost", 4004);
                reader = new BufferedReader(new InputStreamReader(System.in));
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                System.out.println("Input your message");
                String word = "";
                while (!word.equals("stop")) {
                    word = reader.readLine();

                    if (word.equals("read")) {
                        out.write(word + "\n");
                        out.flush();
                        System.out.println("Start reading");
                        String serverWord = in.readLine();
                        System.out.println(serverWord);
                    }
                    if (word.equals("write")) {
                        if(!permission.equals("RW")){
                            System.out.println("Permission denied");
                        }else {
                            out.write(word + "\n");
                            out.flush();
                            System.out.println("start writing user fields");
                            System.out.println("Enter name, lastname, age, spec");
                            for (int i = 0; i < 4; i++) {
                                String string = reader.readLine();
                                out.write(string + "\n");
                                out.flush();
                            }
                            System.out.println("end writing");
                        }
                    }
                }
            } finally {
                System.out.println("Client has been closed...");
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
