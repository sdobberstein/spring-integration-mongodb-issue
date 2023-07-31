# Spring Integration MongoDb Issue

## Issue
The MongoDB Message Stores provided in Spring Integration do not honor the `spring.data.mongodb.auto-index-creation` property.   Even if you turn off this property, it will still create indexes for the Message Store.

When working with AWS DocumentDB this causes issues because the auto-generated index names end up being too long and you receive the following error:
```
{
    "ok": 0,
    "code": 67,
    "errmsg": "namespace name generated from index name is too long",
    "operationTime": {
        "$timestamp": {
            "t": 1690812708,
            "i": 1
        }
    }
}
```

## Expectation
The `spring.data.mongodb.auto-index-creation` should be applied consistently.   If we ask for it to not auto-create indexes, then no indexes should be auto-created.

## This Repo

### Prerequisites
1. This repo assumes you have a locally running MongoDB on the default port (27017).

### To run
Run the following command:
```
mvn spring-boot:run
```

Because this repository has the bare minimum code, it'll shut down automatically after initializing and creating the MongoDB indexes.

### To verify
1. Log into your local MongoDB
2. Change to the example db: `use issueExample;`
3. Check the indexes on the `channelMessages` collection: `db.channelMessages.getIndexes();`
4. Verify the indexes were created, even though we said not to auto create them.  You should see something similar to:
```
rs0 [direct: primary] issueExample> db.channelMessages.getIndexes();
[
  { v: 2, key: { _id: 1 }, name: '_id_' },
  { v: 2, key: { messageId: 1 }, name: 'messageId_1' },
  {
    v: 2,
    key: { groupId: 1, messageId: 1 },
    name: 'groupId_1_messageId_1',
    unique: true
  },
  {
    v: 2,
    key: { groupId: 1, lastModifiedTime: -1, sequence: -1 },
    name: 'groupId_1_lastModifiedTime_-1_sequence_-1'
  },
  {
    v: 2,
    key: { groupId: 1, priority: -1, lastModifiedTime: 1, sequence: 1 },
    name: 'groupId_1_priority_-1_lastModifiedTime_1_sequence_1'
  }
]
```
