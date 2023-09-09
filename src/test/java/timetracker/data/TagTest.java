package timetracker.data;

import org.junit.jupiter.api.Test;
import timetracker.API.DataReader;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class TagTest {

    @Test
    void remove() {
        Tag parent = new Tag(-1, "JUnitTestParent", null, null);
        Tag tag = new Tag(-2, "JUnitTest", parent, null);
        Tag subTag = new Tag(-3, "JUnitTestSub", tag, null);

        tag.remove();
        assertFalse(parent.getChildTags().contains(tag));
        assertNull(subTag.getParent());
    }

    @Test
    void Global() {
        Tag tag = new Tag(-2, "JUnitTest", null, null);
        tag.addGlobal();
        assertTrue(GlobalVariables.ID_TO_TAG_MAP.containsValue(tag));
        assertTrue(GlobalVariables.NAME_TO_TAG_MAP.containsKey(tag.getName()));

        tag.removeGlobal();
        assertFalse(GlobalVariables.ID_TO_TAG_MAP.containsValue(tag));
        assertFalse(GlobalVariables.NAME_TO_TAG_MAP.containsKey(tag.getName()));
    }

    @Test
    void Database() {
        Tag tag = new Tag(-1, "JUnitTest", null, null);
        DataReader dataReader = new DataReader();
        tag.writeDatabase();
        assertTrue(dataReader.readAllTags().contains(tag));
        tag.setName("JUnitTestUpdate");
        tag.updateDatabase();
        dataReader.readInTagsToGlobal();
        assertTrue(GlobalVariables.NAME_TO_TAG_MAP.containsKey("JUnitTestUpdate"));
        tag.removeDatabase();
        tag.remove();
        assertFalse(dataReader.readAllTags().contains(tag));
    }

    @Test
    void Name() {
        Tag tag = new Tag(-2, "JUnitTest", null, null);
        assertEquals("JUnitTest", tag.getName());
        tag.setName("JUnitTestUpdate");
        assertEquals("JUnitTestUpdate", tag.getName());
    }

    @Test
    void Parent() {
        Tag parent = new Tag(-1, "JUnitTestParent", null, null);
        Tag tag = new Tag(-2, "JUnitTest", parent, null);
        assertEquals(parent, tag.getParent());
        tag.setParent(null);
        assertNull(tag.getParent());
    }

    @Test
    void ChildTag() {
        Tag tag = new Tag(-2, "JUnitTest", null, null);
        Tag subTag = new Tag(-3, "JUnitTestSub", tag, null);
        assertTrue(tag.getChildTags().contains(subTag));
        tag.removeChildTag(subTag);
        assertFalse(tag.getChildTags().contains(subTag));
        tag.addChildTag(subTag);
        assertTrue(tag.getChildTags().contains(subTag));
    }

    @Test
    void Color() {
        Tag tag = new Tag(-2, "JUnitTest", null, null);
        assertEquals(Color.WHITE, tag.getColor());
        tag.setColor(Color.BLUE);
        assertEquals(Color.BLUE, tag.getColor());
    }
}