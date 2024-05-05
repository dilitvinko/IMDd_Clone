package com.my.project.imdd_clone.aop;

import com.my.project.imdd_clone.model.mongo.Audit;
import com.my.project.imdd_clone.repository.mongo.AuditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditRepository auditRepository;

    @After("within(com.my.project.imdd_clone.service..*)")
    public void after(JoinPoint jp){
        log.debug("Aspect After service method");
        Audit audit = new Audit();
        audit.setCreatedOn(LocalDateTime.now());
        audit.setAction(jp.getSignature().getDeclaringType().getSimpleName() + " : " + jp.getSignature().getName());
        audit.setMessage(jp.toString());
        auditRepository.save(audit);
    }
}
