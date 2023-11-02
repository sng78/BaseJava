package io.github.sng78.webapp.utils;

import io.github.sng78.webapp.model.Resume;
import io.github.sng78.webapp.model.Section;
import io.github.sng78.webapp.model.TextSection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.github.sng78.webapp.storage.AbstractStorageTest.RESUME_1;

public class JsonParserTest {

    @Test
    public void testResume() {
        String json = JsonParser.writeContent(RESUME_1, Resume.class);
        System.out.println(json);
        Resume resume = JsonParser.readContent(json, Resume.class);
        Assertions.assertEquals(RESUME_1, resume);
    }

    @Test
    public void testSection() {
        Section section1 = new TextSection("Text");
        String json = JsonParser.writeContent(section1, Section.class);
        System.out.println(json);
        Section section2 = JsonParser.readContent(json, Section.class);
        Assertions.assertEquals(section1, section2);
    }
}
