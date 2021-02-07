package com.kvk.config.javassist;

import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JDefinedClass;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;

import javax.persistence.Entity;

public class EntityAnnotationInfo extends AnnotationInfo{
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name = "";
    public EntityAnnotationInfo(){

    }
    public EntityAnnotationInfo(String name){
        this.name = name;
    }

    @Override
    public Annotation getAnnotation(ConstPool constPool) {
        Annotation annotation = new Annotation(Entity.class.getName(), constPool);
        if(name != null && !"".equals(name))
            annotation.addMemberValue("name", new StringMemberValue(name, constPool));
        return annotation;
    }

    public void annotateSource(JDefinedClass definedClass){
        JAnnotationUse annotationUse = definedClass.annotate(Entity.class);
        if(!"".equals(name))
            annotationUse.param("name", name);
    }

}
