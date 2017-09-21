
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.junit.Before;
import org.junit.Test;

import com.zfm.start.two.BlogMapper;
import com.zfm.start.two.MyDataSourceFactory;

import entity.Blog;

public class testStart {

	
	@Test
	public void test() throws IOException {
		
		Properties properties = Resources.getResourceAsProperties("db.properties"); 
		
		String config = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(config);
		
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, properties);
		System.out.println(sqlSessionFactory);
		
		SqlSession session = sqlSessionFactory.openSession();
		System.out.println(session);
		try {
			Blog blog = (Blog)session.selectOne("BlogMapper.selectBlog", 1);
			System.out.println(blog);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		
	}
	
	@Test
	public void test2() throws Exception {
		Properties properties = Resources.getResourceAsProperties("db.properties");
		PooledDataSourceFactory dataSourceFactory = new PooledDataSourceFactory();
		//加载jdbc配置
		dataSourceFactory.setProperties(properties);
		//获取数据源
		DataSource dataSource = dataSourceFactory.getDataSource();
		
		//事务管理器
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		Environment environment = new Environment("environment", transactionFactory, dataSource);
		//配置实例
		Configuration configuration = new Configuration(environment);
		//加载映射接口类
		configuration.addMapper(BlogMapper.class);
		//创建 SqlSessionFactory
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
		TypeAliasRegistry registry = new TypeAliasRegistry();
		System.out.println(sqlSessionFactory);
		
		SqlSession session = sqlSessionFactory.openSession();
		BlogMapper mapper = session.getMapper(BlogMapper.class);
		Blog blog = mapper.selectBlog(1);
		System.out.println(blog);
	}

}
