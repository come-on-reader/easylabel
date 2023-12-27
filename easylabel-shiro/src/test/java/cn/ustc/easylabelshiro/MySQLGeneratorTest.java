package cn.ustc.easylabelshiro;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;

import java.util.Collections;

/**
 * MySQL 代码生成
 *
 * @author lanjerry
 * @since 3.5.3
 */
public class MySQLGeneratorTest {

    @Test
    public void testSimple() {
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/shirojwt?serverTimezone=Asia/Shanghai", "root", "032022")
                .globalConfig(builder -> {
                    builder.author("reader") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
                            .outputDir("/home/ubuntu/java/build"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("cn.ustc.easylabelshiro") // 设置父包名
                            .moduleName("easylabelshiro") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "/home/ubuntu/java/xml")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("permission,role,role_permission,user,user_role") // 设置需要生成的表名
//                            .addTablePrefix("t_", "c_") // 设置过滤表前缀
                    ;
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}