package com.github.peggybrown.speechrank;


import com.google.common.collect.Maps;
import javaslang.collection.List;
import lombok.extern.java.Log;

import java.util.Map;

@Log
public class Storage {
    private List<Conference> conferences;
    private List<Year> years;

    public Storage() {
        conferences = List.empty();
        years = List.empty();
    }

    public void add(Rate rate) {
        log.info("Rate added");
        Presentation presentation = conferences
                .flatMap(Conference::getPresentations)
                .find(prez -> prez.getId().equals(rate.getPresentationId())).get();
        presentation.addRate(rate);
    }

    public void add(Comment comment) {
        log.info("Comment added");
        conferences
                .flatMap(Conference::getPresentations)
                .find(prez -> prez.getId().equals(comment.getPresentationId()))
                .map(prez -> prez.addComment(comment));

    }

    public void add(String year, Conference conf) {
        log.info("Conference added" + conf.toString());
        conferences = conferences.append(conf);
        years.filter(y -> y.getYear().equals(year))
                .map(y -> y.addConference(conf));

    }

    public java.util.List<Conference> getConferences() {
        return conferences.toJavaList();

    }

    public List<Year> getYears() {
        return years;
    }

    public Conference getConference(String id) {
        //TODO what to return whe conference not found?
        return conferences.find(conf -> conf.getId().equals(id)).get();
    }

    public void addYear(String year) {
        years = years.append(new Year(year));
    }
}