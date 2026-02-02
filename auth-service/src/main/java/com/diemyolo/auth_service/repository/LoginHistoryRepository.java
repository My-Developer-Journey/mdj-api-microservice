package com.diemyolo.auth_service.repository;

import com.diemyolo.auth_service.entity.LoginHistory;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, UUID> {}
