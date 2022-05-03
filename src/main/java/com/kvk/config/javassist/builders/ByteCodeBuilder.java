package com.kvk.config.javassist.builders;

import com.kvk.config.javassist.AnnotationInfo;
import com.kvk.config.javassist.EntityClass;
import com.kvk.config.javassist.MemberInfo;
import com.sun.codemodel.JClassAlreadyExistsException;
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
        buildCode(ClassPool.getDefault(), entityClass, directory, false);
    }

    @Override
    public void buildCode(EntityClass entityClass, String directory, Boolean withGettersAndSetters) throws CannotCompileException, IOException, JClassAlreadyExistsException, NotFoundException {
        buildCode(ClassPool.getDefault(), entityClass, directory, withGettersAndSetters);
    }

    public void buildCode(ClassPool classPool, EntityClass entityClass, String directory, Boolean withGettersAndSetters) throws CannotCompileException, NotFoundException, IOException {

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

            if (withGettersAndSetters) {
                String typeName = memberInfo.getType().getName();
                String memberName = memberInfo.getName();
                String capitalizedName = memberName.substring(0, 1).toUpperCase() + memberName.substring(1);

                CtMethod ctMethodGetter = CtMethod.make("public " + typeName + " get" + capitalizedName + "() { return " + memberName + "; }", ctClass);
                ctClass.addMethod(ctMethodGetter);
                CtMethod ctMethodSetter = CtMethod.make("public void set" + capitalizedName + "(" + typeName + " newVal) { "+ memberName + " = newVal; }", ctClass);
                ctClass.addMethod(ctMethodSetter);
            }

        }

        ctClass.writeFile(directory);
    }
}
