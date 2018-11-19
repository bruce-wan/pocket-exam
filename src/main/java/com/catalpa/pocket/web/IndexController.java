package com.catalpa.pocket.web;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by wanchuan01 on 2018/10/22.
 */
@Log4j2
@Controller
public class IndexController {

    @GetMapping(value = {"/", "/index"})
    public String indexPage() {
        return "index";
    }

    @GetMapping(value = {"/{filename}.sql"})
    public String sqlPage(@PathVariable String filename, Model model) {
        System.out.println(filename);

        File file = new File("E:\\test\\create_table.sql");

        StringBuffer content = new StringBuffer();
        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String text = null;
            while ((text = bufferedReader.readLine()) != null) {
                content.append(text);
                content.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(content);
        model.addAttribute("content", content);

        return "test";
    }
}
