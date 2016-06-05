package com.example.iqrakhan.studentapplication;

/**
 * Created by Iqra Khan on 6/2/2016.
 */
public class Classes {
    Integer classId;
    String deptName;
    String program;
    String className;
    String semester;
    String session;


    public Classes() {
    }

    public Classes(Integer classId, String deptName, String program, String className, String semester, String session) {
        this.classId = classId;
        this.deptName = deptName;
        this.program = program;
        this.className = className;
        this.semester = semester;
        this.session = session;
    }

    public Classes(Integer classId, String className, String semester, String session) {
        this.classId = classId;
        this.className = className;
        this.semester = semester;
        this.session = session;
    }


    public String getClassName() {
        return className;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
