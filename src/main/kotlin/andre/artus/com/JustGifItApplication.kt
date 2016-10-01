package andre.artus.com

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class JustGifIt {
  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      SpringApplication.run(JustGifIt::class.java, *args)
//      val context = SpringApplication.run(JustGifIt::class.java, *args)
//      val definitionNames = context.beanDefinitionNames ?: return
//      definitionNames.forEach(::println)


    }
  }
}

