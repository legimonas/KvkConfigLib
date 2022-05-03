package com.kvk.config.javassist;

import com.sun.codemodel.*;

import java.util.ArrayList;
import java.util.List;

public class FieldInfo extends MemberInfo {

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
    public void addSource(JDefinedClass definedClass, Boolean withGettersAndSetters){
        JFieldVar fieldVar = definedClass.field(JMod.PUBLIC, getType(), getName());
        for(AnnotationInfo annotationInfo: annotationsInfo){
            annotationInfo.annotateSource(fieldVar);
        }
        String capitalizedName = getName().substring(0, 1).toUpperCase() + getName().substring(1);

        if (withGettersAndSetters) {
            JMethod methodGetter = definedClass.method(JMod.PUBLIC, getType(), "get" + capitalizedName);
            methodGetter.body()._return(fieldVar);
            JMethod methodSetter = definedClass.method(JMod.PUBLIC, definedClass.owner().VOID, "set" + capitalizedName);
            methodSetter.param(getType(), "new" + capitalizedName);
            methodSetter.body().assign(JExpr._this().ref(getName()), JExpr.ref("new" + capitalizedName));
        }
    }

    @Override
    public void addSource(JDefinedClass definedClass) {
        addSource(definedClass, false);
    }

    @Override
    public List<AnnotationInfo> getAnnotationsInfo(){
        return annotationsInfo;
    }

}
