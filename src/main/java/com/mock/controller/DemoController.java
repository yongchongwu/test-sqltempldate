package com.mock.controller;

import com.ycwu.sqltemplate.core.SqlTemplate;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

  private final Logger logger = LoggerFactory.getLogger(DemoController.class);

  @Autowired
  private SqlTemplate sqlTemplate;

  @RequestMapping("/demo")
  public ResponseEntity<String> test() throws IOException {
    Map<String,Object> paramMap=new HashMap<String,Object>();
    paramMap.put("username","吴永冲");
    String sql=sqlTemplate.getScrollSql("test_sqltemplate_id",new Object[]{1,""},paramMap,0,20);
    logger.info(sql);
    return ResponseEntity.ok().body(sql);
  }

}
