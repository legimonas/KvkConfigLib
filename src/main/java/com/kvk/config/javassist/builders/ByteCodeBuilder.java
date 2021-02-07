package com.kvk.config.javassist.builders;

import com.kvk.config.javassist.AnnotationInfo;
import com.kvk.config.javassist.EntityClass;
import com.kvk.config.javassist.MemberInfo;
import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;

import java.io.IOException;

public class ByteCodeBuilder implements EntityCodeBuilder {
    private static final ByteCodeBuilder instance = new ByteCodeBuilder();
    private ByteCodeBuilder(){}
    public static ByteCodeBuilder create(){
        return instance;
    }
    @Override
    public void buildCode(EntityClass entityClass, String directory) throws CannotCompileException, NotFoundException, IOException {
        buildCode(ClassPool.getDefault(), entityClass, directory);
    }
    public void buildCode(ClassPool classPool, EntityClass entityClass, String directory) throws CannotCompileException, NotFoundException, IOException {
        CtClass ctClass = classPool.makeClass(entityClass.getClassName());
        ConstPool constPool = ctClass.getClassFile().getConstPool();

        AnnotationsAttribute classAttribute = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
        classAttribute.addAnnotation(entityClass.getEntityAnnotationInfo().getAnnotation(constPool));
        classAttribute.addAnnotation(entityClass.getTableAnnotationInfo().getAnnotation(constPool));

        ctClass.getClassFile().addAttribute(classAttribute);

        for (MemberInfo memberInfo: entityClass.getMembersInfo()){
            CtField ctField = CtField.make(memberInfo.getType().getName() + " " + memberInfo.getName() + ";", ctClass);
            ConstPool constFieldPool = ctField.getFieldInfo().getConstPool();

            AnnotationsAttribute fieldAttribute = new AnnotationsAttribute(constFieldPool, AnnotationsAttribute.visibleTag);
            for(AnnotationInfo annotationInfo: memberInfo.getAnnotationsInfo())
                fieldAttribute.addAnnotation(annotationInfo.getAnnotation(constPool));

            ctField.getFieldInfo().addAttribute(fieldAttribute);
            ctClass.addField(ctField);
        }

        ctClass.writeFile(directory);
    }
}
