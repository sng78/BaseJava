package io.github.sng78.webapp.storage;

public class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    public void overflow() {
    }
}
