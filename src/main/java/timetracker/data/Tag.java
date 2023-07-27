package timetracker.data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Tag {

    private final int tagID;

    private String name;

    private Tag parent;

    private final List<Tag> childTags;

    private Color color;

    public Tag(int tagID, String name, Tag parent, Color color) {
        this.tagID = tagID;
        GlobalVariables.TAG_MAP.put(tagID, this);

        if(parent != null) parent.addChildTag(this);

        this.name = name;
        this.parent = parent;
        this.childTags = new ArrayList<>();
        this.color = color;
    }

    public int getTagID() {
        return tagID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tag getParent() {
        return parent;
    }

    public void setParent(Tag parent) {
        this.parent = parent;
    }

    public List<Tag> getChildTags() {
        return childTags;
    }

    public void addChildTag(Tag tag) {
        childTags.add(tag);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
