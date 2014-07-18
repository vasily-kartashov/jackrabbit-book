package com.kartashov.jackrabbit.cookbook;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import org.apache.jackrabbit.commons.cnd.CndImporter;

import javax.jcr.Session;
import java.io.File;
import java.io.FileReader;

public class NorthwindImporter {

    public void importData(Session session) throws Exception
    {
        File cndFile = new File(getClass().getResource("/northwind.cnd").toURI());
        CndImporter.registerNodeTypes(new FileReader(cndFile), session);

        File accessFile = new File(getClass().getResource("/northwind.accdb").toURI());
        Database db = DatabaseBuilder.open(accessFile);

        for (String tableName : db.getTableNames()) {
            System.out.println(tableName);
        }
    }
}
