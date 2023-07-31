package timetracker.data;

import timetracker.API.DataRemover;
import timetracker.API.DataWriter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a tag.
 * A tag is a {@link DataType}
 * A tag has a name, a parent and child tags.
 * A tag can be written to the database, updated in the database and removed from the database.
 * A tag can be added to the global variables, removed from the global variables and updated in the global variables.
 *
 * @author Marlon Rosenberg
 * @version 0.9
 */
public class Tag extends DataType{

    // Attributes

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
     * If parent is not null, the tag will be added to the parent's child tags.
     * If color is null, the color will be set to white.
     *
     * @param id The unique ID of the tag.
     * @param name The name of the tag.
     * @param parent The parent of the tag.
     * @param color The color of the tag.
     */
    public Tag(int id, String name, Tag parent, Color color) {
        super(id);

        if (parent != null) {
            parent.addChildTag(this);
        }

        this.name = name;
        this.parent = parent;
        this.childTags = new ArrayList<>();
        if (color == null) {
            this.color = Color.WHITE;
        } else {
            this.color = color;
        }
    }

    /**
     * Removes the Tag from global variables but not from the database.
     * If the parent is not null, the tag will be removed from the parent's child tags.
     * The parent of the child tags will be set to null.
     */
    public void remove() {
        if(parent != null) parent.removeChildTag(this);
        for (Tag childTag : childTags) {
            childTag.setParent(null);
        }
        removeGlobal();
    }

    // Global methods

    @Override
    public void addGlobal() {
        GlobalVariables.ID_TO_TAG_MAP.put(getID(), this);
        GlobalVariables.NAME_TO_TAG_MAP.put(getName(), this);
    }

    @Override
    public void removeGlobal() {
        GlobalVariables.ID_TO_TAG_MAP.remove(getID());
        GlobalVariables.NAME_TO_TAG_MAP.remove(getName());
    }

    // Database methods

    public void writeDatabase() {
        DataWriter dataWriter = new DataWriter();
        dataWriter.writeTag(this);
        dataWriter.close();
    }

    public void updateDatabase() {
        DataWriter dataWriter = new DataWriter();
        dataWriter.updateTag(this);
        dataWriter.close();
    }

    public void removeDatabase() {
        DataRemover dataRemover = new DataRemover();
        dataRemover.removeTag(this);
        dataRemover.close();
    }

    // Setter and Getter

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
        GlobalVariables.NAME_TO_TAG_MAP.remove(this.name);
        this.name = name;
        GlobalVariables.NAME_TO_TAG_MAP.put(name, this);
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
     * Removes a child tag from the tag.
     *
     * @param tag The child tag to be removed.
     */
    public void removeChildTag(Tag tag) {
        childTags.remove(tag);
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
    public String toString() {
        return "Tag{" +
                "tagID=" + getID() +
                ", name='" + name + '\'' +
                ", parent=" + parent +
                ", color=" + color +
                '}';
    }
}
