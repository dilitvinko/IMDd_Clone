package com.my.project.imdd_clone.repository.mongo;

import com.my.project.imdd_clone.model.mongo.Audit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends MongoRepository<Audit, String> {
}
