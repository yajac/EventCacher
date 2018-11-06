package org.yajac.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.yajac.BaseTestClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PersistCacheManagerTest extends BaseTestClass{

    protected AmazonDynamoDB dynamoDB;
    protected PersistCacheManager persistCacheManager;

    private final String keyName = "keyName";
    private final String keyValue = "value";
    private final String table = "table";

    @Before
    public void setupDatabase(){
        dynamoDB = DynamoDBEmbedded.create().amazonDynamoDB();
        persistCacheManager = new PersistCacheManager(dynamoDB, keyName);
        createTable(dynamoDB, table, keyName);
    }


    protected static CreateTableResult createTable(AmazonDynamoDB ddb, String tableName, String hashKeyName) {
        List<AttributeDefinition> attributeDefinitions = new ArrayList<>();
        attributeDefinitions.add(new AttributeDefinition(hashKeyName, ScalarAttributeType.S));

        List<KeySchemaElement> ks = new ArrayList<>();
        ks.add(new KeySchemaElement(hashKeyName, KeyType.HASH));

        ProvisionedThroughput provisionedthroughput = new ProvisionedThroughput(1000L, 1000L);

        CreateTableRequest request =
                new CreateTableRequest()
                        .withTableName(tableName)
                        .withAttributeDefinitions(attributeDefinitions)
                        .withKeySchema(ks)
                        .withProvisionedThroughput(provisionedthroughput);

        return ddb.createTable(request);
    }

    @Test
    public void testInsertAndRetrieve() throws Exception {
        String newJson = "{\"" + keyName + "\":\""+ keyValue +  "\"}";
        Collection<Item> newItems = new ArrayList<>();
        Item item = Item.fromJSON(newJson);
        newItems.add(item);
        persistCacheManager.insertItems(table, newItems);
        List<String> items = persistCacheManager.getItemsForKey(table, keyValue);
        Assert.assertTrue(items.size() == 1);
        String json = items.get(0);
        Assert.assertEquals(json, newJson);

    }


}