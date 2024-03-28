package com.mu_bball_stats.web;

import java.util.List;
import java.util.ArrayList;

public class Page {
    protected String title;
    protected List<String> scripts;

    public Page(String title) {
        this.title = title;
        this.scripts = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void addScript(String script) {
        scripts.add(script);
    }

    public List<String> getScripts() {
        return scripts;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
