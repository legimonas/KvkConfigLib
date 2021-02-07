package com.kvk.config.javassist.builders;

import com.kvk.config.javassist.EntityClass;
import com.kvk.config.javassist.FieldInfo;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.kvk.config.javassist.MemberInfo;

import java.io.File;
import java.io.IOException;

public class SourceCodeBuilder implements EntityCodeBuilder {
    private static final SourceCodeBuilder instance = new SourceCodeBuilder();
    private SourceCodeBuilder(){}
    public SourceCodeBuilder create(){return instance;}
    @Override
    public void buildCode(EntityClass entityClass, String directory) throws IOException, JClassAlreadyExistsException {
        buildCode(new File(directory), entityClass);
    }
    public void buildCode(File classPathDir, EntityClass entityClass) throws JClassAlreadyExistsException, IOException {
        JCodeModel codeModel = new JCodeModel();
        JDefinedClass definedClass = codeModel._class(entityClass.getClassName());

        entityClass.getEntityAnnotationInfo().annotateSource(definedClass);
        entityClass.getTableAnnotationInfo().annotateSource(definedClass);

        for(MemberInfo memberInfo: entityClass.getMembersInfo()){
            if (memberInfo instanceof FieldInfo){
                memberInfo.addSource(definedClass);
            }
        }
        codeModel.build(classPathDir);

    }
}
