package com.mock.controller;

import com.mock.domain.Person;
import com.ycwu.sqltemplate.core.SqlTemplate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/demo")
public class DemoController {

  private final Logger logger = LoggerFactory.getLogger(DemoController.class);

  @Autowired
  private SqlTemplate sqlTemplate;

  @RequestMapping("/index")
  public String index(Model model) throws IOException {
    Person single = new Person("aa",11);

    List<Person> people=new ArrayList<Person>();

    people.add(new Person("xx",11));
    people.add(new Person("yy",22));
    people.add(new Person("zz",33));

    model.addAttribute("singlePerson",single);
    model.addAttribute("people",people);

    return "index";
  }

  @RequestMapping("/test")
  @ResponseBody
  public ResponseEntity<String> test() throws IOException {
    Map<String,Object> paramMap=new HashMap<String,Object>();
    paramMap.put("username","吴永冲");
    String sql=sqlTemplate.getScrollSql("test_sqltemplate_id",new Object[]{1,""},paramMap,0,20);
    logger.info(sql);
    return ResponseEntity.ok().body(sql);
  }

}
