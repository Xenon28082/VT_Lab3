import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class UserDAO {
    private User checkUser(User user) throws ParserConfigurationException, IOException, SAXException {
        File xmlFile = new File("C:\\Users\\savva\\IdeaProjects\\VT3\\users.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        Document doc = ((DocumentBuilder) dBuilder).parse(xmlFile);

        doc.getDocumentElement().normalize();

        NodeList nList = doc.getElementsByTagName("user");

        for (int i = 0; i < nList.getLength(); i++) {

            Node nNode = nList.item(i);


            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element elem = (Element) nNode;

                Node node1 = elem.getElementsByTagName("login").item(0);
                String login = node1.getTextContent();

                if (login.equals(user.getLogin())) {

                    Node node2 = elem.getElementsByTagName("password").item(0);
                    String password = node2.getTextContent();

                    if (password.equals(user.getPassword())) {

                        Node node3 = elem.getElementsByTagName("permission").item(0);
                        String permission = node3.getTextContent();

                        return new User(login, password, permission);

                    }
                }
            }
        }
        return null;
    }

    public User login() throws ParserConfigurationException, IOException, SAXException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input login");
        String login = sc.nextLine();
        System.out.println("Input password");
        String password = sc.nextLine();
        return(checkUser(new User(login, password, "")));
    }
}
