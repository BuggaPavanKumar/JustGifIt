package andre.artus.com

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration
import org.springframework.boot.autoconfigure.websocket.WebSocketAutoConfiguration
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.web.filter.HiddenHttpMethodFilter
import org.springframework.web.filter.HttpPutFormContentFilter
import org.springframework.web.filter.RequestContextFilter
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import java.io.File
import javax.annotation.PostConstruct

@Suppress("SpringKotlinAutowiring")
@SpringBootApplication(
    exclude = arrayOf(JacksonAutoConfiguration::class,
        JmxAutoConfiguration::class,
        WebSocketAutoConfiguration::class))
open class JustGifIt {
  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      SpringApplication.run(JustGifIt::class.java, *args)
    }
  }

  @Value("\${spring.http.multipart.location}/gif/")
  private lateinit var gifLocation: String

  @PostConstruct
  private fun init() {
    val gifFolder = File(gifLocation)
    if (!gifFolder.exists()) {
      gifFolder.mkdir()
    }
  }

  @Bean
  open fun deRegisterHiddenHttpMethodFilter(filter: HiddenHttpMethodFilter): FilterRegistrationBean {
    val bean = FilterRegistrationBean(filter)
    bean.isEnabled = false
    return bean
  }

  @Bean
  open fun deRegisterHttpPutFormContentFilter(filter: HttpPutFormContentFilter): FilterRegistrationBean {
    val bean = FilterRegistrationBean(filter)
    bean.isEnabled = false
    return bean
  }

  @Bean
  open fun deRegisterRequestContextFilter(filter: RequestContextFilter): FilterRegistrationBean {
    val bean = FilterRegistrationBean(filter)
    bean.isEnabled = false
    return bean
  }

  @Bean
  open fun webMvcConfigurer(): WebMvcConfigurer {
    return object : WebMvcConfigurerAdapter() {
      override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/gif/**").addResourceLocations("file:" + gifLocation)
        super.addResourceHandlers(registry)
      }
    }
  }
}

