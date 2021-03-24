package com.kvk.config.javassist;

import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JDefinedClass;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;

import javax.persistence.Entity;
import java.util.HashMap;
import java.util.Map;

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

    @Override
    public String getAnnotationName() {
        return "Entity";
    }

    @Override
    public String getSerializedName() {
        return "entity";
    }

    @Override
    public Map<String, Object> getParameters() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        return params;
    }

    @Override
    public void setParameters(Map<String, Object> parameters) {
        if(parameters.containsKey("name"))
            name = (String) parameters.get("name");
    }
}
