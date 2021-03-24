package com.kvk.config.javassist;

import com.sun.codemodel.*;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.*;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class AnnotationInfo {
    public static AnnotationInfo of(String s, String s2) {
        return null;
    }

    public Annotation getAnnotation(ConstPool constPool){return null;};
    @Deprecated
    public String getCode(int countTabs){return "";}

    public void annotateSource(JAnnotatable definedClass){}

    public void setParam(String name, String value){}

    public abstract String getAnnotationName();

    public abstract String getSerializedName();

    public abstract Map<String, Object> getParameters();

    public abstract void setParameters(Map<String, Object> parameters);

    public static AnnotationInfo of(Class<? extends java.lang.annotation.Annotation> annotation, Map<String, Object> props){
        final Class<? extends java.lang.annotation.Annotation> persistenceAnnotation = annotation;
        return new AnnotationInfo() {
            Class<? extends java.lang.annotation.Annotation> type = annotation;
            Map<String, Object> properties = props;
            @Override
            public Annotation getAnnotation(ConstPool constPool) {
                Annotation annotation = new Annotation(persistenceAnnotation.getName(), constPool);
                for(Method method: persistenceAnnotation.getDeclaredMethods()){
                    if(properties.containsKey(method.getName())) {
                        Object propValue = properties.get(method.getName());

                        if (method.getReturnType().equals(String.class)) {
                            annotation.addMemberValue(method.getName(),
                                    new StringMemberValue((String) propValue, constPool));
                        } else if(method.getReturnType().equals(boolean.class)){
                            if (propValue instanceof String)
                                propValue = Boolean.valueOf((String) propValue);
                            annotation.addMemberValue(method.getName(),
                                    new BooleanMemberValue((Boolean) propValue, constPool));
                        } else if(method.getReturnType().equals(int.class)){
                            if(propValue instanceof String)
                                propValue = Integer.parseInt((String) propValue);
                            annotation.addMemberValue(method.getName(),
                                    new IntegerMemberValue(constPool, (int) propValue));
                        } else if (method.getReturnType().isEnum()) {

                            EnumMemberValue enumMemberValue = new EnumMemberValue(constPool);
                            enumMemberValue.setType(method.getReturnType().getName());
                            if(propValue instanceof Enum<?>){
                                enumMemberValue.setValue(propValue.toString());
                            } else if(propValue instanceof String) {
                                enumMemberValue.setValue((String) propValue);
                            }else continue;

                            annotation.addMemberValue(method.getName(), enumMemberValue);
                        }

                    }
                }
                return annotation;
            }

            @Override
            public void setParam(String name, String value) {
                properties.put(name, value);
            }

            @Override
            public String getAnnotationName() {
                return annotation.getSimpleName();
            }

            @Override
            public String getSerializedName() {
                return annotation.getSimpleName();
            }

            @Override
            public Map<String, Object> getParameters() {
                return properties;
            }

            @Override
            public void setParameters(Map<String, Object> parameters) {
                this.properties = parameters;
            }


            @Override
            public void annotateSource(JAnnotatable definedClass) {
                JAnnotationUse annotationUse = definedClass.annotate(type);
                for(Map.Entry<String, Object> param: properties.entrySet()){
                    JExpression expression = null;
                    if(param.getValue().getClass().equals(String.class)){

                        expression = new AnnotationParamJExpression("\"" + param.getValue() + "\"");
                    } else expression = new AnnotationParamJExpression(param.getValue().toString());
                    annotationUse.param(param.getKey(), expression);
                }
            }

            @Deprecated
            @Override
            public String getCode(int countTabs) {
                StringBuilder codeBuilder = new StringBuilder();
                codeBuilder.append("\t".repeat(Math.max(0, countTabs)));
                codeBuilder.append("@").append(persistenceAnnotation.getSimpleName());
                boolean begin = true;
                for(Method method: persistenceAnnotation.getDeclaredMethods()){
                    if(properties.containsKey(method.getName())){
                        if(begin){
                            codeBuilder.append("(\n");
                        }
                        codeBuilder.append("\t".repeat(Math.max(0, countTabs+1)));
                        codeBuilder.append(method.getName()).append("=").append(properties.get(method.getName())).append(",\n");
                    }
                }

                return codeBuilder.toString();
            }
        };
    }
    public static AnnotationInfo of(Class<? extends java.lang.annotation.Annotation> type){
        return of(type, new HashMap<>());
    }
    public static AnnotationInfo of(String typeClassName) throws ClassNotFoundException {
        return of((Class<? extends java.lang.annotation.Annotation>) Class.forName(typeClassName));
    }
    public static AnnotationInfo of(String typeClassName, Map<String, Object> props) throws ClassNotFoundException {
        return of((Class<? extends java.lang.annotation.Annotation>) Class.forName(typeClassName), props);
    }
}
