import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StudentDAO {

    private static final String DATABASE_PATH = "students.xml";
    private static Document doc;



    public List<List<String>> getStudents() {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(DATABASE_PATH);
            List<List<String>> objectsInformation = new ArrayList<>();
            NodeList elementList = doc.getElementsByTagName("student");
            for (int i = 0; i < elementList.getLength(); i++) {
                Node p = elementList.item(i);
                if (p.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) p;
                    NodeList nameList = element.getChildNodes();
                    List<String> currentObjectInfo = new ArrayList<>();
                    for (int j = 0; j < nameList.getLength(); j++) {
                        Node n = nameList.item(j);
                        if (n.getNodeType() == Node.ELEMENT_NODE) {
                            Element name = (Element) n;
                            currentObjectInfo.add(name.getTextContent());
                        }
                    }
                    objectsInformation.add(currentObjectInfo);
                }
            }
            return objectsInformation;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void addNewStudent(Student newStud) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse("C:\\Users\\savva\\IdeaProjects\\VT3\\students.xml");
        Element root = document.getDocumentElement();

        Collection<Student> students = new ArrayList<Student>();
        students.add(new Student());

        for (Student student : students) {

            Element newStudent = document.createElement("student");

            Element name = document.createElement("name");
            name.appendChild(document.createTextNode(newStud.getName()));
            newStudent.appendChild(name);

            Element lastname = document.createElement("lastname");
            lastname.appendChild(document.createTextNode(newStud.getLastname()));
            newStudent.appendChild(lastname);

            Element age = document.createElement("age");
            age.appendChild(document.createTextNode(String.valueOf(newStud.getAge())));
            newStudent.appendChild(age);

            Element spec = document.createElement("spec");
            spec.appendChild(document.createTextNode(newStud.getSpec()));
            newStudent.appendChild(spec);

            root.appendChild(newStudent);
        }

        DOMSource source = new DOMSource(document);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult("C:\\Users\\savva\\IdeaProjects\\VT3\\students.xml");
        transformer.transform(source, result);
    }

}
