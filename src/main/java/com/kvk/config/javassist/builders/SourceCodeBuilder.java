package com.kvk.config.javassist.builders;

import com.kvk.config.javassist.EntityClass;
import com.kvk.config.javassist.FieldInfo;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.kvk.config.javassist.MemberInfo;
import javassist.CannotCompileException;
import javassist.NotFoundException;

import java.io.File;
import java.io.IOException;

public class SourceCodeBuilder implements EntityCodeBuilder {
    private static final SourceCodeBuilder instance = new SourceCodeBuilder();
    private SourceCodeBuilder(){}
    public static SourceCodeBuilder create(){return instance;}

    @Override
    public void buildCode(EntityClass entityClass, String directory) throws IOException, JClassAlreadyExistsException {
        buildCode(new File(directory), entityClass, false);
    }

    @Override
    public void buildCode(EntityClass entityClass, String directory, Boolean withGettersAndSetters) throws CannotCompileException, IOException, JClassAlreadyExistsException, NotFoundException {
        buildCode(new File(directory), entityClass, withGettersAndSetters);
    }

    public void buildCode(File classPathDir, EntityClass entityClass, Boolean withGettersAndSetters) throws JClassAlreadyExistsException, IOException {
        JCodeModel codeModel = new JCodeModel();
        JDefinedClass definedClass = codeModel._class(entityClass.getClassName());

        entityClass.getEntityAnnotationInfo().annotateSource(definedClass);
        entityClass.getTableAnnotationInfo().annotateSource(definedClass);

        for(MemberInfo memberInfo: entityClass.getMembersInfo()){
            if (memberInfo instanceof FieldInfo){
                memberInfo.addSource(definedClass, withGettersAndSetters);
            }
        }
        codeModel.build(classPathDir);

    }
}
