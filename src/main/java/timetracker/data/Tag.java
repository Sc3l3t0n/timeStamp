package timetracker.data;

import java.awt.*;

/**
 * This class represents a tag.
 * A tag has a name, a parent and a color.
 *
 * @version 0.1
 */
public class Tag {

    private final int tagID;

    private String name;

    private Tag parent;

    private Color color;

    public Tag(String name, Tag parent, Color color) {
        this.tagID = GlobalVariables.TAG_ID;
        GlobalVariables.TAG_ID += 1;
        this.name = name;
        this.parent = parent;
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
