db.createUser(
    {
        user: "mongoadmin",
        pwd: "mongopass",
        roles: [
            {
                role: "readWrite",
                db: "imdd-mongo"
            }
        ]
    }
);
db.auditLogs.insertOne({"message": "mongo DB created", "date": Date()});