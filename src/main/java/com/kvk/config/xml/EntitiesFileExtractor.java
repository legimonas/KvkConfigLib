package com.kvk.config.xml;

import com.kvk.config.javassist.EntityClass;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class EntitiesFileExtractor {

    private static void  validateEntityConfig(File configFile) throws IOException, SAXException {
        URL url = new URL("https://raw.githubusercontent.com/legimonas/KvkConfigLib/development/src/main/resources/config.xsd");
        Source xmlFile = new StreamSource(configFile);
        SchemaFactory schemaFactory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI );
        Schema schema = schemaFactory.newSchema(url);
        Validator validator = schema.newValidator();
        validator.validate(xmlFile);
        System.out.println("Config xml file is valid!!!");
    }
    public static List<EntityClass> parseClassesFromConfigXML(File configFile) throws ParserConfigurationException, SAXException, IOException {
        validateEntityConfig(configFile);
        SAXParser parser = SAXParserFactory.newInstance().newSAXParser();

        XMLEntityHandler handler = new XMLEntityHandler();
        parser.parse(configFile, handler);
        return handler.getResult();
    }
}
