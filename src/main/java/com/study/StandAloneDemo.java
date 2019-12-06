package com.study;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.study.entity.Blog;
import com.study.mapper.BlogMapper;

public class StandAloneDemo {

	public static void main(String[] args) throws IOException {

		String resource = "mybatis-standalone-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

		// 全限定名
		// queryByFQN(sqlSessionFactory);

		// 接口方式查询
		queryByInterface(sqlSessionFactory);
		// 再次调用测试二级缓存
		queryByInterface(sqlSessionFactory);
	}

	static void queryByInterface(SqlSessionFactory sqlSessionFactory) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			BlogMapper blogMapper = session.getMapper(BlogMapper.class);
			Blog blog = blogMapper.selectBlog(1);
			System.out.println(blog);

			blog = blogMapper.selectBlog(1);
			System.out.println(blog);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void queryByFQN(SqlSessionFactory sqlSessionFactory) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			Blog blog = (Blog) session.selectOne("com.study.mapper.BlogMapper.selectBlog", 1);
			System.out.println(blog);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
