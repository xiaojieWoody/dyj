package com.dyj.controller

import com.dyj.domain.MetaTable
import com.dyj.service.MetaTableService
import com.dyj.utils.ResultVOUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{ModelAttribute, RequestMapping, RequestMethod, ResponseBody, RestController}

@RestController
@RequestMapping(Array("/meta/table"))
class MetaTableController @Autowired()(metaTableService: MetaTableService) {

  @RequestMapping(value = Array("/"), method = Array(RequestMethod.POST))
  @ResponseBody
  def save(@ModelAttribute metaTable: MetaTable) = {
    metaTableService.save(metaTable)
    ResultVOUtils.success()      // scala 调用已有的Java代码
  }

  @RequestMapping(value = Array("/"), method = Array(RequestMethod.GET))
  @ResponseBody
  def query() = {
    ResultVOUtils.success(metaTableService.query())
  }

}
