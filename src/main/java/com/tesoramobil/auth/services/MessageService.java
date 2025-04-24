package com.tesoramobil.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageService {

	@Autowired
    private MessageSource messageSource;


    /**
     * Obtiene el mensaje internacionalizado basado en la clave.
     *
     * @param key La clave definida en messages.properties
     * @return El mensaje localizado
     */
    public String get(String key) {
        return messageSource.getMessage(key, null, Locale.getDefault());
    }

    /**
     * Obtiene el mensaje internacionalizado con argumentos.
     *
     * @param key La clave del mensaje
     * @param args Argumentos para el mensaje (por ejemplo: nombres dinámicos)
     * @return El mensaje con los argumentos aplicados
     */
    public String get(String key, Object... args) {
        return messageSource.getMessage(key, args, Locale.getDefault());
    }
}
