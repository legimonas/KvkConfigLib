package com.kvk.config.javassist;

import com.sun.codemodel.JAnnotatable;
import com.sun.codemodel.JAnnotationUse;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.BooleanMemberValue;
import javassist.bytecode.annotation.IntegerMemberValue;
import javassist.bytecode.annotation.StringMemberValue;

import javax.persistence.Column;
import java.util.HashMap;
import java.util.Map;

public class ColumnAnnotationInfo extends AnnotationInfo {
    private String columnName = "";
    private Boolean unique = false;
    private Boolean nullable = true;
    private Boolean insertable = true;
    private Boolean updatable = true;
    private String columnDefinition = "";
    private String table = "";
    private Integer length = 255;
    private Integer precision = 0;
    private Integer scale = 0;


    public ColumnAnnotationInfo() {
    }

    public ColumnAnnotationInfo(String columnName) {
        this.columnName = columnName;
    }

    public static class Builder {
        private final ColumnAnnotationInfo columnAnnotationInfo;

        private Builder() {
            columnAnnotationInfo = new ColumnAnnotationInfo();
        }



        public Builder name(String name) {
            columnAnnotationInfo.columnName = name;
            return this;
        }

        public Builder unique(boolean unique) {
            columnAnnotationInfo.unique = unique;
            return this;
        }

        public Builder nullable(boolean nullable) {
            columnAnnotationInfo.nullable = nullable;
            return this;
        }

        public Builder insertable(boolean insertable) {
            columnAnnotationInfo.insertable = insertable;
            return this;
        }

        public Builder updatable(boolean updatable) {
            columnAnnotationInfo.updatable = updatable;
            return this;
        }

        public Builder columnDefinition(String columnDefinition) {
            columnAnnotationInfo.columnDefinition = columnDefinition;
            return this;
        }

        public Builder table(String table) {
            columnAnnotationInfo.table = table;
            return this;
        }

        public Builder length(int length) {
            columnAnnotationInfo.length = length;
            return this;
        }

        public Builder precision(int precision) {
            columnAnnotationInfo.precision = precision;
            return this;
        }

        public Builder scale(int scale) {
            columnAnnotationInfo.scale = scale;
            return this;
        }

        public ColumnAnnotationInfo build() {
            return columnAnnotationInfo;
        }
    }

    public static Builder builder() {
        return new Builder();
    }


    @Override
    public String getAnnotationName() {
        return "Column";
    }

    @Override
    public String getSerializedName() {
        return "column";
    }

    @Override
    public Map<String, Object> getParameters() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", columnName);
        params.put("unique", unique);
        params.put("nullable", nullable);
        params.put("insertable", insertable);
        params.put("updatable", updatable);
        params.put("columnDefinition", columnDefinition);
        params.put("table", table);
        params.put("length", length);
        params.put("precision", precision);
        params.put("scale", scale);
        return params;
    }

    @Override
    public void setParameters(Map<String, Object> parameters) {
        if(parameters.containsKey("name"))
            columnName = (String) parameters.get("name");
        if(parameters.containsKey("unique"))
            unique = (Boolean) parameters.get("unique");
        if(parameters.containsKey("nullable"))
            nullable = (Boolean) parameters.get("nullable");
        if(parameters.containsKey("insertable"))
            insertable = (Boolean) parameters.get("insertable");
        if(parameters.containsKey("updatable"))
            updatable = (Boolean) parameters.get("updatable");
        if(parameters.containsKey("columnDefinition"))
            columnDefinition = (String) parameters.get("columnDefinition");
        if(parameters.containsKey("table"))
            table = (String) parameters.get("table");
        if(parameters.containsKey("length"))
            length = (Integer) parameters.get("length");
        if(parameters.containsKey("precision"))
            precision = (Integer) parameters.get("precision");
        if(parameters.containsKey("scale"))
            scale = (Integer) parameters.get("scale");
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isInsertable() {
        return insertable;
    }

    public void setInsertable(boolean insertable) {
        this.insertable = insertable;
    }

    public boolean isUpdatable() {
        return updatable;
    }

    public void setUpdatable(boolean updatable) {
        this.updatable = updatable;
    }

    public String getColumnDefinition() {
        return columnDefinition;
    }

    public void setColumnDefinition(String columnDefinition) {
        this.columnDefinition = columnDefinition;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public Annotation getAnnotation(ConstPool constPool) {
        Annotation annotation = new Annotation(Column.class.getName(), constPool);
        if (!"".equals(columnName))
            annotation.addMemberValue("name", new StringMemberValue(columnName, constPool));
        if (unique)
            annotation.addMemberValue("unique", new BooleanMemberValue(unique, constPool));
        if (!nullable)
            annotation.addMemberValue("nullable", new BooleanMemberValue(nullable, constPool));
        if (!insertable)
            annotation.addMemberValue("insertable", new BooleanMemberValue(insertable, constPool));
        if (!updatable)
            annotation.addMemberValue("updatable", new BooleanMemberValue(updatable, constPool));
        if (!"".equals(columnDefinition))
            annotation.addMemberValue("columnDefinition", new StringMemberValue(columnDefinition, constPool));
        if (!"".equals(table))
            annotation.addMemberValue("table", new StringMemberValue(table, constPool));
        if (length != 255)
            annotation.addMemberValue("length", new IntegerMemberValue(constPool, length));
        if (precision != 0)
            annotation.addMemberValue("precision", new IntegerMemberValue(constPool, precision));
        if (scale != 0)
            annotation.addMemberValue("scale", new IntegerMemberValue(constPool, scale));
        return annotation;
    }


    @Override
    public void annotateSource(JAnnotatable definedClass) {
        JAnnotationUse annotationUse = definedClass.annotate(Column.class);
        if (!"".equals(columnName))
            annotationUse.param("name", columnName);
        if (unique)
            annotationUse.param("unique", unique);
        if (!nullable)
            annotationUse.param("nullable", nullable);

        if (!insertable)
            annotationUse.param("insertable", insertable);
        if (!updatable)
            annotationUse.param("updatable", updatable);

        if (!"".equals(columnDefinition))
            annotationUse.param("columnDefinition", columnDefinition);
        if (!"".equals(table))
            annotationUse.param("table", table);
        if (length != 255)
            annotationUse.param("length", length);
        if (precision != 0)
            annotationUse.param("precision", precision);
        if (scale != 0)
            annotationUse.param("scale", scale);
    }
}
