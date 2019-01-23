package com.dabbler.generator.common.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class FreeMarkerHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(FreeMarkerHelper.class);
    private FreeMarkerHelper(){
        throw new UnsupportedOperationException();
    }

    private static Configuration configuration;

    public static Configuration getConfiguration(String templateDirPath) throws IOException {
        if(configuration!=null){
            return configuration;
        }
        // Create your Configuration instance, and specify if up to what FreeMarker
// version (here 2.3.27) do you want to apply the fixes that are not 100%
// backward-compatible. See the Configuration JavaDoc for details.
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);

// Specify the source where the template files come from. Here I set a
// plain directory for it, but non-file-system sources are possible too:
        cfg.setDirectoryForTemplateLoading(new File(templateDirPath));

// Set the preferred charset template files are stored in. UTF-8 is
// a good choice in most applications:
        cfg.setDefaultEncoding("UTF-8");

// Sets how errors will appear.
// During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

// Don't log exceptions inside FreeMarker that it will thrown at you anyway:
        cfg.setLogTemplateExceptions(false);

// Wrap unchecked exceptions thrown during template processing into TemplateException-s.
        cfg.setWrapUncheckedExceptions(true);
        return cfg;
    }

    /**
     * @param templateDirPath  template文件夹路径
     * @param templateFilePath 相对路径，相对于templateDirPath
     * @return
     */
    public  static Template getTemplate(String templateDirPath,String templateFilePath)throws IOException{
        if(configuration==null){
            configuration = getConfiguration(templateDirPath);
        }
        Template template = null;
        try {
            template = configuration.getTemplate(templateFilePath);
        } catch (IOException e) {
            LOGGER.error("can not get Template",e);
            throw e;
        }
        return template;
    }

}
