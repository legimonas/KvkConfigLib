package com.kvk.config.javassist;

import com.sun.codemodel.JDefinedClass;

import java.util.List;

public abstract class MemberInfo {
    protected String name;
    protected Class<?> type;
    public Class<?> getType(){
        return type;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setType(Class<?> type){
        this.type = type;
    }
    public abstract List<AnnotationInfo> getAnnotationsInfo();
    public abstract void addAnnotationInfo(AnnotationInfo annotationInfo);
    public abstract void addSource(JDefinedClass definedClass);
    public abstract void addSource(JDefinedClass definedClass, Boolean withGettersAndSetters);
}
