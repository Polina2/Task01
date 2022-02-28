package ru.vsu.cs.aisd2022.g92.lyigina_p_s;

import java.util.ArrayList;

public class Document {
    private ArrayList<Paragraph> paragraphs;
    private ArrayList<Style> styles;
    private int width;

    public Document() {
        this.paragraphs = new ArrayList<>();
        this.styles = new ArrayList<>();
        this.width = 40;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int w) {
        this.width = w;
    }

    public ArrayList<Paragraph> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(ArrayList<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }

    public void addParagraph() {
        paragraphs.add(new Paragraph());
    }

    public void deleteParagraph(int index) {
        paragraphs.remove(index);
    }

    public ArrayList<Style> getStyles() {
        return styles;
    }

    public void setStyles(ArrayList<Style> styles) {
        this.styles = styles;
    }

    public void addStyle() {
        styles.add(new Style());
    }

    public void deleteStyle(int index) {
        styles.remove(index);
    }

    public String[] toText() {
        String[] res = new String[paragraphs.size()];
        for (int i = 0; i < paragraphs.size(); i++) {
            res[i] = paragraphs.get(i).forPrinting(width);
        }
        return res;
    }
}
