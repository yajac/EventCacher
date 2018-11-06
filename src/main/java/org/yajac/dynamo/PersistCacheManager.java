package org.yajac.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

import java.util.*;

public class PersistCacheManager {

	private AmazonDynamoDB amazonDynamoDB;
	private String dynamoDBKey;

	public PersistCacheManager(AmazonDynamoDB amazonDynamoDB, final String key){
		this.amazonDynamoDB = amazonDynamoDB;
		this.dynamoDBKey = key;
	}

	public List<String> getItemsForKey(final String tableName, final String keyValue) {
		List<String> events = new ArrayList<>();
		DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
		Table table = dynamoDB.getTable(tableName);
		QuerySpec spec = new QuerySpec()
				.withKeyConditionExpression(dynamoDBKey + " = :value")
				.withScanIndexForward(false)
				.withValueMap(new ValueMap()
						.withString(":value", keyValue));

		ItemCollection<QueryOutcome> items = table.query(spec);
		for(Item item : items){
			events.add(item.toJSON());
		}
		return events;
	}

	public void insertItems(final String tableName, final Collection<Item> items){
		DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
		Table table = dynamoDB.getTable(tableName);
		for(Item item : items) {
			table.putItem(item);
		}
	}

}
