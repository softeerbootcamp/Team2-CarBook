package softeer.carbook.infra.aws;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class S3ConfigTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(S3Config.class);
    @Test
    @DisplayName("S3 빈 출력하기")
    void amazonS3Client() {
        List<String> beans = new ArrayList<>();
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for(String name : beanDefinitionNames){
            BeanDefinition beanDefinition = ac.getBeanDefinition(name);

            // ROLE_APPLICATION: 직접 등록한 빈
            // ROLE_INFRASTRUCTURE: 스프링이 내부에서 사용하는 빈
            if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION){
                Object bean = ac.getBean(name);
                beans.add(name);
                System.out.println("name = " + name + " object = " + bean);
            }
        }
        assertThat(beans.get(0)).isEqualTo("s3Config");
        assertThat(beans.get(1)).isEqualTo("amazonS3Client");
    }
}
