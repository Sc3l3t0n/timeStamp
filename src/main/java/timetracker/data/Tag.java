package timetracker.data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a tag.
 * A tag has a name, a parent, child tags and a color.
 *
 * @author Marlon Rosenberg
 * @version 0.2
 */
public class Tag {

    // Attributes

    /**
     * The unique ID of the tag.
     */
    private final int tagID;

    /**
     * The name of the tag.
     */
    private String name;

    /**
     * The parent of the tag.
     */
    private Tag parent;

    /**
     * The child tags of the tag.
     */
    private final List<Tag> childTags;

    /**
     * The color of the tag.
     */
    private Color color;


    /**
     * Creates a new tag with the given name, parent, child tags and color.
     * If the parent is not null, the tag will be added to the parent's child tags.
     *
     * @param tagID The unique ID of the tag.
     * @param name The name of the tag.
     * @param parent The parent of the tag.
     * @param color The color of the tag.
     */
    public Tag(int tagID, String name, Tag parent, Color color) {
        this.tagID = tagID;
        GlobalVariables.TAG_MAP.put(tagID, this);

        if(parent != null) parent.addChildTag(this);

        this.name = name;
        this.parent = parent;
        this.childTags = new ArrayList<>();
        this.color = color;
    }

    // Setter and Getter

    /**
     * Returns the unique ID of the tag.
     *
     * @return The unique ID of the tag.
     */
    public int getTagID() {
        return tagID;
    }

    /**
     * Returns the name of the tag.
     *
     * @return The name of the tag.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the tag.
     *
     * @param name The name of the tag.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the parent of the tag.
     *
     * @return The parent of the tag.
     */
    public Tag getParent() {
        return parent;
    }

    /**
     * Sets the parent of the tag.
     *
     * @param parent The parent of the tag.
     */
    public void setParent(Tag parent) {
        this.parent = parent;
    }

    /**
     * Returns the child tags of the tag.
     *
     * @return The child tags of the tag.
     */
    public List<Tag> getChildTags() {
        return childTags;
    }

    /**
     * Adds a child tag to the tag.
     *
     * @param tag The child tag to be added.
     */
    public void addChildTag(Tag tag) {
        childTags.add(tag);
    }

    /**
     * Returns the color of the tag.
     *
     * @return The color of the tag.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color of the tag.
     *
     * @param color The color of the tag.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    // Utility

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return tagID == tag.tagID;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tagID=" + tagID +
                ", name='" + name + '\'' +
                ", parent=" + parent +
                ", color=" + color +
                '}';
    }
}
