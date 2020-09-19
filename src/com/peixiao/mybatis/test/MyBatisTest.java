package com.peixiao.mybatis.test;

import com.peixiao.mybatis.bean.Employee;
import com.peixiao.mybatis.dao.EmployeeMapper;
import com.peixiao.mybatis.dao.EmployeeMapperAnnotation;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Peixiao Zhao
 * @date 2020/9/18 22:48
 *
 *
 * 1. 接口式编程
 *  原生:         Dao =====> DaoImpl
 *  mybatis：     Mapper ===> xxMapper.xml
 *
 * 2. SqlSession代表和数据库的一次会话，用完必须关掉
 * 3. SqlSession和connection一样都是非线程安全的,不能写成成员变量，每次使用都应该获取新的对象
 * 4. mapper 接口没有实现类，mybatis会为这个接口生成一个代理对象
 *      // EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
 * 5. 两个重要的配置文件，一个mybatis全局配置文件，包含数据库连接池信息。。。。
 *                      sql映射文件，保存每一个sql语句的映射信息
 *                              将sql抽取了出来
 */
public class MyBatisTest {

    // 非线程安全
//    private SqlSession sqlSession;

    /**
     * 1. 根据xml配置文件（全局配置文件）创建一个sqlSessionFactory对象
     * 2. sql映射文件 配置了每一个sql，已经sql的封装规则等
     * 3. 将sql映射文件注册在全局配置文件中
     * 4. 代码
     * @throws IOException
     */
    @Test
    public void test1() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // 2. 获取sqlSession实例 能直接执行已经映射的sql语句
        SqlSession openSession = sqlSessionFactory.openSession();

        // sql唯一标识 ---- xml文件中的id
        // 执行sql的参数
        try{
            Employee employee = (Employee) openSession.selectOne("com.peixiao.mybatis.EmployeeMapper.selectEmp", 1);
            System.out.println(employee);
        } finally {
            openSession.close();
        }
    }

    @Test
    public void test2() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession openSession = sqlSessionFactory.openSession();

        try{
            // 会自动为接口创建一个代理对象，代理对象去执行方法
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee employee = mapper.getEmployee(1);
            System.out.println(mapper.getClass());  // class com.sun.proxy.$Proxy6
            System.out.println(employee);
        }finally {
            openSession.close();
        }
    }

    @Test
    public void test3() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession openSession = sqlSessionFactory.openSession();

        try{
            EmployeeMapperAnnotation mapper = openSession.getMapper(EmployeeMapperAnnotation.class);
            Employee employee = mapper.getEmployee(1);
            System.out.println(mapper.getClass());  // class com.sun.proxy.$Proxy6
            System.out.println(employee);
        } finally {
            openSession.close();
        }
    }
}
