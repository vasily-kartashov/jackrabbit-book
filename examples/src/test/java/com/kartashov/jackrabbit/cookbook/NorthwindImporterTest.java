package com.kartashov.jackrabbit.cookbook;

import com.healthmarketscience.jackcess.Database;
import org.junit.Test;

public class NorthwindImporterTest extends AbstractTest {

    @Test
    public void testImportData() throws Exception {
        NorthwindImporter importer = new NorthwindImporter();
        importer.importData(session);

    }
}
