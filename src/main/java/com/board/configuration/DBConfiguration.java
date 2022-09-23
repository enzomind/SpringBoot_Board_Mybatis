package com.board.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration //컨트롤러와 같이 Configuration이 지정된 클래스를 자바 기반 설정 파일로 인식하도록 함.
@PropertySource("classpath:/application.properties") //안해도될것같은데 일단..
@RequiredArgsConstructor
public class DBConfiguration {

    private final ApplicationContext applicationContext;
    //스프링 컨테이너 중 하나로써 Bean의 생성과 사용, 관계, 생명주기등을 관리

    @Bean //Configuration 클래스의 메서드 레벨에서만 지정이 가능한 '객체'를 뜻함.
    //이렇게 지정되면 컨테이너에 의해 관리되는 Bean으로 지정됨.
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    //인자에 prefix 속성을 지정할 수 있는데 prefix 값이 spring.datasource.hikari <-
    //클래스 어노테이션으로 PropertySource 로 지정한 application.properties 에서
    //prefix에 해당하는 spring.datasource.hikari로 시작하는 설정을 모두 읽어들려 메서드에 매핑함.
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }


    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception{
        //해당 객체는 데이터베이스의 커넥션과 SQL의 실행에 대해 중요한 역할을 함.

        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        //SqlsessionFactoryBean이 MyBatis와 Spring의 연동 모듈로 사용됨.

        factoryBean.setDataSource(dataSource());
        //이렇게 데이터를 말아서 세팅하고 이 객체가 아닌 getObject 메서드가 리턴하는 SqlSessionFactory를 생성.

        //프로퍼티에 SELECT 컬럼과 DTO 멤버 변수 매핑하고 나서 Bean을 정의!
        factoryBean.setMapperLocations(applicationContext.getResources("classpath*:/mapper/**/*Mapper.xml"));
        factoryBean.setTypeAliasesPackage("com.board.domain");
        //BoardMapper XML에서 parameterType과 resultType은 클래스의 경로가 표함되어있어야하는데 이 메서드를 사용해 풀 패키지 경로 생략 가능해져 클래스 이름만 지정해 사용 가능
        factoryBean.setConfiguration(mybatisConfig());
        //mybatisConfig() 아래 메서드 <- application.properties에서 mybatis.configuration으로 시작하는 모든 설정을 읽어들여 Bean으로 등록
        return factoryBean.getObject();
    }

    @Bean
    @ConfigurationProperties(prefix = "mybatis.configuration")
    public org.apache.ibatis.session.Configuration mybatisConfig(){
        return new org.apache.ibatis.session.Configuration();
    } //3의 기존의 DBConfiguration 클래스의 변경된 내용은 다음과 같다 <- 부터 start
      //XML 작성 후, 작성한 메서드

    @Bean
    public SqlSessionTemplate sqlSession() throws Exception{
        return new SqlSessionTemplate(sqlSessionFactory());
    }



}
