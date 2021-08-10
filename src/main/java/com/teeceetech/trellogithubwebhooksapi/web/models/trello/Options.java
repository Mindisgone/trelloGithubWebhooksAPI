package com.teeceetech.trellogithubwebhooksapi.web.models.trello;

import java.util.List;

public class Options {
    public List<Term> terms;
    public List<Object> modifiers;
    public List<String> modelTypes;
    public boolean partial;

    public Options() {
    }

    public List<Term> getTerms() {
        return terms;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }

    public List<Object> getModifiers() {
        return modifiers;
    }

    public void setModifiers(List<Object> modifiers) {
        this.modifiers = modifiers;
    }

    public List<String> getModelTypes() {
        return modelTypes;
    }

    public void setModelTypes(List<String> modelTypes) {
        this.modelTypes = modelTypes;
    }

    public boolean isPartial() {
        return partial;
    }

    public void setPartial(boolean partial) {
        this.partial = partial;
    }
}
