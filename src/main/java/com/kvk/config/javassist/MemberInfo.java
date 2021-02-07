package com.kvk.config.javassist;

import com.sun.codemodel.JDefinedClass;

import java.util.List;

public interface MemberInfo {
    Class<?> getType();
    String getName();
    List<AnnotationInfo> getAnnotationsInfo();
    void addAnnotationInfo(AnnotationInfo annotationInfo);
    void addSource(JDefinedClass definedClass);
}
