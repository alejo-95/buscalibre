package libreria;

import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfigMVC implements WebMvcConfigurer{
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    WebMvcConfigurer.super.addResourceHandlers(registry);

    String rutaAbsUploads = Paths.get("uploads").toAbsolutePath().toUri().toString();

    registry.addResourceHandler("/uploads/**")
    .addResourceLocations(rutaAbsUploads);
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/error_403").setViewName("error/error_403");
    registry.addViewController("/").setViewName("inicio");
    registry.addViewController("/mantenimiento").setViewName("vista_mmto");
  }

}
