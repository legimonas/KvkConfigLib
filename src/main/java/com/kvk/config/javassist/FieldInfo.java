package com.kvk.config.javassist;

import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMod;

import java.util.ArrayList;
import java.util.List;

public class FieldInfo implements MemberInfo {
    private Class<?> type;
    private String name;
    private List<AnnotationInfo> annotationsInfo;

    public FieldInfo(Class<?> type, String name, List<AnnotationInfo> annotationsInfo) {
        this.type = type;
        this.name = name;
        this.annotationsInfo = annotationsInfo;
    }
    public FieldInfo(String typeClassName, String name) throws ClassNotFoundException {
        this.type = Class.forName(typeClassName);
        this.name = name;
        annotationsInfo = new ArrayList<>();
    }
    public FieldInfo(String typeClassName, String name, List<AnnotationInfo> annotationsInfo) throws ClassNotFoundException {
        this.type = Class.forName(typeClassName);
        this.name = name;
        this.annotationsInfo = annotationsInfo;
    }
    public FieldInfo(Class<?> type, String name){
        this.type = type;
        this.name = name;
        annotationsInfo = new ArrayList<>();
    }
    @Override
    public void addAnnotationInfo(AnnotationInfo annotationInfo){
        annotationsInfo.add(annotationInfo);
    }


    @Override
    public void addSource(JDefinedClass definedClass){
        JFieldVar fieldVar = definedClass.field(JMod.PUBLIC, getType(), getName());
        for(AnnotationInfo annotationInfo: annotationsInfo){
            annotationInfo.annotateSource(fieldVar);
        }
    }




    @Override
    public List<AnnotationInfo> getAnnotationsInfo(){
        return annotationsInfo;
    }

    @Override
    public Class<?> getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }
}
