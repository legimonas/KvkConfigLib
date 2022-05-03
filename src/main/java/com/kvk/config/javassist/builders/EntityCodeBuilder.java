package com.kvk.config.javassist.builders;

import com.kvk.config.javassist.EntityClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import javassist.CannotCompileException;
import javassist.NotFoundException;

import java.io.IOException;

public interface EntityCodeBuilder {
    void buildCode(EntityClass entityClass, String directory) throws CannotCompileException, IOException, JClassAlreadyExistsException, NotFoundException;
    void buildCode(EntityClass entityClass, String directory, Boolean withGettersAndSetters) throws CannotCompileException, IOException, JClassAlreadyExistsException, NotFoundException;
}
