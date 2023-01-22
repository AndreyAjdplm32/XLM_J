package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.*;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse("data.xml");

        List<Employee> list = getListEmployeers(doc);

        String jsonString = listToJson(list);
        System.out.println(jsonString);

        String jsonFile = "data.json";
        createJson(jsonString, jsonFile);

    }

    private static List<Employee> getListEmployeers (Document doc) {

        NodeList nodeList = doc.getElementsByTagName("employee");
        List<Employee> list = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                 Element element = (Element) node;
                    list.add (new Employee(
                            (Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent())),
                (element.getElementsByTagName("firstName").item(0).getTextContent()),
                (element.getElementsByTagName("lastName").item(0).getTextContent()),
                (element.getElementsByTagName("country").item(0).getTextContent()),
                (Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent()))));
            }
        } return list;
    }
    public static String listToJson(List<Employee> list) {
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(list, listType);
    }

    public static void createJson(String input, String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            writer.write(input);
            writer.flush();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

}



