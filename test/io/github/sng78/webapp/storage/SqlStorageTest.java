package io.github.sng78.webapp.storage;

import io.github.sng78.webapp.Config;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(Config.get().getStorage());
    }
}
