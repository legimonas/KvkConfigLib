package com.kvk.config.javassist;

import java.util.ArrayList;
import java.util.List;

public class EntityClass {
    private EntityAnnotationInfo entityAnnotationInfo;
    private TableAnnotationInfo tableAnnotationInfo;
    private List<MemberInfo> membersInfo;
    private String className;

    public EntityClass() {

    }

    public String getClassName() {
        if(className != null)
        return className;
        else {
            String name = tableAnnotationInfo.name;
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public EntityClass(String className, EntityAnnotationInfo entityAnnotationInfo, TableAnnotationInfo tableAnnotationInfo, List<MemberInfo> membersInfo) {
        this.entityAnnotationInfo = entityAnnotationInfo;
        this.tableAnnotationInfo = tableAnnotationInfo;
        this.membersInfo = membersInfo;
    }

    public EntityAnnotationInfo getEntityAnnotationInfo() {
        return entityAnnotationInfo;
    }

    public void setEntityAnnotationInfo(EntityAnnotationInfo entityAnnotationInfo) {
        this.entityAnnotationInfo = entityAnnotationInfo;
    }

    public TableAnnotationInfo getTableAnnotationInfo() {
        return tableAnnotationInfo;
    }

    public void setTableAnnotationInfo(TableAnnotationInfo tableAnnotationInfo) {
        this.tableAnnotationInfo = tableAnnotationInfo;
    }

    public List<MemberInfo> getMembersInfo() {
        return membersInfo;
    }

    public void setMembersInfo(List<MemberInfo> membersInfo) {
        this.membersInfo = membersInfo;
    }

    public EntityClass(String className, EntityAnnotationInfo entityAnnotationInfo, TableAnnotationInfo tableAnnotationInfo) {
        this.className = className;
        this.entityAnnotationInfo = entityAnnotationInfo;
        this.tableAnnotationInfo = tableAnnotationInfo;
        membersInfo = new ArrayList<>();
    }


}
