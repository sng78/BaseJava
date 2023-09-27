package io.github.sng78.webapp.storage;

import io.github.sng78.webapp.storage.serializer.JsonStreamSerializer;

public class JsonPathStorageTest extends AbstractStorageTest {
    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new JsonStreamSerializer()));
    }
}
