// MongoDB initialization script
db = db.getSiblingDB('timemanagement');

// Create collections with indexes
db.createCollection('users');
db.users.createIndex({ "email": 1 }, { unique: true });

db.createCollection('categories');
db.categories.createIndex({ "userId": 1 });

db.createCollection('tasks');
db.tasks.createIndex({ "userId": 1 });
db.tasks.createIndex({ "categoryId": 1 });
db.tasks.createIndex({ "dueDate": 1 });

db.createCollection('time_entries');
db.time_entries.createIndex({ "userId": 1 });
db.time_entries.createIndex({ "taskId": 1 });
db.time_entries.createIndex({ "startTime": 1 });

print('Database initialized successfully!');
