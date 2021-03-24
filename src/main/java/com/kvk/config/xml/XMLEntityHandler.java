package com.kvk.config.xml;

import com.kvk.config.javassist.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLEntityHandler extends DefaultHandler {
    ArrayList<EntityClass> classes;
    String currentElement;

    public XMLEntityHandler(){}

    public List<EntityClass> getResult(){
        return classes;
    }
    @Override
    public void startDocument() throws SAXException {
        classes = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentElement = qName;
        if(currentElement.equals("entity")){
            EntityClass entityClass = new EntityClass();
            classes.add(entityClass);
            String className = attributes.getValue("class-name");
            entityClass.setClassName(className == null ? "" : className);
        }
        if(currentElement.equals("table")){
            TableAnnotationInfo table = new TableAnnotationInfo(attributes.getValue("name"));
            String catalog = attributes.getValue("catalog");
            if (catalog != null)
                table.setCatalog(catalog);
            String schema = attributes.getValue("schema");
            if(schema != null)
                table.setSchema(schema);
            classes.get(classes.size()-1)
                    .setTableAnnotationInfo(table);
        }
        if(currentElement.equals("entity")){
            EntityAnnotationInfo entity = new EntityAnnotationInfo(attributes.getValue("name"));
            classes.get(classes.size()-1)
                    .setEntityAnnotationInfo(entity);
        }
        if(currentElement.equals("members")){
            classes.get(classes.size()-1)
                    .setMembersInfo(new ArrayList<>());
        }
        if(currentElement.equals("member")){
            MemberInfo memberInfo=null;
            String type = attributes.getValue("type");
            if(type.equals("field")){
                String className = attributes.getValue("class");
                String name = attributes.getValue("name");
                try {
                    memberInfo = new FieldInfo(className, name);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if(memberInfo!=null){
                classes.get(classes.size()-1).getMembersInfo().add(memberInfo);
            }
        }
        if(currentElement.equals("Id")){
            List<MemberInfo> memberInfos = classes.get(classes.size()-1).getMembersInfo();
            MemberInfo memberInfo = memberInfos.get(memberInfos.size()-1);
            memberInfo.addAnnotationInfo(AnnotationInfo.of(Id.class));
        }
        if(currentElement.equals("GeneratedValue")){
            List<MemberInfo> memberInfos = classes.get(classes.size()-1).getMembersInfo();
            MemberInfo memberInfo = memberInfos.get(memberInfos.size()-1);

            Map<String, Object> parameters = new HashMap<>();
            String strategyValue = attributes.getValue("strategy");
            String generator = attributes.getValue("generator");
            if(strategyValue != null) {
                GenerationType strategy = GenerationType.valueOf(strategyValue.toUpperCase());
                parameters.put("strategy", strategy);
            }
            if(generator != null)
                parameters.put("generator", generator);

            memberInfo.addAnnotationInfo(AnnotationInfo.of(GeneratedValue.class, parameters));
        }
        if(currentElement.equals("column")){
            List<MemberInfo> memberInfos = classes.get(classes.size()-1).getMembersInfo();
            MemberInfo memberInfo = memberInfos.get(memberInfos.size()-1);

            ColumnAnnotationInfo column = new ColumnAnnotationInfo();

            String columnDef = attributes.getValue("column-definition");
            column.setColumnDefinition(columnDef==null?"":columnDef);

            String insertable = attributes.getValue("insertable");
            if(insertable != null)
                column.setInsertable(Boolean.parseBoolean(insertable));

            String length = attributes.getValue("length");
            if(length != null)
                column.setLength(Integer.parseInt(length));

            String name = attributes.getValue("name");
            column.setColumnName(name == null? "": name);

            String nullable = attributes.getValue("nullable");
            if(nullable != null)
                column.setNullable(Boolean.parseBoolean(nullable));

            String precision = attributes.getValue("precision");
            if (precision != null)
                column.setPrecision(Integer.parseInt(precision));

            String scale = attributes.getValue("scale");
            if(scale != null)
                column.setScale(Integer.parseInt(scale));

            String table = attributes.getValue("table");
            column.setTable(table == null ? "" : table);

            String unique = attributes.getValue("unique");
            if(unique != null)
                column.setNullable(Boolean.parseBoolean(unique));

            String updatable = attributes.getValue("updatable");
            if(updatable != null)
                column.setNullable(Boolean.parseBoolean(updatable));

            memberInfo.addAnnotationInfo(column);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        currentElement = "";
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

    }
}
