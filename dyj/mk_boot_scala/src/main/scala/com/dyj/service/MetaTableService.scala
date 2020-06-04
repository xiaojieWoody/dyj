package com.dyj.service

import com.dyj.domain.MetaTable
import com.dyj.repository.MetaTableRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MetaTableService @Autowired()(metaTableRepository: MetaTableRepository) {

  def save(metaTable:MetaTable) = {
    metaTableRepository.save(metaTable)
  }

  def query() = {
    metaTableRepository.findAll()
  }
}
