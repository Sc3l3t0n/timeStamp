package timetracker.data;

import org.junit.jupiter.api.Test;
import timetracker.API.DataReader;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    @Test
    void remove() {
        Project parent = new Project(-1, "JUnitTestParent", "An Test Parent Project", null);
        Project project = new Project(-2, "JUnitTest", "An Test Project", parent);
        Project subProject = new Project(-3, "JUnitTestSub", "An Test Sub Project", project);
        Task task = new Task(-3, "JUnitTest", project);

        project.remove();
        assertFalse(parent.getSubProjects().contains(project));
        assertNull(task.getProject());
        assertNull(subProject.getParent());
    }

    @Test
    void Global() {
        Project project = new Project(-2, "JUnitTest", "An Test Project", null);
        project.addGlobal();
        assertTrue(GlobalVariables.ID_TO_PROJECT_MAP.containsValue(project));
        assertTrue(GlobalVariables.NAME_TO_PROJECT_MAP.containsKey(project.getName()));

        project.removeGlobal();
        assertFalse(GlobalVariables.ID_TO_PROJECT_MAP.containsValue(project));
        assertFalse(GlobalVariables.NAME_TO_PROJECT_MAP.containsKey(project.getName()));
    }

    @Test
    void Database() {
        Project project = new Project(-2, "JUnitTest", "An Test Project", null);
        DataReader dataReader = new DataReader();
        project.writeDatabase();
        assertTrue(dataReader.readAllProjects().contains(project));
        project.setName("JUnitTestUpdate");
        project.updateDatabase();
        dataReader.readInProjectsToGlobal();
        assertTrue(GlobalVariables.NAME_TO_PROJECT_MAP.containsKey("JUnitTestUpdate"));
        project.removeDatabase();
        project.remove();
        assertFalse(dataReader.readAllProjects().contains(project));
    }

    @Test
    void Name() {
        Project project = new Project(-2, "JUnitTest", "An Test Project", null);
        assertEquals("JUnitTest", project.getName());
        project.setName("JUnitTestUpdate");
        assertEquals("JUnitTestUpdate", project.getName());
    }

    @Test
    void Description() {
        Project project = new Project(-2, "JUnitTest", "An Test Project", null);
        assertEquals("An Test Project", project.getDescription());
        project.setDescription("An Test Project Update");
        assertEquals("An Test Project Update", project.getDescription());
    }

    @Test
    void Parent() {
        Project parent = new Project(-1, "JUnitTestParent", "An Test Parent Project", null);
        Project project = new Project(-2, "JUnitTest", "An Test Project", parent);
        assertEquals(parent, project.getParent());
        project.setParent(null);
        assertNull(project.getParent());
    }

    @Test
    void Tag() {
        Project project = new Project(-2, "JUnitTest", "An Test Project", null);
        Tag tag = new Tag(-2, "JUnitTestTag", null , null);
        project.addTag(tag);
        assertTrue(project.getTags().contains(tag));
        project.removeTag(tag);
        assertFalse(project.getTags().contains(tag));
    }

    @Test
    void SubProject() {
        Project parent = new Project(-1, "JUnitTestParent", "An Test Parent Project", null);
        Project project = new Project(-2, "JUnitTest", "An Test Project", parent);

        assertTrue(parent.getSubProjects().contains(project));
        parent.removeSubProject(project);
        assertFalse(parent.getSubProjects().contains(project));
    }

    @Test
    void Task() {
        Project project = new Project(-2, "JUnitTest", "An Test Project", null);
        Task task = new Task(-3, "JUnitTest", project);

        assertTrue(project.getTasks().contains(task));
        project.removeTask(task);
        assertFalse(project.getTasks().contains(task));
    }
}