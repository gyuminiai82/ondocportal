package kr.ondoc.controller.common;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.mapper.sybase.common.UsrmngrMapper;

@CrossOrigin(origins = "*")
@Controller
public class HomeController {
    
    // /ondoccrm 과 /ondoccrm/ 만 처리
    @GetMapping({"/ondoccrm", "/ondoccrm/"})
    public String index() {
        return "forward:/ondoccrm/index.html";
    }
}