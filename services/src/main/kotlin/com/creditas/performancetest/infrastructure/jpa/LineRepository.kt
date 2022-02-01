package com.creditas.performancetest.infrastructure.jpa

import com.creditas.performancetest.infrastructure.entity.LineEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface LineRepository : JpaRepository<LineEntity, UUID>
