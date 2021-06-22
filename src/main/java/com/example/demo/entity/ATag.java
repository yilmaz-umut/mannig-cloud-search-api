package com.example.demo.entity;

import java.util.List;

public class ATag {

    private List<String> sementiy_list;
    private List<String> semld_list;
    private List<String> verb_list;
    private List<String>  subject_list;

    public List<String> getSementiy_list() {
        return sementiy_list;
    }

    public void setSementiy_list(List<String> sementiy_list) {
        this.sementiy_list = sementiy_list;
    }

    public List<String> getSemld_list() {
        return semld_list;
    }

    public void setSemld_list(List<String> semld_list) {
        this.semld_list = semld_list;
    }

    public List<String> getVerb_list() {
        return verb_list;
    }

    public void setVerb_list(List<String> verb_list) {
        this.verb_list = verb_list;
    }

    public List<String> getSubject_list() {
        return subject_list;
    }

    public void setSubject_list(List<String> subject_list) {
        this.subject_list = subject_list;
    }
}
