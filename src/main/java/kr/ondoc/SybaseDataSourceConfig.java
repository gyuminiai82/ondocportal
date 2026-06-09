package kr.ondoc;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.ini4j.Ini;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import kr.ondoc.util.AES256;
import kr.ondoc.util.Dasom2006INI;
import kr.ondoc.util.StNeoSysINI;

@Configuration
@MapperScan(value="kr.ondoc.mapper.sybase", sqlSessionFactoryRef="firstSqlSessionFactory")
public class SybaseDataSourceConfig {
	@Bean
	public DataSource sybaseDataSource() throws IOException {
		Ini dasom2006ini = Dasom2006INI.getDasom2006Ini();
		Ini stNeoSysIni = StNeoSysINI.getStNeoSysIni();
		
		String ip = dasom2006ini.get("OnDocServer", "IP");
		String port = dasom2006ini.get("OnDocServer", "PORT");
		
		String dbpass = stNeoSysIni.get("NeoSysID", "SysXE");
		try {
			dbpass = (new AES256()).decrypt(dbpass);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setJdbcUrl("jdbc:log4jdbc:sybase:Tds:"+ip+":"+port+"?ServiceName=StNeo&CharSet=utf8");
		hikariConfig.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
		hikariConfig.setUsername("dba");
		hikariConfig.setPassword(dbpass);
		hikariConfig.setMaximumPoolSize(3);
		
		//return new HikariDataSource(hikariConfig);
		
		try {
			return new HikariDataSource(hikariConfig);
		} catch (Exception e) {
			// TODO: handle exception
			// sybase db가 실행 안할경우 무한 재시도해서 cpu사용률이 급격히 올라감
			// sybase db 없으면 inmemory 디비에 접속하도록 하여 cpu사용률 올라가는 증상 없앰
			HikariConfig hikariConfig2 = new HikariConfig();
			hikariConfig2.setJdbcUrl("jdbc:hsqldb:mem:testdb");
			hikariConfig2.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
			hikariConfig2.setUsername("XXXX");
			hikariConfig2.setPassword("XXXX");
			
			return new HikariDataSource(hikariConfig2);
		}
	}
	
	@Bean 
	public SqlSessionFactory firstSqlSessionFactory(DataSource sybaseDataSource, ApplicationContext applicationContext) throws Exception 
	{
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(sybaseDataSource);
		
		return sqlSessionFactoryBean.getObject();
	}
}