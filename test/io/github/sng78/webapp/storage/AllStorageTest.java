package io.github.sng78.webapp.storage;

//import org.junit.runner.RunWith;
//import org.junit.runners.Suite;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

//@RunWith(Suite.class)
//@Suite.SuiteClasses({
//        ArrayStorageTest.class,
//        SortedArrayStorageTest.class,
//        ListStorageTest.class,
//        MapResumeStorageTest.class,
//        MapUuidStorageTest.class
//})
@Suite
@SelectClasses({
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        MapResumeStorageTest.class,
        MapUuidStorageTest.class
})
public class AllStorageTest {
}
