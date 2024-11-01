package carshop.service.application;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Основной конфиг приложения, в котором собираются все дополнительные конфиги
 * А так же в нем основной конфиг помещается в сервлет контекст + создается диспетчер сервлет,
 * который нужен для обработки url и в дальнейшем работе контроллеров
 */

public class WebConfig implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext context
                = new AnnotationConfigWebApplicationContext();
        context.scan("carshop.service");
        context.register(AspectjConfig.class);
        context.register(MvcConfig.class);
        context.register(ApplicationConfig.class);
        context.register(SpringFoxConfig.class);

        servletContext.addListener(new ContextLoaderListener(context));
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("mvc",new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/*");
    }
}
