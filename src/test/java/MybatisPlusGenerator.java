import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

/**
 * Created by bruce on 2018/7/26.
 */
public class MybatisPlusGenerator {

    @Test
    public void generatorCode() {
        AutoGenerator autoGenerator = new AutoGenerator();

        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir("E:\\test\\mp-code");
        globalConfig.setFileOverride(true);
        globalConfig.setActiveRecord(false);
        globalConfig.setEnableCache(false);
        globalConfig.setBaseResultMap(true);
        globalConfig.setBaseColumnList(true);
        autoGenerator.setGlobalConfig(globalConfig);

        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setTypeConvert(new MySqlTypeConvert() {
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                System.out.println("转换类型：" + fieldType);
                return super.processTypeConvert(fieldType);
            }
        });
        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("rootroot");
        dataSourceConfig.setUrl("jdbc:mysql://127.0.0.1/pocket_exam");
        autoGenerator.setDataSource(dataSourceConfig);

        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setTablePrefix("t_");
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setInclude("t_user_info");
        strategyConfig.setLogicDeleteFieldName("del_flg");
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setRestControllerStyle(true);
        strategyConfig.setSuperEntityClass("com.catalpa.pocket.entity.BaseEntity");
        strategyConfig.setSuperEntityColumns("del_flg", "created_date", "updated_date");
        autoGenerator.setStrategy(strategyConfig);

        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.catalpa.pocket");
        packageConfig.setXml("mapping");
        packageConfig.setController("controller");
        autoGenerator.setPackageInfo(packageConfig);


        autoGenerator.execute();

    }
}
